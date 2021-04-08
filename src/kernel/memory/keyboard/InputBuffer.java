package kernel.memory.keyboard;

public class InputBuffer extends STRUCT {
    public int currentIndex = 0;
    public int bytesToRead = 0;

    @SJC(count = 128)
    public KeyCode[] keyCodes;
}
