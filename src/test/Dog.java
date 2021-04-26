package test;

public class Dog {
    private String name;
    private Dog puppy;

    public Dog(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Dog getPuppy() {
        return this.puppy;
    }

    public void setPuppy(Dog puppy) {
        this.puppy = puppy;
    }
}
