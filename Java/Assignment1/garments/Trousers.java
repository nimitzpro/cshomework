package garments;

import fabrics.Fabric;

/**
* Trousers class, this class is used to define all trousers
*
* @author Alexander Stradnic (ID 119377263)
*/
public class Trousers extends Garment{

    /**
     * Trousers Constructor
     * @param name
     * @param units
     * @param fabric
     */
    public Trousers(String name, float units, Fabric fabric){
        super(name, units, fabric);
    }

    
    /**
     * Prints purpose of all Trousers
     */
    public void printPurpose(){
        System.out.println(this.getName() + " cover the legs");
    }

}