public class Paperback implements Book{
    private Title title;
    private Author author;
    private double price = 10;
    private int pageCount;

    public Paperback(Author author, Title title, int pageCount){
        this.author = author;
        this.title = title;
        this.pageCount = pageCount;
    }

    @Override
    public String toString(){
        return "PaperBack[ author = " + this.author + ", title = " + this.title + ", price = " + this.price + ", page count = " + this.pageCount + " ]";
    }

}