package garments;

import fabrics.*;

/**
* Garment class, this is the superclass for all other clothes/garments
*
* @author Alexander Stradnic (ID 119377263)
*/
public abstract class Garment{
    protected String name;
    protected float units;
    protected Fabric fabric;
    
    public Garment(String name, float units, Fabric fabric){
        this.name = name;
        this.units = units;
        this.fabric = fabric;
    }

    public String getName(){
        return this.name;
    }

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

        System.out.println("Cost\t\t\tCost * Units\tTotal");
        System.out.println("environment tax : \t" + unitsStr + " * " + eS + "\t" + Float.toString(this.units * e));
        System.out.println("base price : \t\t" + unitsStr + " * "  + Float.toString(ppu) + "\t" + Float.toString(this.units * ppu));
        System.out.println("grand total : \t\t" + unitsStr + " * " + Float.toString(ppu+e) + "\t" + Float.toString(this.units * (e+ppu)));
        
    }

    public abstract void printPurpose();

}