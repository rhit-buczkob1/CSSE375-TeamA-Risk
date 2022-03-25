package Risk.controller;

public class FortifyPhase extends Phase {

    public FortifyPhase(GameFlowController gameFlowController) {
        super(gameFlowController);
    }

    @Override
    public void doPhase() {
        gameFlowController.phase = "fortify";
        PlayerController pc = gameFlowController.playercontroller;
        if (pc.getCurrentPlayer().hasCaughtTerritory()) {
            pc.addCardToCurrentPlayer(gameFlowController.gbcontroller.gameBoardDeck.drawCard());
            pc.getCurrentPlayer().caughtTerritory(false);
        }
        gameFlowController.updateCardsOnGui();
        gameFlowController.gui.currentPhase = gameFlowController.messages.getString(gameFlowController.phase);
        if (!gameFlowController.gui.testMode) {
            gameFlowController.gui.component.repaint();
        }

        pc.nextPlayer();
//        gameFlowController.updateCurrPhase();
    }
}
