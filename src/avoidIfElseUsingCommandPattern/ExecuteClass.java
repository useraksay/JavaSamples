package avoidIfElseUsingCommandPattern;

import avoidIfElseUsingCommandPattern.good.CommandRunnerGood;
import avoidIfElseUsingCommandPattern.good.executors.BalanceCommandExecutor;
import avoidIfElseUsingCommandPattern.good.executors.CommandExecutor;
import avoidIfElseUsingCommandPattern.good.executors.RechargeCommandExecutor;
import avoidIfElseUsingCommandPattern.other.Command;
import avoidIfElseUsingCommandPattern.other.Database;
import avoidIfElseUsingCommandPattern.other.RechargeProvider;

import java.util.Arrays;
import java.util.List;

public class ExecuteClass {

    public static void main(String[] args) {
        List<CommandExecutor> commandExecutors = Arrays.asList(new BalanceCommandExecutor(new Database()),
                new RechargeCommandExecutor(new Database(), new RechargeProvider()));

        CommandRunnerGood runner = new CommandRunnerGood(commandExecutors);
        Command command = new Command();
        command.setName("balance");
        command.setParams(Arrays.asList("20"));
        runner.runCommand(command);
    }
}
