package org.example.commands;

import org.example.exceptions.SplitWiseServiceException;

import java.util.List;

public abstract class Command {
    private final String commandName;
    public String getCommandName() {
        return commandName;
    }
    public Command(String commandName) {
        this.commandName = commandName;
    }
    public abstract void execute(List<String> params) throws SplitWiseServiceException;
}
