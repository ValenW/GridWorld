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
 * @author Cay Horstmann
 */

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.*;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;
/**
 * This class runs a world that contains a bug and a rock, added at random
 * locations. Click on empty locations to add additional actors. Click on
 * populated locations to invoke methods on their occupants. <br />
 * To build your own worlds, define your own actors and a runner class. See the
 * BoxBugRunner (in the boxBug folder) for an example. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class JumperRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        
        /* make encounter1 and encounter crush */
        Jumper encounter1 = new Jumper();
        Jumper encounter2 = new Jumper();
        
        /* set 2 rocks in front of the Jumper */
        Rock rock1 = new Rock();
        Rock rock2 = new Rock();
        
        world.add(encounter1);
        world.add(encounter2);
        
        world.add(new Location(3, 4), rock1);
        world.add(new Location(5, 2), rock2);

        encounter1.moveTo(new Location(9, 4));
        encounter2.moveTo(new Location(5, 8));
        encounter2.setDirection(270);
        
        world.add(new Location(5, 0), new Actor());
        
        world.show();
    }
}
