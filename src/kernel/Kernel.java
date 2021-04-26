package kernel;

import kernel.interrupt.Interrupt;
import kernel.io.*;
import kernel.memory.map.MemoryMap;
import kernel.pci.PCI;
import kernel.pci.PCIDevice;
import rte.DynamicRuntime;
import test.Dog;

public class Kernel {

    public static void main() {
        Interrupt.initIdt();
        DynamicRuntime.initializeEmptyObjects();

        BIOS.switchToGraphicMode();
        drawRainbowFlag();
        Interrupt.wait(15);
        BIOS.switchToTextMode();

        Output.initScreen();
        Output.println("PCI Bus scan:");
        scanPCIBus();
        Dog bello = new Dog("Bello");
        Dog waldo = new Dog("Waldo");
        bello.setPuppy(waldo);
        Output.print(bello.getName());
        Output.print(" has ");
        Output.print(bello.getPuppy().getName());
        Output.println(" as puppy!");
        Input.printKeyStrokes();
    }

    private static void scanPCIBus() {
        Output.println("Base Cl.|Bus No. |Dev. No.|Fun. No.|Dev. ID |Vend. ID");
        Output.println("======================================================");
        for (int i = 0; i < 256; i++)
            for (int j = 0; j < 32; j++)
                for (int k = 0; k < 8; k++) {
                    PCIDevice pci = PCI.scanAt(i, j, k);
                    if (pci == null)
                        continue;
                    Output.printHex(pci.baseClassCode);
                    Output.print('|');
                    Output.printHex(pci.busNo);
                    Output.print('|');
                    Output.printHex(pci.deviceNo);
                    Output.print('|');
                    Output.printHex(pci.functionNo);
                    Output.print('|');
                    Output.printHex(pci.deviceId);
                    Output.print('|');
                    Output.printHex(pci.vendorId);
                    Output.println();
                }
    }

    private static void getSystemMemoryMap() {
        Output.print('|');
        Output.print("Start address   ");
        Output.print('|');
        Output.print("Length          ");
        Output.println('|');
        Output.println("===================================");
        int continuationIndex = 0;
        do {
            MemoryMap map = BIOS.getSystemMemoryMap(continuationIndex);
            continuationIndex = BIOS.regs.EBX;

            if (map.type != 1)
                continue;

            Output.print('|');
            Output.printHex(map.baseAddress);
            Output.print('|');
            Output.printHex(map.length);
            Output.println('|');
        } while (continuationIndex != 0);
        Output.println("===================================");
        Output.println();
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
    }
}