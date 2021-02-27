public class Shirt extends Garment{

    public Shirt(String name, float units, Fabric fabric){
        super(name, units, fabric);
    }

    public void printPurpose(){
        System.out.println(this.name + " cover the body");
    }

}