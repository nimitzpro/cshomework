package fabrics;

/**
* Synthetic class, this class is the template for all synthetic fabrics
*
* @author Alexander Stradnic (ID 119377263)
*/
public class Synthetic extends Fabric{

    /**
     * Synthetic Fabric Constructor
     * @param name
     * @param price_per_unit
     */
    public Synthetic(String name, float price_per_unit){
        super(name, price_per_unit);
        this.setE(2.0f);
    }
    
}