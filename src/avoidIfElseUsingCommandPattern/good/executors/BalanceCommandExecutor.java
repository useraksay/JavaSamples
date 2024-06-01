package avoidIfElseUsingCommandPattern.good.executors;

import avoidIfElseUsingCommandPattern.other.Command;
import avoidIfElseUsingCommandPattern.other.Database;

public class BalanceCommandExecutor extends CommandExecutor {

    public static final String BALANCE = "balance";

    public BalanceCommandExecutor(Database database) {
        super(database);
    }

    public Boolean isApplicable(Command command) {
        return command.getName().equals(BALANCE);
    }

    protected Boolean isValid(Command command) {
        return command.getParams().size() == 1;
    }

    protected String executeValidCommand(Command command) {
        String user = command.getParams().get(0);
        return database.getUserBalance(user).toString();
    }
}
