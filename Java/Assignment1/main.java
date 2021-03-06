import fabrics.*;
import garments.*;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(final String[] args){

        Natural cotton = new Natural("cotton", 2.0f, "gossypium");
        Natural tweed = new Natural("tweed", 3.0f, "wool");

        Synthetic polyester = new Synthetic("polyester", 5.0f);
        Synthetic acrylic = new Synthetic("acrylic", 6.0f);

        final List<Garment> items = new ArrayList<Garment>();

        items.add(new Trousers("Jeans", 2.0f, cotton));
        items.add(new Shirt("T-Shirt", 1.5f, cotton));
        items.add(new Coat("Winter coat", 2.5f, polyester));
        items.add(new Coat("Rain coat", 2.5f, acrylic));
        items.add(new Coat("Jacket", 2.0f, tweed));
        
        for(Garment item : items){
            item.printPurpose();
        }        
        
        System.out.println();
        for(Garment item : items){
            item.printItemisedBill();
        }
    }
}