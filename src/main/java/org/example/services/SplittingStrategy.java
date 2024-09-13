package org.example.services;

import org.example.exceptions.SplitWiseServiceException;
import org.example.models.Bill;

import java.util.List;

public interface SplittingStrategy {
    Bill getBill(List<String> params) throws SplitWiseServiceException;
}
