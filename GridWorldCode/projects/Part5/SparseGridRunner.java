/* 
* @Author: anchen
* @Date:   2015-08-22 09:19:39
* @Last Modified by:   anchen
* @Last Modified time: 2015-08-22 14:36:07
*/

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;

/**
* This class runs a world with additional grid choices.
*/
public class SparseGridRunner {
    public static void main(String[] args) {
        ActorWorld world = new ActorWorld();
        world.addGridClass("SparseBoundedGrid");
        world.addGridClass("SparseBoundedGridTreeMap");
        world.addGridClass("SparseBoundedGridHashMap");
        world.addGridClass("UnboundedGridExtend");
        world.add(new Location(2, 2), new Critter());
        world.show();
    }
}
