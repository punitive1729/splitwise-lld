package org.example.services.impl;

import org.example.constants.CommandNames;
import org.example.constants.SplittingStrategies;
import org.example.exceptions.SplitWiseServiceException;
import org.example.models.Bill;
import org.example.models.Share;
import org.example.services.SplittingStrategy;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EqualSplittingStrategyImpl implements SplittingStrategy {
    @Override
    public Bill getBill(List<String> params) throws SplitWiseServiceException {
        // EXPENSE u1 1000 4 u1 u2 u3 u4 EQUAL
        //   0      1   2  3 4  5  6   7  8
        try {
            if(!CommandNames.EXPENSE.equalsIgnoreCase(params.getFirst()))
                throw new SplitWiseServiceException("Invalid command name", 400);

            if(!SplittingStrategies.EQUAL.equalsIgnoreCase(params.getLast()))
                throw new SplitWiseServiceException("Invalid strategy", 400);

            String spenderId = params.get(1);
            String billId = UUID.randomUUID().toString();
            double amount = Double.parseDouble(params.get(2));
            int index = 4, totalParticipants = Integer.parseInt(params.get(3));

            double shareAmount = amount/(1.0*totalParticipants);

            List<Share> shares = new ArrayList<>();
            while(totalParticipants>0) {
                String shareId = UUID.randomUUID().toString();
                shares.add(new Share(shareId,billId,params.get(index),shareAmount));
                ++index;
                --totalParticipants;
            }

            return new Bill(billId, spenderId, amount, shares, Instant.now().toEpochMilli());

        } catch (Exception ex) {
            throw new SplitWiseServiceException("Invalid input", 400);
        }
    }
}
