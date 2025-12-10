package bci.core.Rules;

import bci.core.*;

public class AvailableCreationRule extends Rule {
    public AvailableCreationRule() {
        super(3);
    }

    @Override
    public boolean check(Creation c, User u) {
        return c.getAvailable() > 0;
    }
}