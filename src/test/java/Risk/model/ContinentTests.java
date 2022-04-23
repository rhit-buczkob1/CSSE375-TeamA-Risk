package Risk.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ContinentTests {

	/*
	 * BVA Analysis: getPlayer() and updatePlayer(): This seems like a stream that
	 * may contain repeated elements, as even though the elements are not repeated,
	 * what we are checking for(the player) might be Therefore what we are checking
	 * for is: All territories have different players. All territories have the same
	 * player. 3 territories have the same player. more than one territory has the
	 * same player. a territory with the same player appears immediately after
	 * another territory with the same player. There's at least one other territory
	 * between territories with duplicates.
	 */

	@Test
	public void continentGetNameTest() {
		Continent continentToTest = new Continent(null, "Eurasia");
		String result = continentToTest.getName();
		assertEquals("Eurasia", result);
	}

	@Test
	public void continentGetNameEmptyTest() {
		Continent continentToTest = new Continent(null, "");
		try {
			continentToTest.getName();
			fail("Expecting Continent name to be Empty");
		} catch (Exception e) {
			assertEquals("Continent name is empty!", e.getMessage());
		}
	}

	@Test
	public void isTerritoryExistEmptyTest() {
		ArrayList<Territory> territories = new ArrayList<>();
		Continent continentToTest = new Continent(territories, "Eurasia");
		Territory territoryTest = new Territory("testTerritory");
		assertFalse(continentToTest.isTerritoryExist(territoryTest));
	}

	@Test
	public void getPlayerSetPlayerTest() {
		Continent continenttotest = new Continent(null, "Eurasia");
		continenttotest.setPlayer(1);
		assertEquals(1, continenttotest.getPlayer());

		continenttotest.setPlayer(0);
		assertEquals(0, continenttotest.getPlayer());

		continenttotest.setPlayer(4);
		assertEquals(4, continenttotest.getPlayer());
		try {
			continenttotest.setPlayer(7);
			fail("Shouldn't reach this point");
		} catch (Exception e) {
			assertEquals("Invalid Player", e.getMessage());
		}
		try {

			continenttotest.setPlayer(-1);
			fail("Shouldn't reach this point");
		} catch (Exception e) {
			assertEquals("Invalid Player", e.getMessage());
		}
	}

	@Test
	public void isPlayerControlled_3same() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory t1 = new Territory("tst1");
		t1.setPlayer(1);
		Territory t2 = new Territory("tst2");
		t2.setPlayer(2);
		Territory t3 = new Territory("tst3");
		t3.setPlayer(1);
		Territory t4 = new Territory("tst4");
		t4.setPlayer(4);
		Territory t5 = new Territory("tst5");
		t4.setPlayer(1);
		territories.add(t1);
		territories.add(t2);
		territories.add(t3);
		territories.add(t4);
		territories.add(t5);

		Continent totest = new Continent(territories, "TestContinent");

		assertEquals(totest.isPlayerControlled(), 0);
	}

	@Test
	public void isPlayerControlled_allsame() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory t1 = new Territory("tst1");
		t1.setPlayer(1);
		Territory t2 = new Territory("tst2");
		t2.setPlayer(1);
		Territory t3 = new Territory("tst3");
		t3.setPlayer(1);
		Territory t4 = new Territory("tst4");
		t4.setPlayer(1);
		territories.add(t1);
		territories.add(t2);
		territories.add(t3);
		territories.add(t4);

		Continent totest = new Continent(territories, "TestContinent");

		assertEquals(totest.isPlayerControlled(), 1);
	}

	@Test
	public void isPlayerControlled_alldifferent() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory t1 = new Territory("tst1");
		t1.setPlayer(1);
		Territory t2 = new Territory("tst2");
		t2.setPlayer(2);
		Territory t3 = new Territory("tst3");
		t3.setPlayer(3);
		Territory t4 = new Territory("tst4");
		t4.setPlayer(4);
		territories.add(t1);
		territories.add(t2);
		territories.add(t3);
		territories.add(t4);

		Continent totest = new Continent(territories, "TestContinent");

		assertEquals(totest.isPlayerControlled(), 0);
	}

	@Test
	public void isPlayerControlled_2repeated() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory t1 = new Territory("tst1");
		t1.setPlayer(1);
		Territory t2 = new Territory("tst2");
		t2.setPlayer(1);
		Territory t3 = new Territory("tst3");
		t3.setPlayer(2);
		Territory t4 = new Territory("tst4");
		t4.setPlayer(2);
		territories.add(t1);
		territories.add(t2);
		territories.add(t3);
		territories.add(t4);

		Continent totest = new Continent(territories, "TestContinent");

		assertEquals(totest.isPlayerControlled(), 0);
	}

	@Test
	public void isPlayerControlled_immediatelyafter() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory t1 = new Territory("tst1");
		t1.setPlayer(1);
		Territory t2 = new Territory("tst2");
		t2.setPlayer(2);
		Territory t3 = new Territory("tst3");
		t3.setPlayer(2);
		Territory t4 = new Territory("tst4");
		t4.setPlayer(4);
		territories.add(t1);
		territories.add(t2);
		territories.add(t3);
		territories.add(t4);

		Continent totest = new Continent(territories, "TestContinent");

		assertEquals(totest.isPlayerControlled(), 0);
	}

	@Test
	public void isPlayerControlled_1elementbetween2duplicates() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory t1 = new Territory("tst1");
		t1.setPlayer(1);
		Territory t2 = new Territory("tst2");
		t2.setPlayer(2);
		Territory t3 = new Territory("tst3");
		t3.setPlayer(1);

		territories.add(t1);
		territories.add(t2);
		territories.add(t3);

		Continent totest = new Continent(territories, "TestContinent");

		assertEquals(totest.isPlayerControlled(), 0);
	}

	@Test
	public void testGetSetValue_toolow() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Continent continent = new Continent(territories, "test");

		try {
			continent.setValue(1);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "invalid value");
		}

	}
	
	@Test
	public void testGetSetValue_toohigh() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Continent continent = new Continent(territories, "test");

		try {
			continent.setValue(8);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "invalid value");
		}

	}
	
	@Test
	public void testGetSetValue_lowest() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Continent continent = new Continent(territories, "test");

		continent.setValue(2);
		assertEquals(continent.getValue(), 2);

	}
	
	@Test
	public void testGetSetValue_highest() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Continent continent = new Continent(territories, "test");

		continent.setValue(7);
		assertEquals(continent.getValue(), 7);

	}

	@Test
	public void toStringEmptyTest() {
		ArrayList<Territory> territories = new ArrayList<Territory>();
		territories.add(new Territory("testTerritory"));

		Continent continent = new Continent(territories, "test");
		continent.setPlayer(1);
		continent.setValue(5);

		String result = continent.toString();
		String expected = "\nContinent{\n"
			+ "\tname='" + "test" + "\n"
			+ "\tplayer=" + 1 + "\n"
			+ "\tvalue=" + 5 + "\n"
			+ "\tterritories=[" + "\n"
			+ "\tTerritory{name='testTerritory', player=0, armies=0, neighboringterritories=[]}]'}";
		assertEquals(expected, result);
	}
	
	

}
