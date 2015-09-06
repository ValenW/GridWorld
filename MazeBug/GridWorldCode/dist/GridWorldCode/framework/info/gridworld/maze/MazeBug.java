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
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public boolean isEnd = false;
	public boolean stepBack = false;
	public Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>();
	public Integer stepCount = 0;
	boolean hasShown = false;//final message has been shown

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		crossLocation.push(new ArrayList<Location>());
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		boolean willMove = canMove();
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (!hasShown) {
				move();
				stepCount++;
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else if (willMove) {
			move();
			//increase step count when move 
			stepCount++;
		} else {
			if (!hasShown) {
				String msg = "no way out!";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		}
	}

	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return null;
		ArrayList<Location> valid = new ArrayList<Location>();
		int[] dirs =
            { Location.AHEAD, Location.RIGHT, Location.LEFT };
        for (Location l : getLocationsInDirections(dirs)) {
            Actor a = getGrid().get(l);
            if (a == null) {
                valid.add(l);
            } else if (a.getColor().equals(Color.RED)) {
            	isEnd = true;
            	next = l;
            	break;
            }
        }
		return valid;
	}

	/**
	 * Tests whether this bug can move forward into a location that is empty or
	 * contains a flower.
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		ArrayList<Location> valid = getValid(getLocation());
		if (isEnd) return true;
		stepBack = (valid.size() <= 0);
		if (stepBack) {
	    	if (crossLocation.size() <= 0) {
	    		return false;
	    	}
			next = crossLocation.peek().remove(crossLocation.peek().size() - 1);
			if (crossLocation.peek().size() <= 0) {
				crossLocation.pop();
			}
		} else {
			int n = valid.size();
			next = getNext(valid);
			if (n > 1) {
				crossLocation.push(new ArrayList<Location>());
			}
			crossLocation.peek().add(getLocation());
		}
		return true;
	}
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

    public ArrayList<Location> getLocationsInDirections(int[] directions) {
        ArrayList<Location> locs = new ArrayList<Location>();
        Location loc = getLocation();
    
        for (int d : directions) {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (getGrid().isValid(neighborLoc)) {
                locs.add(neighborLoc);
            }
        }
        return locs;
    }

    public Location getNext(ArrayList<Location> valid) {
    	return valid.get( (int) (Math.random() * valid.size()) );
    }
}
