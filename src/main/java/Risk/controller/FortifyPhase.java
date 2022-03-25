package Risk.controller;

public class FortifyPhase extends Phase {

    public FortifyPhase(GameFlowController gameFlowController) {
        super(gameFlowController);
        phaseName = "fortify";
    }

    @Override
    public void doPhase() {
        gameFlowController.playercontroller.nextPlayer();
        next_phase();
    }

    public void next_phase() {
        gameFlowController.phase = "assignment";
        gameFlowController.updateCardsOnGui();

        gameFlowController.playercontroller.addNewArmiestoPlayer();
        int toaddarmies = gameFlowController.gbcontroller
                .getNewContinentPlayerArmies(gameFlowController.playercontroller.getCurrentPlayer().getId());
        gameFlowController.playercontroller.addArmiesToCurrentPlayer(toaddarmies);
        gameFlowController.gbcontroller.updateGameBoard();
        gameFlowController.gui.currentPhase = gameFlowController.messages.getString(gameFlowController.phase);
        if (!gameFlowController.gui.testMode) {
            gameFlowController.gui.component.repaint();
        }

        gameFlowController.updateCurrPhase(1);
    }
}
