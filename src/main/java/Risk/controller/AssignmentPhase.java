package Risk.controller;

public class AssignmentPhase extends Phase {


    public AssignmentPhase(GameFlowController gameFlowController) {
        super(gameFlowController);
        phaseName = "assignment";
    }

    @Override
    public void doPhase() {
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

        gameFlowController.updateCardsOnGui();
        boolean donePlacing = gameFlowController.playercontroller.playerDonePlacingNew();
        boolean doneCards = gameFlowController.playercontroller.playerDoneWithCards();

        if (donePlacing && doneCards) {
            System.out.println("Done");
            gameFlowController.updateCurrPhase(false);

            return;
        }
        if (!donePlacing) {
            throw new IllegalArgumentException("Player has unplaced armies");
        } else {
            throw new IllegalArgumentException("Player has too many cards in hand");
        }
    }
}
