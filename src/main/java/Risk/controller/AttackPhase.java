package Risk.controller;

public class AttackPhase extends Phase {
    public AttackPhase(GameFlowController gameFlowController) {
        super(gameFlowController);
    }

    @Override
    public void doPhase() {
        gameFlowController.phase = "attack";
        gameFlowController.gui.currentPhase = gameFlowController.messages.getString(gameFlowController.phase);
        if (!gameFlowController.gui.testMode) {
            gameFlowController.gui.component.repaint();
        }

//        gameFlowController.updateCurrPhase();
    }
}
