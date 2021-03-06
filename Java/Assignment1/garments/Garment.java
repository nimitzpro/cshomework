package garments;

import fabrics.*;

/**
* Garment class, this is the superclasses for all other clothes
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

        System.out.println("Itemised bill for " + fname);

        System.out.print("Made of " + unitsStr + " units of " + fname);
        if(this.fabric instanceof Natural) System.out.println(" made of " + this.fabric.getSource());
        else System.out.println();

        System.out.println("environment tax : " + unitsStr + " * " + eS + " = " + Float.toString(this.units * e));
        System.out.println("base price : " + unitsStr + " * "  + Float.toString(ppu) + " = " + Float.toString(this.units * ppu));
        System.out.println("grand total : " + unitsStr + " * " + Float.toString(ppu+e) + " = " + Float.toString(this.units * (e+ppu)));
        
    }

    public abstract void printPurpose();

}