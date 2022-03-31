package Risk.controller;

import Risk.model.Card;
import Risk.model.Deck;
import Risk.model.Player;
import Risk.model.Territory;
import Risk.view.GraphicalUserInterface;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

public class GameFlowControllerTest {

	Locale locale = new Locale("en", "US");
	ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", locale);

	@Test
	public void initTurnGetPhaseTest() {
		PlayerController pc = new PlayerController();
		GameBoardController gb = new GameBoardController();
		PhaseController phc = new PhaseController(pc, gb);
		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);

		assertEquals(gfc.getPhase(), "setup");
	}

//	@Test
//	public void assignmentPhaseGetPhaseTest() {
//		PlayerController playercontroller = EasyMock.strictMock(PlayerController.class);
//		PhaseController phaseController = EasyMock.strictMock(PhaseController.class);
//		GameBoardController gbcontroller = EasyMock.strictMock(GameBoardController.class);
//		Player player = EasyMock.strictMock(Player.class);
//		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
//		gui.testMode = true;
//		GameFlowController gfc = EasyMock.partialMockBuilder(GameFlowController.class)
//				.addMockedMethod("updateCardsOnGui").withConstructor(phaseController, playercontroller, gbcontroller,
//						new AttackerDefenderController(), gui, msg)
//				.createMock();
//		gfc.updateCardsOnGui();
//		playercontroller.addNewArmiestoPlayer();
//		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(player);
//		EasyMock.expect(player.getId()).andReturn(1);
//		EasyMock.expect(gbcontroller.getNewContinentPlayerArmies(1)).andReturn(1);
//		EasyMock.expect(phaseController.getUpdateCards()).andReturn(false);
//		phaseController.next_phase();
//		EasyMock.expect(phaseController.getUpdateCards()).andReturn(true);
//
//		playercontroller.addArmiesToCurrentPlayer(1);
//		gbcontroller.updateGameBoard();
//
//		EasyMock.replay(gbcontroller);
//		EasyMock.replay(player);
//		EasyMock.replay(playercontroller);
//		EasyMock.replay(gfc);
//
//		gfc.next_phase();
//
//		assertEquals(gfc.getPhase(), "assignment");
//		EasyMock.verify(gbcontroller);
//
//		EasyMock.verify(phaseController);
//		EasyMock.verify(playercontroller);
//		EasyMock.verify(player);
//		EasyMock.verify(gfc);
//	}
//
//	@Test
//	public void nextPhaseAssignmentPhaseTest() {
//		PlayerController playercontroller = EasyMock.strictMock(PlayerController.class);
//		Player p1 = new Player(1);
//
//		EasyMock.expect(playercontroller.getInit()).andReturn(true);
//		EasyMock.expect(playercontroller.getInit()).andReturn(false);
//
//		GameFlowController gfc = EasyMock.partialMockBuilder(GameFlowController.class)
//				.addMockedMethod("assignment_phase").withConstructor(playercontroller, new GameBoardController(),
//						new AttackerDefenderController(), new GraphicalUserInterface(msg), msg)
//				.createMock();
//		gfc.assignment_phase();
//
//		EasyMock.replay(playercontroller);
//		EasyMock.replay(gfc);
//
//		gfc.init_turn();
//		try {
//			gfc.next_phase();
//			fail("shouldn't reach this point");
//
//		} catch (Exception e) {
//			assertEquals(e.getMessage(), "Init Phase isn't over");
//		}
//		assertEquals(gfc.getPhase(), "setup");
//		gfc.next_phase();
//
//		EasyMock.verify(gfc);
//		EasyMock.verify(playercontroller);
//	}
//
//	@Test
//	public void nextPhaseCardPhaseTestUnplacedarmies() {
//		PlayerController playercontroller = EasyMock.strictMock(PlayerController.class);
//		GameBoardController gbcontroller = EasyMock.strictMock(GameBoardController.class);
//		Player player = EasyMock.strictMock(Player.class);
//		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
//		gui.testMode = true;
//		GameFlowController gfc = EasyMock.partialMockBuilder(GameFlowController.class).addMockedMethod("attack_phase")
//				.addMockedMethod("updateCardsOnGui").withConstructor(playercontroller, gbcontroller,
//						new AttackerDefenderController(), gui, msg)
//				.createMock();
//		gfc.updateCardsOnGui();
//
//		playercontroller.addNewArmiestoPlayer();
//
//		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(player);
//		EasyMock.expect(player.getId()).andReturn(1);
//		EasyMock.expect(gbcontroller.getNewContinentPlayerArmies(1)).andReturn(1);
//
//		playercontroller.addArmiesToCurrentPlayer(1);
//		gbcontroller.updateGameBoard();
//
//		EasyMock.expect(playercontroller.playerDonePlacingNew()).andReturn(false);
//		EasyMock.expect(playercontroller.playerDoneWithCards()).andReturn(true);
//		EasyMock.expect(playercontroller.playerDonePlacingNew()).andReturn(true);
//
//		EasyMock.expect(playercontroller.playerDoneWithCards()).andReturn(true);
//		gfc.updateCardsOnGui();
//
//		gfc.attack_phase();
//		gfc.updateCardsOnGui();
//
//		EasyMock.replay(gbcontroller);
//		EasyMock.replay(player);
//
//		EasyMock.replay(playercontroller);
//		EasyMock.replay(gfc);
//
//		gfc.assignment_phase();
//		try {
//			gfc.next_phase();
//			fail("shouldn't reach this point");
//
//		} catch (Exception e) {
//			assertEquals(e.getMessage(), "Player has unplaced armies");
//		}
//		assertEquals(gfc.getPhase(), "assignment");
//		gfc.next_phase();
//
//		EasyMock.verify(gfc);
//		EasyMock.verify(playercontroller);
//		EasyMock.verify(gbcontroller);
//		EasyMock.verify(player);
//	}
//
//	@Test
//	public void nextPhaseCardPhaseTestUnplayedCards() {
//		PlayerController playercontroller = EasyMock.strictMock(PlayerController.class);
//		GameBoardController gbcontroller = EasyMock.strictMock(GameBoardController.class);
//		Player player = EasyMock.strictMock(Player.class);
//
//		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
//		gui.testMode = true;
//		GameFlowController gfc = EasyMock.partialMockBuilder(GameFlowController.class).addMockedMethod("attack_phase")
//				.addMockedMethod("updateCardsOnGui").withConstructor(playercontroller, gbcontroller,
//						new AttackerDefenderController(), gui, msg)
//				.createMock();
//		gfc.updateCardsOnGui();
//
//		playercontroller.addNewArmiestoPlayer();
//		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(player);
//		EasyMock.expect(player.getId()).andReturn(1);
//		EasyMock.expect(gbcontroller.getNewContinentPlayerArmies(1)).andReturn(1);
//
//		playercontroller.addArmiesToCurrentPlayer(1);
//		gbcontroller.updateGameBoard();
//
//		EasyMock.expect(playercontroller.playerDonePlacingNew()).andReturn(true);
//		EasyMock.expect(playercontroller.playerDoneWithCards()).andReturn(false);
//		EasyMock.expect(playercontroller.playerDonePlacingNew()).andReturn(true);
//		EasyMock.expect(playercontroller.playerDoneWithCards()).andReturn(true);
//		gfc.updateCardsOnGui();
//		gfc.updateCardsOnGui();
//
//		gfc.attack_phase();
//		EasyMock.replay(gbcontroller);
//		EasyMock.replay(player);
//		EasyMock.replay(playercontroller);
//		EasyMock.replay(gfc);
//
//		gfc.assignment_phase();
//		try {
//			gfc.next_phase();
//			fail("shouldn't reach this point");
//
//		} catch (Exception e) {
//			assertEquals(e.getMessage(), "Player has too many cards in hand");
//		}
//		assertEquals(gfc.getPhase(), "assignment");
//		gfc.next_phase();
//
//		EasyMock.verify(gfc);
//		EasyMock.verify(playercontroller);
//		EasyMock.verify(gbcontroller);
//		EasyMock.verify(player);
//	}
//
//	@Test
//	public void attackPhaseGetPhaseTest() {
//		PlayerController pc = EasyMock.strictMock(PlayerController.class);
//		GameBoardController gbc = new GameBoardController();
//
//		EasyMock.replay(pc);
//
//		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
//		gui.testMode = true;
//		PhaseController phc = new PhaseController(pc, gbc);
//
//		GameFlowController gfc = new GameFlowController(phc, pc, gbc,
//				new AttackerDefenderController(), gui, msg);
//
//
//		phc.setPhase("attack");
//
//		assertEquals("attack", gfc.getPhase());
//
//		EasyMock.verify(pc);
//	}
//	@Test
//	public void fortifyPhaseGetPhaseTest() {
//		PlayerController playercontroller = EasyMock.strictMock(PlayerController.class);
//		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(new Player(1));
//		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(new Player(1));
//
//		EasyMock.replay(playercontroller);
//		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
//		gui.testMode = true;
//
//		GameFlowController gfc = new GameFlowController(playercontroller, new GameBoardController(),
//				new AttackerDefenderController(), gui, msg);
//
//		gfc.fortify_phase();
//
//		assertEquals(gfc.getPhase(), "fortify");
//
//		EasyMock.verify(playercontroller);
//	}

	@Test
	public void testInitiateCombat() {
		PlayerController pc = EasyMock.strictMock(PlayerController.class);
		AttackerDefenderController adc = new AttackerDefenderController();
		GameBoardController gbc = EasyMock.mock(GameBoardController.class);
		Random rand = EasyMock.strictMock(Random.class);
		PhaseController phc = new PhaseController(pc, gbc);
		GameFlowController gfc = new GameFlowController(phc, pc, gbc, adc, new GraphicalUserInterface(msg), msg);

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
		gfc.initiateCombat("test1", "test2", rand, 1, 1);

		EasyMock.verify(pc, gbc, rand);
	}

	@Test
	public void testInitiateCombatTerritoryLost() {
		PlayerController pc = new PlayerController();
		AttackerDefenderController adc = new AttackerDefenderController();
		GameBoardController gbc = EasyMock.mock(GameBoardController.class);
		Random rand = EasyMock.strictMock(Random.class);
		PhaseController phc = new PhaseController(pc, gbc);
		GameFlowController gfc = new GameFlowController(phc, pc, gbc, adc, new GraphicalUserInterface(msg), msg);

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
		gfc.initiateCombat("test1", "test2", rand, 1, 1);

		assertEquals(1, test2.getPlayer());
		EasyMock.verify(gbc, rand);
	}

	@Test
	public void testInitiateCombat_notattackphase() {
		PlayerController pc = new PlayerController();
		GameBoardController gb = new GameBoardController();
		PhaseController phc = new PhaseController(pc, gb);
		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);

		try {
			gfc.initiateCombat("test", "test2", gfc.rand, 0, 0);
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
		GameFlowController gfc = new GameFlowController(phc, pc, gbc, adc, new GraphicalUserInterface(msg), msg);
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
				new GraphicalUserInterface(msg), msg);

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
				new GraphicalUserInterface(msg), msg);

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
				new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);

		gfc.verifyAdjacent("Indiana", "Ohio");

		EasyMock.verify(gameBoard);
	}

//	@Test
//	public void testAddCardFromTerritoryCapture() {
//		PlayerController pc = new PlayerController();
//		AttackerDefenderController adc = EasyMock.mock(AttackerDefenderController.class);
//		GameBoardController gbc = new GameBoardController();
//		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
//		PhaseController phc = new PhaseController(pc, gbc);
//		gui.testMode = true;
//		GameFlowController gfc = new GameFlowController(phc, pc, gbc, adc, gui, msg);
//		gbc.initGame();
//		Territory test = new Territory("test");
//
//		Player p1 = new Player(1);
//		pc.addPlayer(p1);
//		p1.caughtTerritory(true);
//		EasyMock.replay(adc);
//
//
//		pc.addPlayer(p1);
//		gfc.fortify_phase();
//
//		assertEquals(1, p1.getDeck().size());
//		assertFalse(p1.hasCaughtTerritory());
//
//		EasyMock.verify(adc);
//	}

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
				new GraphicalUserInterface(msg), msg);
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
		EasyMock.expect(gameBoard.checkOwnedTerritory(card1.territory, card2.territory, card3.territory, 1))
				.andReturn(true);
		playercontroller.addArmiesToCurrentPlayer(2);
		EasyMock.expect(playercontroller.getCurrentPlayer()).andReturn(player);
		gameBoard.incrementTradeCounter();
		EasyMock.replay(gameBoard, playercontroller, player);

		GameFlowController gfc = new GameFlowController(phc, playercontroller, gameBoard, new AttackerDefenderController(),
				new GraphicalUserInterface(msg), msg);
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
				new GraphicalUserInterface(msg), msg);
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
				new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);
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
				new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);

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
				new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);
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
				new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);
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
				new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);

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
				new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);

		Card card = new Card("Russia", "Infantry");

		assertTrue(gfc.convertCardForGui(card).equals("<html>Russia<br>Infantry<html/>"));
	}
//
//	@Test
//	public void initiateCombatPaintTest_shouldwin() {
//		PlayerController pc = EasyMock.strictMock(PlayerController.class);
//		AttackerDefenderController adc = new AttackerDefenderController();
//		GameBoardController gbc = EasyMock.mock(GameBoardController.class);
//		Random rand = EasyMock.strictMock(Random.class);
//		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
//		gui.testMode = true;
//		GameFlowController gfc = new GameFlowController(pc, gbc, adc, gui, msg);
//
//		Territory test1 = new Territory("test1");
//		Territory test2 = new Territory("test2");
//		test2.setPlayer(2);
//		Player player = new Player(1);
//		Player player2 = new Player(2);
//
//		pc.addPlayer(player);
//		pc.addPlayer(player2);
//
//		test1.setPlayer(1);
//		test1.setArmyCount(30);
//		test2.setArmyCount(30);
//
//		EasyMock.expect(gbc.getTerritoryOwner("test2")).andReturn(2);
//		EasyMock.expect(gbc.getTerritory("test1")).andReturn(test1);
//		EasyMock.expect(gbc.getTerritory("test2")).andReturn(test2);
//		EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//		EasyMock.expect(pc.getPlayer(2)).andReturn(player2);
//		EasyMock.expect(rand.nextInt(6)).andReturn(5);
//		EasyMock.expect(rand.nextInt(6)).andReturn(4);
//		pc.playerLoss();
//		EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//		EasyMock.expect(pc.getNumberOfPlayers()).andReturn(1);
//
//		EasyMock.replay(pc, gbc, rand);
//
//		pc.addPlayer(player);
//		pc.addPlayer(player2);
//
//		gfc.phase = "attack";
//		gfc.initiateCombat("test1", "test2", rand, 1, 1);
//
//		EasyMock.verify(pc, gbc, rand);
//	}
//
//	@Test
//	public void attackPhaseFortifyPhaseTest() {
//		GameFlowController gfc = EasyMock.partialMockBuilder(GameFlowController.class).addMockedMethod("fortify_phase")
//				.withConstructor(new PlayerController(), new GameBoardController(), new AttackerDefenderController(),
//						new GraphicalUserInterface(msg), msg)
//				.createMock();
//		gfc.fortify_phase();
//
//		EasyMock.replay(gfc);
//		gfc.setPhase("attack");
//		assertEquals(gfc.getPhase(), "attack");
//		System.out.println(gfc.getPhase());
//
//		gfc.next_phase();
//
//		EasyMock.verify(gfc);
//	}
//
//	@Test
//	public void fortifyPhaseAssignmentPhaseTest() {
//		PlayerController playercontroller = EasyMock.strictMock(PlayerController.class);
//
//		GameFlowController gfc = EasyMock.partialMockBuilder(GameFlowController.class)
//				.addMockedMethod("assignment_phase").withConstructor(playercontroller, new GameBoardController(),
//						new AttackerDefenderController(), new GraphicalUserInterface(msg), msg)
//				.createMock();
//		playercontroller.nextPlayer();
//		gfc.assignment_phase();
//
//		EasyMock.replay(gfc);
//		EasyMock.replay(playercontroller);
//
//		gfc.setPhase("fortify");
//		assertEquals(gfc.getPhase(), "fortify");
//		gfc.next_phase();
//
//		EasyMock.verify(gfc);
//		EasyMock.verify(playercontroller);
//	}
//
	@Test
	public void verifyAdjacenttest_null() {
		GameBoardController gb = EasyMock.mock(GameBoardController.class);

		PlayerController pc = new PlayerController();
		PhaseController phc = new PhaseController(pc, gb);

		GameFlowController gfc = new GameFlowController(phc, pc, gb,
				new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);
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
				new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);
		EasyMock.expect(gb.isAdjacent("North_America", "South_America")).andReturn(true);
		EasyMock.replay(gb);

		assertEquals(true, gfc.verifyAdjacent("North_America", "South_America"));

		EasyMock.verify(gb);
	}
}
