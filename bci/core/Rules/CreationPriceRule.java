package bci.core.Rules;

import bci.core.*;

public class CreationPriceRule extends Rule {

    private static final int MAX_PRICE = 25;

    public CreationPriceRule() {
        super(6);
    }

    @Override
    public boolean check(Creation c, User u) {
        return c.getPrice() <= MAX_PRICE || u.getClassification() == UserBehavior.CUMPRIDOR;
    }
}