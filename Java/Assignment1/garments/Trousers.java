public class Trousers extends Garment{

    public Trousers(String name, float units, Fabric fabric){
        super(name, units, fabric);
    }

    public void printPurpose(){
        System.out.println(this.name + " cover the legs");
    }

}