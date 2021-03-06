package fabrics;

public class Synthetic extends Fabric{

    public Synthetic(String name, float price_per_unit){
        super(name, price_per_unit);
        this.e = 2.0f;
    }

    public String getSource(){
        return "No source";
    }

}