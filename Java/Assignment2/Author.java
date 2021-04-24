// import java.util.ArrayList;

public enum Author {
    JRR_Tolkien("JRR", "Tolkien"),
    JD_Sallinger("JD", "Sallinger");

    // private String firstname;
    // private String surname;
    // private ArrayList<Book> collection;
    private Reader r;
    // private double earnings = 0;

    Author(String firstname, String surname){
        this.r = new Reader(firstname, surname);
        // this.firstname = firstname;
        // this.surname = surname;
        // this.collection = new ArrayList<Book>();
    }

    public void getEarnings(){
        System.out.println();
    }

    public void receive(double amount){
        this.r.receive(amount);
    }

    public void printBooksPublished(){

    }
}
