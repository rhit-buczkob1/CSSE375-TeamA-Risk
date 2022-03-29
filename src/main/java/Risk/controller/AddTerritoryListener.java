package Risk.controller;

import Risk.model.Territory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTerritoryListener implements ActionListener {
    private String fromTerritory = "";
    private String toTerritory = "";
    private GameFlowController gfController;

    public AddTerritoryListener(GameFlowController gfController) {
        this.gfController = gfController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton toset = (JButton) e.getSource();
        if (gfController.phase.equals("fortify") && fromTerritory.equals("")) {
            if (!(gfController.gui.clickedTerritory.equals(""))) {
                fromTerritory = gfController.gui.clickedTerritory;
            }
        } else if (gfController.phase.equals("fortify") && toTerritory.equals("")) {
            if (!(gfController.gui.clickedTerritory.equals(""))) {
                toTerritory = gfController.gui.clickedTerritory;

                if (!gfController.verifyOwnership(fromTerritory) || !gfController.verifyOwnership(toTerritory)
                        || !gfController.verifyAdjacent(fromTerritory, toTerritory)) {
                    fromTerritory = "";
                    toTerritory = "";
                }
            }
        } else if (gfController.phase.equals("fortify")) {
            try {
                gfController.playercontroller.moveArmy(gfController.gbcontroller.getTerritory(fromTerritory),
                        gfController.gbcontroller.getTerritory(toTerritory), 1);
                gfController.gui.territoryArmiesNumber
                        .setText(String.valueOf(gfController.gbcontroller.getTerritory(gfController.gui.clickedTerritory).getArmyCount()));
            } catch (IllegalArgumentException e1) {
                System.err.println(e1.getMessage());
            }
        } else if (toset.getText().equals(gfController.messages.getString("addArmy"))) {
            if (!(gfController.gui.clickedTerritory.equals(""))) {
                int player = gfController.playercontroller.getCurrentPlayer().getId();
                gfController.addInfantrytoTerritoryfromString(gfController.gui.clickedTerritory);
                if (player == gfController.gbcontroller.getTerritoryOwner(gfController.gui.clickedTerritory)) {
                    gfController.gui.setTerritoryColor(gfController.gui.clickedTerritory, player);
                }
                Territory territory = gfController.gbcontroller.getTerritory(gfController.gui.clickedTerritory);

                gfController.gui.setCurrentPlayerArmies(Integer.toString(gfController.playercontroller.getCurrentPlayer().getPlayerArmies()));
                gfController.gui.setCurrentPlayer(String.valueOf(gfController.playercontroller.getCurrentPlayer().getId()));
                gfController.gui.currentTerritoryArmyCount = territory.getArmyCount();
                gfController.gui.currentTerritoryPlayer = territory.getPlayer();
                gfController.gui.component.repaint();
            } else {
                throw new IllegalArgumentException("Mouse not clicked");
            }
        }
    }
}
