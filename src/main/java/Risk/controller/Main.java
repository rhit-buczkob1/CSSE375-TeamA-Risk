package Risk.controller;

import Risk.model.Player;
import Risk.view.GraphicalUserInterface;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
	
	public static void main(String[] args) {
		PlayerController pc = new PlayerController();
		PhaseController phc = new PhaseController(pc);
		GameBoardController gameBoard = new GameBoardController();
		Locale l = new Locale("en", "US");
		ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", l);
		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
		AttackerDefenderController adc = new AttackerDefenderController();

		GameFlowController gameflowcontroller = new GameFlowController(phc, pc, gameBoard, adc, gui, msg);

		runMain(pc, gameBoard);

		gui.initializeFrame();

	}

	public static void runMain(PlayerController pc, GameBoardController gameBoard) {

		for (int i = 0; i < 4; i++) {
			Player p = new Player(i + 1);
			pc.addPlayer(p);
		}
		gameBoard.initGame();
		pc.initializePlayer();

	}

}
