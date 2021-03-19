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
        Output.setCursor(1, 1);
        Output.print("PRINTING String: ");
        Output.println("Thats a string");
        Output.println();

        Output.setColor(Color.GREEN, Color.BLUE);
        Output.setCursor(2, 3);
        Output.print("PRINTING char: ");
        Output.println('x');
        Output.println();

        Output.setColor(Color.BLACK, Color.TURQOISE);
        Output.print("PRINTING positive int: ");
        Output.println(42);
        Output.print("PRINTING negative int: ");
        Output.println(-42);
        Output.print("PRINTING zero int: ");
        Output.println(0);
        Output.println();

        Output.setColor(Color.BLUE, Color.BROWN);
        Output.print("PRINTING positive long: ");
        Output.println(4711L);
        Output.print("PRINTING negative long: ");
        Output.println(-4711L);
        Output.print("PRINTING zero long: ");
        Output.println(0L);
        Output.println();
    }
}