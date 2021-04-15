package kernel.io;

import kernel.interrupt.Interrupt;
import kernel.memory.keyboard.InputBufferManager;


public class Input {
    public static boolean capsActive, shiftPressed, ctrlPressed, altPressed = false;

    public static void printKeyStrokes() {
        while (true) {
            int[] readCodes = InputBufferManager.readFromBuffer();
            if (readCodes == null) {
                Interrupt.wait(1);
                continue;
            }
            for (int i = 0; i < readCodes.length; i++) {

                boolean isMakeCode = (readCodes[i] >>> 7) == 0;
                int keyCode = evaluatePressedKey(readCodes[i]);

                if (keyCode != 0 && keyCode < 255 && isMakeCode && !ctrlPressed && !altPressed)
                    Output.print((char)keyCode);
                else
                    evaluateControlSequence(keyCode, isMakeCode);
            }
        }
    }

    private static boolean isUpper() {
        return shiftPressed || capsActive;
    }

    @SJC.Inline
    private static void evaluateControlSequence(int keyCode, boolean isMake) {
        switch (keyCode) {
            case KeyCode.CAPS:
                if (isMake)
                    capsActive = !capsActive;
                break;
            case KeyCode.BACKSPACE:
                if (isMake)
                    Output.backspace();
                break;
            case KeyCode.SHIFT:
                shiftPressed = isMake;
                break;
            case KeyCode.CTRL:
                ctrlPressed = isMake;
                break;
            case KeyCode.ALT:
                altPressed = isMake;
                break;
            case KeyCode.D:
                if (isMake && ctrlPressed && altPressed) // CTRL + ALT + D
                    MAGIC.inline(0xCC);
                break;
        }

        String ctrlPressedString;
        if (ctrlPressed)
            ctrlPressedString = "[CTRL]";
        else
            ctrlPressedString = "[----]";

        String altPressedString;
        if (altPressed)
            altPressedString = "[ALT]";
        else
            altPressedString = "[---]";

        Output.directPrint(1, 24, ctrlPressedString, Color.TURQOISE);
        Output.directPrint(7, 24, altPressedString, Color.TURQOISE);
    }

    @SJC.Inline
    private static int evaluatePressedKey(int pressedKey) {
        Output.directPrintHex(76, 24, (byte)(pressedKey & 0x7F), Color.BLUE);
        switch (pressedKey & 0x7F) {
            case 1: // ESC
                break;
            case 2:
                return KeyCode.NR_1;
            case 3:
                return KeyCode.NR_2;
            case 4:
                return KeyCode.NR_3;
            case 5:
                return KeyCode.NR_4;
            case 6:
                return KeyCode.NR_5;
            case 7:
                return KeyCode.NR_6;
            case 8:
                return KeyCode.NR_7;
            case 9:
                return KeyCode.NR_8;
            case 10:
                return KeyCode.NR_9;
            case 11:
                return KeyCode.NR_0;
            case 14:
                return KeyCode.BACKSPACE;
            case 16:
                if (isUpper())
                    return KeyCode.Q - 32;
                return KeyCode.Q;
            case 17:
                if (isUpper())
                    return KeyCode.W - 32;
                return KeyCode.W;
            case 18:
                if (isUpper())
                    return KeyCode.E - 32;
                return KeyCode.E;
            case 19:
                if (isUpper())
                    return KeyCode.R - 32;
                return KeyCode.R;
            case 20:
                if (isUpper())
                    return KeyCode.T - 32;
                return KeyCode.T;
            case 21:
                if (isUpper())
                    return KeyCode.Z - 32;
                return KeyCode.Z;
            case 22:
                if (isUpper())
                    return KeyCode.U - 32;
                return KeyCode.U;
            case 23:
                if (isUpper())
                    return KeyCode.I - 32;
                return KeyCode.I;
            case 24:
                if (isUpper())
                    return KeyCode.O - 32;
                return KeyCode.O;
            case 25:
                if (isUpper())
                    return KeyCode.P - 32;
                return KeyCode.P;
            case 29:
                return KeyCode.CTRL;
            case 30:
                if (isUpper())
                    return KeyCode.A - 32;
                return KeyCode.A;
            case 31:
                if (isUpper())
                    return KeyCode.S - 32;
                return KeyCode.S;
            case 32:
                if (isUpper())
                    return KeyCode.D - 32;
                return KeyCode.D;
            case 33:
                if (isUpper())
                    return KeyCode.F - 32;
                return KeyCode.F;
            case 34:
                if (isUpper())
                    return KeyCode.G - 32;
                return KeyCode.G;
            case 35:
                if (isUpper())
                    return KeyCode.H - 32;
                return KeyCode.H;
            case 36:
                if (isUpper())
                    return KeyCode.J - 32;
                return KeyCode.J;
            case 37:
                if (isUpper())
                    return KeyCode.K - 32;
                return KeyCode.K;
            case 38:
                if (isUpper())
                    return KeyCode.L - 32;
                return KeyCode.L;
            case 42:
                return KeyCode.SHIFT;
            case 44:
                if (isUpper())
                    return KeyCode.Y - 32;
                return KeyCode.Y;
            case 45:
                if (isUpper())
                    return KeyCode.X - 32;
                return KeyCode.X;
            case 46:
                if (isUpper())
                    return KeyCode.C - 32;
                return KeyCode.C;
            case 47:
                if (isUpper())
                    return KeyCode.V - 32;
                return KeyCode.V;
            case 48:
                if (isUpper())
                    return KeyCode.B - 32;
                return KeyCode.B;
            case 49:
                if (isUpper())
                    return KeyCode.N - 32;
                return KeyCode.N;
            case 50:
                if (isUpper())
                    return KeyCode.M - 32;
                return KeyCode.M;
            case 56:
                return KeyCode.ALT;
            case 57:
                return KeyCode.SPACE;
            case 58:
                return KeyCode.CAPS;
            default:
                Output.directPrintHex(78, 24, (byte) pressedKey, Color.RED);
                return 0;
        }
        return 0;
    }
}