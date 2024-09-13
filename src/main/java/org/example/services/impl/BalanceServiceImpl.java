package org.example.services.impl;

import org.example.exceptions.SplitWiseServiceException;
import org.example.models.Balance;
import org.example.models.Bill;
import org.example.models.Share;
import org.example.repository.BalanceRepository;
import org.example.repository.impl.HashMapBalanceRepositoryImpl;
import org.example.services.BalanceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BalanceServiceImpl implements BalanceService {
    private final BalanceRepository balanceRepository = new HashMapBalanceRepositoryImpl();
    @Override
    public Balance getBalanceForTwoUsers(String userId1, String userId2) throws SplitWiseServiceException {
        Balance spentByUser1 = balanceRepository.getBalance(userId1, userId2);
        Balance spentByUser2 = balanceRepository.getBalance(userId2, userId1);

        if(Objects.isNull(spentByUser2) && Objects.isNull(spentByUser1))
            throw new SplitWiseServiceException("Users have not spent any money on each other", 404);

        if(Objects.isNull(spentByUser1))
            return spentByUser2;

        if(Objects.isNull(spentByUser2))
            return spentByUser1;

        if(spentByUser1.getAmount() > spentByUser2.getAmount()) {
            spentByUser1.setAmount(spentByUser1.getAmount() - spentByUser2.getAmount());
            return spentByUser1;
        } else {
            spentByUser2.setAmount(spentByUser2.getAmount() - spentByUser1.getAmount());
            return spentByUser2;
        }
    }

    @Override
    public List<Balance> getBalanceForAllUserPairs() {
        return balanceRepository.getAllBalances();
    }

    @Override
    public List<Balance> saveBalances(Bill bill) {
        List<Balance> balances = new ArrayList<>();
        for(Share share: bill.getShares()) {
            if(!bill.getSpenderId().equals(share.getUserId()))
                balances.add(balanceRepository.saveBalance(new Balance(bill.getSpenderId(), share.getUserId(), share.getAmount())));
        }
        return balances;
    }

    @Override
    public List<Balance> showBalanceForAUser(String userId) {
        return balanceRepository.getBalanceForAUser(userId);
    }
}
