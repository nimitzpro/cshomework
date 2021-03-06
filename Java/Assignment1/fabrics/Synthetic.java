package fabrics;

/**
* Synthetic class, this class is the template for all synthetic fabrics
*
* @author Alexander Stradnic (ID 119377263)
*/
public class Synthetic extends Fabric{

    public Synthetic(String name, float price_per_unit){
        super(name, price_per_unit);
        this.e = 2.0f;
    }

    public String getSource(){
        return "No source";
    }

}