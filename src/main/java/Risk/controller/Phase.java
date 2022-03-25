package Risk.controller;

import Risk.model.Player;

public abstract class Phase {
    protected GameFlowController gameFlowController;

    public Phase(GameFlowController gameFlowController) {
        this.gameFlowController = gameFlowController;
    }

    public void doPhase() {
        throw new IllegalArgumentException("Invalid Phase");
    }
}
