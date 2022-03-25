package Risk.controller;

public class SetupPhase extends Phase {
    public SetupPhase(GameFlowController gameFlowController) {
        super(gameFlowController);
        phaseName = "setup";
    }

    @Override
    public void doPhase() {
        if (!gameFlowController.playercontroller.getInit()) {

            gameFlowController.updateCurrPhase(false);

            return;
        }

        throw new IllegalArgumentException("Init Phase isn't over");
    }
}
