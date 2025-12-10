package bci.core.Rules;

import bci.core.Creation;
import bci.core.User;

public abstract class Rule implements java.io.Serializable {
    private int _id;
    public abstract boolean check(Creation c, User u);
    public Rule(int id){
        _id = id;
    }
    public int getId(){
        return _id;
    }
}
