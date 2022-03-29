package Risk.controller;

public class AttackPhase extends Phase {
    public AttackPhase(GameFlowController gameFlowController) {
        super(gameFlowController);
        phaseName = "attack";
    }

    @Override
    public void doPhase() {
        next_phase();
    }

    public void next_phase() {
        gameFlowController.phase = "fortify";
        if (gameFlowController.playercontroller.getCurrentPlayer().hasCaughtTerritory()) {
            gameFlowController.playercontroller.addCardToCurrentPlayer(gameFlowController.gbcontroller.gameBoardDeck.drawCard());
            gameFlowController.playercontroller.getCurrentPlayer().caughtTerritory(false);
        }
        gameFlowController.updateCardsOnGui();
        gameFlowController.gui.currentPhase = gameFlowController.messages.getString(gameFlowController.phase);
        if (!gameFlowController.gui.testMode) {
            gameFlowController.gui.component.repaint();
        }
    }
}
