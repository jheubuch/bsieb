package kernel;

import kernel.io.Output;

public class Kernel {

    public static void main() {
        Hund hund = new Hund("Luna");
        Hund hund2 = new Hund("Lilli");

        hund.setWelpe(hund2);

        Output.initScreen();
        Output.setCursor(2, 2);
        Output.print(hund.getName());
        Output.setCursor(15, 15);
        Output.print(hund.getWelpe().getName());
    }
}