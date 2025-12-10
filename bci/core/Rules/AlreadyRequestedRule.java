package bci.core.Rules;

import bci.core.*;

public class AlreadyRequestedRule extends Rule {
    public AlreadyRequestedRule() {
        super(1);
    }

    @Override
    public boolean check(Creation c, User u) {
        return !u.hasRequestedCreation(c);
    }
}