package avoidIfElseUsingCommandPattern.good.executors;

import avoidIfElseUsingCommandPattern.other.Command;
import avoidIfElseUsingCommandPattern.other.Database;

public abstract class CommandExecutor {
    protected Database database;

    public CommandExecutor(Database database) {
        this.database = database;
    }

    public String execute(final Command command) {
        if (!isValid(command)) {
            return "Invalid Command";
        }
        return executeValidCommand(command);
    }

    public abstract Boolean isApplicable(final Command command);

    protected abstract Boolean isValid(final Command command);

    protected abstract String executeValidCommand(final Command command);
}
