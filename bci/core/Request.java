package bci.core;

public class Request implements java.io.Serializable{
    private User _user;
    private Creation _creation;
    private final int _requestDate;
    private final int _dueDate;
    private final int _creationCopiesAtRequest;

    public Request(User user, Creation creation, int requestDate){
        _user = user;
        _creation = creation;
        _requestDate = requestDate;
        _creationCopiesAtRequest = creation.getQuantity();
        _dueDate = calculateDueDate(user);
    }

    private int calculateDueDate(User user){
        int base = 0;
        int copies = _creationCopiesAtRequest;
        UserBehavior behavior = user.getClassification();
        //System.out.println("Calculating due date for user behavior: " + behavior + " with copies: " + copies);
        if(copies == 1){
            switch(behavior){
                case UserBehavior.NORMAL :
                    base = 3; 
                    break;
                case UserBehavior.CUMPRIDOR :
                    base = 8;
                    break;
                case UserBehavior.FALTOSO : 
                    base = 2;
                    break;
            }
        }else if(copies <=5){
            switch(behavior){
                case UserBehavior.NORMAL :
                    base = 8;
                    break;
                case UserBehavior.CUMPRIDOR : 
                    base = 15;
                    break;
                case UserBehavior.FALTOSO : 
                    base = 2;
                    break;
            }
        }else{
            switch(behavior){
                case UserBehavior.NORMAL :
                    base = 15;
                    break;
                case UserBehavior.CUMPRIDOR : 
                    base = 30;
                    break;
                case UserBehavior.FALTOSO : 
                    base = 2;
                    break;
            }
       }
        return _requestDate + base;
    }
    public int calculateFine(int date){
        int lateDays = date - _dueDate;
        return lateDays * 5;
    }

    public User getUser(){
        return _user;
    }

    public Creation getCreation(){
        return _creation;
    }

    public int getRequestDate(){
        return _requestDate;
    }

    public int getDueDate(){
        return _dueDate;
    }
    
    public boolean isPastDue(int currentDate){
        return currentDate > _dueDate;
    }

    public boolean equals(Object o){
        if(!(o instanceof Request)){
            return false;
        }
        Request r = (Request) o;
        return r._user.equals(this._user) && r._creation.equals(this._creation)
            && r._requestDate == this._requestDate;
    }
}
