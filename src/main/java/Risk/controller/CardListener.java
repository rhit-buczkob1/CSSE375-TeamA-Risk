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

        gfController.performCardTransaction();
    }
}
