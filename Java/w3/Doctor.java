package w3;

public class Doctor {
    public String name;

    public Doctor(String name){
        this.name = name;
    }    

    public void heal(String pname) throws InterruptedException {
        System.out.println("Healing " + pname + "...");
        Thread.sleep(5000);
        System.out.println("Healed!");
    }

}