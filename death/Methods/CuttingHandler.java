package death.Methods;

import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.SceneObject;

import death.Resources.Constants;
import death.Resources.Variables;

public class CuttingHandler extends Node {
	
	public boolean waitFor(int time, boolean Condition) {
		Variables.timer = new Timer(time);
		while(Variables.timer.isRunning()) {
			if(Condition)
				Task.sleep(2000);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean activate() {
		SceneObject tree = SceneEntities.getNearest(Constants.getWillowtree());
		return tree.getLocation().distanceTo() < 7 && !Inventory.isFull();
	}

	@Override
	public void execute() {
		SceneObject tree = SceneEntities.getNearest(Constants.getWillowtree());
		Variables.setStatus("Cutting Trees");
		if(tree != null && tree.isOnScreen()) {
			if(Players.getLocal().getAnimation() == -1) {
				tree.interact("Chop down");
				Task.sleep(500);
				waitFor(5000, (
						Players.getLocal().isMoving()
						));
			} else {
				if(tree.getLocation().distanceTo() > 1) {
					tree.interact("Chop down");
					Task.sleep(1000);
				}
			}
		}
	}
}