package kernel;

public class Hund {
    private String name;
    private Hund welpe;

    public Hund(String n) {
        name = n;
    }

    public Hund() {
        name = "Wuffi";
    }

    public String getName() {
        return name;
    }

    public void setWelpe(Hund h)  {
        welpe = h;
    }

    public Hund getWelpe() {
        return welpe;
    }
}
