package fabrics;

/**
* Fabric class, this class is the template for all types of fabrics
*
* @author Alexander Stradnic (ID 119377263)
*/
public abstract class Fabric{
    private String name;
    private float ppu;
    private float e = 0.0f;

    public Fabric(String name, float price_per_unit){
        this.name = name;
        this.ppu = price_per_unit;
    }

    public String getName(){
        return this.name;
    }
    public float getPPU(){
        return this.ppu;
    }
    public float getE(){
        return this.e;
    }
    public float setE(float e){
        this.e = e;
        return this.e;
    }
    
}