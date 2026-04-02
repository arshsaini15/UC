package org.uc15.interfaces;

public interface IMeasurable {
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();
    boolean supportsArithmetic();
}
