package kernel.memory.keyboard;

public class InputBuffer extends STRUCT {
    @SJC(count = InputBufferManager.BUFFER_SIZE)
    public int[] keyCodes;
}
