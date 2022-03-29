package Risk.controller;

import Risk.model.Card;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CardListener implements ActionListener {
    private GameFlowController gfController;
    public CardListener(GameFlowController gfController) {
        this.gfController = gfController;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(gfController.phase.equals("assignment"))) {
            return;
        }
        int card1Index = gfController.gui.card1.getSelectedIndex();
        int card2Index = gfController.gui.card2.getSelectedIndex();
        int card3Index = gfController.gui.card3.getSelectedIndex();
        ArrayList<Card> availableCards = gfController.playercontroller.getCurrentPlayer().getDeck();
        gfController.turnInCards(availableCards.get(card1Index),
                availableCards.get(card2Index),
                availableCards.get(card3Index));
        gfController.gui.setCurrentPlayerArmies(Integer.toString(gfController.playercontroller.getCurrentPlayer().getPlayerArmies()));
        gfController.gui.setCurrentPlayer(String.valueOf(gfController.playercontroller.getCurrentPlayer().getId()));
        gfController.gui.component.repaint();
    }
}
