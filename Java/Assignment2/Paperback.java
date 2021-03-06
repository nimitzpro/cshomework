/**
* Paperback class, this class is an implementation of Book and has Paperback-specific properties, any instance of Paperback has a price of €12
*
* @author Alexander Stradnic (ID 119377263)
*/

public class Paperback implements Book{
    private Title title;
    private Author author;
    private static final double price = 10;
    private int pageCount;

    public Paperback(Author author, Title title, int pageCount){
        this.author = author;
        this.title = title;
        this.pageCount = pageCount;

        this.author.addBook(this);
    }

    public String toString(){
        return "Paperback[ author = " + this.author + ", title = " + this.title.toString() + ", price = " + price + ", page count = " + this.pageCount + " ]";
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