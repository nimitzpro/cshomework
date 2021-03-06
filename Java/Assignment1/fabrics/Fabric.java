package fabrics;

/**
* Fabric class, this class is the template for all types of fabrics
*
* @author Alexander Stradnic (ID 119377263)
*/
public abstract class Fabric{
    /** Name of Fabric */
    private String name;
    /** Price of Fabric per Unit of material */
    private float ppu;
    /** The environmental tax rate for this Fabric */
    private float e = 0.0f;

    /**
     * Generic Fabric Constructor
     * @param name
     * @param price_per_unit
     */
    public Fabric(String name, float price_per_unit){
        this.name = name;
        this.ppu = price_per_unit;
    }

    /** name getter */
    public String getName(){
        return this.name;
    }
    /** price per unit getter */
    public float getPPU(){
        return this.ppu;
    }
    /** environmental tax rate getter */
    public float getE(){
        return this.e;
    }
    /** environmental tax rate setter */
    public float setE(float e){
        this.e = e;
        return this.e;
    }
    
}