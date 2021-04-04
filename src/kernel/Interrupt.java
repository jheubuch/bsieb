package kernel;

import kernel.io.Color;
import kernel.io.Output;

public class Interrupt {

    public static final int IDT_START = 0x7E00;

    public static void initIdt() {
        for (int i = 0; i < 48; i++)
            addIdtEntry(i, getInterruptHandlerBaseAddress("handleInterrupt"));

        long tmp = (((long) IDT_START) << 16) | (long) (48 * 8);
        MAGIC.inline(0x0F, 0x01, 0x5D);
        MAGIC.inlineOffset(1, tmp); // lidt [ebp-0x08/tmp]
    }

    private static void addIdtEntry(int interruptId, int handlerReference) {
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

    public static int getInterruptHandlerBaseAddress(String interruptMethodName) {
        int handlerMemAddress = MAGIC.cast2Ref(MAGIC.clssDesc("Interrupt")) + MAGIC.mthdOff("Interrupt", "handleInterrupt");
        int handlerRefAddress = MAGIC.rMem32(handlerMemAddress);
        return handlerRefAddress + MAGIC.getCodeOff();
    }

    @SJC.Interrupt
    public static void handleInterrupt() {
        Output.setCursor(5, 5);
        Output.setColor(Color.GREY, Color.RED);
        Output.print('X');
        while(true);
    }
}
