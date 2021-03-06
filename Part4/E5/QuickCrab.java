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
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A <code>CrabCritter</code> looks at a limited set of neighbors when it eats and moves.
 * <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class QuickCrab extends CrabCritter {
    /**
     * @return list of empty locations immediately to the right and to the left
     */
    public ArrayList<Location> getMoveLocations() {
        ArrayList<Location> locs = new ArrayList<Location>();
        int[] dirs =
            { Location.LEFT, Location.RIGHT };
        for (Location loc : getNLocationsInDirections(dirs, 2)) {
            if (getGrid().get(loc) == null) {
                locs.add(loc);
            }
        }

        return locs;
    }
    
    /**
     * Finds the valid adjacent locations of this critter in different
     * directions.
     * @param directions - an array of directions (which are relative to the
     * current direction)
     * @return a set of valid locations that are neighbors of the current
     * location in the given directions
     */
    public ArrayList<Location> getNLocationsInDirections(int[] directions, int n) {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
    
        for (int d : directions) {
            Location loc = getLocation();
            for (int i = 0; i < n; i++) {
                Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
                if (gr.isValid(neighborLoc) && gr.get(neighborLoc) == null) {
                    loc = neighborLoc;
                } else {
                    break;
                }
            }
            if (loc != getLocation()) {
                locs.add(loc);
            }
        }
        return locs;
    }
}
