import java.util.ArrayList;

/**
* Person interface, this interface defines all of the key methods every Person must have
*
* @author Alexander Stradnic (ID 119377263)
*/

public interface Person {
    
    public void buy(Book book);

    public boolean charge(double amount);

    public void receive(double amount);

    public void printBooksOwned();

    public void printBooks(String str, ArrayList<Book> books);

}