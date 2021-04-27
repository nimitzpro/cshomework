import java.util.ArrayList;

/**
* Author enumerated class, this class is an implementation of Person and has Author-specific features such as publishing books.
* As it is an enum, it has multiple fixed instances (JRR Tolkien, JD Sallinger).
* It also delegates a few methods to an instance of Reader
*
* @author Alexander Stradnic (ID 119377263)
*/

public enum Author implements Person{
    JRR_Tolkien("JRR", "Tolkien"),
    JD_Sallinger("JD", "Sallinger");

    private Reader r;
    private ArrayList<Book> books;

    Author(String firstname, String surname){
        this.r = new Reader(firstname, surname);
        this.books = new ArrayList<Book>();
    }

    public double getEarnings(){
        return this.r.getEarnings();
    }

    public void receive(double amount){
        this.r.receive(amount);
    }

    public void printBooksOwned(){
        this.r.printBooksOwned();
    }

    public void buy(Book book) {
        this.r.buy(book);
    }

    public void addBook(Book book){
        this.books.add(book);
    }

    public void printBooksPublished(){
        String str = this.r.firstname + " " + this.r.surname + " has published:";
        
        this.printBooks(str, this.books);
    }

    public void printBooks(String str, ArrayList<Book> books){
        this.r.printBooks(str, books);
    }

    public boolean charge(double amount){
        return this.r.charge(amount);
    }

}
