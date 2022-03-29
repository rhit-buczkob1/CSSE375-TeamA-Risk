package Risk.controller;

import Risk.model.Territory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackListener implements ActionListener {
    private String attackingterritory = "";
    private GameFlowController gfController;
    public AttackListener(GameFlowController gfController) {
        this.gfController = gfController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gfController.phase.equals("attack") && attackingterritory.equals("")) {
            if (!(gfController.gui.clickedTerritory.equals(""))) {
                if (!gfController.verifyOwnership(gfController.gui.clickedTerritory)) {
                    return;
                }

                attackingterritory = gfController.gui.clickedTerritory;
            }
        } else if (gfController.phase.equals("attack")) {
            if (!(gfController.gui.clickedTerritory.equals(""))) {
                String attack = this.attackingterritory;
                this.attackingterritory = "";

                gfController.initiateCombat(attack, gfController.gui.clickedTerritory, GameFlowController.rand, gfController.gui.attackerDiceSlider.getValue(),
                        gfController.gui.defenderDiceSlider.getValue());
                Territory defendingTerritory = gfController.gbcontroller.getTerritory(gfController.gui.clickedTerritory);

                gfController.gui.setCurrentPlayerArmies(Integer.toString(gfController.playercontroller.getCurrentPlayer().getPlayerArmies()));
                gfController.gui.setCurrentPlayer(String.valueOf(gfController.playercontroller.getCurrentPlayer().getId()));
                gfController.gui.territoryArmiesNumber.setText(String.valueOf(defendingTerritory.getArmyCount()));
                gfController.gui.territoryPlayerNumber.setText(String.valueOf(defendingTerritory.getPlayer()));
            }
        }
    }
}
