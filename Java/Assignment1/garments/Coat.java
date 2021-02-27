public class Coat extends Garment{

    public Coat(String name, float units, String fabric){
        super(name, units, fabric);
    }

    public void printPurpose(){
        System.out.println(this.name + " provide extra protection against the elements");
    }
}