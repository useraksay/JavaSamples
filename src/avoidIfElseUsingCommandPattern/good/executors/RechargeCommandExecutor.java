package avoidIfElseUsingCommandPattern.good.executors;

import avoidIfElseUsingCommandPattern.other.Command;
import avoidIfElseUsingCommandPattern.other.Database;
import avoidIfElseUsingCommandPattern.other.RechargeProvider;

public class RechargeCommandExecutor extends CommandExecutor {
    RechargeProvider rechargeProvider;

    public RechargeCommandExecutor(Database database, RechargeProvider rechargeProvider) {
        super(database);
        this.rechargeProvider = rechargeProvider;
    }

    public Boolean isApplicable(Command command) {
        return command.getName().equals("recharge");
    }

    protected Boolean isValid(Command command) {
        return command.getParams().size() == 2;
    }

    protected String executeValidCommand(Command command) {
        String user = command.getParams().get(0);
        Integer rechargeAmount = Integer.parseInt(command.getParams().get(1));

        Integer userBalance = database.getUserBalance(user);
        if (userBalance < rechargeAmount) {
            return "Not sufficient balance";
        }
        rechargeProvider.recharge(user, rechargeAmount);
        return "Recharge Done";
    }
}
