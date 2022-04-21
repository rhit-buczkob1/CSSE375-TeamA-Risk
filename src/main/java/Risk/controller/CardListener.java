package Risk.controller;

import Risk.model.Card;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class CardListener implements EventHandler<MouseEvent> {
	
    private GameFlowController gfc;
    
    public CardListener(GameFlowController gfController) {
        this.gfc = gfController;
    }
    
    @Override
	public void handle(MouseEvent event) {
		if (!(gfc.getPhase().equals("assignment"))) {
			return;
		}
		ArrayList<Card> playerHand = gfc.playercontroller.getCurrentPlayer().getDeck();
		ArrayList<String> converted = new ArrayList<String>();
		
		for (int i = 0; i < playerHand.size(); i++) {
			converted.add(gfc.convertCardForGui(playerHand.get(i)));
		}
		
		Card card1 = playerHand.get(converted.indexOf(gfc.gui.card1.getValue()));
		Card card2 = playerHand.get(converted.indexOf(gfc.gui.card2.getValue()));
		Card card3 = playerHand.get(converted.indexOf(gfc.gui.card3.getValue()));
		gfc.turnInCards(card1, card2, card3);
		gfc.gui.setCurrentPlayerArmies(Integer.toString(gfc.playercontroller.getCurrentPlayer().getPlayerArmies()));
	}
}
