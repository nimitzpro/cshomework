import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(final String[] args){

        Natural cotton = new Natural("cotton", 2.0, "gossypium");
        Natural tweed = new Natural("tweed", 3.0, "wool");

        Synthetic polyester = new Synthetic("polyester", 5.0);
        Synthetic acrylic = new Synthetic("acrylic", 6.0);

        final List<Garment> items = new ArrayList<Garment>();

        items.add(Trousers("jeans", 2.0, cotton));
        items.add(Shirt("t-shirt", 1.5, cotton));
        items.add(Coat("winter coat", 2.5, polyester));
        items.add(Coat("rain coat", 2.5, acrylic));
        items.add(Coat("jacket", 2.0, tweed));
        
        for(Garment item : items){
            item.printPurpose();
        }        
        
        System.out.println();
        for(Garment item : items){
            item.printItemisedBill();
        }
    }
}