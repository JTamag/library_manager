package bci.core.exception;

public class RuleViolationException extends Exception{
    int _ruleId;
    public RuleViolationException(int id){
        super(id + "");
        _ruleId = id;
    }
    public int getRuleId(){
        return _ruleId;
    }
}