public class Title {
    private String title;
    private String subtitle;

    public Title(String title){
        this.title = title;
    }
    
    public Title(String title, String subtitle){
        this.title = title;
        this.subtitle = subtitle;
    }

    @Override
    public String toString(){
        return this.title + " - " + this.subtitle;
    }

}
