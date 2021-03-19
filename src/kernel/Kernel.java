package kernel;

import kernel.io.Output;

public class Kernel {

    public static void main() {
        Hund hund = new Hund("Luna");
        Hund hund2 = new Hund("Lilli");

        hund.setWelpe(hund2);

        Output.initScreen();
        Output.setColor(1,2);
        Output.println(4711);
        Output.println(42);
        Output.println(1810);
        Output.println(hund.getName());
        Output.println(hund.getWelpe().getName());
        Output.println(9);
        Output.println(-42);
        Output.println(0);
    }
}