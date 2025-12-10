package bci.core;
import java.util.*;

import bci.core.UserBehavior;
import bci.core.exception.*;

public class User implements Comparable<User>, Observer {
    private int _userId;
    private String _name;
    private String _email;
    private boolean _active;
    private int _fines;
    private int _successiveCorrectReturns;
    private int _successiveIncorrectReturns;
    private UserBehavior _classification;

    private List<Request> _activeRequests = new ArrayList<>();
    private List<Notification> _notifications = new ArrayList<>();
    
    public User(int id,String name,String email){
        _name = name;
        _email = email;
        _fines = 0;
        _successiveCorrectReturns = 0;
        _successiveIncorrectReturns = 0;
        _userId = id;
        _active = true;
        _classification = UserBehavior.NORMAL;
    }

    public void update(Notification notification){
        _notifications.add(notification);
    }

    public void addNotification(Notification notification){
        _notifications.add(notification);
    }

    public List<Notification> getNotifications(){
        List<Notification> notifs = new ArrayList<>(_notifications);
        _notifications.clear();
        return notifs;
    }

    public int getId(){
        return _userId;
    }
    public String getName(){
        return _name;  
    }
    public String getEmail(){
        return _email;
    }
    public boolean isActive(){
        return _active;
    }
    public int getFines(){
        return _fines;
    }
    public UserBehavior getClassification(){
        return _classification;
    }
    public void deactivate(){
        _active = false;
        if(_classification == UserBehavior.CUMPRIDOR){
            _classification = UserBehavior.NORMAL;
        }
    }
    public void activate(){ 
        _active = true;      
    }
    public String toString(){
        if(_active){
            return _userId+" - "+_name+" - "+_email+" - "+_classification+" - ACTIVO";
        }
        return _userId+" - "+_name+" - "+_email+" - "+_classification+" - SUSPENSO - EUR "+_fines;
    }
    
    public int compareTo(User u){
        // compare by name, then by id
        int nameCmp = _name.compareTo(u._name);
        if(nameCmp != 0){
            return nameCmp;
        }
        return Integer.compare(_userId, u._userId);
    }

    public void addRequest(Request r){
        _activeRequests.add(r);
        Iterator<Notification> it = _notifications.iterator();
        while (it.hasNext()) {
            Notification n = it.next();
            if (n.getCreation().equals(r.getCreation()) && n.getType() == NotificationType.REQUEST) {
                it.remove();
                break;
            }
    }
    }

    public Request removeRequest(Creation c){
        Iterator<Request> it = _activeRequests.iterator();
        while(it.hasNext()){
            Request r = it.next();
            if(r.getCreation().equals(c)){
                it.remove();
                return r;
            }
        }
        return null;
    }
    public List<Request> getRequests(){
        return _activeRequests;
    }
    public int getNumRequests(){
        return _activeRequests.size();
    }
    public void addFine(int fine){
        _fines+=fine;
    }
    public void liquidateFines(){
        _fines =0;
    }

    public boolean hasRequestedCreation(Creation c){
        for(Request r : _activeRequests){
            if(r.getCreation().equals(c)){
                return true;
            }
        }
        return false;
    }

    public void addSuccessiveLateReturn() {
        _successiveIncorrectReturns += 1;
        _successiveCorrectReturns = 0;
        if (_successiveIncorrectReturns >= 3){
            _classification = UserBehavior.FALTOSO;
        }
    }
    public void addSuccessiveOnTimeReturn() {
        _successiveCorrectReturns += 1;
        _successiveIncorrectReturns = 0;
        if (_classification == UserBehavior.FALTOSO && _successiveCorrectReturns >=3){
            _classification = UserBehavior.NORMAL;
            return;
        }
        if (_classification == UserBehavior.NORMAL && _successiveCorrectReturns >= 5){
            _classification = UserBehavior.CUMPRIDOR;
            return;
        }
    }
    public void checkActiveStatus(int date){
        boolean hasLateReturns = false;
        for(Request r : _activeRequests){
            if(r.isPastDue(date)){
                hasLateReturns = true;
                deactivate();
                return;
            }
        }
        if(!hasLateReturns && _fines == 0){
            activate();
        }
    }

    public void payFines(int date) throws UserIsActiveException{
        if (isActive()){
            throw new UserIsActiveException(_userId);
        }
        _fines = 0;
        checkActiveStatus(date);
    }
}
