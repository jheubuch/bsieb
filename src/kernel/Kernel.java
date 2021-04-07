package kernel;

import kernel.interrupt.Interrupt;
import kernel.io.*;

public class Kernel {

    public static void main() {
        Output.initScreen();
        Interrupt.initIdt();

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
        Output.print("PRINTING int as hex: ");
        Output.printHex(4711);
        Output.println();
        Output.println();

        Output.setColor(Color.BLUE, Color.BROWN);
        Output.print("PRINTING positive long: ");
        Output.println(4711L);
        Output.print("PRINTING negative long: ");
        Output.println(-4711L);
        Output.print("PRINTING zero long: ");
        Output.println(0L);
        Output.print("PRINTING long as hex: ");
        Output.printHex(18109942L);
        Output.println();
        Output.println();

        Output.setColor(Color.BLUE, Color.GREEN);
        Output.print("PRINTING byte as hex: ");
        Output.printHex((byte)42);
        Output.println();
        Output.println();

        Output.setColor(Color.GREEN, Color.BLUE);
        Output.print("PRINTING short as hex: ");
        Output.printHex((short)488);
        Output.println();
        Output.println();

        Output.setColor(Color.GREY, Color.BLACK);
        Output.println("CREATING Dog with puppy... ");
        Output.println();
        Hund wuffi = new Hund("Wuffi");
        Hund cookie = new Hund("Cookie");
        wuffi.setWelpe(cookie);
        Output.print("The dog's name is... ");
        Output.println(wuffi.getName());
        Output.print("The dog's puppy's name is... ");
        Output.println(wuffi.getWelpe().getName());

        while(true);
    }
}