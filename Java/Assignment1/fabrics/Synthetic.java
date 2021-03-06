package fabrics;

public class Synthetic extends Fabric{
    private float e = 2.0f;

    public Synthetic(String name, float price_per_unit){
        super(name, price_per_unit);
    }

    public String getSource(){
        return "No source";
    }

}