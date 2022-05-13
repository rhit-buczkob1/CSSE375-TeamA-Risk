package Risk.controller;

import Risk.model.Card;
import Risk.model.Deck;
import Risk.model.Player;
import Risk.model.Territory;
import Risk.view.GraphicalUserInterface;
import javafx.stage.Stage;

import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.*;

public class GameFlowControllerTest extends ApplicationTest {

	Locale locale = new Locale("en", "US");
	ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", locale);

	Random rand = EasyMock.strictMock(Random.class);
	GraphicalUserInterface gui;
	
    @BeforeClass
    public static void headless() {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("testfx.setup.timeout", "2500");
    }
	
	@Override
	public void start(Stage stage) throws Exception {
        gui = new GraphicalUserInterface(msg, stage);
	}

	@Test
	public void testInitiateCombat() {
		PlayerController pc = EasyMock.mock(PlayerController.class);
		AttackerDefenderController adc = new AttackerDefenderController();
		GameBoardController gbc = EasyMock.mock(GameBoardController.class);
		Random rand = EasyMock.strictMock(Random.class);
		PhaseController phc = new PhaseController(pc, gbc);
		GameFlowController gfc = new GameFlowController(phc, pc, gbc, adc, gui, msg);
		
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		test2.setPlayer(2);
		Player player = new Player(1);
		Player player2 = new Player(2);

		pc.addPlayer(player);
		pc.addPlayer(player2);

		test1.setPlayer(1);
		test1.setArmyCount(30);
		test2.setArmyCount(30);
		
		EasyMock.expect(gbc.getTerritoryOwner("test2")).andReturn(2);
		EasyMock.expect(gbc.getTerritory("test1")).andReturn(test1);
		EasyMock.expect(gbc.getTerritory("test2")).andReturn(test2);
		EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
		EasyMock.expect(pc.getPlayer(2)).andReturn(player2);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		pc.playerLoss();
		EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
		EasyMock.expect(pc.getNumberOfPlayers()).andReturn(2);

		EasyMock.replay(pc, gbc, rand);

		pc.addPlayer(player);
		pc.addPlayer(player2);

		phc.setPhase("attack");
		gfc.initiateCombat("test1", "test2");
		gfc.randomizeCombat( rand, 1, 1);
		gfc.finishCombat();

		EasyMock.verify(pc, gbc, rand);
	}

	@Test
	public void testInitiateCombatTerritoryLost() {
		PlayerController pc = new PlayerController();
		AttackerDefenderController adc = new AttackerDefenderController();
		GameBoardController gbc = EasyMock.mock(GameBoardController.class);
		Random rand = EasyMock.strictMock(Random.class);
		PhaseController phc = new PhaseController(pc, gbc);
		GameFlowController gfc = new GameFlowController(phc, pc, gbc, adc, gui, msg);

		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		test2.setPlayer(2);
		Player player = new Player(1);
		Player player2 = new Player(2);

		pc.addPlayer(player);
		pc.addPlayer(player2);

		test1.setPlayer(1);
		test1.setArmyCount(30);
		test2.setArmyCount(1);
		test2.setPlayer(2);
		player.addTerritory();
		player2.addTerritory();

		EasyMock.expect(gbc.getTerritoryOwner("test2")).andReturn(2);
		EasyMock.expect(gbc.getTerritory("test1")).andReturn(test1);
		EasyMock.expect(gbc.getTerritory("test2")).andReturn(test2);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.replay(gbc, rand);

		pc.addPlayer(player);
		pc.addPlayer(player2);

		phc.setPhase("attack");
		gfc.initiateCombat("test1", "test2");
		gfc.randomizeCombat(rand, 1, 1);
		gfc.finishCombat();

		assertEquals(1, test2.getPlayer());
		EasyMock.verify(gbc, rand);
	}

	@Test
	public void testInitiateCombat_notattackphase() {
		PlayerController pc = new PlayerController();
		GameBoardController gb = new GameBoardController();
		PhaseController phc = new PhaseController(pc, gb);
		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), gui, msg);

		try {
			gfc.initiateCombat("test", "test2");
			gfc.randomizeCombat( gfc.rand, 0, 0);
			gfc.finishCombat();

			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "It's not Attack Phase");
		}
	}

	@Test
	public void testAddInfantrytoTerritoryfromString() {
		PlayerController pc = EasyMock.mock(PlayerController.class);
		AttackerDefenderController adc = EasyMock.mock(AttackerDefenderController.class);
		GameBoardController gbc = EasyMock.mock(GameBoardController.class);
		PhaseController phc = new PhaseController(pc, gbc);
		GameFlowController gfc = new GameFlowController(phc, pc, gbc, adc, gui, msg);
		Territory test = new Territory("test");
		EasyMock.expect(gbc.getTerritory("test")).andReturn(test);
		pc.addInfantryToTerritory(test, 1);

		EasyMock.replay(pc);
		EasyMock.replay(adc);
		EasyMock.replay(gbc);
		gfc.addInfantrytoTerritoryfromString("test");
		EasyMock.verify(pc);
		EasyMock.verify(adc);
		EasyMock.verify(gbc);
	}

	@Test
	public void verifyOwnershipIsValid() {
		PlayerController playercontroller = EasyMock.strictMock(PlayerController.class);
		GameBoardController gameBoard = EasyMock.strictMock(GameBoardController.class);
		PhaseController phc = new PhaseController(playercontroller, gameBoard);
		Player player = EasyMock.strictMock(Player.class);
		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(player);
		EasyMock.expect(player.getId()).andReturn(1);
		EasyMock.expect(gameBoard.getTerritoryOwner("Indiana")).andReturn(1);

		EasyMock.replay(playercontroller, gameBoard, player);

		GameFlowController gfc = new GameFlowController(phc, playercontroller, gameBoard, new AttackerDefenderController(),
				gui, msg);

		if (!gfc.verifyOwnership("Indiana")) {
			fail();
		}

		EasyMock.verify(playercontroller, gameBoard, player);
	}

	@Test
	public void verifyOwnershipIsInvalid() {
		PlayerController playercontroller = EasyMock.strictMock(PlayerController.class);
		GameBoardController gameBoard = EasyMock.strictMock(GameBoardController.class);
		PhaseController phc = new PhaseController(playercontroller, gameBoard);
		Player player = EasyMock.strictMock(Player.class);
		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(player);
		EasyMock.expect(player.getId()).andReturn(0);
		EasyMock.expect(gameBoard.getTerritoryOwner("Indiana")).andReturn(2);

		EasyMock.replay(playercontroller, gameBoard, player);
		GameFlowController gfc = new GameFlowController(phc, playercontroller, gameBoard, new AttackerDefenderController(),
				gui, msg);

		if (gfc.verifyOwnership("Indiana")) {
			fail();
		}

		EasyMock.verify(playercontroller, gameBoard, player);
	}

	@Test
	public void verifyAdjacent() {
		GameBoardController gameBoard = EasyMock.strictMock(GameBoardController.class);
		PlayerController pc = new PlayerController();
		PhaseController phc = new PhaseController(pc, gameBoard);

		EasyMock.expect(gameBoard.isAdjacent("Indiana", "Ohio")).andReturn(true);

		EasyMock.replay(gameBoard);

		GameFlowController gfc = new GameFlowController(phc, pc, gameBoard,
				new AttackerDefenderController(), gui, msg);

		gfc.verifyAdjacent("Indiana", "Ohio");

		EasyMock.verify(gameBoard);
	}

	@Test
	public void turnInCardsTest_valid() {
		Card card1 = new Card("", "Infantry");
		Card card2 = new Card("", "Infantry");
		Card card3 = new Card("", "Infantry");
		GameBoardController gameBoard = EasyMock.strictMock(GameBoardController.class);
		PlayerController playercontroller = EasyMock.strictMock(PlayerController.class);
		PhaseController phc = new PhaseController(playercontroller, gameBoard);
		Player player = EasyMock.strictMock(Player.class);

		playercontroller.spendCards(card1, card2, card3);
		EasyMock.expect(gameBoard.getTradeCounter()).andReturn(4);
		playercontroller.addArmiesToCurrentPlayer(4);
		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(player);
		EasyMock.expect(player.getId()).andReturn(1);
		EasyMock.expect(gameBoard.checkOwnedTerritory("", "", "", 1)).andReturn(false);
		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(player);

		gameBoard.incrementTradeCounter();
		EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
		EasyMock.replay(gameBoard, playercontroller, player);

		GameFlowController gfc = new GameFlowController(phc, playercontroller, gameBoard, new AttackerDefenderController(),
				gui, msg);
		gfc.turnInCards(card1, card2, card3);
		EasyMock.verify(playercontroller, gameBoard);
	}

	@Test
	public void turnInCardsTest_valid_w_ownedterritory() {
		Card card1 = new Card("Peru", "Infantry");
		Card card2 = new Card("Argentina", "Infantry");
		Card card3 = new Card("Russia", "Infantry");
		GameBoardController gameBoard = EasyMock.strictMock(GameBoardController.class);
		PlayerController playercontroller = EasyMock.strictMock(PlayerController.class);
		PhaseController phc = new PhaseController(playercontroller, gameBoard);
		Player player = EasyMock.strictMock(Player.class);

		ArrayList<Card> deck = new ArrayList<>();
		deck.add(card1);
		deck.add(card2);
		deck.add(card3);
		playercontroller.spendCards(card1, card2, card3);
		EasyMock.expect(gameBoard.getTradeCounter()).andReturn(4);
		playercontroller.addArmiesToCurrentPlayer(4);
		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(player);
		EasyMock.expect(player.getId()).andReturn(1);
		EasyMock.expect(player.getDeck()).andReturn(deck);
		EasyMock.expect(gameBoard.checkOwnedTerritory(card1.getTerritory(), card2.getTerritory(), card3.getTerritory(), 1))
				.andReturn(true);
		playercontroller.addArmiesToCurrentPlayer(2);
		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(player);
		gameBoard.incrementTradeCounter();
		EasyMock.replay(gameBoard, playercontroller, player);

		GameFlowController gfc = new GameFlowController(phc, playercontroller, gameBoard, new AttackerDefenderController(),
				gui, msg);
		gfc.turnInCards(card1, card2, card3);
		EasyMock.verify(playercontroller, gameBoard, player);
	}

	@Test
	public void turnInCardsTest_invalid() {
		Card card1 = new Card("Infantry");
		Card card2 = new Card("Cavalry");
		Card card3 = new Card("Infantry");
		GameBoardController gameBoard = EasyMock.strictMock(GameBoardController.class);
		PlayerController playercontroller = EasyMock.strictMock(PlayerController.class);
		PhaseController phc = new PhaseController(playercontroller, gameBoard);

		playercontroller.spendCards(card1, card2, card3);
		EasyMock.expectLastCall().andThrow(new IllegalArgumentException("cards don't match"));

		EasyMock.replay(gameBoard, playercontroller);

		GameFlowController gfc = new GameFlowController(phc, playercontroller, gameBoard, new AttackerDefenderController(),
				gui, msg);
		try {
			gfc.turnInCards(card1, card2, card3);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Invalid cards played");
		}
		EasyMock.verify(playercontroller, gameBoard);
	}

	@Test
	public void turnInCardsTest_allsame() {
		Card card1 = new Card("Infantry");

		GameBoardController gb = new GameBoardController();
		PlayerController pc = new PlayerController();
		PhaseController phc = new PhaseController(pc, gb);

		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), gui, msg);
		try {
			gfc.turnInCards(card1, card1, card1);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Cannot play duplicate cards");
		}
	}

	@Test
	public void turnInCardsTest_23_same() {
		Card card1 = new Card("Infantry");
		Card card2 = new Card("cavalry");

		GameBoardController gb = new GameBoardController();
		PlayerController pc = new PlayerController();
		PhaseController phc = new PhaseController(pc, gb);

		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), gui, msg);

		try {
			gfc.turnInCards(card2, card1, card1);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Cannot play duplicate cards");
		}
	}

	@Test
	public void turnInCardsTest_12_same() {
		Card card1 = new Card("Infantry");
		Card card2 = new Card("cavalry");

		GameBoardController gb = new GameBoardController();
		PlayerController pc = new PlayerController();
		PhaseController phc = new PhaseController(pc, gb);

		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), gui, msg);
		try {
			gfc.turnInCards(card1, card1, card2);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Cannot play duplicate cards");
		}
	}

	@Test
	public void turnInCardsTest_13_same() {
		Card card1 = new Card("Infantry");
		Card card2 = new Card("cavalry");

		GameBoardController gb = new GameBoardController();
		PlayerController pc = new PlayerController();
		PhaseController phc = new PhaseController(pc, gb);

		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), gui, msg);
		try {
			gfc.turnInCards(card1, card2, card1);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Cannot play duplicate cards");
		}
	}

	@Test
	public void convertCardForGui_nullcard() {
		GameBoardController gb = new GameBoardController();
		PlayerController pc = new PlayerController();
		PhaseController phc = new PhaseController(pc, gb);

		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), gui, msg);

		try {
			Card card = new Card("", "");
			gfc.convertCardForGui(card);
			fail();
		} catch (MissingResourceException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void convertCardForGui_normal() {
		GameBoardController gb = new GameBoardController();
		PlayerController pc = new PlayerController();
		PhaseController phc = new PhaseController(pc, gb);

		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), gui, msg);

		Card card = new Card("Russia", "Infantry");

		assertTrue(gfc.convertCardForGui(card).equals("<html>Russia<br>Infantry<html/>"));
	}
	
	@Test
	public void verifyAdjacenttest_null() {
		GameBoardController gb = EasyMock.mock(GameBoardController.class);

		PlayerController pc = new PlayerController();
		PhaseController phc = new PhaseController(pc, gb);

		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), gui, msg);
		EasyMock.expect(gb.isAdjacent("", "")).andReturn(false);
		EasyMock.replay(gb);

		assertEquals(false, gfc.verifyAdjacent("", ""));

		EasyMock.verify(gb);
	}

	@Test
	public void verifyAdjacenttest_notnull() {
		GameBoardController gb = EasyMock.mock(GameBoardController.class);

		PlayerController pc = new PlayerController();
		PhaseController phc = new PhaseController(pc, gb);

		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), gui, msg);
		EasyMock.expect(gb.isAdjacent("North_America", "South_America")).andReturn(true);
		EasyMock.replay(gb);

		assertEquals(true, gfc.verifyAdjacent("North_America", "South_America"));

		EasyMock.verify(gb);
	}
}