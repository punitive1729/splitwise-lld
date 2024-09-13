package org.example.repository.impl;

import org.example.models.Balance;
import org.example.repository.BalanceRepository;

import java.util.*;

public class HashMapBalanceRepositoryImpl implements BalanceRepository {
    /*

    The reason we store Balance in this way because we only want the sender to update the record separately. Image that we have users u1 and u2

    1. u1 spends 100 on u2 so it will add a record

    2. u2 spends 45 on u1 so it will add a separate record and wont be allowed to modify the above record since u1 is the owner of the 1st record

    3. also this approach allows for traceable

    4. the receiver should not manipulate the balance spent by the spender it should only manipulate the date which it owns

     */
    private final Map<String, Map<String, Balance>> balanceDb = new HashMap<>();
    @Override
    public List<Balance> getAllBalances() {
        List<Balance> balances = new ArrayList<>();
        Set<String> pairAlreadyExists = new HashSet<>();

        for(String sender: balanceDb.keySet())
            balances.addAll(getBalancesForAUser(sender, pairAlreadyExists));

        return balances;
    }

    @Override
    synchronized public Balance saveBalance(Balance balance) {
        String senderId = balance.getSenderId();
        String receiverId = balance.getReceiverId();
        if(Objects.isNull(balanceDb.get(senderId)))
            balanceDb.put(senderId, new HashMap<>());
        if(Objects.isNull(balanceDb.get(senderId).get(receiverId)))
            balanceDb.get(senderId).put(receiverId, balance);
        else {
            Balance originalBalance = balanceDb.get(senderId).get(receiverId);
            balance.setAmount(balance.getAmount()+originalBalance.getAmount());
            balanceDb.get(senderId).put(receiverId, balance);
        }
        return balance;
    }

    @Override
    public Balance getBalance(String spenderId, String receiverId) {
        if(Objects.isNull(balanceDb.get(spenderId)) || Objects.isNull(balanceDb.get(spenderId).get(receiverId)))
            return balanceDb.get(spenderId).get(receiverId);
        return null;
    }

    private List<Balance> getBalancesForAUser(String sender, Set<String> pairAlreadyExists) {
        List<Balance> balances = new ArrayList<>();

        Map<String, Balance> balanceMap = balanceDb.get(sender);

        if(Objects.isNull(balanceMap))
            return balances;


        for(String receiver: balanceMap.keySet()) {
            List<String> pair = null;
            if(sender.compareTo(receiver)>0) {
                // sender > receiver
                pair.add(receiver);
                pair.add(sender);
            } else if(sender.compareTo(receiver)<0) {
                // sender < receiver
                pair.add(sender);
                pair.add(receiver);
            }

            if(Objects.isNull(pair) || pairAlreadyExists.contains(pair.get(0)+"|"+pair.get(1)))
                continue;

            pairAlreadyExists.add(pair.get(0)+"|"+pair.get(1));

            Balance balance = new Balance(sender, receiver, balanceMap.get(receiver).getAmount());

            if(!Objects.isNull(balanceDb.get(receiver)) && !Objects.isNull(balanceDb.get(receiver).get(sender)))
                balance.setAmount(balance.getAmount()-balanceDb.get(receiver).get(sender).getAmount());

            if(balance.getAmount()<0)
                balance = new Balance(receiver, sender, Math.abs(balance.getAmount()));

            balances.add(balance);
        }

        return balances;
    }

    @Override
    public List<Balance> getBalanceForAUser(String sender) {
        return getBalancesForAUser(sender, new HashSet<>());
    }
}
