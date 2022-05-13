package Risk.controller;

import Risk.model.Continent;
import Risk.model.GameBoard;
import Risk.model.Territory;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class GameBoardControllerTest {


	@Test
	public void loadshuffletest() {
		GameBoardController controller = new GameBoardController();
		controller.map = "-globe";
		controller.loadGameBoard();
		controller.territoryController = new GameBoardTerritoryController(controller.gameBoard);
		controller.territoryController.map = "-globe";
		controller.territoryController.loadTerritoryNeighboring();
		controller.populateGameBoardDeckTroops();
		assertFalse(controller.gameBoardDeck.drawCard().territory.equals("New Guinea"));
	}

	@Test
	public void initGameTest_allterritories() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();

		controller.map = "-globe";

		controller.initGame();
		assertEquals(controller.gameBoard .continents.size(), 6);
		int territorycount = 0;
		for (Continent continent : controller.gameBoard .continents) {
			for (Territory territories : continent.territories) {
				assertTrue((territories.getNeighboring().size() > 0));
				territorycount++;
			}


		}
		assertEquals(42, controller.gameBoard.getDeck().cards.size());
		
		assertEquals(42, territorycount);
	}

	@Test
	public void initGameTest_mocked() {
		GameBoardController controller = EasyMock.partialMockBuilder(GameBoardController.class)
				.addMockedMethod("loadGameBoard")
				.addMockedMethod("populateGameBoardDeckTroops").createMock();
		controller.loadGameBoard();
		controller.territoryController = new GameBoardTerritoryController(new GameBoard());
		controller.territoryController.loadTerritoryNeighboring();
		controller.populateGameBoardDeckTroops();
		EasyMock.replay(controller);
		controller.initGame();

		EasyMock.verify(controller);
	}

	@Test
	public void getTerritoryOwner_input1() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.territoryController = new GameBoardTerritoryController(controller.gameBoard);
		try {
			controller.territoryController.getTerritoryOwner("");
			fail("Should throw an IllegalArgumentException.");

		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "No Territory Found");
		}

	}

	@Test
	public void getTerritoryOwner_input2() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();

		try {
			int player = controller.territoryController.getTerritoryOwner("Alberta");
			assertEquals(0, player); // not owned so 0
		} catch (IllegalArgumentException e) {
			fail("Shouldn't cause an exception.");
		}
	}


	@Test
	public void getTerritoryOwner_inputvalid() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";
		controller.initGame();

		controller.territoryController.map = "-globe";
		controller.territoryController.getTerritory("Alberta").setPlayer(2);

		try {
			int player = controller.territoryController.getTerritoryOwner("Alberta");
			assertEquals(2, player); // not owned so 0

		} catch (IllegalArgumentException e) {
			fail("Shouldn't cause an exception.");
		}
	}

	@Test
	public void testIsAdjacent_input1() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();
		try {
			assertTrue(controller.territoryController.isAdjacent("Alberta", "Alaska"));
		} catch (IllegalArgumentException e) {
			fail("Shouldn't cause an exception.");
		}
	}

	@Test
	public void testIsAdjacent_input2() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();
		try {
			assertFalse(controller.territoryController.isAdjacent("Alberta", "Brazil"));
		} catch (IllegalArgumentException e) {
			fail("Shouldn't cause an exception.");
		}
	}

	@Test
	public void testIsAdjacent_input3() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();
		try {
			controller.territoryController.isAdjacent("Alberta", "Indiana");
			fail("Should cause an exception.");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "No Territory Found");
		}
	}

	@Test
	public void testGetTerritory_input1() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();
		try {
			Territory territory = controller.territoryController.getTerritory("Alberta");
			assertEquals("Alberta", territory.getName());
			territory = controller.territoryController.getTerritory("Egypt");
			assertEquals("Egypt", territory.getName());
		} catch (IllegalArgumentException e) {
			fail("Shouldn't cause an exception.");
		}
	}

	@Test
	public void testGetTerritory_input2() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();
		try {
			controller.territoryController.getTerritory("Indiana");
			fail("Should cause an exception.");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "No Territory Found");
		}
	}

	@Test
	public void testPopulateGameBoardDeckTerritory() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();
		controller.populateGameBoardDeckTroops();
		String troopResult = "New Guinea";
		assertEquals(42, controller.gameBoardDeck.cards.size());
	}

	@Test
	public void testPopulateGameBoardDeckTroops() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

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
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

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
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

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
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();
		controller.populateGameBoardDeckTroops();

		GameBoard gameboard = controller.gameBoard ;

		for (Continent continent : gameboard.continents) {
			continent.setPlayer(1);
		}

		assertEquals(controller.getNewContinentPlayerArmies(1), 24);
	}

	@Test
	public void testgetNewContinentPlayerArmies_min() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();

		GameBoard gameboard = controller.gameBoard ;
		System.out.println(gameboard.continents.size());

		for (Continent continent : gameboard.continents) {
			continent.setPlayer(1);
		}

		assertEquals(controller.getNewContinentPlayerArmies(4), 0);
	}

	@Test
	public void updatePlayer_owned() {

		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();

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
		controller.gameBoard = new GameBoard();

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
	public void updateGameBoard() {
		GameBoardController controller = EasyMock.partialMockBuilder(GameBoardController.class)
				.addMockedMethod("updatePlayer").createMock();

		Set<Continent> continents = new HashSet<Continent>();
		for (int i = 0; i < 7; i++) {
			Continent continent = new Continent(new ArrayList<Territory>(), "test" + (i+1));
			continents.add(continent);
			controller.updatePlayer(continent);
		}

		EasyMock.replay(controller);
		controller.gameBoard = new GameBoard();
		controller.gameBoard .continents = continents;
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
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();

		assertTrue(controller.territoryController.checkOwnedTerritory("Peru", "Argentina", "Russia", 0));
	}

	@Test
	public void checkOwnedTerritorytest_invalidplayer() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";
		controller.initGame();

		assertFalse(controller.territoryController.checkOwnedTerritory("Peru", "Argentina", "Russia", 1));
	}

	@Test
	public void checkOwnedTerritorytest_invalidterritories() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();

		assertFalse(controller.territoryController.checkOwnedTerritory("not", "real", "territories", 0));
	}

	@Test
	public void checkOwnedTerritorytest_invalidterritories_1and2() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();

		assertTrue(controller.territoryController.checkOwnedTerritory("not", "real", "Russia", 0));
	}

	@Test
	public void checkOwnedTerritorytest_invalidterritories_1and3() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();

		assertTrue(controller.territoryController.checkOwnedTerritory("not", "Russia", "territories", 0));
	}

	@Test
	public void checkOwnedTerritorytest_invalidterritories_allnullstring() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();

		assertFalse(controller.territoryController.checkOwnedTerritory("", "", "", 0));
	}

	@Test
	public void checkOwnedTerritorytest_invalidterritories_invalid_player() {
		GameBoardController controller = new GameBoardController();
		controller.gameBoard = new GameBoard();
		controller.map = "-globe";

		controller.initGame();

		assertFalse(controller.territoryController.checkOwnedTerritory("Peru", "Argentina", "Russia", -1));
	}
}
