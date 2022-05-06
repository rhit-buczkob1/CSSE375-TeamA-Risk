//package Risk.controller;
//import Risk.model.Card;
//import Risk.model.Deck;
//import Risk.model.Player;
//import Risk.model.Territory;
//import Risk.view.GraphicalUserInterface;
//import javafx.stage.Stage;
//
//import org.easymock.EasyMock;
//import org.junit.Test;
//import org.testfx.framework.junit.ApplicationTest;
//
//import java.util.ArrayList;
//import java.util.Locale;
//import java.util.ResourceBundle;
//
//import static org.junit.Assert.*;
//
//public class PhaseControllerTest extends ApplicationTest {
//
//    Locale locale = new Locale("en", "US");
//    ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", locale);
//    
//    GraphicalUserInterface gui;
//    
//    @Override
//	public void start(Stage stage) throws Exception {
//		gui = new GraphicalUserInterface(msg, stage);
//	}
//
//	@Test
//    public void initTurnGetPhaseTest() {
//        PlayerController pc = new PlayerController();
//        GameBoardController gb = new GameBoardController();
//        PhaseController phc = new PhaseController(pc, gb);
//        GameFlowController gfc = new GameFlowController(phc, pc, gb,
//                new AttackerDefenderController(), gui, msg);
//
//        assertEquals(gfc.getPhase(), "setup");
//    }
//
//	@Test
//	public void assignmentPhaseGetPhaseTest() {
//        PlayerController pc = EasyMock.mock(PlayerController.class);
//        GameBoardController gb = new GameBoardController();
//        PhaseController phc = new PhaseController(pc, gb);
//        Player player = EasyMock.mock(Player.class);
//		gui.testMode = true;
//        GameFlowController gfc = new GameFlowController(phc, pc, gb,
//                new AttackerDefenderController(), gui, msg);
//
//        pc.addNewArmiestoPlayer();
//        EasyMock.expectLastCall();
//        EasyMock.expect(pc.getInit()).andReturn(false);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(player.getId()).andReturn(1);
//        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
//        pc.addArmiesToCurrentPlayer(0);
//
//        assertEquals("setup", gfc.getPhase());
//
//        EasyMock.replay(pc);
//        EasyMock.replay(player);
//        gfc.next_phase();
//
//
//        assertEquals("assignment", gfc.getPhase());
//
//        EasyMock.verify(pc);
//        EasyMock.verify(player);
//	}
//
//	@Test
//	public void nextPhaseAssignmentPhaseTest() {
//        PlayerController pc = new PlayerController();
//        GameBoardController gb = new GameBoardController();
//        PhaseController phc = new PhaseController(pc, gb);
//        GameFlowController gfc = new GameFlowController(phc, pc, gb,
//                new AttackerDefenderController(), gui, msg);
//
//        assertEquals(gfc.getPhase(), "setup");
//
//		try {
//			gfc.next_phase();
//			fail("shouldn't reach this point");
//
//		} catch (Exception e) {
//			assertEquals("Init Phase isn't over", e.getMessage());
//		}
//		assertEquals("setup", gfc.getPhase());
//	}
//
//
//	@Test
//	public void nextPhaseCardPhaseTestUnplacedarmies() {
//        PlayerController pc = EasyMock.mock(PlayerController.class);
//        GameBoardController gb = new GameBoardController();
//        PhaseController phc = new PhaseController(pc, gb);
//        Player player = EasyMock.mock(Player.class);
//        gui.testMode = true;
//        GameFlowController gfc = new GameFlowController(phc, pc, gb,
//                new AttackerDefenderController(), gui, msg);
//
//        pc.addNewArmiestoPlayer();
//        EasyMock.expectLastCall();
//        EasyMock.expect(pc.getInit()).andReturn(false);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(player.getId()).andReturn(1);
//        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
//        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
//        pc.addArmiesToCurrentPlayer(0);
//
//        EasyMock.expect(pc.playerDonePlacingNew()).andReturn(false);
//        EasyMock.expect(pc.playerDoneWithCards()).andReturn(false);
//
//        assertEquals("setup", gfc.getPhase());
//
//        EasyMock.replay(pc);
//        EasyMock.replay(player);
//        gfc.next_phase();
//
//        assertEquals("assignment", gfc.getPhase());
//
//		try {
//			gfc.next_phase();
//			fail("shouldn't reach this point");
//
//		} catch (Exception e) {
//			assertEquals(e.getMessage(), "Player has unplaced armies");
//		}
//
//        EasyMock.verify(pc);
//        EasyMock.verify(player);
//	}
//
//	@Test
//	public void nextPhaseCardPhaseTestUnplayedCards() {
//        PlayerController pc = EasyMock.mock(PlayerController.class);
//        GameBoardController gb = new GameBoardController();
//        PhaseController phc = new PhaseController(pc, gb);
//        Player player = EasyMock.mock(Player.class);
//        gui.testMode = true;
//        GameFlowController gfc = new GameFlowController(phc, pc, gb,
//                new AttackerDefenderController(), gui, msg);
//
//        pc.addNewArmiestoPlayer();
//        EasyMock.expectLastCall();
//        EasyMock.expect(pc.getInit()).andReturn(false);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(player.getId()).andReturn(1);
//        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
//        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
//        pc.addArmiesToCurrentPlayer(0);
//
//        EasyMock.expect(pc.playerDonePlacingNew()).andReturn(true);
//        EasyMock.expect(pc.playerDoneWithCards()).andReturn(false);
//
//        assertEquals("setup", gfc.getPhase());
//
//        EasyMock.replay(pc);
//        EasyMock.replay(player);
//        gfc.next_phase();
//
//        assertEquals("assignment", gfc.getPhase());
//
//        try {
//            gfc.next_phase();
//            fail("shouldn't reach this point");
//
//        } catch (Exception e) {
//            assertEquals(e.getMessage(), "Player has too many cards in hand");
//        }
//
//        EasyMock.verify(pc);
//        EasyMock.verify(player);
//	}
//
//	@Test
//	public void attackPhaseGetPhaseTest() {
//        PlayerController pc = EasyMock.mock(PlayerController.class);
//        GameBoardController gb = new GameBoardController();
//        PhaseController phc = new PhaseController(pc, gb);
//        Player player = EasyMock.mock(Player.class);
//        gui.testMode = true;
//        GameFlowController gfc = new GameFlowController(phc, pc, gb,
//                new AttackerDefenderController(), gui, msg);
//
//        pc.addNewArmiestoPlayer();
//        EasyMock.expectLastCall();
//        EasyMock.expect(pc.getInit()).andReturn(false);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(player.getId()).andReturn(1);
//        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
//        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
//        pc.addArmiesToCurrentPlayer(0);
//
//        EasyMock.expect(pc.playerDonePlacingNew()).andReturn(true);
//        EasyMock.expect(pc.playerDoneWithCards()).andReturn(true);
//
//        assertEquals("setup", gfc.getPhase());
//
//        EasyMock.replay(pc);
//        EasyMock.replay(player);
//        gfc.next_phase();
//
//        assertEquals("assignment", gfc.getPhase());
//
//        gfc.next_phase();
//
//        assertEquals("attack", gfc.getPhase());
//
//        EasyMock.verify(pc);
//        EasyMock.verify(player);
//	}
//
//	@Test
//	public void fortifyPhaseGetPhaseTest() {
//        PlayerController pc = EasyMock.mock(PlayerController.class);
//        GameBoardController gb = new GameBoardController();
//        PhaseController phc = new PhaseController(pc, gb);
//        Player player = EasyMock.mock(Player.class);
//        gui.testMode = true;
//        GameFlowController gfc = new GameFlowController(phc, pc, gb,
//                new AttackerDefenderController(), gui, msg);
//
//        pc.addNewArmiestoPlayer();
//        EasyMock.expectLastCall();
//        EasyMock.expect(pc.getInit()).andReturn(false);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(pc.getCurrentPlayer()).andReturn(player);
//        EasyMock.expect(player.getId()).andReturn(1);
//        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
//        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
//        EasyMock.expect(player.getDeck()).andReturn(new ArrayList<Card>());
//        pc.addArmiesToCurrentPlayer(0);
//
//        EasyMock.expect(pc.playerDonePlacingNew()).andReturn(true);
//        EasyMock.expect(pc.playerDoneWithCards()).andReturn(true);
//        EasyMock.expect(player.hasCaughtTerritory()).andReturn(false);
//
//        assertEquals("setup", gfc.getPhase());
//
//        EasyMock.replay(pc);
//        EasyMock.replay(player);
//        gfc.next_phase();
//
//        assertEquals("assignment", gfc.getPhase());
//
//        gfc.next_phase();
//
//        assertEquals("attack", gfc.getPhase());
//
//        gfc.next_phase();
//
//        assertEquals("fortify", gfc.getPhase());
//
//        EasyMock.verify(pc);
//        EasyMock.verify(player);
//	}
//}
