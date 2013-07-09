package death.Methods;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;

import death.Resources.Constants;
import death.Resources.Variables;

public class BankingHandler extends Node {

	@Override
	public boolean activate() {
		return Constants.inBank() && Inventory.isFull();
	}

	@Override
	public void execute() {
		Variables.setStatus("Banking");
		if(Bank.isOpen()) {
			Bank.deposit(Constants.getWillowLog(), Inventory.getCount(Constants.getWillowLog()));
			Bank.close();
		} else {
			Bank.open();
		}
	}

}
