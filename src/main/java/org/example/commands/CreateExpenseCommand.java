package org.example.commands;

import org.example.constants.CommandNames;
import org.example.exceptions.SplitWiseServiceException;
import org.example.factory.SplittingStrategyFactory;
import org.example.models.Balance;
import org.example.models.Bill;
import org.example.services.BalanceService;
import org.example.services.SplittingStrategy;

import java.util.List;
import java.util.Objects;

public class CreateExpenseCommand extends Command {
    private final BalanceService balanceService;
    private final SplittingStrategyFactory splittingStrategyFactory;
    public CreateExpenseCommand(String commandName, BalanceService balanceService, SplittingStrategyFactory splittingStrategyFactory) {
        super(commandName);
        this.balanceService = balanceService;
        this.splittingStrategyFactory = splittingStrategyFactory;
    }
    private Bill getPayload(List<String> params) throws SplitWiseServiceException {
        if(!CommandNames.EXPENSE.equalsIgnoreCase(params.getFirst()))
            throw new SplitWiseServiceException("Invalid input", 400);
        try {
            String strategyName = params.get(4 + Integer.parseInt(params.get(3))).toLowerCase();
            return splittingStrategyFactory.getSplittingStrategy(strategyName).getBill(params);
        } catch (Exception ex) {
            throw new SplitWiseServiceException("Invalid command", 400);
        }
    }
    @Override
    public void execute(List<String> params) throws SplitWiseServiceException {
        Bill bill = getPayload(params);
        List<Balance> balances = balanceService.saveBalances(bill);
        for(Balance balance: balances)
            balance.printStatement();
    }
    public BalanceService getBalanceService() {
        return balanceService;
    }
}
