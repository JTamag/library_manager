package bci.core;

public class Date implements java.io.Serializable{
    private int _currentDate;
    private static Date _instance;
    private Date(){
        _currentDate = 1;
    }
    public static Date getInstance(){
        if(_instance == null){
            _instance = new Date();
        }
        return _instance;
    }
    public int getCurrentDate(){
        return _currentDate;
    }
    public void advanceDate(int days){
        if(days > 0){
            _currentDate += days;
        }
    }
}
