package Risk.controller;

public class SetupPhase extends Phase {
    public SetupPhase(GameFlowController gameFlowController) {
        super(gameFlowController);
        phaseName = "setup";
    }

    @Override
    public void doPhase() {
        if (!gameFlowController.playercontroller.getInit()) {

            nextPhase();

            return;
        }

        throw new IllegalArgumentException("Init Phase isn't over");
    }

    public void nextPhase() {
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
