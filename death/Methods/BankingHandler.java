package death.Methods;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.methods.Players;
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
			if(Inventory.contains(Constants.getHatchet())) {
				Bank.deposit(Constants.getWillowLog(), 27);
				Bank.close();
			} else {
				Bank.depositInventory();
				Bank.close();
			}
		} else {
			
			if(Players.getLocal().isMoving()) {
				Task.sleep(100);
			}
			Bank.open();
		}
	}

}