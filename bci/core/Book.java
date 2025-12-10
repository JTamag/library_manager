package bci.core;
import java.util.List;

public class Book extends Creation{
    private String _isbn;
    private List<Creator> _authors;

    public Book(List<Creator> authors, int creationId, String title, Category genre, int price, int quantity, String isbn){
        super(creationId, title, genre, price, quantity);
        _isbn = isbn;
        _authors = authors; 
    }

    public String getIsbn(){
        return _isbn;
    }

    public List<Creator> getCreators(){
        return _authors;
    }

    public String toString(){
        String str = "";
        for(int i = 0; i < _authors.size(); i++){
            str += " ";
            str += _authors.get(i).getName();
            if(i < _authors.size() - 1){
                str += ";";
            }
        }
        return super.getCreationId()+" - "+super.getAvailable()+" de "+super.getQuantity()+" - Livro - "+
            super.getTitle()+" - "+super.getPrice()+" - "+super.getGenre()+" -"+str+" - "+getIsbn();
    }
}
