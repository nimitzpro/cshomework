import java.util.ArrayList;

public class Reader implements Person{
    private String firstname;
    private String surname;
    private ArrayList<Book> collection;
    private double earnings = 0;

    public Reader(String firstname, String surname){
        this.firstname = firstname;
        this.surname = surname;
        this.collection = new ArrayList<Book>();
    }

    public void printBooksOwned(){
        String str = this.firstname + " " + this.surname + "owns:";

        if(this.collection.size() == 0) System.out.println(" no collection!");
        else{
            for(Book x: this.collection){
                str += "\n\t" + x.getTitle();
            }
            System.out.println(str);
        }
    }

    public void buy(Book book){
        double price = book.getPrice();
        if(this.charge(price)){
            collection.add(book);
            book.getAuthor().receive(0.1*price)
        }
    }

    public void receive(double amount){
        this.earnings += amount;
    }

    public boolean charge(double amount){
        boolean payment = true;
        if(this.earnings < amount){
            payment = false;
        }
        else{
            this.earnings -= amount;
        }
        return payment;
    }

}
