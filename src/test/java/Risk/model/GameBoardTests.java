package Risk.model;

import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class GameBoardTests {
	/*
	 * BVA: GetPlayer and SetPlayer returns the player that controls the territory
	 * This is collection searching so our cases are: 1. Match not found, 2. Exactly
	 * one match, 3. More than one match in the collection(Not sure if possible
	 * given hashmap). 4. Single match in first position, 5. Single match in last
	 * position. Actually setting the player on the territories side has already
	 * been tested in our territory tests. I don't believe we have to test player
	 * inputs, as that has already been covered by my territory tests.
	 * 
	 * 
	 * addArmiesToTerritory and getTerritoryArmies are similar to SetPlayer and
	 * GetPlayer, This is collection searching, everything else has already been
	 * tested in the Territory class. Therefore it has the same conditions as above.
	 * 
	 * 
	 * GetContinentPlayer is again a search through a collection, this again has the
	 * same 5 cases listed above under GetPlayer and SetPlayer
	 * 
	 * I'm building everything up iteratively so I shouldn't even have to mock,
	 * which is nice // Nevermind
	 * 
	 * The following are more or less just exhaustive tests:
	 * 
	 * InitializeGameBoard()
	 * 
	 * makeAustralia()
	 * 
	 * makeAfrica() makeSouthAmerica() makeNorthAmerica makeEurope() makeAsia()
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	// This is more refactors so I'm gonna need to talk to yiji
	// TODO:update bva
	// I have no idea how to do half this stuff so I need to talk to yiji

	@Test
	public void getGameBoardEmptyTest() {
		GameBoard expected = GameBoard.getGameBoard();
		assertEquals(expected, GameBoard.getGameBoard());
	}

	@Test
	public void gameBoardToStringTest() {
		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();

		Territory territory1 = new Territory("test1");

		territories.add(territory1);

		Continent testContinent = new Continent(territories, "testContinent");
		testGb.addContinent(testContinent);

		String result = testGb.toString();
		String expected = "GameBoard{continents=[\n"
				+ "Continent{\n"
				+ "\tname='" + "testContinent" + "\n"
				+ "\tplayer=" + 0 + "\n"
				+ "\tvalue=" + 0 + "\n"
			 	+ "\tterritories=[" + "\n"
				+ "\tTerritory{name='test1', player=0, armies=0, neighboringterritories=[]}]'}]}";

		assertEquals(expected, result);
	}

	@Test
	public void getTerritoryFromStringTestNullString() {

		GameBoard gameboard = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();

		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);

		Continent testContinent = new Continent(territories, "testcontinent");

		gameboard.addContinent(testContinent);

		try {
			gameboard.getTerritoryFromString("");
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Territory Found");
		}

	}

	@Test
	public void getTerritoryFromStringTestPresent() {

		GameBoard gameboard = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();

		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);

		Continent testContinent = new Continent(territories, "testcontinent");

		gameboard.addContinent(testContinent);

		Territory tocheck = gameboard.getTerritoryFromString("test1");

		assertEquals(tocheck.getName(), "test1");

	}

	@Test
	public void getTerritoryFromStringTestNotPresent() {

		GameBoard gameboard = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();

		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);

		Continent testContinent = new Continent(territories, "testcontinent");

		gameboard.addContinent(testContinent);

		try {

			Territory tocheck = gameboard.getTerritoryFromString("test4");
			fail("Shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Territory Found");
		}

	}

	@Test
	public void testGetSetTerritoryPlayerNoMatching() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();

		Territory territory0 = new Territory("test0");
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);

		Continent testContinent = new Continent(territories, "testcontinent");
		testGb.addContinent(testContinent);

		try {
			testGb.setPlayer(territory4, 1);
			fail("This shouldn't be reached");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Territory Found");

		}

		try {
			int player = testGb.getTerritoryPlayer(territory0);
			fail("This shouldn't be reached");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Territory Found");

		}

	}

	@Test
	public void testGetSetPlayerOneMatch() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();

		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);

		Continent testContinent = new Continent(territories, "testcontinent");
		testGb.addContinent(testContinent);

		testGb.setPlayer(territory2, 1);

		assertEquals(1, testGb.getTerritoryPlayer(territory2));

	}

	@Test
	public void testGetSetPlayerMoreThanOneMatch() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();

		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory1);

		Continent testContinent = new Continent(territories, "testcontinent");
		testGb.addContinent(testContinent);

		testGb.setPlayer(territory1, 1);

		assertEquals(1, testGb.getTerritoryPlayer(territory1));
	}

	@Test
	public void testGetSetPlayerOneMatchFirstPosition() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();

		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);

		Continent testContinent = new Continent(territories, "testcontinent");
		testGb.addContinent(testContinent);

		testGb.setPlayer(territory4, 4);

		assertEquals(4, testGb.getTerritoryPlayer(territory4));

	}

	@Test
	public void testGetSetPlayerOneMatchLastPosition() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();

		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		testGb.addContinent(testContinent);

		testGb.setPlayer(territory4, 3);

		assertEquals(3, testGb.getTerritoryPlayer(territory4));

	}

	@Test
	public void testGetSetArmiesNoMatching() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory0 = new Territory("test0");
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		testGb.addContinent(testContinent);
		try {
			testGb.addArmiesToTerritory(territory0, 15);
			fail("This shouldn't be reached");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Territory Found");

		}

		try {
			int armies = testGb.getTerritoryArmies(territory0);
			fail("This shouldn't be reached");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Territory Found");

		}

	}

	@Test
	public void testGetSetArmyOneMatch() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		testGb.addContinent(testContinent);

		testGb.addArmiesToTerritory(territory2, 20);

		assertEquals(20, testGb.getTerritoryArmies(territory2));

	}

	@Test
	public void testGetSetArmyMoreThanOneMatch() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		territories.add(territory1);
		Continent testContinent = new Continent(territories, "testcontinent");
		testGb.addContinent(testContinent);

		testGb.addArmiesToTerritory(territory1, 5);

		assertEquals(5, testGb.getTerritoryArmies(territory1));
	}

	@Test
	public void testGetSetArmyOneMatchFirstPosition() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		testGb.addContinent(testContinent);

		testGb.addArmiesToTerritory(territory1, 67);

		assertEquals(67, testGb.getTerritoryArmies(territory1));

	}

	@Test
	public void testGetSetArmyOneMatchLastPosition() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		testGb.addContinent(testContinent);

		testGb.addArmiesToTerritory(territory4, 3);

		assertEquals(3, testGb.getTerritoryArmies(territory4));

	}

	@Test
	public void testGetContinentPlayerNoMatching() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");

		Continent testContinent2 = new Continent(territories, "testcontinent2");
		testGb.addContinent(testContinent);
		try {
			int player = testGb.getContinentPlayer(testContinent2);
			fail("This shouldn't be reached");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Continent Found");

		}

	}

	@Test
	public void testGetContinentPlayerOneMatching() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		Continent testContinent2 = new Continent(territories, "testcontinent2");
		Continent testContinent3 = new Continent(territories, "testcontinent3");
		Continent testContinent4 = new Continent(territories, "testcontinent4");

		testGb.addContinent(testContinent);
		testGb.addContinent(testContinent2);
		testGb.addContinent(testContinent3);
		testGb.addContinent(testContinent4);

		assertEquals(testGb.getContinentPlayer(testContinent2), 0);

	}

	@Test
	public void testGetContinentPlayerMoreThanOneMatching() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territory2.setPlayer(3);
		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		Continent testContinent2 = new Continent(territories, "testcontinent2");
		testContinent2.setPlayer(3);
		Continent testContinent3 = new Continent(territories, "testcontinent3");
		Continent testContinent4 = new Continent(territories, "testcontinent4");

		testGb.addContinent(testContinent);
		testGb.addContinent(testContinent2);
		testGb.addContinent(testContinent3);
		testGb.addContinent(testContinent4);
		testGb.addContinent(testContinent2);

		assertEquals(testGb.getContinentPlayer(testContinent2), 3);

	}

	@Test
	public void testGetContinentPlayerOneMatchingFirstPosition() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		Continent testContinent2 = new Continent(territories, "testcontinent2");
		Continent testContinent3 = new Continent(territories, "testcontinent3");
		Continent testContinent4 = new Continent(territories, "testcontinent4");
		
		testContinent.setPlayer(2);

		testGb.addContinent(testContinent);
		testGb.addContinent(testContinent2);
		testGb.addContinent(testContinent3);
		testGb.addContinent(testContinent4);

		assertEquals(testGb.getContinentPlayer(testContinent), 2);

	}

	@Test
	public void testGetContinentPlayerOneMatchingLastPosition() {

		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		Continent testContinent2 = new Continent(territories, "testcontinent2");
		Continent testContinent3 = new Continent(territories, "testcontinent3");
		Continent testContinent4 = new Continent(territories, "testcontinent4");

		testGb.addContinent(testContinent);
		testGb.addContinent(testContinent2);
		testGb.addContinent(testContinent3);
		testGb.addContinent(testContinent4);

		assertEquals(testGb.getContinentPlayer(testContinent4), 0);

	}

	@Test
	public void testDrawFromMainDeckSetDeck() {
		Deck testDeck = EasyMock.strictMock(Deck.class);
		
		EasyMock.expect(testDeck.drawCard()).andReturn(new Card(null, null));

		EasyMock.replay(testDeck);

		GameBoard testGb = new GameBoard();

		testGb.setDeck(testDeck);

		assertTrue(testGb.drawFromMainDeck().getClass() == Card.class);

		EasyMock.verify(testDeck);

	}

	@Test
	public void testDrawFromMainDeckSetDeckEmpty() {
		Deck testDeck = EasyMock.strictMock(Deck.class);

		EasyMock.expect(testDeck.drawCard()).andThrow(new NoSuchElementException("Deck is currently Empty!"));

		EasyMock.replay(testDeck);

		GameBoard testGb = new GameBoard();

		testGb.setDeck(testDeck);

		try {
			testGb.drawFromMainDeck();
			fail("shouldn't reach this point");

		} catch (Exception e) {
			assertEquals(e.getMessage(), "Main Deck Empty");

		}

		EasyMock.verify(testDeck);

	}
	
	@Test
	public void getTerritoryFromStringNullTestNoneMatching() {
		
		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		Continent testContinent2 = new Continent(territories, "testcontinent2");
		Continent testContinent3 = new Continent(territories, "testcontinent3");
		Continent testContinent4 = new Continent(territories, "testcontinent4");

		testGb.addContinent(testContinent);
		testGb.addContinent(testContinent2);
		testGb.addContinent(testContinent3);
		testGb.addContinent(testContinent4);
		
		try {
			testGb.getTerritoryFromString("");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Territory Found");
		}
		
	}
	
	@Test
	public void getTerritoryFromStringOneMatchingFirstPosition() {
		
		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		Continent testContinent2 = new Continent(territories, "testcontinent2");
		Continent testContinent3 = new Continent(territories, "testcontinent3");
		Continent testContinent4 = new Continent(territories, "testcontinent4");

		testGb.addContinent(testContinent);
		testGb.addContinent(testContinent2);
		testGb.addContinent(testContinent3);
		testGb.addContinent(testContinent4);
		
		Territory tocheck = testGb.getTerritoryFromString("test1");
		
		assertEquals(tocheck.getName(), "test1");
		
	}
	
	@Test
	public void getTerritoryFromStringMoreThanOneMatching() {
		
		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		territories.add(territory1);
		Continent testContinent = new Continent(territories, "testcontinent");
		Continent testContinent2 = new Continent(territories, "testcontinent2");
		Continent testContinent3 = new Continent(territories, "testcontinent3");
		Continent testContinent4 = new Continent(territories, "testcontinent4");

		testGb.addContinent(testContinent);
		testGb.addContinent(testContinent2);
		testGb.addContinent(testContinent3);
		testGb.addContinent(testContinent4);
		
		Territory tocheck = testGb.getTerritoryFromString("test1");
		
		assertEquals(tocheck.getName(), "test1");
		
	}
	
	@Test
	public void getTerritoryFromStringLastMatching() {
		
		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		territories.add(territory1);
		Continent testContinent = new Continent(territories, "testcontinent");
		Continent testContinent2 = new Continent(territories, "testcontinent2");
		Continent testContinent3 = new Continent(territories, "testcontinent3");
		Continent testContinent4 = new Continent(territories, "testcontinent4");

		testGb.addContinent(testContinent);
		testGb.addContinent(testContinent2);
		testGb.addContinent(testContinent3);
		testGb.addContinent(testContinent4);
		
		Territory tocheck = testGb.getTerritoryFromString("test4");
		
		assertEquals(tocheck.getName(), "test4");
		
	}
	
	@Test
	public void getContinentFromStringNullTestNoneMatching() {
		
		GameBoard testGb = new GameBoard();
		ArrayList<Territory> territories = new ArrayList<Territory>();
		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);
		Continent testContinent = new Continent(territories, "testcontinent");
		Continent testContinent2 = new Continent(territories, "testcontinent2");
		Continent testContinent3 = new Continent(territories, "testcontinent3");
		Continent testContinent4 = new Continent(territories, "testcontinent4");

		testGb.addContinent(testContinent);
		testGb.addContinent(testContinent2);
		testGb.addContinent(testContinent3);
		testGb.addContinent(testContinent4);
		
		try {
			testGb.getContinentFromString("");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No Continent Found");
		}
		
	}
	
	@Test
	public void getContinentFromStringOneMatchingFirstPosition() {
		
		GameBoard testGb = new GameBoard();

		ArrayList<Territory> territories = new ArrayList<Territory>();

		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);

		Continent testContinent = new Continent(territories, "testcontinent");
		Continent testContinent2 = new Continent(territories, "testcontinent2");
		Continent testContinent3 = new Continent(territories, "testcontinent3");
		Continent testContinent4 = new Continent(territories, "testcontinent4");

		testGb.addContinent(testContinent);
		testGb.addContinent(testContinent2);
		testGb.addContinent(testContinent3);
		testGb.addContinent(testContinent4);
		
		Continent tocheck = testGb.getContinentFromString("testcontinent");
		
		assertEquals(tocheck.getName(), "testcontinent");
		
	}
	
	@Test
	public void getContinentFromStringLastMatching() {
		
		GameBoard testGb = new GameBoard();

		ArrayList<Territory> territories = new ArrayList<Territory>();

		Territory territory1 = new Territory("test1");
		Territory territory2 = new Territory("test2");
		Territory territory3 = new Territory("test3");
		Territory territory4 = new Territory("test4");

		territories.add(territory1);
		territories.add(territory2);
		territories.add(territory3);
		territories.add(territory4);

		Continent testContinent = new Continent(territories, "testcontinent");
		Continent testContinent2 = new Continent(territories, "testcontinent2");
		Continent testContinent3 = new Continent(territories, "testcontinent3");
		Continent testContinent4 = new Continent(territories, "testcontinent4");

		testGb.addContinent(testContinent);
		testGb.addContinent(testContinent2);
		testGb.addContinent(testContinent3);
		testGb.addContinent(testContinent4);
		
		Continent tocheck = testGb.getContinentFromString("testcontinent4");
		
		assertEquals(tocheck.getName(), "testcontinent4");
		
	}

}
