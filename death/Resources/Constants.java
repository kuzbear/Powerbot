package death.Resources;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class Constants {
	
	private static final Area BANK = new Area(new Tile(3092, 3240, 0), new Tile(3096, 3246, 0));
	
	public static final Tile WALK_TO_BANK = new Tile(3092, 3243, 0);
	public static final Tile WALK_TO_TREE = new Tile(3086, 3232, 0);
	
	private static final int[] HATCHET = {1351, 1349, 1353, 1355, 1357, 1359, 6739};
	private static final int[] WILLOW_TREE = {38616, 38627, 38616, 38627, 58006};
	
	private static final int WILLOW_LOG = 1519;
	private static final int SKILL = Skills.WOODCUTTING;
	
	public static boolean inBank() {
		if(BANK.contains(Players.getLocal().getLocation())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static int getWillowLog() {
		return WILLOW_LOG;
	}
	
	public static int getSkill() {
		return SKILL;
	}

	public static int[] getHatchet() {
		return HATCHET;
	}
	
	public static int[] getWillowtree() {
		return WILLOW_TREE;
	}
		
}
