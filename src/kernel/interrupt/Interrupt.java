package kernel.interrupt;

import kernel.io.Color;
import kernel.io.Output;

public class Interrupt {

    public static final int IDT_START = 0x7E00;

    public static void initIdt() {
        addIdtEntry(0x00, MAGIC.mthdOff("Interrupt", "handleInterrupt00"));
        addIdtEntry(0x01, MAGIC.mthdOff("Interrupt", "handleInterrupt01"));
        addIdtEntry(0x02, MAGIC.mthdOff("Interrupt", "handleInterrupt02"));
        addIdtEntry(0x03, MAGIC.mthdOff("Interrupt", "handleInterrupt03"));
        addIdtEntry(0x04, MAGIC.mthdOff("Interrupt", "handleInterrupt04"));
        addIdtEntry(0x05, MAGIC.mthdOff("Interrupt", "handleInterrupt05"));
        addIdtEntry(0x06, MAGIC.mthdOff("Interrupt", "handleInterrupt06"));
        addIdtEntry(0x07, MAGIC.mthdOff("Interrupt", "handleInterrupt07"));
        addIdtEntry(0x08, MAGIC.mthdOff("Interrupt", "handleInterrupt08"));
        addIdtEntry(0x09, MAGIC.mthdOff("Interrupt", "handleInterrupt09"));
        addIdtEntry(0x0A, MAGIC.mthdOff("Interrupt", "handleInterrupt0A"));
        addIdtEntry(0x0B, MAGIC.mthdOff("Interrupt", "handleInterrupt0B"));
        addIdtEntry(0x0C, MAGIC.mthdOff("Interrupt", "handleInterrupt0C"));
        addIdtEntry(0x0D, MAGIC.mthdOff("Interrupt", "handleInterrupt0D"));
        addIdtEntry(0x0E, MAGIC.mthdOff("Interrupt", "handleInterrupt0E"));
        addIdtEntry(0x0F, MAGIC.mthdOff("Interrupt", "handleInterrupt0F"));
        addIdtEntry(0x10, MAGIC.mthdOff("Interrupt", "handleInterrupt10"));
        addIdtEntry(0x11, MAGIC.mthdOff("Interrupt", "handleInterrupt11"));
        addIdtEntry(0x12, MAGIC.mthdOff("Interrupt", "handleInterrupt12"));
        addIdtEntry(0x13, MAGIC.mthdOff("Interrupt", "handleInterrupt13"));
        addIdtEntry(0x14, MAGIC.mthdOff("Interrupt", "handleInterrupt14"));
        addIdtEntry(0x15, MAGIC.mthdOff("Interrupt", "handleInterrupt15"));
        addIdtEntry(0x16, MAGIC.mthdOff("Interrupt", "handleInterrupt16"));
        addIdtEntry(0x17, MAGIC.mthdOff("Interrupt", "handleInterrupt17"));
        addIdtEntry(0x18, MAGIC.mthdOff("Interrupt", "handleInterrupt18"));
        addIdtEntry(0x19, MAGIC.mthdOff("Interrupt", "handleInterrupt19"));
        addIdtEntry(0x1A, MAGIC.mthdOff("Interrupt", "handleInterrupt1A"));
        addIdtEntry(0x1B, MAGIC.mthdOff("Interrupt", "handleInterrupt1B"));
        addIdtEntry(0x1C, MAGIC.mthdOff("Interrupt", "handleInterrupt1C"));
        addIdtEntry(0x1D, MAGIC.mthdOff("Interrupt", "handleInterrupt1D"));
        addIdtEntry(0x1E, MAGIC.mthdOff("Interrupt", "handleInterrupt1E"));
        addIdtEntry(0x1F, MAGIC.mthdOff("Interrupt", "handleInterrupt1F"));
        addIdtEntry(0x20, MAGIC.mthdOff("Interrupt", "handleInterrupt20"));
        addIdtEntry(0x21, MAGIC.mthdOff("Interrupt", "handleInterrupt21"));
        addIdtEntry(0x22, MAGIC.mthdOff("Interrupt", "handleInterrupt22"));
        addIdtEntry(0x23, MAGIC.mthdOff("Interrupt", "handleInterrupt23"));
        addIdtEntry(0x24, MAGIC.mthdOff("Interrupt", "handleInterrupt24"));
        addIdtEntry(0x25, MAGIC.mthdOff("Interrupt", "handleInterrupt25"));
        addIdtEntry(0x26, MAGIC.mthdOff("Interrupt", "handleInterrupt26"));
        addIdtEntry(0x27, MAGIC.mthdOff("Interrupt", "handleInterrupt27"));
        addIdtEntry(0x28, MAGIC.mthdOff("Interrupt", "handleInterrupt28"));
        addIdtEntry(0x29, MAGIC.mthdOff("Interrupt", "handleInterrupt29"));
        addIdtEntry(0x2A, MAGIC.mthdOff("Interrupt", "handleInterrupt2A"));
        addIdtEntry(0x2B, MAGIC.mthdOff("Interrupt", "handleInterrupt2B"));
        addIdtEntry(0x2C, MAGIC.mthdOff("Interrupt", "handleInterrupt2C"));
        addIdtEntry(0x2D, MAGIC.mthdOff("Interrupt", "handleInterrupt2D"));
        addIdtEntry(0x2E, MAGIC.mthdOff("Interrupt", "handleInterrupt2E"));
        addIdtEntry(0x2F, MAGIC.mthdOff("Interrupt", "handleInterrupt2F"));

        // set LIDT register
        long tmp = (((long) IDT_START) << 16) | (long) ((48 * 8) - 1);
        MAGIC.inline(0x0F, 0x01, 0x5D);
        MAGIC.inlineOffset(1, tmp);
    }

    private static void addIdtEntry(int interruptId, int methodOffset) {
        int handlerReference = getInterruptHandlerReference(methodOffset);

        int startWriteAddress = IDT_START + interruptId * 8;
        int leftOffset = (handlerReference & 0xFFFF0000) >>> 16;
        int rightOffset = handlerReference & 0xFFFF;
        int segmentSelector = 8;
        int configurationBits = 0x8E00;

        MAGIC.wMem16(startWriteAddress, (short) rightOffset);
        MAGIC.wMem16(startWriteAddress + 2, (short) segmentSelector);
        MAGIC.wMem16(startWriteAddress + 4, (short) configurationBits);
        MAGIC.wMem16(startWriteAddress + 6, (short) leftOffset);
    }

    public static int getInterruptHandlerReference(int methodOffset) {
        int handlerMemAddress = MAGIC.cast2Ref(MAGIC.clssDesc("Interrupt")) + methodOffset;
        int handlerRefAddress = MAGIC.rMem32(handlerMemAddress);
        return handlerRefAddress + MAGIC.getCodeOff();
    }

    @SJC.Interrupt
    public static void handleInterrupt00() {
        Output.directPrint(0, 0, "IR00: Divide error!", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt01() {
        Output.directPrint(0, 0, "IR01: Debug exception!", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt02() {
        Output.directPrint(0, 0, "IR02: NMI!", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt03() {
        Output.directPrint(0, 0, "IR03: Breakpoint!", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt04() {
        Output.directPrint(0, 0, "IR04: INTO (Overflow)!", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt05() {
        Output.directPrint(0, 0, "IR05: Index out of range!", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt06() {
        Output.directPrint(0, 0, "IR06: Invalid Opcode!", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt07() {
        Output.directPrint(0, 0, "IR07", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt08() {
        Output.directPrint(0, 0, "IR08: Double Fault!", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt09() {
        Output.directPrint(0, 0, "IR09", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt0A() {
        Output.directPrint(0, 0, "IR0A", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt0B() {
        Output.directPrint(0, 0, "IR0B", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt0C() {
        Output.directPrint(0, 0, "IR0C", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt0D() {
        Output.directPrint(0, 0, "IR0D: General Protection Error!", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt0E() {
        Output.directPrint(0, 0, "IR0E: Page Fault!", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt0F() {
        Output.directPrint(0, 0, "IR0F", Color.RED);
        while(true);
    }
    
    @SJC.Interrupt
    public static void handleInterrupt10() {
        Output.directPrint(0, 0, "IR10", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt11() {
        Output.directPrint(0, 0, "IR11", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt12() {
        Output.directPrint(0, 0, "IR12", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt13() {
        Output.directPrint(0, 0, "IR13", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt14() {
        Output.directPrint(0, 0, "IR14", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt15() {
        Output.directPrint(0, 0, "IR15", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt16() {
        Output.directPrint(0, 0, "IR16", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt17() {
        Output.directPrint(0, 0, "IR17", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt18() {
        Output.directPrint(0, 0, "IR18", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt19() {
        Output.directPrint(0, 0, "IR19", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt1A() {
        Output.directPrint(0, 0, "IR1A", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt1B() {
        Output.directPrint(0, 0, "IR1B", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt1C() {
        Output.directPrint(0, 0, "IR1C", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt1D() {
        Output.directPrint(0, 0, "IR1D", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt1E() {
        Output.directPrint(0, 0, "IR1E", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt1F() {
        Output.directPrint(0, 0, "IR1F", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt20() {
        Output.directPrint(0, 0, "IR20 - IRQ0: Timer", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt21() {
        Output.directPrint(0, 0, "IR21 - IRQ1: Keyboard", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt22() {
        Output.directPrint(0, 0, "IR22", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt23() {
        Output.directPrint(0, 0, "IR23", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt24() {
        Output.directPrint(0, 0, "IR24", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt25() {
        Output.directPrint(0, 0, "IR25", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt26() {
        Output.directPrint(0, 0, "IR26", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt27() {
        Output.directPrint(0, 0, "IR27", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt28() {
        Output.directPrint(0, 0, "IR28", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt29() {
        Output.directPrint(0, 0, "IR29", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2A() {
        Output.directPrint(0, 0, "IR2A", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2B() {
        Output.directPrint(0, 0, "IR2B", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2C() {
        Output.directPrint(0, 0, "IR2C", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2D() {
        Output.directPrint(0, 0, "IR2D", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2E() {
        Output.directPrint(0, 0, "IR2E", Color.RED);
        while(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2F() {
        Output.directPrint(0, 0, "IR2F", Color.RED);
        while(true);
    }
}
