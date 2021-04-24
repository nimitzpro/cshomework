public class Paperback implements Book{
    private Title title;
    private Author author;
    private static final double price = 10;
    private int pageCount;

    public Paperback(Author author, Title title, int pageCount){
        this.author = author;
        this.title = title;
        this.pageCount = pageCount;
    }

    public String toString(){
        return "PaperBack[ author = " + this.author + ", title = " + this.title.toString() + ", price = " + price + ", page count = " + this.pageCount + " ]";
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