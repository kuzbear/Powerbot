package death.Methods;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

import death.Resources.Constants;
import death.Resources.Variables;

public class WalkingHandler extends Node {

	@Override
	public boolean activate() {
		return (Constants.inBank() && !Inventory.isFull()) || (!Constants.inBank() && Inventory.isFull());
	}

	@Override
	public void execute() {
		if(!Players.getLocal().isMoving()) {
			if(Constants.inBank() && (!Inventory.isFull())) {
				Variables.setStatus("Walking to Tree");
				Walking.walk(Constants.WALK_TO_TREE);
			}
			if((!Constants.inBank()) && Inventory.isFull()) {
				Variables.setStatus("Walking to Bank");
				Walking.walk(Constants.WALK_TO_BANK);
			}
		}
	}

}