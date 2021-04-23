public class Paperback implements Book{
    private Title title;
    private Author author;
    private double price = 10;

    public Paperback(Author author, Title title){
        this.author = author;
        this.title = title;
    }

    @Override
    public String toString(){
        return "Title: " + this.title + "\nAuthor: " + this.author + "\nPrice: " + this.price;
    }

}