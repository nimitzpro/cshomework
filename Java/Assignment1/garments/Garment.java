package garments;

import fabrics.*;

/**
* Garment class, this is the superclass for all other clothes/garments
*
* @author Alexander Stradnic (ID 119377263)
*/
public abstract class Garment{
    /** A name for the instance of garment */
    private String name;
    /** The units of fabric needed to make said garment */
    private float units;
    /** The type of fabric needed */
    private Fabric fabric;
    
    /**
     * Generic constructor for all Garments
     * @param name
     * @param units
     * @param fabric
     */
    public Garment(String name, float units, Fabric fabric){
        this.name = name;
        this.units = units;
        this.fabric = fabric;
    }

    /**
     * name getter
     */
    public String getName(){
        return this.name;
    }

    /**
     * Prints a breakdown of the cost to produce this Garment
     */
    public void printItemisedBill(){
        String unitsStr = Float.toString(this.units);
        String fname = this.fabric.getName();

        float e = this.fabric.getE();
        String eS = Float.toString(e);
        float ppu = this.fabric.getPPU();

        System.out.println("Itemised bill for " + this.name);

        System.out.print("Made of " + unitsStr + " units of " + fname);
        if(this.fabric instanceof Natural) System.out.println(" made of " + ((Natural) this.fabric).getSource());
        else System.out.println();

        System.out.println("Cost\t\t\tUnits * Cost\tTotal");
        System.out.println("environment tax : \t" + unitsStr + " * " + eS + "\t" + Float.toString(this.units * e));
        System.out.println("base price : \t\t" + unitsStr + " * "  + Float.toString(ppu) + "\t" + Float.toString(this.units * ppu));
        System.out.println("grand total : \t\t" + unitsStr + " * " + Float.toString(ppu+e) + "\t" + Float.toString(this.units * (e+ppu)));
        
    }

    /**
     * Used to print the purpose of the Garment
     */
    public abstract void printPurpose();

}