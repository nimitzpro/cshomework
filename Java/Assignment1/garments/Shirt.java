package garments;

import fabrics.Fabric;

/**
* Shirt class, this class is used to define all shirts
*
* @author Alexander Stradnic (ID 119377263)
*/
public class Shirt extends Garment{

    public Shirt(String name, float units, Fabric fabric){
        super(name, units, fabric);
    }

    public void printPurpose(){
        System.out.println(this.name + " covers the body");
    }

}