package Risk.controller;

public class AssignmentPhase extends Phase {


    public AssignmentPhase(GameFlowController gameFlowController) {
        super(gameFlowController);
        phaseName = "assignment";
    }

    @Override
    public void doPhase() {

        gameFlowController.updateCardsOnGui();
        boolean donePlacing = gameFlowController.playercontroller.playerDonePlacingNew();
        boolean doneCards = gameFlowController.playercontroller.playerDoneWithCards();

        if (donePlacing && doneCards) {
            System.out.println("Done");
            next_phase();

            return;
        }
        if (!donePlacing) {
            throw new IllegalArgumentException("Player has unplaced armies");
        } else {
            throw new IllegalArgumentException("Player has too many cards in hand");
        }
    }

    public void next_phase() {
        gameFlowController.phase = "attack";
        gameFlowController.gui.currentPhase = gameFlowController.messages.getString(gameFlowController.phase);
        if (!gameFlowController.gui.testMode) {
            gameFlowController.gui.component.repaint();
        }
    }
}
