package kernel;

import kernel.io.Color;
import kernel.io.Output;

public class Kernel {

    public static void main() {
        Hund hund = new Hund("Luna");
        Hund hund2 = new Hund("Lilli");

        hund.setWelpe(hund2);

        Output.initScreen();
        Output.setColor(Color.RED, Color.BLACK);

        Output.print("PRINTING String: ");
        Output.println("Thats a string");
        Output.println();

        Output.print("PRINTING char: ");
        Output.println('x');
        Output.println();

        Output.print("PRINTING int: ");
        Output.println(42);
        Output.println();

        Output.print("PRINTING long: ");
        Output.println(4711L);
        Output.println();
    }
}