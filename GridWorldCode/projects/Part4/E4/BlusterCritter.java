/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 * @author Cay Horstmann
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import java.util.List;

import java.awt.Color;
import java.util.ArrayList;
/**
 * A <code>ChameleonCritter</code> takes on the color of neighboring actors as
 * it moves through the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class BlusterCritter extends Critter
{
    /**
     * Randomly selects a neighbor and changes this critter's color to be the
     * same as that neighbor's. If there are no neighbors, the critter will darken.
     */

    private static final double CHANGING_FACTOR = 5;
    // lose or add 5% of color value in each step
    private int courage;

    public BlusterCritter(int c) {
        courage = c;
        setColor(new Color(125, 125, 125));
    }

    public ArrayList<Actor> getActors() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        for (Location loc : getLocationInStep(2)) {
            Actor a = getGrid().get(loc);
            if (a != null) {
                actors.add(a);
            }
        }

        return actors;
    }

    public void processActors(ArrayList<Actor> actors) {
        int count = 0, mul = 1;
        for (Actor a : actors) {
            if (a instanceof Critter) {
                count++;
            }
        }

        if (count >= courage) {
            mul = -1;
        }

        Color c = getColor();
        int red = (int) (c.getRed() + mul * CHANGING_FACTOR);
        int blue = (int) (c.getBlue() - mul * CHANGING_FACTOR);
        if (red > 255) {
            red = 255;
        }
        if (blue > 255) {
            blue = 255;
        }
        if (red < 5) {
            red = 10;
        }
        if (blue < 5) {
            blue = 10;
        }
        setColor(new Color(red, c.getGreen(), blue));

        return;
    }

    public int getCourage() {
        return courage;
    }

    private ArrayList<Location> getLocationInStep(int n) {
        ArrayList<Location> locs = new ArrayList<Location>();
        for (int i = 1; i <= n; i++) {
            for (int j = -i; j <= i; j++) {
                for (int k = -i; k <= i; k++) {
                    if (Math.abs(j) == i || Math.abs(k) == i) {
                        int newRow = getLocation().getRow() + k;
                        int newCol = getLocation().getCol() + j;
                        Location loc = new Location(newRow, newCol);
                        if (getGrid().isValid(loc)) {
                            locs.add(loc);
                        }
                    }
                }
            }
        }
        return locs;
    }
}
