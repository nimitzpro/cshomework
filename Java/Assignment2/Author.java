import java.util.ArrayList;

// import java.util.ArrayList;

public enum Author {
    JRR_Tolkien("JRR", "Tolkien"),
    JD_Sallinger("JD", "Sallinger");

    // private String firstname;
    // private String surname;
    // private ArrayList<Book> collection;
    private Reader r;
    private ArrayList<Book> books;
    // private double earnings = 0;

    Author(String firstname, String surname){
        this.r = new Reader(firstname, surname);
        this.books = new ArrayList<Book>();
        // this.firstname = firstname;
        // this.surname = surname;
        // this.collection = new ArrayList<Book>();
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

    public void printBooksPublished(){
        String str = this.r.firstname + " " + this.r.surname + " has published:";

        if(this.books.size() == 0) System.out.println(" no books!");
        else{
            for(Book x: this.books){
                str += "\n\t" + x.getTitle().toString();
            }
            System.out.println(str);
        }
    }

}
