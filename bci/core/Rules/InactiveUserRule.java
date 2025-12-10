package bci.core.Rules;

import bci.core.*;

public class InactiveUserRule extends Rule {
    public InactiveUserRule() {
        super(2);
    }

    @Override
    public boolean check(Creation c, User u) {
        return u.isActive();
    }
}