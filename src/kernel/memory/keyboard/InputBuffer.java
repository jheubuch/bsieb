package kernel.memory.keyboard;

public class InputBuffer extends STRUCT {
    @SJC(count = 128)
    public int[] keyCodes;
}
