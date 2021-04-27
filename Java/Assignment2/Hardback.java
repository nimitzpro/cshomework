/**
* Hardback class, this class is an implementation of Book and has Hardback-specific properties, any instance of Hardback has a price of €12
*
* @author Alexander Stradnic (ID 119377263)
*/

public class Hardback implements Book{
    private Title title;
    private Author author;
    private static final double price = 12;
    private int pageCount;

    public Hardback(Author author, Title title, int pageCount){
        this.author = author;
        this.title = title;
        this.pageCount = pageCount;

        this.author.addBook(this);
    }

    public String toString(){
        return "Hardback[ author = " + this.author + ", title = " + this.title.toString() + ", price = " + price + ", page count = " + this.pageCount + " ]";
    }

    public Title getTitle() {
        return this.title;
    }

    public double getPrice() {
        return price;
    }

    public Author getAuthor() {
        return this.author;
    }

}