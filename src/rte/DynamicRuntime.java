package rte;

import kernel.BIOS;
import kernel.GlobalAddresses;

public class DynamicRuntime {

  // calculate RAM writing start address
  private static Object previousObject = null;
  private static ImageInformation imageInformation = (ImageInformation) MAGIC.cast2Struct(MAGIC.imageBase);
  private static EmptyObject firstEmptyObject = null;

  public static void initializeEmptyObjects() {
    // init initial empty objects by using SMAP
    int continuationIndex = 0;
    do {
      BIOS.systemMemoryMap(continuationIndex);
      continuationIndex = BIOS.regs.EBX;

      int type = MAGIC.rMem32(GlobalAddresses.SYSTEM_MEMORY_MAP_BUFFER_START + 16);
      if (type != 0x01)
        continue;

      int baseAddress = (int)MAGIC.rMem64(GlobalAddresses.SYSTEM_MEMORY_MAP_BUFFER_START);
      long length = MAGIC.rMem64(GlobalAddresses.SYSTEM_MEMORY_MAP_BUFFER_START + 8);

      if (baseAddress > 0xFFFFF && baseAddress < (0x100000 + imageInformation.imageSize))
        baseAddress = imageInformation.imageStart + imageInformation.imageSize;

      Object object = MAGIC.cast2Obj(baseAddress + ((MAGIC.getInstRelocEntries("EmptyObject") + 2) * MAGIC.ptrSize));
      object._r_type = (SClassDesc) MAGIC.clssDesc("EmptyObject");
      object._r_relocEntries = MAGIC.getInstRelocEntries("EmptyObject");
      object._r_scalarSize = (int)(length - 8 - (2 * MAGIC.ptrSize));
      object._r_next = null;

      EmptyObject emptyObject = (EmptyObject) object;

      // concat empty objects
      if (firstEmptyObject == null)
        firstEmptyObject = emptyObject;
      else {
        EmptyObject lastEmptyObject = firstEmptyObject;
        while (lastEmptyObject._r_next != null)
          lastEmptyObject = (EmptyObject) lastEmptyObject._r_next;
        lastEmptyObject._r_next = emptyObject;
      }
    } while (continuationIndex != 0);
  }

  private static int requestMemoryFromEmptyObject(int requestedMemorySize) {
    EmptyObject emptyObjectWithSufficientMemory = firstEmptyObject;
    do {
      // check if empty object has enough space
      if (emptyObjectWithSufficientMemory._r_scalarSize >= requestedMemorySize)
        break;

      emptyObjectWithSufficientMemory = (EmptyObject) emptyObjectWithSufficientMemory._r_next;
    } while (emptyObjectWithSufficientMemory != null);

    // if no empty object has enough space, return -1
    if (emptyObjectWithSufficientMemory == null)
      return -1;

    // make found empty object smaller and return start address for new object
    MAGIC.assign(emptyObjectWithSufficientMemory._r_scalarSize, emptyObjectWithSufficientMemory._r_scalarSize - requestedMemorySize);
    // return object base address + empty scalars + 1 address (scalarSize)
    return MAGIC.cast2Ref(emptyObjectWithSufficientMemory) + emptyObjectWithSufficientMemory._r_scalarSize + 1;
  }
  
  public static Object newInstance(int scalarSize, int relocEntries, SClassDesc type) {
    // calculate memory requirement and object start address
    int memSize = scalarSize + relocEntries * MAGIC.ptrSize;
    // align memory to 4 bytes
    int filler = (4 - (memSize % 4)) % 4;
    memSize += filler;
    int startWritingAddress = requestMemoryFromEmptyObject(memSize);
    // Break system when no memory is available
    if (startWritingAddress == -1)
      MAGIC.inline(0xCC);
    int objectStartAddress = startWritingAddress + memSize - scalarSize;

    // initialize memory with 0
    for (int i = 0; i < memSize; i += 4)
      MAGIC.wMem32(startWritingAddress + i, 0);

    Object object = MAGIC.cast2Obj(objectStartAddress);

    // fill object kernel fields
    object._r_type = type;
    object._r_relocEntries = relocEntries;
    object._r_scalarSize = scalarSize;

    // Connect to previous object --> set next of previous entry to reloc of current
    if (previousObject != null)
      previousObject._r_next = object;

    previousObject = object;

    return object;
  }
  
  public static SArray newArray(int length, int arrDim, int entrySize, int stdType,
      SClassDesc unitType) { //unitType is not for sure of type SClassDesc
    int scS, rlE;
    SArray me;
    
    if (stdType==0 && unitType._r_type!=MAGIC.clssDesc("SClassDesc"))
      MAGIC.inline(0xCC); //check type of unitType, we don't support interface arrays
    scS=MAGIC.getInstScalarSize("SArray");
    rlE=MAGIC.getInstRelocEntries("SArray");
    if (arrDim>1 || entrySize<0) rlE+=length;
    else scS+=length*entrySize;
    me=(SArray)newInstance(scS, rlE, (SClassDesc) MAGIC.clssDesc("SArray"));
    MAGIC.assign(me.length, length);
    MAGIC.assign(me._r_dim, arrDim);
    MAGIC.assign(me._r_stdType, stdType);
    MAGIC.assign(me._r_unitType, unitType);
    return me;
  }
  
  public static void newMultArray(SArray[] parent, int curLevel, int destLevel,
      int length, int arrDim, int entrySize, int stdType, SClassDesc clssType) {
    int i;
    
    if (curLevel+1<destLevel) { //step down one level
      curLevel++;
      for (i=0; i<parent.length; i++) {
        newMultArray((SArray[])((Object)parent[i]), curLevel, destLevel,
            length, arrDim, entrySize, stdType, clssType);
      }
    }
    else { //create the new entries
      destLevel=arrDim-curLevel;
      for (i=0; i<parent.length; i++) {
        parent[i]=newArray(length, destLevel, entrySize, stdType, clssType);
      }
    }
  }
  
  public static boolean isInstance(Object o, SClassDesc dest, boolean asCast) {
    SClassDesc check;
    
    if (o==null) {
      if (asCast) return true; //null matches all
      return false; //null is not an instance
    }
    check=o._r_type;
    while (check!=null) {
      if (check==dest) return true;
      check=check.parent;
    }
    if (asCast) MAGIC.inline(0xCC);
    return false;
  }
  
  public static SIntfMap isImplementation(Object o, SIntfDesc dest, boolean asCast) {
    SIntfMap check;
    
    if (o==null) return null;
    check=o._r_type.implementations;
    while (check!=null) {
      if (check.owner==dest) return check;
      check=check.next;
    }
    if (asCast) MAGIC.inline(0xCC);
    return null;
  }
  
  public static boolean isArray(SArray o, int stdType, SClassDesc clssType, int arrDim, boolean asCast) {
    SClassDesc clss;
    
    //in fact o is of type "Object", _r_type has to be checked below - but this check is faster than "instanceof" and conversion
    if (o==null) {
      if (asCast) return true; //null matches all
      return false; //null is not an instance
    }
    if (o._r_type!=MAGIC.clssDesc("SArray")) { //will never match independently of arrDim
      if (asCast) MAGIC.inline(0xCC);
      return false;
    }
    if (clssType==MAGIC.clssDesc("SArray")) { //special test for arrays
      if (o._r_unitType==MAGIC.clssDesc("SArray")) arrDim--; //an array of SArrays, make next test to ">=" instead of ">"
      if (o._r_dim>arrDim) return true; //at least one level has to be left to have an object of type SArray
      if (asCast) MAGIC.inline(0xCC);
      return false;
    }
    //no specials, check arrDim and check for standard type
    if (o._r_stdType!=stdType || o._r_dim<arrDim) { //check standard types and array dimension
      if (asCast) MAGIC.inline(0xCC);
      return false;
    }
    if (stdType!=0) {
      if (o._r_dim==arrDim) return true; //array of standard-type matching
      if (asCast) MAGIC.inline(0xCC);
      return false;
    }
    //array of objects, make deep-check for class type (PicOS does not support interface arrays)
    if (o._r_unitType._r_type!=MAGIC.clssDesc("SClassDesc")) MAGIC.inline(0xCC);
    clss=o._r_unitType;
    while (clss!=null) {
      if (clss==clssType) return true;
      clss=clss.parent;
    }
    if (asCast) MAGIC.inline(0xCC);
    return false;
  }
  
  public static void checkArrayStore(SArray dest, SArray newEntry) {
    if (dest._r_dim>1) isArray(newEntry, dest._r_stdType, dest._r_unitType, dest._r_dim-1, true);
    else if (dest._r_unitType==null) MAGIC.inline(0xCC);
    else isInstance(newEntry, dest._r_unitType, true);
  }
}
