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

public class ExactSplittingStrategyImpl implements SplittingStrategy {
    @Override
    public Bill getBill(List<String> params) {
        // EXPENSE  u1      1250    2       u2      u3      EXACT       370     880
        //  0       1       2       3       4       5       6           7       8
        try {
            if(!CommandNames.EXPENSE.equalsIgnoreCase(params.getFirst()))
                throw new SplitWiseServiceException("Invalid command name", 400);

            String senderId = params.get(1);
            double total = Double.parseDouble(params.get(2));
            int index=4,receivers = Integer.parseInt(params.get(3));
            String billId = UUID.randomUUID().toString();

            List<Share> shares = new ArrayList<>();
            for(index = 4;index<4+receivers;index++) {
                String shareId = UUID.randomUUID().toString();
                shares.add(new Share(shareId,billId,params.get(index),0));
            }

            if(!SplittingStrategies.EXACT.equalsIgnoreCase(params.get(index)))
                throw new SplitWiseServiceException("Splitting strategy is invalid", 400);

            index++;
            int i=0;
            double sharesTotal = 0;
            while (receivers>0) {
                double shareAmount = Double.parseDouble(params.get(index));
                sharesTotal += shareAmount;
                shares.get(i).setAmount(shareAmount);
                index++;
                i++;
                receivers--;
            }

            if(total != sharesTotal)
                throw new SplitWiseServiceException("Share distribution is invalid", 400);

            return new Bill(billId, senderId, total, shares, Instant.now().toEpochMilli());
        } catch (Exception ex) {

        }
        return null;
    }
}
