package Risk.controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class NextButtonListener implements EventHandler<MouseEvent> {

	GameFlowController gfc;
	
	public NextButtonListener(GameFlowController gfc) {
		this.gfc = gfc;
	}
	
	@Override
	public void handle(MouseEvent event) {
		gfc.next_phase();
		gfc.gui.setCurrentPlayerArmies(Integer.toString(gfc.playercontroller.getCurrentPlayer().getPlayerArmies()));
		gfc.gui.setCurrentPlayer(String.valueOf(gfc.playercontroller.getCurrentPlayer().getId()));
		
	}
}
