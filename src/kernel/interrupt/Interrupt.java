package kernel.interrupt;

import kernel.io.Color;
import kernel.io.Output;

public class Interrupt {

    public static final int IDT_START = 0x7E00;

    public static void initIdt() {
        addIdtEntry(0x00, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt00"));
        addIdtEntry(0x01, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt01"));
        addIdtEntry(0x02, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt02"));
        addIdtEntry(0x03, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt03"));
        addIdtEntry(0x04, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt04"));
        addIdtEntry(0x05, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt05"));
        addIdtEntry(0x06, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt06"));
        addIdtEntry(0x07, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt07"));
        addIdtEntry(0x08, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt08"));
        addIdtEntry(0x09, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt09"));
        addIdtEntry(0x0A, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt0A"));
        addIdtEntry(0x0B, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt0B"));
        addIdtEntry(0x0C, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt0C"));
        addIdtEntry(0x0D, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt0D"));
        addIdtEntry(0x0E, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt0E"));
        addIdtEntry(0x0F, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt0F"));
        addIdtEntry(0x10, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt10"));
        addIdtEntry(0x11, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt11"));
        addIdtEntry(0x12, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt12"));
        addIdtEntry(0x13, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt13"));
        addIdtEntry(0x14, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt14"));
        addIdtEntry(0x15, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt15"));
        addIdtEntry(0x16, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt16"));
        addIdtEntry(0x17, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt17"));
        addIdtEntry(0x18, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt18"));
        addIdtEntry(0x19, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt19"));
        addIdtEntry(0x1A, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt1A"));
        addIdtEntry(0x1B, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt1B"));
        addIdtEntry(0x1C, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt1C"));
        addIdtEntry(0x1D, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt1D"));
        addIdtEntry(0x1E, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt1E"));
        addIdtEntry(0x1F, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt1F"));
        addIdtEntry(0x20, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt20"));
        addIdtEntry(0x21, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt21"));
        addIdtEntry(0x22, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt22"));
        addIdtEntry(0x23, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt23"));
        addIdtEntry(0x24, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt24"));
        addIdtEntry(0x25, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt25"));
        addIdtEntry(0x26, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt26"));
        addIdtEntry(0x27, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt27"));
        addIdtEntry(0x28, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt28"));
        addIdtEntry(0x29, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt29"));
        addIdtEntry(0x2A, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt2A"));
        addIdtEntry(0x2B, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt2B"));
        addIdtEntry(0x2C, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt2C"));
        addIdtEntry(0x2D, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt2D"));
        addIdtEntry(0x2E, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt2E"));
        addIdtEntry(0x2F, MAGIC.mthdOff("InterruptHandlers", "handleInterrupt2F"));

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
        int handlerMemAddress = MAGIC.cast2Ref(MAGIC.clssDesc("InterruptHandlers")) + methodOffset;
        int handlerRefAddress = MAGIC.rMem32(handlerMemAddress);
        return handlerRefAddress + MAGIC.getCodeOff();
    }
}
