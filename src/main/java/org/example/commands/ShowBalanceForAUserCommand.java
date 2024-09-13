package org.example.commands;

import org.example.constants.CommandNames;
import org.example.exceptions.SplitWiseServiceException;
import org.example.models.Balance;
import org.example.services.BalanceService;

import java.util.List;

public class ShowBalanceForAUserCommand extends Command {
    private final BalanceService balanceService;
    public ShowBalanceForAUserCommand(String commandName, BalanceService balanceService) {
        super(commandName);
        this.balanceService = balanceService;
    }
    private void validate(List<String> params) throws SplitWiseServiceException {
        if(params.size()!=2 || !CommandNames.SHOW.equalsIgnoreCase(params.getFirst()))
            throw new SplitWiseServiceException("command is invalid", 400);
    }
    @Override
    public void execute(List<String> params) throws SplitWiseServiceException {
        validate(params);
        List<Balance> balances = balanceService.showBalanceForAUser(params.get(1));
        for(Balance balance: balances)
            balance.printStatement();
    }
    public BalanceService getBalanceService() {
        return balanceService;
    }
}
