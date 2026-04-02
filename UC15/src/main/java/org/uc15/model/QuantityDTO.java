package org.uc15.model;

public class QuantityDTO {
    public double value;
    public String unit;

    public QuantityDTO(double value, String unit) {
        this.value = value;
        this.unit = unit;
    }
}