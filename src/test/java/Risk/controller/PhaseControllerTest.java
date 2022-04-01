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
import java.util.ResourceBundle;

import static org.junit.Assert.*;

public class PhaseControllerTest {

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

	@Test
	public void assignmentPhaseGetPhaseTest() {
        PlayerController pc = EasyMock.mock(PlayerController.class);
        GameBoardController gb = new GameBoardController();
        PhaseController phc = new PhaseController(pc, gb);
        Player player = EasyMock.mock(Player.class);
        GraphicalUserInterface gui = new GraphicalUserInterface(msg);
		gui.testMode = true;
        GameFlowController gfc = new GameFlowController(phc, pc, gb,
                new AttackerDefenderController(), gui, msg);

        pc.addNewArmiestoPlayer();
        EasyMock.expectLastCall();
        EasyMock.expect(pc.getInit()).andReturn(false);
        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.getId()).andReturn(1);
        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
        pc.addArmiesToCurrentPlayer(0);

        assertEquals("setup", gfc.getPhase());

        EasyMock.replay(pc);
        EasyMock.replay(player);
        gfc.next_phase();


        assertEquals("assignment", gfc.getPhase());

        EasyMock.verify(pc);
        EasyMock.verify(player);
	}

	@Test
	public void nextPhaseAssignmentPhaseTest() {
        PlayerController pc = new PlayerController();
        GameBoardController gb = new GameBoardController();
        PhaseController phc = new PhaseController(pc, gb);
        GameFlowController gfc = new GameFlowController(phc, pc, gb,
                new AttackerDefenderController(), new GraphicalUserInterface(msg), msg);

        assertEquals(gfc.getPhase(), "setup");

		try {
			gfc.next_phase();
			fail("shouldn't reach this point");

		} catch (Exception e) {
			assertEquals("Init Phase isn't over", e.getMessage());
		}
		assertEquals("setup", gfc.getPhase());
	}


	@Test
	public void nextPhaseCardPhaseTestUnplacedarmies() {
        PlayerController pc = EasyMock.mock(PlayerController.class);
        GameBoardController gb = new GameBoardController();
        PhaseController phc = new PhaseController(pc, gb);
        Player player = EasyMock.mock(Player.class);
        GraphicalUserInterface gui = new GraphicalUserInterface(msg);
        gui.testMode = true;
        GameFlowController gfc = new GameFlowController(phc, pc, gb,
                new AttackerDefenderController(), gui, msg);

        pc.addNewArmiestoPlayer();
        EasyMock.expectLastCall();
        EasyMock.expect(pc.getInit()).andReturn(false);
        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
        EasyMock.expect(player.getId()).andReturn(1);
        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
        pc.addArmiesToCurrentPlayer(0);

        EasyMock.expect(pc.playerDonePlacingNew()).andReturn(false);
        EasyMock.expect(pc.playerDoneWithCards()).andReturn(false);

        assertEquals("setup", gfc.getPhase());

        EasyMock.replay(pc);
        EasyMock.replay(player);
        gfc.next_phase();

        assertEquals("assignment", gfc.getPhase());
//        EasyMock.replay(pc);
//        EasyMock.replay(player);

		try {
			gfc.next_phase();
			fail("shouldn't reach this point");

		} catch (Exception e) {
			assertEquals(e.getMessage(), "Player has unplaced armies");
		}

        EasyMock.verify(pc);
        EasyMock.verify(player);
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
	}

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
}
