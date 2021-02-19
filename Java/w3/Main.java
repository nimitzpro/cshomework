package w3;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        Doctor doc = new Doctor("Rick");
        Surgeon doc2 = new Surgeon("Mick");

        doc.heal("Nick");
        doc2.heal("Nick");
    }
}
