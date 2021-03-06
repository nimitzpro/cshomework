package garments;

import fabrics.Fabric;

/**
* Shirt class, this class is used to define all shirts
*
* @author Alexander Stradnic (ID 119377263)
*/
public class Shirt extends Garment{
    
    /**
     * Shirt constructor
     * @param name
     * @param units
     * @param fabric
     */
    public Shirt(String name, float units, Fabric fabric){
        super(name, units, fabric);
    }

    
    /**
     * Prints purpose of all Shirts
     */
    public void printPurpose(){
        System.out.println(this.getName() + " covers the body");
    }

}