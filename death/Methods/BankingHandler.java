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
		Variables.status = "Banking";
		if(Bank.isOpen()) {
			Bank.deposit(Constants.WILLOW_LOG, Inventory.getCount(Constants.WILLOW_LOG);
			Task.sleep(100);
			if(!Inventory.contains(Constants.WILLOW_LOG) {
				Bank.close();
			}
		} else {
			Bank.open();
			if(!Bank.open()) {
				Timer timer = new Timer(2000);
				while(!Bank.isOpen()) {
					Task.sleep(20);
					if(Players.getLocal().isMoving())
					timer.reset();
					if(!timer.isRunning() || Bank.isOpen())
					break;
				}
			}
		}
	}

}
