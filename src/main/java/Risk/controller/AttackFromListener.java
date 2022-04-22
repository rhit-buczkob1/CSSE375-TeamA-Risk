package Risk.controller;

import Risk.model.Territory;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackFromListener implements EventHandler<MouseEvent> {

	GameFlowController gfc;
	
	public AttackFromListener(GameFlowController gfc) {
		this.gfc = gfc;
	}
	
	public class AttackListener implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (!(gfc.gui.clickedTerritory.getText().equals(""))) {
				String attack = gfc.gui.attackingTerritory.replace('_', ' ');
				gfc.gui.attackingTerritory = "";
				Territory defendingTerritory = gfc.gbcontroller.getTerritory(gfc.gui.clickedTerritory.getText());
				gfc.initiateCombat(attack, defendingTerritory.getName());
				
				gfc.randomizeCombat(gfc.rand, (int)gfc.gui.attackerDiceSlider.getValue(), (int)gfc.gui.defenderDiceSlider.getValue());
				
				gfc.finishCombat();
				
				gfc.gui.setCurrentPlayerArmies(Integer.toString(gfc.playercontroller.getCurrentPlayer().getPlayerArmies()));
				gfc.gui.setCurrentPlayer(String.valueOf(gfc.playercontroller.getCurrentPlayer().getId()));
				gfc.gui.territoryArmiesNumber.setText(String.valueOf(defendingTerritory.getArmyCount()));
				gfc.gui.territoryPlayerNumber.setText(String.valueOf(defendingTerritory.getPlayer()));
				gfc.gui.paintTerritoryBounds();

			}
		}
	}
	
	@Override
	public void handle(MouseEvent event) {
		if (!(gfc.gui.clickedTerritory.getText().equals(""))) {
			if (!gfc.verifyOwnership(gfc.gui.clickedTerritory.getText())) {
				return;
			}

			gfc.gui.setAttacking(gfc.gui.clickedTerritory.getText());
			gfc.changeGuiButtons(false, true, true, false, true);
			gfc.gui.paintTerritoryBounds();
		}
	}
}