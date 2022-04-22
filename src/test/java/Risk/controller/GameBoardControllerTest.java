package Risk.controller;

import Risk.model.Card;
import Risk.model.Continent;
import Risk.model.Deck;
import Risk.model.GameBoard;
import Risk.model.Territory;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class 	GameBoardControllerTest {
	
	
	@Test
	public void loadshuffletest() {
		GameBoardController controller = new GameBoardController();

		controller.loadGameBoard();
		controller.loadTerritoryNeighboring();
		controller.populateGameBoardDeckTroops();
		assertFalse(controller.gameBoardDeck.drawCard().territory.equals("New Guinea"));
	}

	@Test
	public void initGameTest() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		System.out.println(controller.getGameBoard());
		System.out.println(controller.getGameBoard().continents.size());

		assertTrue(true);
	}
	
	@Test
	public void initGameTest_allterritories() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		assertEquals(controller.getGameBoard().continents.size(), 6);
		int territorycount = 0;
		for (Continent continent : controller.getGameBoard().continents) {
			for (Territory territories : continent.territories) {
				assertEquals(true, (territories.getNeighboring().size() > 0));
				territorycount++;
			}
			
			
		}
		
		assertEquals(42, controller.getGameBoard().getDeck().cards.size());
		
		assertEquals(42, territorycount);
	}

	@Test
	public void initGameTest_mocked() {
		GameBoardController controller = EasyMock.partialMockBuilder(GameBoardController.class)
				.addMockedMethod("loadGameBoard").addMockedMethod("loadTerritoryNeighboring")
				.addMockedMethod("populateGameBoardDeckTroops").createMock();
		controller.loadGameBoard();
		controller.loadTerritoryNeighboring();
		controller.populateGameBoardDeckTroops();
		EasyMock.replay(controller);
		controller.initGame();

		EasyMock.verify(controller);
	}

	@Test
	public void getTerritoryOwner_input1() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		try {
			controller.getTerritoryOwner("");
			fail("Should throw an IllegalArgumentException.");

		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "No Territory Found");
		}

	}

	@Test
	public void getTerritoryOwner_input2() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();
		controller.initGame();

		try {
			int player = controller.getTerritoryOwner("Alberta");
			assertEquals(0, player); // not owned so 0
		} catch (IllegalArgumentException e) {
			fail("Shouldn't cause an exception.");
		}
	}
	
	
	@Test
	public void getTerritoryOwner_inputvalid() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();
		controller.initGame();

		controller.getTerritory("Alberta").setPlayer(2);

		try {
			int player = controller.getTerritoryOwner("Alberta");
			assertEquals(2, player); // not owned so 0

		} catch (IllegalArgumentException e) {
			fail("Shouldn't cause an exception.");
		}
	}

	@Test
	public void testIsAdjacent_input1() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		try {
			assertTrue(controller.isAdjacent("Alberta", "Alaska"));
		} catch (IllegalArgumentException e) {
			fail("Shouldn't cause an exception.");
		}
	}

	@Test
	public void testIsAdjacent_input2() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		try {
			assertFalse(controller.isAdjacent("Alberta", "Brazil"));
		} catch (IllegalArgumentException e) {
			fail("Shouldn't cause an exception.");
		}
	}

	@Test
	public void testIsAdjacent_input3() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		try {
			controller.isAdjacent("Alberta", "Indiana");
			fail("Should cause an exception.");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "No Territory Found");
		}
	}

	@Test
	public void testGetTerritory_input1() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		try {
			Territory territory = controller.getTerritory("Alberta");
			assertEquals("Alberta", territory.getName());
			territory = controller.getTerritory("Egypt");
			assertEquals("Egypt", territory.getName());
		} catch (IllegalArgumentException e) {
			fail("Shouldn't cause an exception.");
		}
	}

	@Test
	public void testGetTerritory_input2() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		try {
			controller.getTerritory("Indiana");
			fail("Should cause an exception.");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "No Territory Found");
		}
	}

	@Test
	public void testPopulateGameBoardDeckTerritory() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		controller.populateGameBoardDeckTroops();
		String troopResult = "New Guinea";
		assertEquals(42, controller.gameBoardDeck.cards.size());
	}

	@Test
	public void testPopulateGameBoardDeckTroops() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		controller.populateGameBoardDeckTroops();
		String troopResult = controller.gameBoardDeck.drawCard().troopType;
		boolean isTroop = troopResult.equals("Infantry")
				|| troopResult.equals("Cavalry")
				|| troopResult.equals("Artillery");
		assertTrue(isTroop);
	}

	@Test
	public void testgetNewContinentPlayerArmies_toolow() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		controller.populateGameBoardDeckTroops();
		try {
			controller.getNewContinentPlayerArmies(0);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "invalid player");

		}
	}

	@Test
	public void testgetNewContinentPlayerArmies_toohigh() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		controller.populateGameBoardDeckTroops();
		try {
			controller.getNewContinentPlayerArmies(5);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "invalid player");

		}
	}

	@Test
	public void testgetNewContinentPlayerArmies_max() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();
		controller.populateGameBoardDeckTroops();

		GameBoard gameboard = controller.getGameBoard();

		for (Continent continent : gameboard.continents) {
			continent.setPlayer(1);
		}

		assertEquals(controller.getNewContinentPlayerArmies(1), 24);
	}

	@Test
	public void testgetNewContinentPlayerArmies_min() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		controller.initGame();

		GameBoard gameboard = controller.getGameBoard();
		System.out.println(gameboard.continents.size());

		for (Continent continent : gameboard.continents) {
			continent.setPlayer(1);
		}

		assertEquals(controller.getNewContinentPlayerArmies(4), 0);
	}

	@Test
	public void updatePlayer_owned() {

		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		Territory test3 = new Territory("test3");
		Territory test4 = new Territory("test4");
		ArrayList<Territory> territories = new ArrayList<Territory>();
		territories.add(test1);
		territories.add(test2);
		territories.add(test3);
		territories.add(test4);

		for (Territory territory : territories) {
			territory.setPlayer(1);
		}

		Continent continent = new Continent(territories, "testcontinent");

		controller.updatePlayer(continent);

		assertEquals(continent.getPlayer(), 1);
	}

	@Test
	public void updatePlayer_unowned() {

		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();

		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		Territory test3 = new Territory("test3");
		Territory test4 = new Territory("test4");
		ArrayList<Territory> territories = new ArrayList<Territory>();
		territories.add(test1);
		territories.add(test2);
		territories.add(test3);
		territories.add(test4);
		int player = 0;
		for (Territory territory : territories) {
			territory.setPlayer(player);
			player++;
		}

		Continent continent = new Continent(territories, "testcontinent");

		controller.updatePlayer(continent);

		assertEquals(continent.getPlayer(), 0);
	}

	@Test
	public void updateGameBoard_min() {
		GameBoardController controller = EasyMock.partialMockBuilder(GameBoardController.class)
				.addMockedMethod("updatePlayer").createMock();
		Set<Continent> continents = new HashSet<Continent>();

		EasyMock.replay(controller);
		controller.initializeNewBoardForTest();
		controller.getGameBoard().continents = continents;
		controller.updateGameBoard();
		EasyMock.verify(controller);
	}

	@Test
	public void updateGameBoard_1() {
		GameBoardController controller = EasyMock.partialMockBuilder(GameBoardController.class)
				.addMockedMethod("updatePlayer").createMock();
		Continent testcontinent = new Continent(new ArrayList<Territory>(), "test");
		Set<Continent> continents = new HashSet<Continent>();
		continents.add(testcontinent);
		controller.updatePlayer(testcontinent);
		EasyMock.replay(controller);
		controller.initializeNewBoardForTest();
		controller.getGameBoard().continents = continents;
		controller.updateGameBoard();
		EasyMock.verify(controller);
	}

	@Test
	public void updateGameBoard_max() {
		GameBoardController controller = EasyMock.partialMockBuilder(GameBoardController.class)
				.addMockedMethod("updatePlayer").createMock();

		Continent testcontinent = new Continent(new ArrayList<Territory>(), "test1");
		Continent testcontinent2 = new Continent(new ArrayList<Territory>(), "test2");
		Continent testcontinent3 = new Continent(new ArrayList<Territory>(), "test3");
		Continent testcontinent4 = new Continent(new ArrayList<Territory>(), "test4");
		Continent testcontinent5 = new Continent(new ArrayList<Territory>(), "test5");
		Continent testcontinent6 = new Continent(new ArrayList<Territory>(), "test6");
		Continent testcontinent7 = new Continent(new ArrayList<Territory>(), "test7");

		Set<Continent> continents = new HashSet<Continent>();
		continents.add(testcontinent);
		continents.add(testcontinent2);
		continents.add(testcontinent3);
		continents.add(testcontinent4);
		continents.add(testcontinent5);
		continents.add(testcontinent6);
		continents.add(testcontinent7);

		controller.updatePlayer(testcontinent);
		controller.updatePlayer(testcontinent2);
		controller.updatePlayer(testcontinent3);
		controller.updatePlayer(testcontinent4);
		controller.updatePlayer(testcontinent5);
		controller.updatePlayer(testcontinent6);
		controller.updatePlayer(testcontinent7);

		EasyMock.replay(controller);
		controller.initializeNewBoardForTest();
		controller.getGameBoard().continents = continents;
		controller.updateGameBoard();
		EasyMock.verify(controller);
	}

	@Test
	public void testIncrementTradeCounter() {
		int[] expected = {4, 6, 8, 10, 12, 15, 20, 25, 30, 35, 40, 45, 50};
		GameBoardController controller = new GameBoardController();
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], controller.getTradeCounter());
			controller.incrementTradeCounter();
		}
		assertEquals(55, controller.getTradeCounter());
		try {
			controller.incrementTradeCounter();
			fail("shouldn't reach this point");

		} catch (Exception e) {
			assertEquals(e.getMessage(), "Out of cards to trade in");
		}
	}

	@Test
	public void checkOwnedTerritorytest_valid() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();
		controller.initGame();

		assertEquals(true, controller.checkOwnedTerritory("Peru", "Argentina", "Russia", 0));
	}

	@Test
	public void checkOwnedTerritorytest_invalidplayer() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();
		controller.initGame();

		assertEquals(false, controller.checkOwnedTerritory("Peru", "Argentina", "Russia", 1));
	}

	@Test
	public void checkOwnedTerritorytest_invalidterritories() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();
		controller.initGame();

		assertEquals(false, controller.checkOwnedTerritory("not", "real", "territories", 0));
	}

	@Test
	public void checkOwnedTerritorytest_invalidterritories_1and2() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();
		controller.initGame();

		assertEquals(true, controller.checkOwnedTerritory("not", "real", "Russia", 0));
	}

	@Test
	public void checkOwnedTerritorytest_invalidterritories_1and3() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();
		controller.initGame();

		assertEquals(true, controller.checkOwnedTerritory("not", "Russia", "territories", 0));
	}

	@Test
	public void checkOwnedTerritorytest_invalidterritories_allnullstring() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();
		controller.initGame();

		assertEquals(false, controller.checkOwnedTerritory("", "", "", 0));
	}

	@Test
	public void checkOwnedTerritorytest_invalidterritories_invalid_player() {
		GameBoardController controller = new GameBoardController();
		controller.initializeNewBoardForTest();
		controller.initGame();

		assertEquals(false, controller.checkOwnedTerritory("Peru", "Argentina", "Russia", -1));
	}
}
