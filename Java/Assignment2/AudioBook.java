public class AudioBook {
    private Author author;
    private Title title;
    private double price;
    private double duration;

    public AudioBook(Author author, Title title, double duration){

    }


    @Override
    public String toString(){
        return "AudioBook[ author = " + this.author + ", title = " + this.title + ", price = " + this.price + ", duration = " + this.duration + " ]";
    }
}
