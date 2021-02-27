public class Fabric{
    private String name;
    private float ppu;
    private float e = 0.0;

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
    
}