/**
* Title class, this class contains the title of a Book, along with any subtitle the Book may have
*
* @author Alexander Stradnic (ID 119377263)
*/

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

    public String toString(){
        String str = this.title;

        if(this.subtitle != null) str += " / " + this.subtitle;

        return str;
    }

}
