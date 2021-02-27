public class Natural extends Fabric{
    private String source;

    public Natural(String name, float price_per_unit, String source){
        super(name, price_per_unit);
        this.source = source;
    }

    public String getSource(){
        return this.source;
    }

}