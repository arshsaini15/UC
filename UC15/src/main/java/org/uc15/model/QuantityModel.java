package org.uc15.model;

import org.uc15.interfaces.IMeasurable;

public class QuantityModel<U extends IMeasurable> {
    public double value;
    public U unit;

    public QuantityModel(double value, U unit) {
        this.value = value;
        this.unit = unit;
    }
}
