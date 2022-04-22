package Risk.controller;

import Risk.model.Territory;
import javafx.event.EventHandler;

public class AddTerritoryListener implements EventHandler<javafx.event.ActionEvent> {
	String toTerritory = "";
	GameFlowController gfc;
	
	public AddTerritoryListener(GameFlowController gfc) {
		this.gfc = gfc;
	}
	
	@Override
	public void handle(javafx.event.ActionEvent event) {
		if (gfc.getPhase().equals("fortify")) {
			if (!(gfc.gui.clickedTerritory.getText().equals(""))) {
				if (toTerritory.equals("")) {
					toTerritory = gfc.gui.clickedTerritory.getText();

					if (!gfc.verifyOwnership(gfc.gui.transportingTerritory) || !gfc.verifyOwnership(toTerritory)
							|| !gfc.verifyAdjacent(gfc.gui.transportingTerritory, toTerritory)) {
						gfc.gui.transportingTerritory = "";
						toTerritory = "";
						return;
					}
				}
				if (toTerritory.equals(gfc.gui.clickedTerritory.getText())) {
					try {
						gfc.playercontroller.moveArmy(gfc.gbcontroller.territoryController.getTerritory(gfc.gui.transportingTerritory),
								gfc.gbcontroller.territoryController.getTerritory(toTerritory), 1);
						gfc.gui.territoryArmiesNumber
								.setText(String.valueOf(gfc.gbcontroller.territoryController.getTerritory(gfc.gui.clickedTerritory.getText()).getArmyCount()));
					} catch (IllegalArgumentException e1) {
						System.err.println(e1.getMessage());
					}
				}
			} 
		} else {
			if (!(gfc.gui.clickedTerritory.getText().equals(""))) {
				int player = gfc.playercontroller.getCurrentPlayer().getId();
				gfc.addInfantrytoTerritoryfromString(gfc.gui.clickedTerritory.getText());
				if (player == gfc.gbcontroller.territoryController.getTerritoryOwner(gfc.gui.clickedTerritory.getText())) {
					System.err.println(gfc.gui.clickedTerritory.getText());
					System.err.println(player);
					gfc.gui.setTerritoryColor(gfc.gui.clickedTerritory.getText(), player);
				}
				Territory territory = gfc.gbcontroller.territoryController.getTerritory(gfc.gui.clickedTerritory.getText());

				int playerArmyCount = gfc.playercontroller.getCurrentPlayer().getPlayerArmies();

				gfc.gui.setCurrentPlayerArmies(Integer.toString(playerArmyCount));
				gfc.gui.setCurrentPlayer(String.valueOf(gfc.playercontroller.getCurrentPlayer().getId()));
				gfc.gui.setTerritoryArmyCount(territory.getArmyCount());
				gfc.gui.setCurrentTerritoryOwner(territory.getPlayer());
				gfc.gui.paintTerritoryBounds();

				if (playerArmyCount == 0) {
					gfc.gui.changeNextTurnButton(false);
					gfc.gui.changeAddArmyButton(true);
				}
			} else {
				throw new IllegalArgumentException("Mouse not clicked");
			}

		}
	}
	
	public class MoveListener implements EventHandler<javafx.event.ActionEvent> {

		@Override
		public void handle(javafx.event.ActionEvent event) {
			if (!(gfc.gui.clickedTerritory.getText().equals("")) && toTerritory.equals("")) {
				gfc.gui.transportingTerritory = gfc.gui.clickedTerritory.getText();
				gfc.gui.paintTerritoryBounds();
			}
		}
		
	}
}
