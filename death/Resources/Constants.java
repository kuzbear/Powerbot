package death.Resources;

import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class Constants {
	
	public static final Area BANK = new Area(new Tile(3092, 3240, 0), new Tile(3096, 3246, 0));
	
	public static final Tile WALK_TO_BANK = new Tile(3092, 3243, 0);
	public static final Tile WALK_TO_TREE = new Tile(3086, 3232, 0);
	
	public static final int[] HATCHET = {1351, 1349, 1353, 1355, 1357, 1359, 6739};
	public static final int[] WILLOW_TREE = {38616, 38627, 38616, 38627, 58006};
	
	public static final int WILLOW_LOG = 1519;
	public static final int SKILL = Skills.WOODCUTTING;
	
	public static boolean inBank() {
		return BANK.contains(Players.getLocal().getLocation());
	}
		
}
