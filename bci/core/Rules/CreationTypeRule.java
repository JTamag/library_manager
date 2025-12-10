package bci.core.Rules;

import bci.core.*;

public class CreationTypeRule extends Rule {
    public CreationTypeRule() {
        super(5);
    }

    @Override
    public boolean check(Creation c, User u) {
        return c.getCategory() != Category.REFERENCE;
    }
}