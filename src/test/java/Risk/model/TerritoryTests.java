package Risk.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TerritoryTests {

	/*
	 * BVA for Territory: Most of these are essentially "counts"
	 * 
	 * getName(): Test a normal name ("Denmark") and the empty string
	 * 
	 * setPlayer() and getPlayer(): Test with a "normal number" (1 and 3), the
	 * unclaimed number 0, and then an out of range number(5 and -1), as well as the
	 * max number (4), also check default of 0.
	 * 
	 * addArmies() and getArmies() Test with a "normal number" (5), the valid but
	 * still boundary value 0, and then an out of range number (-1 and 91), the
	 * minimum nonzero number (1), and finally the default number (0) and the max
	 * number (90)
	 * 
	 * AddNeighboring() and getNeighboring() This is a collection, so it has the
	 * following tests: Adding a territory when empty, Adding a territory when one
	 * element is present, adding a territory when more than one element is present,
	 * adding to the maximum territory size(this will be 7 due to the risk
	 * constraints)
	 * 
	 * Because getNeighboring returns a collection of strings, these tests can work
	 * on both units.
	 * 
	 * finally we have removeArmies(), this removes armies, so we can test this
	 * using the boundaries of armies being 0, -1 and some legitimate number(15). We
	 * don't need to consider 90 and 91 because that has been already covered by our
	 * addArmies test cases
	 */

	@Test
	public void testgetName_normal() {
		String stringchecker = "Denmark";

		Territory territorytotest = new Territory(stringchecker);

		assertTrue(territorytotest.getName().equals(stringchecker));

	}

	@Test
	public void testgetName_empty() {
		String stringchecker = "";

		Territory territorytotest = new Territory(stringchecker);

		assertTrue(territorytotest.getName().equals(stringchecker));

	}

	@Test
	public void testsetplayer_getPlayer() {
		String stringchecker = "Denmark";

		Territory territorytotest = new Territory(stringchecker);

		assertEquals(0, territorytotest.getPlayer());

		territorytotest.setPlayer(1);

		assertEquals(1, territorytotest.getPlayer());

		territorytotest.setPlayer(3);

		assertEquals(3, territorytotest.getPlayer());

		territorytotest.setPlayer(0);

		assertEquals(0, territorytotest.getPlayer());

		territorytotest.setPlayer(4);

		assertEquals(4, territorytotest.getPlayer());

		try {
			territorytotest.setPlayer(-1);
			fail("shouldn't reach this point");

		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Invalid player number"));

		}

		try {
			territorytotest.setPlayer(9);
			fail("shouldn't reach this point");

		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Invalid player number"));

		}
	}

	@Test
	public void testaddArmiesgetArmies_default() {
		String stringchecker = "Denmark";

		Territory territorytotest = new Territory(stringchecker);

		assertEquals(0, territorytotest.getArmyCount());

	}

	@Test
	public void testaddArmiesgetArmies_normal() {
		String stringchecker = "Denmark";

		Territory territorytotest = new Territory(stringchecker);
		territorytotest.addArmies(5);

		assertEquals(5, territorytotest.getArmyCount());

	}

	@Test
	public void testaddArmiesgetArmies_minmax() {
		String stringchecker = "Denmark";

		Territory territorytotest = new Territory(stringchecker);
		territorytotest.addArmies(1);

		assertEquals(1, territorytotest.getArmyCount());

		territorytotest.addArmies(89);

		assertEquals(90, territorytotest.getArmyCount());

		territorytotest.addArmies(0);

		assertEquals(90, territorytotest.getArmyCount());

	}

	@Test
	public void testaddArmiesgetArmies_outofbounds() {
		String stringchecker = "Denmark";

		Territory territorytotest = new Territory(stringchecker);
		territorytotest.addArmies(0);

		try {
			territorytotest.addArmies(-1);
			fail("shouldn't reach this point");

		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Invalid army number"));

		}

		try {
			territorytotest.addArmies(91);
			fail("shouldn't reach this point");

		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Invalid army number"));

		}

	}

	@Test
	public void testaddgetneighboring() {
		String stringchecker = "Denmark";

		Territory territorytotest = new Territory(stringchecker);

		ArrayList<String> neighboring;

		neighboring = territorytotest.getNeighboring();

		assertEquals(0, neighboring.size());

		territorytotest.addNeighboring(new Territory("t1"));
		neighboring = territorytotest.getNeighboring();

		assertEquals(1, neighboring.size());
		assertTrue("t1".equals(neighboring.get(0)));

		territorytotest.addNeighboring(new Territory("t2"));
		neighboring = territorytotest.getNeighboring();

		assertEquals(2, neighboring.size());
		assertTrue(("t1".equals(neighboring.get(0))) && ("t2".equals(neighboring.get(1))));

		territorytotest.addNeighboring(new Territory("t3"));
		neighboring = territorytotest.getNeighboring();

		assertEquals(3, neighboring.size());
		assertTrue(("t1".equals(neighboring.get(0))) && ("t2".equals(neighboring.get(1)))
				&& ("t3".equals(neighboring.get(2))));

	}

	@Test
	public void testaddgetneighboring_max() {
		String stringchecker = "Denmark";

		Territory territorytotest = new Territory(stringchecker);

		ArrayList<String> neighboring;

		for (int i = 0; i < 7; i++) {

			territorytotest.addNeighboring(new Territory("t" + i));
		}

		neighboring = territorytotest.getNeighboring();
		int i = 0;
		for (String entry : neighboring) {
			assertTrue(entry.equals("t" + i));
			i++;

		}

	}

	@Test
	public void testremoveArmies() {
		String stringchecker = "Denmark";

		Territory territorytotest = new Territory(stringchecker);

		try {
			territorytotest.removeArmies(1);
			fail("shouldn't reach this point");

		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Armies can't be below 0"));

		}

		territorytotest.addArmies(35);
		territorytotest.removeArmies(20);
		assertEquals(territorytotest.getArmyCount(), 15);

		territorytotest.removeArmies(15);

		assertEquals(territorytotest.getArmyCount(), 0);

	}

	// TODO: document bva for setArmyCount and getArmyCount
	@Test
	public void testgetArmySetArmy() {
		String stringchecker = "Denmark";

		Territory territorytotest = new Territory(stringchecker);

		territorytotest.setArmyCount(0);
		assertEquals(0, territorytotest.getArmyCount());

		territorytotest.setArmyCount(90);
		assertEquals(90, territorytotest.getArmyCount());

		territorytotest.setArmyCount(1);
		assertEquals(1, territorytotest.getArmyCount());

		try {
			territorytotest.setArmyCount(-1);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Invalid Army Number");

		}

		try {
			territorytotest.setArmyCount(91);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Invalid Army Number");

		}

	}

}
