package org.example.services;

import org.example.exceptions.SplitWiseServiceException;
import org.example.models.Balance;
import org.example.models.Bill;

import java.util.List;

public interface BalanceService {
    Balance getBalanceForTwoUsers(String userId1, String userId2) throws SplitWiseServiceException;
    List<Balance> getBalanceForAllUserPairs();
    List<Balance> saveBalances(Bill bill) throws SplitWiseServiceException;
    List<Balance> showBalanceForAUser(String userId);
}
