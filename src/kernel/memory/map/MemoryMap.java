package kernel.memory.map;

public class MemoryMap {
    public long baseAddress;
    public long length;
    public int type;

    public MemoryMap(long base, long len, int t) {
        baseAddress = base;
        length = len;
        type = t;
    }
}
