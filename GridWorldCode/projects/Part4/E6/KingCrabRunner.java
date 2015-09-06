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

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

/**
 * This class runs a world that contains crab critters. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public final class KingCrabRunner
{
    private static final int MAGICNUM1 = 1;
    private static final int MAGICNUM2 = 2;
    private static final int MAGICNUM3 = 3;
    private static final int MAGICNUM4 = 4;
    private static final int MAGICNUM5 = 5;
    private static final int MAGICNUM6 = 6;
    private static final int MAGICNUM7 = 7;
    private static final int MAGICNUM8 = 8;
    private KingCrabRunner() {}

    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        world.add(new Location(MAGICNUM7, MAGICNUM5), new Rock());
        world.add(new Location(MAGICNUM5, MAGICNUM4), new Rock());
        world.add(new Location(MAGICNUM5, MAGICNUM7), new Rock());
        world.add(new Location(MAGICNUM7, MAGICNUM3), new Rock());
        world.add(new Location(MAGICNUM7, MAGICNUM8), new Flower());
        world.add(new Location(MAGICNUM2, MAGICNUM2), new Flower());
        world.add(new Location(MAGICNUM3, MAGICNUM5), new Flower());
        world.add(new Location(MAGICNUM3, MAGICNUM8), new Flower());
        world.add(new Location(MAGICNUM6, MAGICNUM5), new Bug());
        world.add(new Location(MAGICNUM5, MAGICNUM3), new Bug());
        world.add(new Location(MAGICNUM4, MAGICNUM5), new KingCrab());
        world.add(new Location(MAGICNUM6, MAGICNUM1), new KingCrab());
        world.add(new Location(MAGICNUM7, MAGICNUM4), new KingCrab());
        world.show();
    }
}
