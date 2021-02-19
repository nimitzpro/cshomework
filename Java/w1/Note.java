public class Note{

    private final String note;

    public Note(final String text){
        note = text;
    }
    
    @Override
    public String toString(){
        return note;
    }

}