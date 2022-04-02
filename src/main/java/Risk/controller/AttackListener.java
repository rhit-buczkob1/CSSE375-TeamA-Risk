package Risk.controller;

import Risk.model.Territory;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackListener implements EventHandler<MouseEvent> {

	String attackingterritory = "";
	GameFlowController gfc;
	
	public AttackListener(GameFlowController gfc) {
		this.gfc = gfc;
	}
	
	
	@Override
	public void handle(MouseEvent event) {
		
		gfc.gui.territoryArmiesNumber.setText(attackingterritory);
		
		if (gfc.phase.equals("attack") && attackingterritory.equals("")) {
			if (!(gfc.gui.clickedTerritory.getText().equals(""))) {
				if (!gfc.verifyOwnership(gfc.gui.clickedTerritory.getText())) {
					return;
				}

				attackingterritory = gfc.gui.clickedTerritory.getText();
			}
		} else if (gfc.phase.equals("attack")) {
			if (!(gfc.gui.clickedTerritory.getText().equals(""))) {
				String attack = this.attackingterritory;
				this.attackingterritory = "";
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

}