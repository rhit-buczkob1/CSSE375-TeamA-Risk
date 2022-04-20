package Risk.controller;

import Risk.model.Territory;
import javafx.event.EventHandler;

public class AddTerritoryListener implements EventHandler<javafx.event.ActionEvent> {
	String fromTerritory = "";
	String toTerritory = "";
	GameFlowController gfc;
	
	public AddTerritoryListener(GameFlowController gfc) {
		this.gfc = gfc;
	}
	
	@Override
	public void handle(javafx.event.ActionEvent event) {
		if (gfc.phase.equals("fortify") && fromTerritory.equals("")) {
			if (!(gfc.gui.clickedTerritory.getText().equals(""))) {
				fromTerritory = gfc.gui.clickedTerritory.getText();
			}
		} else if (gfc.phase.equals("fortify") && toTerritory.equals("")) {
			if (!(gfc.gui.clickedTerritory.equals(""))) {
				toTerritory = gfc.gui.clickedTerritory.getText();

				if (!gfc.verifyOwnership(fromTerritory) || !gfc.verifyOwnership(toTerritory)
						|| !gfc.verifyAdjacent(fromTerritory, toTerritory)) {
					fromTerritory = "";
					toTerritory = "";
					return;
				}

			}
		} else if (gfc.phase.equals("fortify")) {
			try {
				gfc.playercontroller.moveArmy(gfc.gbcontroller.getTerritory(fromTerritory),
						gfc.gbcontroller.getTerritory(toTerritory), 1);
				gfc.gui.territoryArmiesNumber
						.setText(String.valueOf(gfc.gbcontroller.getTerritory(gfc.gui.clickedTerritory.getText()).getArmyCount()));
			} catch (IllegalArgumentException e1) {
				System.err.println(e1.getMessage());
			}

		} else {
			if (!(gfc.gui.clickedTerritory.equals(""))) {
				int player = gfc.playercontroller.getCurrentPlayer().getId();
				gfc.addInfantrytoTerritoryfromString(gfc.gui.clickedTerritory.getText());
				if (player == gfc.gbcontroller.getTerritoryOwner(gfc.gui.clickedTerritory.getText())) {
					gfc.gui.setTerritoryColor(gfc.gui.clickedTerritory.getText(), player);
				}
				Territory territory = gfc.gbcontroller.getTerritory(gfc.gui.clickedTerritory.getText());

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
}
