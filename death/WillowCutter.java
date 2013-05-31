package death;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.Job;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Random;

import death.Methods.BankingHandler;
import death.Methods.CuttingHandler;
import death.Methods.WalkingHandler;
import death.Resources.Constants;
import death.Resources.Variables;

@Manifest(
		authors = { "kuzbear" }, 
		description = "cuts willows and drops logs at Draynor 0.04", 
		name = "kuzbearWillowCutter",
		version = 0.01
		)
public class WillowCutter extends ActiveScript implements MessageListener, PaintListener {
	
	private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
	private Tree jobContainer = null;

    public synchronized final void provide(final Node... jobs) {
        for (final Node job : jobs) {
            if(!jobsCollection.contains(job)) {
                jobsCollection.add(job);
            }
        }
        jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
    }

    public synchronized final void revoke(final Node... jobs) {
        for (final Node job : jobs) {
            if(jobsCollection.contains(job)) {
                jobsCollection.remove(job);
            }
        }
        jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
    }

    public final void submit(final Job... jobs) {
        for (final Job job : jobs) {
            getContainer().submit(job);
        }
    }
    
    @Override
    public int loop() {
        if (jobContainer != null) {
            final Node job = jobContainer.state();
            if (job != null) {
                jobContainer.set(job);
                getContainer().submit(job);
                job.join();
            }
        }
        return Random.nextInt(10, 50);
    }
	
	public void onStart() {
		Variables.setLevel(Skills.getRealLevel(Constants.getSkill()));
		Variables.setStartTime(System.currentTimeMillis());
		provide(new CuttingHandler());
		provide(new WalkingHandler());
		provide(new BankingHandler());
	}

	@Override
	public void messageReceived(MessageEvent e) {
		String x = e.getMessage();
		if(x.contains("You've just advanced a Woodcutting level!")) {
			Variables.addGained();
			Variables.setLevel(Skills.getRealLevel(Constants.getSkill()));
		}
		if(x.contains("You get some willow logs")) {
			Variables.addCut();
		}
	}
	
	//START: Code generated using Enfilade's Easel
    private final Color color1 = new Color(255, 255, 255);
    private final Color color2 = new Color(0, 0, 0, 200);
    private final Color color3 = new Color(0, 0, 0);

    private final BasicStroke stroke1 = new BasicStroke(1);

    private final Font font1 = new Font("Arial", 1, 10);

    public void onRepaint(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;
        
        long millis = System.currentTimeMillis() - Variables.getStartTime();        
    	long hours = millis / (1000 * 60 * 60);        
    	millis -= hours * (1000 * 60 * 60);        
    	long minutes = millis / (1000 * 60);        
    	millis -= minutes * (1000 * 60);        
    	long seconds = millis / 1000;
        
        if (seconds >= 60) {
			minutes = seconds / 60;
			seconds -= minutes * 60;
		}
		if (minutes >= 60) {
			hours = minutes / 60;
			minutes -= hours * 60;
		}
		
		Variables.setTotalxp(Variables.getCut() * 67.5);
		Variables.expTillNextLevel = Skills.getExperienceToLevel(Constants.getSkill(), Variables.getLevel() + 1 + Variables.getGained());

		if ((minutes > 0 || hours > 0 || seconds > 0) && Variables.totalXp > 0) {
			Variables.secondExp = (float) Variables.totalXp
			/ (seconds + minutes * 60 + hours * 60 * 60);
		}
		
    	Variables.minuteExp = Variables.secondExp * 60;
    	Variables.hourExp = Variables.minuteExp * 60;

		if (Variables.secondExp > 0) {
			Variables.secondsToLevel = (int) (Variables.expTillNextLevel / Variables.secondExp);
		}
		if (Variables.secondsToLevel >= 60) {
			Variables.minutesToLevel = Variables.secondsToLevel / 60;
			Variables.secondsToLevel -= Variables.minutesToLevel * 60;
		} else {
			Variables.minutesToLevel = 0;
		}
		if (Variables.minutesToLevel >= 60) {
			Variables.hoursToLevel = Variables.minutesToLevel / 60;
			Variables.minutesToLevel -= Variables.hoursToLevel * 60;
		} else {
			Variables.hoursToLevel = 0;
		}
		
		g.setColor(color2);
        g.fillRect(4, 4, 131, 84);
        g.setColor(color3);
        g.setStroke(stroke1);
        g.drawRect(4, 4, 131, 84);
        g.setFont(font1);
        g.setColor(color1);
        g.drawString("Status: " + Variables.getStatus(), 13, 22);
        g.drawString("RunTime: " + hours + ":" + minutes + ":" + seconds, 13, 33);
        g.drawString("Level: " + Variables.getLevel() + " (" + Variables.getGained() + ")", 13, 44);
        g.drawString("TTL: " + Variables.hoursToLevel + ":" + Variables.minutesToLevel + ":" + Variables.secondsToLevel, 13, 55);
        g.drawString("EXP: " + Variables.totalXp, 13, 66);
        g.drawString("Cut: " + Variables.getCut(), 13, 77);
    }
}