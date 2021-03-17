package kernel;

import kernel.io.Output;

public class Kernel {

    public static void main() {
        Hund hund = new Hund("Luna");
        Hund hund2 = new Hund("Lilli");

        Output.print(hund.getName(), (byte) 0x03);
        Output.print(hund2.getName(), (byte) 0x03);
    }
}