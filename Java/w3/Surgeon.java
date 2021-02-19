package w3;

public class Surgeon extends Doctor{
    public Surgeon(String name) {
        super(name);
    }

    @Override
    public void heal(String pname) throws InterruptedException {
        System.out.println("Conducting surgery on " + pname + "...");
        Thread.sleep(10000);
        System.out.println("Healed!");
    }
}