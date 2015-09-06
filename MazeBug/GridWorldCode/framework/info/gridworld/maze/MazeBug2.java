package info.gridworld.maze;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug2</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug2 extends MazeBug {
	public int[] directions = new int[] {0, 0, 0, 0};
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return;
		}
		Location loc = getLocation();
		if (gr.isValid(next)) {
			setDirection(getLocation().getDirectionToward(next));
			if (!stepBack) {
				directions[getDirection() / Location.RIGHT]++;
			} else {
				directions[getDirection() / Location.RIGHT]--;
			}
			moveTo(next);
		} else {
			removeSelfFromGrid();
		}
		if (stepBack) {
			Rock rock = new Rock();
			rock .putSelfInGrid(gr, loc);
		} else {
			Flower flower = new Flower(getColor());
			flower.putSelfInGrid(gr, loc);
		}
	}
    
    public Location getNext(ArrayList<Location> valid) {
    	if (valid.size() <= 1) return valid.get(0);
        if (valid == null) return next;
        
    	int[] dir = new int[valid.size()];
    	for (int i = 0; i < valid.size(); i++) {
    		dir[i] = getLocation().getDirectionToward(valid.get(i));
            System.out.println(dir[i]);
    	}

    	double divied = 0;
    	for (int i : dir) {
    		divied += directions[i / Location.RIGHT];
    	}
        System.out.println(divied);

    	divied *= Math.random();
    	int div = 0;
    	for (int i : dir) {
            System.out.println(i + "\n" + div + "\n" + div + directions[i / Location.RIGHT] + "\n\n");
    		if ((div + directions[i / Location.RIGHT]) >= divied) {
    			return getLocation().getAdjacentLocation(i);
    		}
    	}
    	return valid.get(0);
    }
}
