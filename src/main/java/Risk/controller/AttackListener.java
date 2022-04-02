package Risk.controller;

import Risk.model.Territory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttackListener implements ActionListener {
    private String attackingTerritory = "";
    private GameFlowController gfController;
    public AttackListener(GameFlowController gfController) {
        this.gfController = gfController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gfController.updatePhase();
        if (selectingAttackingTerritory()) {
            if (gfController.clickedOnValidLocation()) {
                if (!gfController.verifyOwnership(gfController.gui.clickedTerritory)) {
                    return;
                }

                attackingTerritory = gfController.gui.clickedTerritory;
            }
        } else if (gfController.phase.equals("attack")) {
            if (gfController.clickedOnValidLocation()) {
                String attack = this.attackingTerritory;
                this.attackingTerritory = "";

                gfController.initiateCombat(attack, gfController.gui.clickedTerritory);
                gfController.randomizeCombat(GameFlowController.rand, gfController.gui.attackerDiceSlider.getValue(),
                        gfController.gui.defenderDiceSlider.getValue());
                gfController.finishCombat();

                Territory defendingTerritory = gfController.gbcontroller.getTerritory(gfController.gui.clickedTerritory);
                gfController.gui.setCurrentPlayerArmies(Integer.toString(gfController.playercontroller.getCurrentPlayer().getPlayerArmies()));
                gfController.gui.setCurrentPlayer(String.valueOf(gfController.playercontroller.getCurrentPlayer().getId()));
                gfController.gui.territoryArmiesNumber.setText(String.valueOf(defendingTerritory.getArmyCount()));
                gfController.gui.territoryPlayerNumber.setText(String.valueOf(defendingTerritory.getPlayer()));
            }
        }
    }

    private boolean selectingAttackingTerritory() {
        return gfController.phase.equals("attack") && attackingTerritory.equals("");
    }
}
