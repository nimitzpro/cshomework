package garments;

import fabrics.Fabric;

public class Shirt extends Garment{

    public Shirt(String name, float units, Fabric fabric){
        super(name, units, fabric);
    }

    public void printPurpose(){
        System.out.println(this.name + " covers the body");
    }

}