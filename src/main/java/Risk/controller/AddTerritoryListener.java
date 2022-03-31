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
            if (gfController.clickedOnValidLocation()) {
                fromTerritory = gfController.gui.clickedTerritory;
            }
        } else if (gfController.phase.equals("fortify") && toTerritory.equals("")) {
            setTerritoryToFortify();
        } else if (gfController.phase.equals("fortify")) {
            gfController.fortifyTerritory(fromTerritory, toTerritory);
        } else if (toset.getText().equals(gfController.messages.getString("addArmy"))) {
            if (gfController.clickedOnValidLocation()) {
                gfController.addArmy();
            } else {
                throw new IllegalArgumentException("Mouse not clicked");
            }
        }
    }

    private void setTerritoryToFortify() {
        if (gfController.clickedOnValidLocation()) {
            toTerritory = gfController.gui.clickedTerritory;

            if (!gfController.verifyOwnership(fromTerritory) || !gfController.verifyOwnership(toTerritory)
                    || !gfController.verifyAdjacent(fromTerritory, toTerritory)) {
                fromTerritory = "";
                toTerritory = "";
            }
        }
    }
}
