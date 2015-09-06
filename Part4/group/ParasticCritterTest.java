/* 
* @Author: anchen
* @Date:   2015-08-22 15:53:50
* @Last Modified by:   anchen
* @Last Modified time: 2015-08-22 17:13:26
*/
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import info.gridworld.actor.*;
import info.gridworld.grid.*;
import info.gridworld.actor.ActorWorld;

public class ParasticCritterTest {
    public ActorWorld w = new ActorWorld();
    public ParasticCritter a = new ParasticCritter();
    public ParasticCritter b = new ParasticCritter();
    public Rock r = new Rock();
    public Flower f = new Flower();
    public Actor food1 = new Actor();
    public Actor food2 = new Actor();
    public Actor food3 = new Actor();
    public Actor food4 = new Actor();

    @Before
    public void setUp() throws Exception {
        w.add(new Location(5, 5), a);
        w.add(new Location(4, 5), food1);
        w.add(new Location(6, 5), food2);
        w.add(new Location(5, 4), food3);
        w.add(new Location(5, 6), food4);
    }
    
    @After
    public void tearDown() throws Exception {
        a.removeSelfFromGrid();
    }
        
    @Test
    public void testNormalEat() {
        a.act();
        assertEquals(food1, a.getGrid().get(new Location(4, 5)));
        assertEquals(food2, a.getGrid().get(new Location(6, 5)));
        assertEquals(null, food3.getGrid());
        assertEquals(null, food4.getGrid());
        assertEquals(Location.NORTH, a.getDirection());
        
        a.setDirection(Location.EAST);
        a.moveTo(new Location(5, 5));
        a.setFullPoint(2);
        w.add(new Location(3, 5), food3);
        w.add(new Location(7, 5), food4);
        a.act();
        assertEquals(null, food1.getGrid());
        assertEquals(null, food2.getGrid());
        assertEquals(null, food3.getGrid());
        assertEquals(null, food4.getGrid());
        assertEquals(Location.EAST, a.getDirection());
        assertEquals(5, a.getFullPoint());
    }
        
    @Test
    public void testFullPointAndDie() {
        assertEquals(5, a.getFullPoint());
        a.act();
        assertEquals(5, a.getFullPoint());
        
        a.setDirection(Location.EAST);
        a.moveTo(new Location(5, 5));
        a.setFullPoint(2);
        w.add(new Location(3, 5), food3);
        w.add(new Location(7, 5), food4);
        a.act();
        assertEquals(5, a.getFullPoint());

        a.setDirection(Location.EAST);
        a.moveTo(new Location(5, 5));
        a.setFullPoint(1);
        w.add(new Location(3, 5), food3);
        w.add(new Location(7, 5), food4);
        a.act();
        assertEquals(2, a.getFullPoint());

        a.setDirection(Location.EAST);
        a.moveTo(new Location(5, 5));
        a.setFullPoint(0);
        w.add(new Location(3, 5), food3);
        w.add(new Location(7, 5), food4);
        a.act();
        assertEquals(w.getGrid(), food3.getGrid());
        assertEquals(w.getGrid(), food4.getGrid());
        assertEquals(null, a.getGrid());

        food3.removeSelfFromGrid();
        food4.removeSelfFromGrid();
        w.add(a);
    }
    
    @Test
    public void testEatFlowerButRockOrCritter() {
        a.act();
        a.setDirection(Location.NORTH);
        a.moveTo(new Location(5, 5));
        w.add(new Location(5, 4), r);
        w.add(new Location(5, 6), f);
        a.act();
        assertEquals(r, a.getGrid().get(new Location(5, 4)));
        assertEquals(null, f.getGrid());

        a.setDirection(Location.NORTH);
        a.moveTo(new Location(5, 5));
        w.add(new Location(5, 6), b);
        a.act();
        assertEquals(w.getGrid(), b.getGrid());

        r.removeSelfFromGrid();
        b.removeSelfFromGrid();
        food1.removeSelfFromGrid();
        food2.removeSelfFromGrid();
    }

    @Test
    public void testJump() {
        a.act();
        a.moveTo(new Location(0, 0));
        a.setDirection(Location.NORTH);
        w.add(new Location(0, 1), r);
        w.add(new Location(1, 0), b);
        a.act();
        assertEquals(new Location(0, 0), a.getLocation());

        a.setFullPoint(2);
        a.setDirection(Location.NORTH);
        w.add(new Location(0, 2), food3);
        a.act();
        assertEquals(new Location(0, 2), a.getLocation());
        assertEquals(null, food3.getGrid());
        assertEquals(r, a.getGrid().get(new Location(0, 1)));
        assertEquals(b, a.getGrid().get(new Location(1, 0)));

        food1.removeSelfFromGrid();
        food2.removeSelfFromGrid();
        b.removeSelfFromGrid();
        r.removeSelfFromGrid();
    }
}
