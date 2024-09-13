package org.example.repository;

import org.example.models.Balance;

import java.util.List;

public interface BalanceRepository {
    List<Balance> getAllBalances();
    Balance saveBalance(Balance balance);
    Balance getBalance(String spenderId, String receiverId);
    List<Balance> getBalanceForAUser(String userId);
}
