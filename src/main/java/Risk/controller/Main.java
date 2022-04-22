package Risk.controller;

import Risk.model.Player;
import Risk.view.GraphicalUserInterface;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		PlayerController pc = new PlayerController();
		GameBoardController gameBoard = new GameBoardController();
		PhaseController phc = new PhaseController(pc, gameBoard);
		Locale l = new Locale("en", "US");
		ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", l);
		GraphicalUserInterface gui = new GraphicalUserInterface(msg, primaryStage);
		AttackerDefenderController adc = new AttackerDefenderController();

		GameFlowController gameflowcontroller = new GameFlowController(phc, pc, gameBoard, adc, gui, msg);

		gui.initializeFrame();
	}

}