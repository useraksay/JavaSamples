package avoidIfElseUsingCommandPattern.good;

import avoidIfElseUsingCommandPattern.good.executors.CommandExecutor;
import avoidIfElseUsingCommandPattern.other.Command;

import java.util.List;

public class CommandRunnerGood {
    List<CommandExecutor> commandExecutors;

    public CommandRunnerGood(List<CommandExecutor> commandExecutors) {
        this.commandExecutors = commandExecutors;
    }

    public String runCommand(Command command) {
        for (CommandExecutor commandExecutor: commandExecutors) {
            if (commandExecutor.isApplicable(command)) {
                return commandExecutor.execute(command);
            }
        }

        return "Invalid Command";
    }
}
