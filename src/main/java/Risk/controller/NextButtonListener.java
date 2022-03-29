package Risk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextButtonListener implements ActionListener {

    private GameFlowController gfController;
    public NextButtonListener(GameFlowController gfController) {
        this.gfController = gfController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gfController.next_phase();
        gfController.gui.setCurrentPlayerArmies(Integer.toString(gfController.playercontroller.getCurrentPlayer().getPlayerArmies()));
        gfController.gui.setCurrentPlayer(String.valueOf(gfController.playercontroller.getCurrentPlayer().getId()));
    }
}
