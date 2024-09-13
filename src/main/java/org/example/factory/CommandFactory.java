package org.example.factory;

import org.example.commands.Command;
import org.example.commands.CreateExpenseCommand;
import org.example.commands.ShowAllBalancesCommand;
import org.example.commands.ShowBalanceForAUserCommand;
import org.example.constants.CommandNames;
import org.example.exceptions.SplitWiseServiceException;
import org.example.services.BalanceService;

public class CommandFactory {
    /*
    Command Factory pattern allows for loose coupling and decouples client and executor code

    1. Separation of concerns since single command is responsible for execution of a single task
    2. Runtime injection
    3. Extensible: new commands can be added by implementing the Command interface and adding it to this factory
    4. Open/Close principle
     */
    private final BalanceService balanceService;
    private final SplittingStrategyFactory splittingStrategyFactory;
    public CommandFactory(BalanceService balanceService, SplittingStrategyFactory splittingStrategyFactory) {
        this.balanceService = balanceService;
        this.splittingStrategyFactory = splittingStrategyFactory;
    }

    public Command getCommand(String commandName) throws SplitWiseServiceException {
        switch (commandName.toLowerCase()) {
            case CommandNames.SHOW_ALL:
                return new ShowAllBalancesCommand(CommandNames.SHOW_ALL, balanceService);
            case CommandNames.SHOW:
                return new ShowBalanceForAUserCommand(CommandNames.SHOW, balanceService);
            case CommandNames.EXPENSE:
                return new CreateExpenseCommand(CommandNames.EXPENSE, balanceService, splittingStrategyFactory);
        }
        throw new SplitWiseServiceException(commandName+" is invalid", 400);
    }
}
