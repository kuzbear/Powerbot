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
		description = "cuts willows and banks logs at Draynor", 
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
		Variables.level = Skills.getRealLevel(Constants.skill);
		Variables.startTime = System.currentTimeMillis();
		provide(new CuttingHandler());
		provide(new WalkingHandler());
		provide(new BankingHandler());
	}

	@Override
	public void messageReceived(MessageEvent e) {
		if(e.getMessage().contains("You've just advanced a Woodcutting level!")) {
			Variables.gained++;
			Variables.level = Skills.getRealLevel(Constants.SKILL);
		} else if(e.getMessage().contains("You get some willow logs")) {
			Variables.cut++;
		}
	}
	
	//START: Code generated using Enfilade's Easel
    private static final Color WHITE = new Color(255, 255, 255);
    private static final Color TRANSPARENT_BLACK = new Color(0, 0, 0, 200);
    private static final Color BLACK = new Color(0, 0, 0);

    private static final BasicStroke BORDER = new BasicStroke(1);

    private static final Font ARIAL = new Font("Arial", 1, 10);

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
		
		Variables.totalXp = Variables.getCut() * 67.5;
		Variables.expTillNextLevel = Skills.getExperienceToLevel(Constants.SKILL, Variables.level + 1 + Variables.gained;

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
		
		g.setColor(TRANSPARENT_BLACK);
        g.fillRect(4, 4, 150, 85);
        g.setColor(BLACK);
        g.setStroke(BORDER);
        g.drawRect(4, 4, 150, 85);
        g.setFont(ARIAL);
        g.setColor(WHITE);
        g.drawString("Status: " + Variables.status, 13, 22);
        g.drawString("RunTime: " + hours + ":" + minutes + ":" + seconds, 13, 33);
        g.drawString("Level: " + Variables.level + " (" + Variables.gained + ")", 13, 44);
        g.drawString("TTL: " + Variables.hoursToLevel + ":" + Variables.minutesToLevel + ":" + Variables.secondsToLevel, 13, 55);
        g.drawString("EXP: " + Variables.totalXp + " (" + ((int)Variables.hourExp) + ")", 13, 66);
        g.drawString("Cut: " + Variables.cut, 13, 77);
    }
}
