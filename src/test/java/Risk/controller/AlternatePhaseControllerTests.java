package Risk.controller;

import Risk.view.GraphicalUserInterface;
import com.sun.javafx.application.PlatformImpl;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.easymock.EasyMock;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import org.testfx.framework.junit.ApplicationTest;
import org.testfx.robot.Motion;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AlternatePhaseControllerTests extends ApplicationTest{

    Locale locale = new Locale("en", "US");
    ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", locale);
    GameFlowController gfc;

    @Override
    public void start(Stage stage) throws Exception {
        PlayerController pc = new PlayerController();
        GameBoardController gbController = new GameBoardController();
        PhaseController phaseController = new PhaseController(pc, gbController);
        AttackerDefenderController adController = new AttackerDefenderController();
        GraphicalUserInterface gui = new GraphicalUserInterface(msg, stage);

        gfc = new GameFlowController(phaseController, pc, gbController, adController, gui, msg);

        gui.initializeFrame();
    }

    @Test
    public void test_button_existance() {
        verifyThat("#chooseGameMode", hasText("Choose Game Mode"));
    }

    @Test
    public void test_swap_phase_controller() {
        verifyThat("#chooseGameMode", hasText("Choose Game Mode"));

        clickOn("#chooseGameMode");
        clickOn("#gameModeSelectionBox");

        clickOn("#confirmModeChange");

        assertEquals("class Risk.controller.AlternatePhaseController", gfc.phaseController.getClass().toString());
    }
}
