package kernel;

import kernel.interrupt.Interrupt;
import kernel.io.*;
import rte.BIOS;

public class Kernel {

    public static void main() {
        Output.initScreen();
        Interrupt.initIdt();

        BIOS.switchToGraphicMode();
        drawRainbowFlag();
        Interrupt.wait(45);
        BIOS.switchToTextMode();

        printTestFunctions();
    }

    private static void drawRainbowFlag() {
        int rechteckNo = 0;
        for (int i = rechteckNo * 320; i < 320 * rechteckNo + 33 * 320; i++)
            MAGIC.wMem8(0xA0000 + i + 320, (byte)41);
        rechteckNo += 33;
        for (int i = rechteckNo * 320; i < 320 * rechteckNo + 33 * 320; i++)
            MAGIC.wMem8(0xA0000 + i + 320, (byte)42);;
        rechteckNo += 33;
        for (int i = rechteckNo * 320; i < 320 * rechteckNo + 33 * 320; i++)
            MAGIC.wMem8(0xA0000 + i + 320, (byte)43);;
        rechteckNo += 33;
        for (int i = rechteckNo * 320; i < 320 * rechteckNo + 33 * 320; i++)
            MAGIC.wMem8(0xA0000 + i + 320, (byte)120);;
        rechteckNo += 33;
        for (int i = rechteckNo * 320; i < 320 * rechteckNo + 33 * 320; i++)
            MAGIC.wMem8(0xA0000 + i + 320, (byte)0x01);;
        rechteckNo += 33;
        for (int i = rechteckNo * 320; i < 320 * rechteckNo + 33 * 320; i++)
            MAGIC.wMem8(0xA0000 + i + 320, (byte)0x6B);
    }

    private static void printTestFunctions() {
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
    }
}