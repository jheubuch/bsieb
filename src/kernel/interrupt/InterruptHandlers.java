package kernel.interrupt;

import kernel.io.Color;
import kernel.io.Output;

public class InterruptHandlers {
    static int TIMER_CNT = 0;
    static int TIME_TO_WAIT = -1;
    static int TIME_WAITED = -1;

    @SJC.Interrupt
    public static void handleInterrupt00() {
        Output.directPrint(0, 0, "IR00: Divide error!", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt01() {
        Output.directPrint(0, 0, "IR01: Debug exception!", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt02() {
        Output.directPrint(0, 0, "IR02: NMI!", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt03() {
        Output.directPrint(0, 0, "IR03: Breakpoint!", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt04() {
        Output.directPrint(0, 0, "IR04: INTO (Overflow)!", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt05() {
        Output.directPrint(0, 0, "IR05: Index out of range!", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt06() {
        Output.directPrint(0, 0, "IR06: Invalid Opcode!", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt07() {
        Output.directPrint(0, 0, "IR07", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt08() {
        Output.directPrint(0, 0, "IR08: Double Fault!", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt09() {
        Output.directPrint(0, 0, "IR09", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt0A() {
        Output.directPrint(0, 0, "IR0A", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt0B() {
        Output.directPrint(0, 0, "IR0B", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt0C() {
        Output.directPrint(0, 0, "IR0C", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt0D() {
        Output.directPrint(0, 0, "IR0D: General Protection Error!", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt0E() {
        Output.directPrint(0, 0, "IR0E: Page Fault!", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt0F() {
        Output.directPrint(0, 0, "IR0F", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt10() {
        Output.directPrint(0, 0, "IR10", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt11() {
        Output.directPrint(0, 0, "IR11", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt12() {
        Output.directPrint(0, 0, "IR12", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt13() {
        Output.directPrint(0, 0, "IR13", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt14() {
        Output.directPrint(0, 0, "IR14", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt15() {
        Output.directPrint(0, 0, "IR15", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt16() {
        Output.directPrint(0, 0, "IR16", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt17() {
        Output.directPrint(0, 0, "IR17", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt18() {
        Output.directPrint(0, 0, "IR18", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt19() {
        Output.directPrint(0, 0, "IR19", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt1A() {
        Output.directPrint(0, 0, "IR1A", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt1B() {
        Output.directPrint(0, 0, "IR1B", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt1C() {
        Output.directPrint(0, 0, "IR1C", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt1D() {
        Output.directPrint(0, 0, "IR1D", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt1E() {
        Output.directPrint(0, 0, "IR1E", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt1F() {
        Output.directPrint(0, 0, "IR1F", Color.RED);
    }

    @SJC.Interrupt
    public static void handleInterrupt20() {
        // count waiting variable if waiting is needed
        if (TIME_TO_WAIT != -1) {
            TIME_WAITED++;
            if (TIME_TO_WAIT == TIME_WAITED) {
                TIME_WAITED = -1;
                TIME_TO_WAIT = -1;
            }
        }

        // Animation
        char toPrint = ' ';
        switch (TIMER_CNT) {
            case 0:
                toPrint = '|';
                TIMER_CNT++;
                break;
            case 5:
                toPrint = '/';
                TIMER_CNT = 0;
                break;
            case 1:
                toPrint = '/';
                TIMER_CNT++;
                break;
            case 2:
            case 4:
                toPrint = '-';
                TIMER_CNT++;
                break;
            case 3:
                toPrint = '\\';
                TIMER_CNT++;
                break;
            default:
                toPrint = '.';
        }
        Output.directPrint(0, 24, toPrint, Color.GREEN);
        Interrupt.confirmInterrupt(false);
    }

    @SJC.Interrupt
    public static void handleInterrupt21() {
        Output.directPrint(0, 0, "IR21 - IRQ1: Keyboard", Color.RED);
        Interrupt.confirmInterrupt(false);
    }

    @SJC.Interrupt
    public static void handleInterrupt22() {
        Output.directPrint(0, 0, "IR22", Color.RED);
        Interrupt.confirmInterrupt(false);
    }

    @SJC.Interrupt
    public static void handleInterrupt23() {
        Output.directPrint(0, 0, "IR23", Color.RED);
        Interrupt.confirmInterrupt(false);
    }

    @SJC.Interrupt
    public static void handleInterrupt24() {
        Output.directPrint(0, 0, "IR24", Color.RED);
        Interrupt.confirmInterrupt(false);
    }

    @SJC.Interrupt
    public static void handleInterrupt25() {
        Output.directPrint(0, 0, "IR25", Color.RED);
        Interrupt.confirmInterrupt(false);
    }

    @SJC.Interrupt
    public static void handleInterrupt26() {
        Output.directPrint(0, 0, "IR26", Color.RED);
        Interrupt.confirmInterrupt(false);
    }

    @SJC.Interrupt
    public static void handleInterrupt27() {
        Output.directPrint(0, 0, "IR27", Color.RED);
        Interrupt.confirmInterrupt(false);
    }

    @SJC.Interrupt
    public static void handleInterrupt28() {
        Output.directPrint(0, 0, "IR28", Color.RED);
        Interrupt.confirmInterrupt(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt29() {
        Output.directPrint(0, 0, "IR29", Color.RED);
        Interrupt.confirmInterrupt(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2A() {
        Output.directPrint(0, 0, "IR2A", Color.RED);
        Interrupt.confirmInterrupt(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2B() {
        Output.directPrint(0, 0, "IR2B", Color.RED);
        Interrupt.confirmInterrupt(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2C() {
        Output.directPrint(0, 0, "IR2C", Color.RED);
        Interrupt.confirmInterrupt(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2D() {
        Output.directPrint(0, 0, "IR2D", Color.RED);
        Interrupt.confirmInterrupt(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2E() {
        Output.directPrint(0, 0, "IR2E", Color.RED);
        Interrupt.confirmInterrupt(true);
    }

    @SJC.Interrupt
    public static void handleInterrupt2F() {
        Output.directPrint(0, 0, "IR2F", Color.RED);
        Interrupt.confirmInterrupt(true);
    }
}
