public class AudioBook implements Book{
    private Author author;
    private Title title;
    private static final double price = 15;
    private double duration;

    public AudioBook(Author author, Title title, double duration){
        this.author = author;
        this.title = title;
        this.duration = duration;
    }


    @Override
    public String toString(){
        return "AudioBook[ author = " + this.author + ", title = " + this.title.toString() + ", price = " + price + ", duration = " + this.duration + " ]";
    }


    @Override
    public Title getTitle() {
        return this.title;
    }


    @Override
    public double getPrice() {
        return price;
    }


    @Override
    public Author getAuthor() {
        return this.author;
    }
}
