package bci.core;
import java.util.List;
public class Dvd extends Creation{
    private String _igac;
    Creator _director;
    public Dvd(int creationId, String title, Category genre, int price, int quantity, String igac, Creator director){
        super(creationId, title, genre, price, quantity);
        _igac = igac;
        _director = director;
    }
    public String getIgac(){
        return _igac;
    }
    public String toString(){
        return super.getCreationId()+" - "+super.getAvailable()+" de "+super.getQuantity()+" - DVD - "+
            super.getTitle()+" - "+super.getPrice()+" - "+super.getGenre()+" - "+getCreator().getName()+" - "+getIgac();
    }
    public Creator getCreator(){
        return _director;
    }
    public List<Creator> getCreators(){
        return List.of(_director);
    }
}
