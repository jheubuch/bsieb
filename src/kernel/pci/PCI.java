package kernel.pci;

public class PCI {
    public static PCIDevice scanAt(int busNo, int deviceNo, int functionNo) {
        int addressBase = 0x80;
        addressBase = (addressBase << 8) | busNo;
        addressBase = (addressBase << 5) | deviceNo;
        addressBase = (addressBase << 3) | functionNo;


        // First configuration register
        MAGIC.wIOs32(0x0CF8, addressBase << 8);

        // Read device ID and vendor ID
        int reg0 = MAGIC.rIOs32(0x0CFC);
        if (reg0 == 0 || reg0 == -1)
            return null;

        // Third configuration register
        MAGIC.wIOs32(0x0CF8, (((addressBase << 6) | 2) << 2));
        int reg2 = MAGIC.rIOs8(0xCFC);

        PCIDevice pci = new PCIDevice();
        pci.busNo = busNo;
        pci.deviceNo = deviceNo;
        pci.functionNo = functionNo;
        pci.vendorId = reg0 & 0xFFFF;
        pci.deviceId = reg0 >>> 16;
        pci.baseClassCode = reg2 >>> 24;
        pci.subClassCode = (reg2 >>> 16) & 0xFF;

        return pci;
    }
}
