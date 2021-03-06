package fabrics;

public abstract class Fabric{
    protected String name;
    protected float ppu;
    protected float e = 0.0f;

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

    public abstract String getSource();
    
}