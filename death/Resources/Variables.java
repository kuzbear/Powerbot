package death.Resources;

import org.powerbot.game.api.util.Timer;

public class Variables {
	
	private static int level;
	private static int gained;
	private static int cut;
	public static int expTillNextLevel = 0;
	
	public static double totalXp = 0;
	
	private static long startTime;
	public static long secondsToLevel = 0;
	public static long minutesToLevel = 0;
	public static long hoursToLevel = 0;
	
	public static float secondExp = 0;
	public static float minuteExp = 0;
	public static float hourExp = 0;
	
	private static String status = "";
	
	public static Timer timer;
	
	public static double setTotalxp(double totalXp) {
		Variables.totalXp = totalXp;
		return totalXp;
	}
	
	public static double getTotalxp() {
		return totalXp;
	}
	
	public static String setStatus(String status) {
		Variables.status = status;
		return status;
	}
	
	public static String getStatus() {
		return status;
	}
	
	public static int getLevel() {
		return level;
	}
	
	public static int setLevel(int level) {
		Variables.level = level;
		return level;
	}
	
	public static int addGained() {
		return gained++;
	}
	
	public static int getGained() {
		return gained;
	}
	
	public static int addCut() {
		return cut++;
	}
	
	public static int getCut() {
		return cut;
	}
	
	public static long setStartTime(long startTime) {
		Variables.startTime = startTime;
		return startTime;
	}
	
	public static long getStartTime() {
		return startTime;
	}

}
