package Risk.controller;

import Risk.view.GraphicalUserInterface;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.*;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Locale;
import java.util.ResourceBundle;

public class IntegrationTests extends ApplicationTest {
    Locale locale = new Locale("en", "US");
    ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", locale);

    GraphicalUserInterface gui;

//    @BeforeClass
//    public static void headless() {
//        System.setProperty("java.awt.headless", "true");
//        System.setProperty("testfx.robot", "glass");
//        System.setProperty("testfx.headless", "true");
//        System.setProperty("glass.platform", "Monocle");
//        System.setProperty("monocle.platform", "Headless");
//        System.setProperty("prism.order", "sw");
//        System.setProperty("prism.text", "t2k");
//        System.setProperty("testfx.setup.timeout", "2500");
//    }

    @Override
    public void start(Stage stage) throws Exception {
        gui = new GraphicalUserInterface(msg, stage);
    }

    @Test
    public void testButtonResponsiveness() {
        PlayerController pc = new PlayerController();
        GameBoardController gameBoard = new GameBoardController();
        PhaseController phc = new PhaseController(pc, gameBoard);
        AttackerDefenderController adc = new AttackerDefenderController();

        GameFlowController gameflowcontroller = new GameFlowController(phc, pc, gameBoard, adc, gui, msg);
    }
}
