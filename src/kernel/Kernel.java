package kernel;

import kernel.io.Output;

public class Kernel {

    public static void main() {
        Output.print("Hello OS world!", (byte) 0x03);
        while(true);
    }
}