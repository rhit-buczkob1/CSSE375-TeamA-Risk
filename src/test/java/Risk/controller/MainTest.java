package Risk.controller;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

import Risk.model.Player;
import Risk.view.GraphicalUserInterface;

public class MainTest {

	@Test
	public void runMaintest() {
		PlayerController pc = new PlayerController();
		GameBoardController gameBoard = new GameBoardController();
		PhaseController phc = new PhaseController(pc, gameBoard);
		Locale l = new Locale("en", "US");
		ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", l);
		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
		AttackerDefenderController adc = new AttackerDefenderController();
		gameBoard.initializeNewBoardForTest();

		GameFlowController gameflowcontroller = new GameFlowController(phc, pc, gameBoard, adc, gui, msg);
		Main main = new Main();
		main.runMain(pc, gameBoard);
		int playercount = 0;
		int index = 1;
		for (Player player : pc.players) {
			assertEquals(index, player.getId());
			assertEquals(30, player.getPlayerArmies());
			playercount++;
			index++;
		}

		assertEquals(playercount, 4);
		assertEquals(6, gameBoard.getGameBoard().continents.size());

	}

}