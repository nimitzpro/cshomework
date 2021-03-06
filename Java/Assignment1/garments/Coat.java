package garments;

import fabrics.*;


/**
* Coat class, this class is used to define all coats
*
* @author Alexander Stradnic (ID 119377263)
*/
public class Coat extends Garment{

    public Coat(String name, float units, Synthetic fabric){
        super(name, units, fabric);
    }
    public Coat(String name, float units, Natural fabric){
        super(name, units, fabric);
    }

    public void printPurpose(){
        System.out.println(this.name + " provides extra protection against the elements");
    }
}