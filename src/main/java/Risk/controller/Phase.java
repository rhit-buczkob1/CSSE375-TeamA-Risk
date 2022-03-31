package Risk.controller;

public abstract class Phase {
    protected GameFlowController gameFlowController;
    protected String phaseName;

    public Phase(String phaseName) {
        this.phaseName = phaseName;
    }

    public void doPhase() {
        throw new IllegalArgumentException("Invalid Phase");
    }

    public abstract void nextPhase();

    public String getPhaseName() {
        return phaseName;
    }
}
