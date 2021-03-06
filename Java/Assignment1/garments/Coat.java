package garments;

import fabrics.*;

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