package bci.core;
import java.util.*;

public class Creator implements java.io.Serializable{
    private String _name;
    private List<Creation> _creations = new ArrayList<>();
    public Creator(String name){
        _name = name;
    }
    public String getName(){
        return _name;
    }
    public void addCreation(Creation c){  
        _creations.add(c);

    }
    public void removeCreation(Creation c){
        _creations.remove(c);
    }
    public int getNumberOfCreations(){
        return _creations.size();
    }
    public boolean equals(Object o){
        if (!(o instanceof Creator)) return false;
        Creator creator = (Creator) o;
        return _name.equals(creator._name);
    }
}
