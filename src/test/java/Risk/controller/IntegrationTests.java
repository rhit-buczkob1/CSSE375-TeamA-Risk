package Risk.controller;

import Risk.view.GraphicalUserInterface;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.*;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextMatchers;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.control.TextMatchers.hasText;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class IntegrationTests extends ApplicationTest {
    Locale locale = new Locale("en", "US");
    ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", locale);

    GraphicalUserInterface gui;
    PlayerController pc;
    PhaseController phc;
    GameBoardController gbController;
    GameFlowController gameflowcontroller;
    AttackerDefenderController adController;

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
        pc = new PlayerController();
        gbController = new GameBoardController();
        phc = new PhaseController(pc, gbController);
        adController = new AttackerDefenderController();
        GraphicalUserInterface gui = new GraphicalUserInterface(msg, stage);

        gameflowcontroller = new GameFlowController(phc, pc, gbController, adController, gui, msg);
        gui.initializeFrame();
    }

    @Test
    public void testGameSetup() {
        verifyThat("#chooseGameMode", LabeledMatchers.hasText("Choose Game Mode"));
        verifyThat("#setNumPlayers", LabeledMatchers.hasText("Start game"));
    }

    @Test
    public void testPlayerCount3() {
        gameSetup();
        selectPlayers(3);
    }

    @Test
    public void testPlayerCount4() {
        gameSetup();
        selectPlayers(4);
    }

    @Test
    public void testPlayerCount5() {
        gameSetup();
        selectPlayers(5);
    }

    @Test
    public void testPlayerCount6() {
        gameSetup();
        selectPlayers(6);
    }

    public void selectPlayers(int playerCount) {
        clickOn(144 + (42 * (playerCount-3)), 234);
        clickOn("#setNumPlayers");
        int expectedArmies = 35 - (5 * (playerCount-3));
        verifyThat("#playerArmiesNumber", TextMatchers.hasText("" + expectedArmies));
        assertEquals(playerCount, pc.getNumberOfPlayers());
    }

    @Test
    public void testButtonResponsiveness() {
        gameSetup();
        clickOn("#setNumPlayers");
        setupPhase();
        assignmentPhase();
    }

    @Test
    public void testMapSelectEurope() {
        clickOn("#maps");
        clickOn("Europe");
        clickOn("#setNumPlayers");
        clickOn(790, 550);
        verifyThat("#clickedTerritory", TextMatchers.hasText("Bulgaria"));
    }

    @Test
    public void testAttackHighlighting() {
        gameSetup();
        clickOn("#setNumPlayers"); // d. Click "start game"
        setupPhase();
        assignmentPhase();
        clickOn(987, 627); // t. Select a territory owned by the current player
        verifyThat("#attackFrom", isEnabled()); // r. Verify that "Attack From" is enabled
        assertEquals("attack", phc.getPhase());
        clickOn("#attackFrom"); // m. Click "Attack From"
        clickOn(970, 610);
        clickOn(1062,590);
        verifyThat("#attack", isEnabled());
        clickOn("#attack");
    }

    public void setupPhase() {
        assertEquals("setup", phc.getPhase());
        verifyThat("#addArmy", LabeledMatchers.hasText("Add Army"));
        verifyThat("#addArmy", Node::isDisabled);
        for (int i = 0; i < 35; i++) { // h. Repeat e-g until armies are depleted
            assertEquals(1, pc.getCurrentPlayer().getId());
            assertEquals(35-i, pc.getCurrentPlayer().getPlayerArmies());
            setupPhase(987, 627, "Western Australia");
            assertEquals(2, pc.getCurrentPlayer().getId());
            assertEquals(35-i, pc.getCurrentPlayer().getPlayerArmies());
            setupPhase(1062, 590, "Eastern Australia");
            assertEquals(3, pc.getCurrentPlayer().getId());
            assertEquals(35-i, pc.getCurrentPlayer().getPlayerArmies());
            setupPhase(1025, 492, "New Guinea");
        }
        verifyThat("#addArmy", Node::isDisabled); // i. Verify that "Add Army" has been disabled
        verifyThat("#nextTurn", isEnabled()); // j. Verify that "Next Phase" is enabled
        clickOn("#nextTurn"); // k. Click "Next Phase"
    }

    public void assignmentPhase() {
        clickOn(1062, 590); // l. Select a territory not owned by the current player
        verifyThat("#addArmy", Node::isDisabled); // m. Verify that "Add Army" has been disabled
        clickOn(987, 627); // n. Select a territory owned by the current player
        verifyThat("#addArmy", isEnabled()); // o. Verify that "Add Army" has been enabled
        assertEquals("assignment", phc.getPhase());
        for (int i = 0; i < 3; i++) { // p. Add the assigned armies wherever applicable
            assertEquals(3-i, pc.getCurrentPlayer().getPlayerArmies());
            clickOn("#addArmy");
            assertEquals(2-i, pc.getCurrentPlayer().getPlayerArmies());
        }
        verifyThat("#addArmy", Node::isDisabled); // q. Verify that "Add Army" has been disabled
        verifyThat("#nextTurn", isEnabled()); // r. Verify that "Next Phase" is enabled
        clickOn("#nextTurn"); // s. Click "Next Phase"
    }

    public void gameSetup() {
        clickOn("#maps");
        clickOn("Globe"); // b. Select "Globe" in the map selection dropdown
    }

    public void setupPhase(int x, int y, String territoryName) {
        clickOn(x, y); // e. Click on a territory
        verifyThat("#clickedTerritory", TextMatchers.hasText(territoryName));
        verifyAddArmy(); // f. Verify that the only available button is "Add Army"
        clickOn("#addArmy"); // g. Click on "Add Army"
    }

    public void verifyAddArmy() {
        verifyThat("#addArmy", isEnabled());
        verifyThat("#attack", Node::isDisabled);
        verifyThat("#attackFrom", Node::isDisabled);
        verifyThat("#spendCards", Node::isDisabled);
        verifyThat("#moveFrom", Node::isDisabled);
        verifyThat("#nextTurn", Node::isDisabled);
    }
}
