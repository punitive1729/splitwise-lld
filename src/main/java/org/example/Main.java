package org.example;

import org.example.commands.Command;
import org.example.constants.CommandNames;
import org.example.exceptions.SplitWiseServiceException;
import org.example.factory.CommandFactory;
import org.example.factory.SplittingStrategyFactory;
import org.example.models.User;
import org.example.services.BalanceService;
import org.example.services.UserService;
import org.example.services.impl.BalanceServiceImpl;
import org.example.services.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BalanceService balanceService = new BalanceServiceImpl();
        CommandFactory commandFactory = new CommandFactory(balanceService, new SplittingStrategyFactory());
        // create some users
        for(int i=1;i<6;++i) {
            User user = new User("u"+i,"f"+i, "l"+i,"u"+i+"@gmail.com");
            userService.saveUser(user);
        }

        Scanner sc = new Scanner(System.in);
        while(true) {
            String line = sc.nextLine();
            if(line.isBlank())
                continue;
            List<String> params = Arrays.stream(line.split(" ")).filter(param->!param.isBlank()).toList();

            if(CommandNames.QUIT.equalsIgnoreCase(params.getFirst()))
                break;

            try {
                Command command = commandFactory.getCommand(params.getFirst());
                command.execute(params);
            } catch (SplitWiseServiceException ex) {
                System.out.println("Error: "+ex.getMessage()+" code: "+ex.getStatusCode());
            }
        }

    }
}