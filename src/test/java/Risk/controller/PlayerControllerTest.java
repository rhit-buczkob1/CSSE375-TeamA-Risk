package Risk.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Risk.model.Card;
import Risk.model.Player;
import Risk.model.Territory;

public class PlayerControllerTest {

	// TODO: update bva

	@Test
	public void setMultiplePlayersTest() {
		PlayerController pc = new PlayerController();
		Player player = new Player(1);
		Player player2 = new Player(2);
		Player player3 = new Player(3);
		pc.addPlayer(player);
		pc.addPlayer(player2);
		pc.addPlayer(player3);

		assertEquals(player, pc.getCurrentPlayer());

		pc.nextPlayer();

		assertEquals(player2, pc.getCurrentPlayer());

		pc.nextPlayer();

		assertEquals(player3, pc.getCurrentPlayer());

	}

	@Test
	public void initializePlayers3Test() {
		PlayerController pc = new PlayerController();
		Player player = new Player(1);
		Player player2 = new Player(2);
		Player player3 = new Player(3);
		pc.addPlayer(player);
		pc.addPlayer(player2);
		pc.addPlayer(player3);
		try {
			pc.initializePlayer();
		} catch(Exception e) {
			e.printStackTrace();
		}

		assertEquals(35, player.getPlayerArmies());
	}

	@Test
	public void initializePlayers4Test() {
		PlayerController pc = new PlayerController();
		Player player = new Player(1);
		Player player2 = new Player(2);
		Player player3 = new Player(3);
		Player player4 = new Player(3);
		pc.addPlayer(player);
		pc.addPlayer(player2);
		pc.addPlayer(player3);
		pc.addPlayer(player4);
		try {
			pc.initializePlayer();
		} catch(Exception e) {
			e.printStackTrace();
		}

		assertEquals(30, player.getPlayerArmies());
	}

	@Test
	public void addOwnershipTerritoryTest() {
		PlayerController pc = new PlayerController();
		pc.setGameMode("NORMAL");
		Player player = new Player(1);
		Territory territory = new Territory("Test");
		pc.addPlayer(player);
		pc.setTerritoryOwnership(territory);

		assertEquals(1, territory.getPlayer());

	}

	@Test
	public void addArmyToEnemyTest() {
		PlayerController pc = new PlayerController();
		Player player = new Player(1);
		Player player2 = new Player(2);
		Territory t1 = new Territory("t1");
		t1.setPlayer(1);
		pc.addPlayer(player);
		pc.addPlayer(player2);
		try {
			pc.initializePlayer();
		} catch(Exception e) {
			e.printStackTrace();
		}
		pc.nextPlayer();
		pc.addInfantryToTerritory(t1, 1);

		assertEquals(player2, pc.getCurrentPlayer());
	}

	@Test
	public void addArmyTerritoryTest() {
		PlayerController pc = new PlayerController();
		pc.setGameMode("NORMAL");
		Player player = new Player(1);
		Territory territory = new Territory("Test");
		pc.addPlayer(player);
		player.addPlayerArmies(30);
		pc.setTerritoryOwnership(territory);

		pc.addInfantryToTerritory(territory, 3);

		assertEquals(3, territory.getArmyCount());
		assertEquals(27, player.getPlayerArmies());
		assertEquals(0, player.getTerritories());
		assertEquals(true, pc.getInit());

	}

	@Test
	public void addArmyTerritoryTest_not_already_owned_by_player_not_init_phase() {
		PlayerController pc = new PlayerController();
		pc.setGameMode("NORMAL");
		Player player = new Player(1);
		Player player2 = new Player(2);

		Territory territory = new Territory("Test");
		pc.addPlayer(player);
		pc.addPlayer(player2);

		player.addPlayerArmies(30);
		assertEquals(0, player.getTerritories());

		assertEquals(player, pc.getCurrentPlayer());

		pc.addInfantryToTerritory(territory, 3);

		assertEquals(3, territory.getArmyCount());
		assertEquals(27, player.getPlayerArmies());
		assertEquals(1, player.getTerritories());
		assertEquals(player2, pc.getCurrentPlayer());

		assertEquals(true, pc.getInit());

	}

	@Test
	public void addArmyTerritoryTest_init_phase() {
		PlayerController pc = new PlayerController();
		pc.setGameMode("NORMAL");
		Player player = new Player(1);
		Player player2 = new Player(2);
		Territory territory = new Territory("Test");
		pc.addPlayer(player);
		pc.addPlayer(player2);
		player.addPlayerArmies(30);
		assertEquals(0, player.getTerritories());
		pc.initSetup = true;

		assertEquals(pc.getCurrentPlayer(), player);

		pc.addInfantryToTerritory(territory, 3);

		assertEquals(3, territory.getArmyCount());
		assertEquals(27, player.getPlayerArmies());
		assertEquals(1, player.getTerritories());
		assertEquals(pc.getCurrentPlayer(), player2);
		assertEquals(true, pc.getInit());

	}

	@Test
	public void initSetupTest() {
		PlayerController pc = new PlayerController();
		pc.setGameMode("NORMAL");
		Player player = new Player(1);
		Territory territory = new Territory("Test");
		pc.addPlayer(player);
		player.addPlayerArmies(30);
		pc.setTerritoryOwnership(territory);

		pc.addInfantryToTerritory(territory, 3);

		assertEquals(3, territory.getArmyCount());
		assertEquals(27, player.getPlayerArmies());
		assertEquals(true, pc.getInit());
		pc.addInfantryToTerritory(territory, 27);
		assertFalse(pc.getInit());

	}

	@Test
	public void addArmyToUnownedTerritoryTest() {
		PlayerController pc = new PlayerController();
		pc.setGameMode("NORMAL");
		Player player = new Player(1);
		Territory territory = new Territory("Test");
		pc.addPlayer(player);
		player.addPlayerArmies(30);
		pc.addInfantryToTerritory(territory, 3);

		assertEquals(1, territory.getPlayer());
		assertEquals(3, territory.getArmyCount());
		assertEquals(27, player.getPlayerArmies());
		assertEquals(true, pc.getInit());

	}

	@Test
	public void addArmyToEnemyTerritoryTest() {
		PlayerController pc = new PlayerController();
		Player player = new Player(1);
		Player player2 = new Player(2);
		Territory territory = new Territory("Test");
		pc.addPlayer(player);
		player.addPlayerArmies(30);
		territory.setPlayer(player2.getId());
		;
		pc.addInfantryToTerritory(territory, 3);

		assertEquals(2, territory.getPlayer());
		assertEquals(0, territory.getArmyCount());
		assertEquals(30, player.getPlayerArmies());
		assertEquals(true, pc.getInit());

	}

	@Test
	public void addArmyToEnemyTerritoryFlagTest() {
		Player player = new Player(1);
		PlayerController pc = new PlayerController();
		pc.setGameMode("NORMAL");
		pc.addPlayer(player);
		player.addPlayerArmies(1);
		Territory territory = new Territory("Test");
		pc.addInfantryToTerritory(territory, 1);

		assertEquals(1, territory.getPlayer());
		assertEquals(1, territory.getArmyCount());
		assertEquals(0, player.getPlayerArmies());
		assertEquals(false, pc.getInit());

	}

	@Test
	public void calculateTurnArmies_input0() {
		PlayerController controller = new PlayerController();
		Player player = new Player(1);

		controller.addPlayer(player);
		assertEquals(3, controller.calculateTurnArmies());
	}

	@Test
	public void calculateTurnArmies_input42() {
		PlayerController controller = new PlayerController();
		Player player = new Player(1);
		for (int i = 0; i < 42; i++) {
			player.addTerritory();
		}
		controller.addPlayer(player);

		assertEquals(14, controller.calculateTurnArmies());

	}

	@Test
	public void calculateTurnArmies_input1() {
		PlayerController controller = new PlayerController();
		Player player = new Player(1);
		controller.addPlayer(player);

		assertEquals(3, controller.calculateTurnArmies());
	}

	@Test
	public void calculateTurnArmies_input9() {
		PlayerController controller = new PlayerController();
		Player player = new Player(1);
		controller.addPlayer(player);
		for (int i = 0; i < 12; i++) {
			player.addTerritory();
		}

		assertEquals(4, controller.calculateTurnArmies());
	}

	@Test
	public void addNewArmiesToPlayerTest0() {
		// Because these two methods are already being tested, I'm fairly sure we just
		// need to test that these are being called
		// TODO: check with Yiji about this test
		Player player = new Player();
		PlayerController controller = new PlayerController();
		controller.addPlayer(player);
		controller.addNewArmiestoPlayer();

		assertEquals(player.getPlayerArmies(), 3);

	}

	@Test
	public void addNewArmiesToPlayerTest1() {
		Player player = new Player();
		PlayerController controller = new PlayerController();
		controller.addPlayer(player);
		player.addTerritory();
		controller.addNewArmiestoPlayer();
		assertEquals(player.getPlayerArmies(), 3);

	}

	@Test
	public void addNewArmiesToPlayerTest42() {
		Player player = new Player();
		PlayerController controller = new PlayerController();
		controller.addPlayer(player);
		for (int i = 0; i < 42; i++) {
			player.addTerritory();
		}
		controller.addNewArmiestoPlayer();

		assertEquals(player.getPlayerArmies(), 14);

	}

	@Test
	public void moveArmyBetweenNonAdjacentTerritoryTest() {
		Territory terrMockOne = new Territory("test1");
		Territory terrMockTwo = new Territory("test2");

		ArrayList<String> mockedList = new ArrayList<String>();
		mockedList.add("Alaska");

		PlayerController pc = new PlayerController();

		try {
			pc.moveArmy(terrMockOne, terrMockTwo, 5);
			fail("Should throw IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Territories are not adjacent.");
		}

	}

	@Test
	public void moveTooMuchArmyBetweenAdjacentTerritoryTest() {
		Territory terrMockOne = new Territory("test1");
		Territory terrMockTwo = new Territory("Alaska");

		ArrayList<String> mockedList = new ArrayList<String>();
		mockedList.add("Alaska");
		terrMockOne.addNeighboring(terrMockTwo);
		terrMockOne.addArmies(5);

		PlayerController pc = new PlayerController();

		try {
			pc.moveArmy(terrMockOne, terrMockTwo, 5);
			fail("Should throw IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Not enough pieces to move.");
		}

	}

	@Test
	public void moveArmyBetweenAdjacentTerritoryTest() {
		Territory terrMockOne = new Territory("test1");
		Territory terrMockTwo = new Territory("Alaska");

		ArrayList<String> mockedList = new ArrayList<String>();
		mockedList.add("Alaska");

		terrMockOne.addNeighboring(terrMockTwo);
		terrMockOne.addArmies(10);

		PlayerController pc = new PlayerController();

		try {
			pc.moveArmy(terrMockOne, terrMockTwo, 5);

		} catch (IllegalArgumentException e) {
			fail("Shouldn't throw IllegalArgumentException.");
		}

		assertEquals(5, terrMockOne.getArmyCount());
		assertEquals(5, terrMockTwo.getArmyCount());

	}

	@Test
	public void moveInvalidArmyBetweenAdjacentTerritoryTest() {
		Territory terrMockOne = new Territory("test1");
		Territory terrMockTwo = new Territory("Alaska");

		ArrayList<String> mockedList = new ArrayList<String>();
		mockedList.add("Alaska");

		PlayerController pc = new PlayerController();
		terrMockOne.addNeighboring(terrMockTwo);
		terrMockOne.addArmies(6);

		try {
			pc.moveArmy(terrMockOne, terrMockTwo, 0);
			fail("Should throw IllegalArgumentException.");

		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Pieces is out of range.");
		}

	}

	@Test
	public void playerDonePlacingNewTestMocked() {
		PlayerController controller = new PlayerController();
		Player player = new Player(1);

		player.addPlayerArmies(1);
		controller.addPlayer(player);

		assertEquals(controller.playerDonePlacingNew(), false);
		player.removePlayerArmies(1);
		assertEquals(controller.playerDonePlacingNew(), true);
	}

	@Test
	public void addArmiesToCurrentPlayerTest_toolow() {
		PlayerController controller = new PlayerController();
		Player player = new Player(1);
		controller.addPlayer(player);
		try {
			controller.addArmiesToCurrentPlayer(-1);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Invalid armies");
		}

	}

	@Test
	public void addArmiesToCurrentPlayerTest_toohigh() {
		PlayerController controller = new PlayerController();
		Player player = new Player(1);
		controller.addPlayer(player);
		try {
			controller.addArmiesToCurrentPlayer(91);
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Invalid armies");
		}

	}

	@Test
	public void addArmiesToCurrentPlayerTest_lowest() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);

		controller.addArmiesToCurrentPlayer(0);
		assertEquals(player.getPlayerArmies(), 0);

	}

	@Test
	public void addArmiesToCurrentPlayerTest_highest() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);

		controller.addArmiesToCurrentPlayer(90);
		assertEquals(player.getPlayerArmies(), 90);

	}

	@Test
	public void playerLosesTest() {
		PlayerController pc = new PlayerController();

		Player player = new Player(1);
		Player player2 = new Player(2);
		Player player3 = new Player(3);

		pc.addPlayer(player);
		pc.addPlayer(player2);
		pc.addPlayer(player3);

		player.addTerritory();
		player3.addTerritory();

		pc.playerLoss();

		assertEquals(2, pc.players.size());

	}

	@Test
	public void twoPlayerLosesTest() {
		PlayerController pc = new PlayerController();

		Player player = new Player(1);
		Player player2 = new Player(2);
		Player player3 = new Player(3);

		pc.addPlayer(player);
		pc.addPlayer(player2);
		pc.addPlayer(player3);

		player3.addTerritory();

		pc.playerLoss();

		assertEquals(1, pc.players.size());

	}

	@Test
	public void playerNoLosesTest() {
		PlayerController pc = new PlayerController();

		Player player = new Player(1);
		Player player2 = new Player(2);
		Player player3 = new Player(3);

		pc.addPlayer(player);
		pc.addPlayer(player2);
		pc.addPlayer(player3);

		player.addTerritory();
		player2.addTerritory();
		player3.addTerritory();

		pc.playerLoss();

		assertEquals(3, pc.players.size());

	}

	@Test
	public void playerDoneWithCardsTest() {
		PlayerController pc = new PlayerController();
		Player p = new Player(1);
		pc.addPlayer(p);
		p.addCardToPlayer(new Card("Alaska"));
		p.addCardToPlayer(new Card("Alaska"));
		p.addCardToPlayer(new Card("Alaska"));
		assertTrue(pc.playerDoneWithCards());
	}

	@Test
	public void playerNotDoneWithCardsTest() {
		PlayerController pc = new PlayerController();
		Player p = new Player(1);
		pc.addPlayer(p);
		p.addCardToPlayer(new Card("Alaska"));
		p.addCardToPlayer(new Card("Alaska"));
		p.addCardToPlayer(new Card("Alaska"));
		p.addCardToPlayer(new Card("Alaska"));
		p.addCardToPlayer(new Card("Alaska"));
		p.addCardToPlayer(new Card("Alaska"));
		assertFalse(pc.playerDoneWithCards());
	}

	@Test
	public void playerLosesIntegrationTest() {
		PlayerController pc = new PlayerController();

		Player player = new Player(1);
		Player player2 = new Player(2);
		Player player3 = new Player(3);

		pc.addPlayer(player);
		pc.addPlayer(player2);
		pc.addPlayer(player3);

		player.addTerritory();
		player3.addTerritory();

		pc.playerLoss();

		assertEquals(2, pc.players.size());

	}

	@Test
	public void getCurrentIndexNoPlayerTest() {
		PlayerController pc = new PlayerController();
		assertEquals(0, pc.getcurrentIndex());

	}

	@Test
	public void getCurrentIndexPlayersTest() {
		PlayerController pc = new PlayerController();
		Player p = new Player();
		Player p2 = new Player();
		Player p3 = new Player();

		pc.addPlayer(p);
		pc.addPlayer(p2);
		pc.addPlayer(p3);

		assertEquals(0, pc.getcurrentIndex());

		pc.nextPlayer();

		assertEquals(1, pc.getcurrentIndex());

	}

	@Test
	public void playerNoLosesIntegrationTest() {
		PlayerController pc = new PlayerController();

		Player player = new Player(1);
		Player player2 = new Player(2);
		Player player3 = new Player(3);

		pc.addPlayer(player);
		pc.addPlayer(player2);
		pc.addPlayer(player3);

		player.addTerritory();
		player2.addTerritory();
		player3.addTerritory();

		pc.playerLoss();

		assertEquals(3, pc.players.size());

	}

	@Test
	public void addCardToCurrentTestMin() {
		Card card1 = new Card("test");
		PlayerController controller = new PlayerController();
		controller.addPlayer(new Player(1));

		controller.addCardToCurrentPlayer(card1);

		Player tocheckplayer = controller.getCurrentPlayer();
		ArrayList<Card> tocheckdeck = tocheckplayer.getDeck();
		assertEquals(tocheckdeck.size(), 1);
		assertEquals(tocheckdeck.get(0), card1);

	}

	@Test
	public void addCardToCurrentTestMultiple() {
		Card card1 = new Card("test1");
		Card card2 = new Card("test2");
		Card card3 = new Card("test3");
		PlayerController controller = new PlayerController();
		controller.addPlayer(new Player(1));

		controller.addCardToCurrentPlayer(card1);
		controller.addCardToCurrentPlayer(card2);
		controller.addCardToCurrentPlayer(card3);

		Player tocheckplayer = controller.getCurrentPlayer();
		ArrayList<Card> tocheckdeck = tocheckplayer.getDeck();
		assertEquals(tocheckdeck.size(), 3);
		assertEquals(tocheckdeck.get(0), card1);
		assertEquals(tocheckdeck.get(1), card2);
		assertEquals(tocheckdeck.get(2), card3);

	}

	@Test
	public void addCardToCurrentTestMax() {
		Card card1 = new Card("test1");
		Card card2 = new Card("test2");
		Card card3 = new Card("test3");
		Card card4 = new Card("test3");
		Card card5 = new Card("test3");
		Card card6 = new Card("test3");
		Card card7 = new Card("test3");
		PlayerController controller = new PlayerController();
		controller.addPlayer(new Player(1));

		controller.addCardToCurrentPlayer(card1);
		controller.addCardToCurrentPlayer(card2);
		controller.addCardToCurrentPlayer(card3);
		controller.addCardToCurrentPlayer(card4);
		controller.addCardToCurrentPlayer(card5);
		controller.addCardToCurrentPlayer(card6);
		try {
			controller.addCardToCurrentPlayer(card7);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Current player deck is full");
		}

		Player tocheckplayer = controller.getCurrentPlayer();
		ArrayList<Card> tocheckdeck = tocheckplayer.getDeck();
		assertEquals(tocheckdeck.size(), 6);
		assertEquals(tocheckdeck.get(0), card1);
		assertEquals(tocheckdeck.get(1), card2);
		assertEquals(tocheckdeck.get(2), card3);
		assertEquals(tocheckdeck.get(3), card4);
		assertEquals(tocheckdeck.get(4), card5);
		assertEquals(tocheckdeck.get(5), card6);

	}

	@Test
	public void playerDoneWithCardstest() {
		Card card1 = new Card("test1");
		PlayerController controller = new PlayerController();
		Player tocheckplayer = new Player(1);
		controller.addPlayer(tocheckplayer);
		assertEquals(controller.playerDoneWithCards(), true);
		tocheckplayer.addCardToPlayer(card1);
		assertEquals(controller.playerDoneWithCards(), true);
		tocheckplayer.addCardToPlayer(card1);
		tocheckplayer.addCardToPlayer(card1);
		tocheckplayer.addCardToPlayer(card1);
		assertEquals(controller.playerDoneWithCards(), true);
		tocheckplayer.addCardToPlayer(card1);
		assertEquals(controller.playerDoneWithCards(), false);

		tocheckplayer.addCardToPlayer(card1);
		assertEquals(controller.playerDoneWithCards(), false);

	}

	@Test
	public void spendCards_deckempty() {
		PlayerController controller = new PlayerController();
		controller.addPlayer(new Player());
		try {
			controller.spendCards(new Card("test1"), new Card("test 2"), new Card("test 3"));
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Not enough cards in deck");
		}
	}

	@Test
	public void spendCards_deckalmostempty() {
		PlayerController controller = new PlayerController();
		controller.addPlayer(new Player());
		controller.getCurrentPlayer().addCardToPlayer(new Card("test1"));
		controller.getCurrentPlayer().addCardToPlayer(new Card("test2"));

		try {
			controller.spendCards(new Card("test1"), new Card("test 2"), new Card("test 3"));
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Not enough cards in deck");
		}
	}

	@Test
	public void spendCards_deck_matchingtypes() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Infantry";
		Card test2 = new Card("test2");
		test2.troopType = "Infantry";
		Card test3 = new Card("test3");
		test3.troopType = "Infantry";
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);

		controller.spendCards(test1, test2, test3);
		assertEquals(controller.getCurrentPlayer().getDeck().size(), 0);

	}

	@Test
	public void spendCards_deck_nonmatchingtypes() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Infantry";
		Card test2 = new Card("test2");
		test2.troopType = "not infantry";
		Card test3 = new Card("test3");
		test3.troopType = "Infantry";
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);
		try {
			controller.spendCards(test1, test2, test3);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Cards don't match");
		}
		assertEquals(controller.getCurrentPlayer().getDeck().size(), 3);

	}

	@Test
	public void spendCards_deck_nonmatchingtypes_morepaths() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Infantry";
		Card test2 = new Card("test2");
		test2.troopType = "Infantry";
		Card test3 = new Card("test3");
		test3.troopType = "Cavalry";
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);
		try {
			controller.spendCards(test1, test2, test3);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Cards don't match");
		}
		assertEquals(controller.getCurrentPlayer().getDeck().size(), 3);

	}

	@Test
	public void spendCards_deck_nonmatchingtypes_morepaths2() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Infantry5454";
		Card test2 = new Card("test2");
		test2.troopType = "Infantry";
		Card test3 = new Card("test3");
		test3.troopType = "Infantry";
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);
		try {
			controller.spendCards(test1, test2, test3);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Cards don't match");
		}
		assertEquals(controller.getCurrentPlayer().getDeck().size(), 3);

	}

	@Test
	public void spendCards_deck_nonmatchingtypes_variables() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Infantry";
		Card test2 = new Card("test2");
		test2.troopType = "Infantry";
		Card test3 = new Card("test3");

		Card test4 = new Card("test4");

		test3.troopType = "Infantry";
		player.addCardToPlayer(test4);
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);
		try {
			controller.spendCards(test1, test2, test3);
			assertTrue(true);
		} catch (Exception e) {
			fail("shouldn't reach this point");
		}
		assertEquals(controller.getCurrentPlayer().getDeck().size(), 1);

	}

	@Test
	public void spendCards_deck_wildcard() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Infantry";
		Card test2 = new Card("test2");
		test2.troopType = "Wildcard";
		Card test3 = new Card("test3");
		test3.troopType = "Infantry";
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);

		controller.spendCards(test1, test2, test3);

		assertEquals(controller.getCurrentPlayer().getDeck().size(), 0);

	}

	@Test
	public void spendCards_deck_wildcard2() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Wildcard";
		Card test2 = new Card("test2");
		test2.troopType = "Infantry";
		Card test3 = new Card("test3");
		test3.troopType = "Infantry";
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);

		controller.spendCards(test1, test2, test3);

		assertEquals(controller.getCurrentPlayer().getDeck().size(), 0);

	}

	@Test
	public void spendCards_deck_wildcard3() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Infantry";
		Card test2 = new Card("test2");
		test2.troopType = "Infantry";
		Card test3 = new Card("test3");
		test3.troopType = "Wildcard";
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);

		controller.spendCards(test1, test2, test3);

		assertEquals(controller.getCurrentPlayer().getDeck().size(), 0);

	}

	@Test
	public void spendCards_deck_wildcard4() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Cavalry";
		Card test2 = new Card("test2");
		test2.troopType = "Wildcard";
		Card test3 = new Card("test3");
		test3.troopType = "Infantry";
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);

		try {
			controller.spendCards(test1, test2, test3);
			assertEquals(0, player.getDeck().size());
		} catch (Exception e) {
			fail("shouldn't reach this point");
		}

	}

	@Test
	public void spendCards_deck_wildcard5() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Wildcard";
		Card test2 = new Card("test2");
		test2.troopType = "Cavalry";
		Card test3 = new Card("test3");
		test3.troopType = "Infantry";
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);
		
		try {
			controller.spendCards(test1, test2, test3);
			assertEquals(0, player.getDeck().size());
		} catch (Exception e) {
			fail("shouldn't reach this point");
		}


	}

	@Test
	public void spendCards_deck_wildcard6() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Infantry";
		Card test2 = new Card("test2");
		test2.troopType = "Cavalry";
		Card test3 = new Card("test3");
		test3.troopType = "Wildcard";
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);

		try {
			controller.spendCards(test1, test2, test3);
			assertEquals(0, player.getDeck().size());
		} catch (Exception e) {
			fail("shouldn't reach this point");
		}

	}
	
	
	@Test
	public void spendCards_deck_combo1() {
		PlayerController controller = new PlayerController();
		Player player = new Player();
		controller.addPlayer(player);
		Card test1 = new Card("test1");
		test1.troopType = "Infantry";
		Card test2 = new Card("test2");
		test2.troopType = "Cavalry";
		Card test3 = new Card("test3");
		test3.troopType = "Wildcard";
		player.addCardToPlayer(test1);
		player.addCardToPlayer(test2);
		player.addCardToPlayer(test3);
		assertEquals(controller.getCurrentPlayer().getDeck().size(), 3);

		controller.spendCards(test1, test2, test3);


		assertEquals(controller.getCurrentPlayer().getDeck().size(), 0);


	}
	
	
	
	@Test
	public void getCurrentPlayerCards_test() {
		Player player = new Player(1);
		Card card1 = new Card("test1");
		Card card2 = new Card("test2");
		Card card3 = new Card("test3");
		Card card4 = new Card("test4");
		Card card5 = new Card("test5");
		Card card6 = new Card("test6");

		player.addCardToPlayer(card1);
		player.addCardToPlayer(card2);
		player.addCardToPlayer(card3);
		player.addCardToPlayer(card4);
		player.addCardToPlayer(card5);
		player.addCardToPlayer(card6);

		PlayerController controller = new PlayerController();
		controller.addPlayer(player);

		ArrayList<Card> testdeck = controller.getCurrentPlayerCards();

		assertEquals(testdeck.size(), 6);
		assertEquals(testdeck.get(0), card1);
		assertEquals(testdeck.get(1), card2);
		assertEquals(testdeck.get(2), card3);
		assertEquals(testdeck.get(3), card4);
		assertEquals(testdeck.get(4), card5);
		assertEquals(testdeck.get(5), card6);

	}

	@Test
	public void addTooManyArmiesTest() {
		Player p = new Player();

		try {
			p.addPlayerArmies(100);
			fail();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void addNegativeArmiesTest() {
		Player p = new Player();

		try {
			p.addPlayerArmies(-100);
			fail();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void removeTooManyArmiesTest() {
		Player p = new Player();

		try {
			p.addPlayerArmies(10);
			p.removePlayerArmies(100);
			fail();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void removeNegativeArmiesTest() {
		Player p = new Player();

		try {
			p.removePlayerArmies(-100);
			fail();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}

	}

	@Test
	public void getPlayerTest() {
		PlayerController pc = new PlayerController();
		Player p1 = new Player(1);
		Player p2 = new Player(2);
		pc.addPlayer(p1);
		pc.addPlayer(p2);

		assertEquals(p1, pc.getPlayer(1));
		assertEquals(p2, pc.getPlayer(2));

		try {
			pc.getPlayer(3);
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void getPlayersTest() {
		PlayerController pc = new PlayerController();
		Player p1 = new Player(1);
		Player p2 = new Player(2);
		Player p3 = new Player(3);

		assertEquals(0, pc.getNumberOfPlayers());

		pc.addPlayer(p1);

		assertEquals(1, pc.getNumberOfPlayers());

		pc.addPlayer(p2);

		assertEquals(2, pc.getNumberOfPlayers());

		pc.addPlayer(p3);

		assertEquals(3, pc.getNumberOfPlayers());

	}

}
