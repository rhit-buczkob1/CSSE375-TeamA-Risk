package Risk.controller;

public class SetupPhase extends Phase {
    public SetupPhase(GameFlowController gameFlowController) {
        super(gameFlowController);
    }

    @Override
    public void doPhase() {
        if (!gameFlowController.playercontroller.getInit()) {

//            gameFlowController.updateCurrPhase();

            return;
        }

        throw new IllegalArgumentException("Init Phase isn't over");
    }
}
