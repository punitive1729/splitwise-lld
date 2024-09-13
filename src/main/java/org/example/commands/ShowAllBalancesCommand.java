package org.example.commands;

import org.example.exceptions.SplitWiseServiceException;
import org.example.models.Balance;
import org.example.services.BalanceService;

import java.util.List;
import java.util.Objects;

public class ShowAllBalancesCommand extends Command {
    private final BalanceService balanceService;
    public ShowAllBalancesCommand(String commandName, BalanceService balanceService) {
        super(commandName);
        this.balanceService = balanceService;
    }

    private void validate(List<String> params) throws SplitWiseServiceException {
        if(Objects.isNull(params) || params.isEmpty() || !this.getCommandName().equalsIgnoreCase(params.getFirst()))
            throw new SplitWiseServiceException("Invalid input", 400);
    }

    @Override
    public void execute(List<String> params) throws SplitWiseServiceException {
        validate(params);
        List<Balance> balances = balanceService.getBalanceForAllUserPairs();
        for(Balance balance: balances)
            balance.printStatement();
    }
    public BalanceService getBalanceService() {
        return balanceService;
    }
}
