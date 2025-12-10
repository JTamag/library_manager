package bci.core;

import bci.core.exception.*;
import java.util.*;

public abstract class Creation implements Subject{
    private int _creationId;
    private int _price;
    private int _numberOfCopies;
    private int _numberOfCopiesAvailable;
    private String _title;
    private Category _genre;
    private List<Observer> _availabilityObservers = new ArrayList<>();
    private List<Observer> _requestObservers = new ArrayList<>();
    public Creation(int creationId, String title, Category genre, int price, int quantity){
        _creationId = creationId;
        _title = title;
        _genre = genre;
        _price = price;
        _numberOfCopiesAvailable = quantity;
        _numberOfCopies = quantity;
    }
    public void addAvailabilityObserver(Observer observer){
        _availabilityObservers.add(observer);
    }
    public void removeAvailabilityObserver(Observer observer){
        _availabilityObservers.remove(observer);
    }
    public void addRequestObserver(Observer observer){
        _requestObservers.add( observer);
    }
    public void removeRequestObserver(Observer observer){
        _requestObservers.remove(observer);
    }
    public void notifyRequestObservers(NotificationType type){
        for(Observer o : _requestObservers){
            o.update(new Notification(type, this));
        }
    }
    public void notifyAvailabilityObservers(NotificationType type){
        for(Observer o : _availabilityObservers){
            o.update(new Notification(type, this));
        }
    }
    public int changeQuantity(int quantity) throws NotEnoughCopiesException{
        if (_numberOfCopiesAvailable + quantity < 0){
            throw new NotEnoughCopiesException();
        }
        int oldAvailable = _numberOfCopiesAvailable;
        _numberOfCopiesAvailable += quantity;
        _numberOfCopies += quantity;
        if (oldAvailable == 0 && _numberOfCopiesAvailable > 0){
            notifyAvailabilityObservers(NotificationType.AVAILABILITY);
        }
        return _numberOfCopies;
    }
    public int changeCopiesAvailable(int quantity) throws NotEnoughCopiesException{
        if (_numberOfCopiesAvailable + quantity < 0){
            throw new NotEnoughCopiesException();
        }
        int oldAvailable = _numberOfCopiesAvailable;
        _numberOfCopiesAvailable += quantity;
        if (oldAvailable == 0 && _numberOfCopiesAvailable > 0){
            notifyAvailabilityObservers(NotificationType.AVAILABILITY);
        }
        return _numberOfCopiesAvailable;
    }

    public int getCreationId(){
        return _creationId;
    }
    public String getTitle(){
        return _title;
    }
    public int getPrice(){
        return _price;
    }
    public int getQuantity(){
        return _numberOfCopies;
    }
    public Category getCategory(){
        return _genre;
    }

    public String getGenre(){
        switch(_genre){
            case REFERENCE: return "Referência";
            case FICTION: return "Ficção";
            case SCITECH: return "Técnica e Científica";
            default: return "Género inexistente";
        }
    }
    public abstract String toString();
    public abstract List<Creator> getCreators();
    public int getAvailable(){
        return _numberOfCopiesAvailable;
    }
    public boolean equals(Object o){
        if (!(o instanceof Creation)) return false;
        Creation creation = (Creation) o;
        return _creationId == creation._creationId;
    }
}
