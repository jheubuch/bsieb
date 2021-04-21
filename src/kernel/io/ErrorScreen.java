package kernel.io;

public class ErrorScreen {
    public static void show(int ebp) {
        redScreen();

        Output.println("Oooops, an error occurred!");
        Output.println();
        Output.println("Stackframe:");
        Output.println();

        int eip = 0;
        int depth = 0;
        // interrupt frame -> EIP is 9 registers a 4 bytes away
        eip = MAGIC.rMem32(ebp + 36);
        ebp = MAGIC.rMem32(ebp);

        do {
            for (int i = 0; i < depth; i++)
                Output.print(' ');

            Output.print("-> EBP: ");
            Output.printHex(ebp);
            Output.print(", EIP: ");
            Output.printHex(eip);
            Output.println();

            // "normal" stackframes
            ebp = MAGIC.rMem32(ebp);
            eip = MAGIC.rMem32(ebp + 4);
            depth++;
        } while (ebp <= 0x9BFFC);
    }

    @SJC.Inline
    private static void redScreen() {
        Output.initScreen();
        Output.setCursor(0, 0);
        Output.setColor(Color.GREY, Color.RED);
        for (int i = 0; i < 2000; i++)
            Output.print(' ');
        Output.setCursor(0, 0);
    }
}
