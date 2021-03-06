package fabrics;

/**
* Natural class, this class is the template for all natural fabrics
*
* @author Alexander Stradnic (ID 119377263)
*/
public class Natural extends Fabric{
    /** String describing the source of the Natural Fabric */
    private String source;

    /**
     * Natural Fabric Constructor
     * @param name
     * @param price_per_unit
     * @param source
     */
    public Natural(String name, float price_per_unit, String source){
        super(name, price_per_unit);
        this.source = source;
    }

    /**
     * source getter
     * @return
     */
    public String getSource(){
        return this.source;
    }

}