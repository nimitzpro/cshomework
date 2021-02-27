/**
* Garment class, this is the superclasses for all other clothes
*/
abstract class Garment{
    private String name;
    private float units;
    private String fabric;
    
    public Garment(String name, float units, Fabric fabric){
        this.name = name;
        this.units = units;
        this.fabric = fabric;
    }

    public String getName(){
        return this.name;
    }

    public void getItemisedBill(){
        String unitsStr = this.units.toString();
        String fname = this.fabric.getName();

        float e = this.fabric.getE();
        float ppu = this.fabric.getPPU();

        System.out.println("Itemised bill for " + fname);

        System.out.print(System.out.println("Made of " + unitsStr + " units of " + fname);
        if(Natural.isInstance(this.fabric)) System.out.println(" made of " + this.fabric.getSource());
        else System.out.println();

        System.out.println("environment tax : " + unitsStr + " * " + e.toString() + " = " + (this.units * e).toString());
        System.out.println("base price : " + unitsStr + " * "  + ppu.toString() + " = " + (this.units * ppu).toString());
        System.out.println("grand total : " + unitsStr + " * " + (ppu+e).toString() " = " + (this.units * (e+ppu)).toString());
        
    }

}