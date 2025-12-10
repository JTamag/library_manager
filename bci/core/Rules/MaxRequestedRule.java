package bci.core.Rules;

import bci.core.*;

public class MaxRequestedRule extends Rule {
    public MaxRequestedRule() {
        super(4);
    }

    @Override
    public boolean check(Creation c, User u) {
        switch(u.getClassification()){
            case NORMAL:
                return u.getRequests().size() < 3;
            case CUMPRIDOR:
                return u.getRequests().size() < 5;
            case FALTOSO:
                return u.getRequests().size() < 1;
            default:
                return false;
        }
    }
}