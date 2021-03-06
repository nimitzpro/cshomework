package garments;

import fabrics.Fabric;

/**
* Trousers class, this class is used to define all trousers
*
* @author Alexander Stradnic (ID 119377263)
*/
public class Trousers extends Garment{

    public Trousers(String name, float units, Fabric fabric){
        super(name, units, fabric);
    }

    public void printPurpose(){
        System.out.println(this.getName() + " cover the legs");
    }

}