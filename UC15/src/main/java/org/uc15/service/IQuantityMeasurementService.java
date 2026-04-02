package org.uc15.service;

import org.uc15.model.QuantityDTO;

public interface IQuantityMeasurementService {
    String compare(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO convert(QuantityDTO q, String targetUnit);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2);

}
