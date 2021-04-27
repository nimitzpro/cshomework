/**
* AudioBook class, this class is an implementation of Book and has Audiobook-specific features such as duration
*
* @author Alexander Stradnic (ID 119377263)
*/

public class AudioBook implements Book{
    private Author author;
    private Title title;
    private static final double price = 15;
    private double duration;

    public AudioBook(Author author, Title title, double duration){
        this.author = author;
        this.title = title;
        this.duration = duration;

        this.author.addBook(this);
    }


    public String toString(){
        return "AudioBook[ author = " + this.author + ", title = " + this.title.toString() + ", price = " + price + ", duration = " + this.duration + " ]";
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
