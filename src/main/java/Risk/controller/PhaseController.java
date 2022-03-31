package Risk.controller;

import java.util.HashMap;

public class PhaseController {
    private String phase;
    private boolean updateCards = false;
    private HashMap<String, Phase> phases = new HashMap<String, Phase>();
    private PlayerController playerController;
    private GameBoardController gameBoardController;

    public PhaseController(PlayerController playerController) {
        phase = "setup";
        this.playerController = playerController;
        phases.put("assignment", new AssignmentPhase());
        phases.put("attack", new AttackPhase());
        phases.put("fortify", new FortifyPhase());
        phases.put("setup", new SetupPhase());
    }

    public void next_phase() {
        updateCards = false;
        phases.get(phase).doPhase();
        System.out.println("Doing phase " + phase);
    }

    public String getPhase() {
        return phase;
    }

    public boolean getUpdateCards() {
        return updateCards;
    }

    class AssignmentPhase extends Phase {

        public AssignmentPhase() {
            super("assignment");
        }

        @Override
        public void doPhase() {
            updateCards = true;
            boolean donePlacing = playerController.playerDonePlacingNew();
            boolean doneCards = playerController.playerDoneWithCards();

            if (donePlacing && doneCards) {
                System.out.println("Done");
                nextPhase();

                return;
            }
            if (!donePlacing) {
                throw new IllegalArgumentException("Player has unplaced armies");
            } else {
                throw new IllegalArgumentException("Player has too many cards in hand");
            }
        }

        public void nextPhase() {
            phase = "attack";
            updateCards = false;
        }
    }

    class AttackPhase extends Phase {
        public AttackPhase() {
            super("attack");
        }
        public void doPhase() {
            nextPhase();
        }

        public void nextPhase() {
            phase = "fortify";
            if (playerController.getCurrentPlayer().hasCaughtTerritory()) {
                playerController.addCardToCurrentPlayer(gameBoardController.gameBoardDeck.drawCard());
                playerController.getCurrentPlayer().caughtTerritory(false);
            }
            updateCards = true;
        }
    }

    class FortifyPhase extends Phase {

        public FortifyPhase() {
            super("fortify");
        }

        @Override
        public void doPhase() {
            playerController.nextPlayer();
            nextPhase();
        }

        public void nextPhase() {
            phase = "assignment";

            playerController.addNewArmiestoPlayer();
            int toaddarmies = gameBoardController
                    .getNewContinentPlayerArmies(playerController.getCurrentPlayer().getId());
            playerController.addArmiesToCurrentPlayer(toaddarmies);
            gameBoardController.updateGameBoard();
            updateCards = true;
        }
    }

    class SetupPhase extends Phase {
        public SetupPhase() {
            super("setup");
        }

        @Override
        public void doPhase() {
            if (!playerController.getInit()) {

                nextPhase();

                return;
            }

            throw new IllegalArgumentException("Init Phase isn't over");
        }

        public void nextPhase() {
            phase = "assignment";

            playerController.addNewArmiestoPlayer();
            int toaddarmies = gameBoardController
                    .getNewContinentPlayerArmies(playerController.getCurrentPlayer().getId());
            playerController.addArmiesToCurrentPlayer(toaddarmies);
            gameBoardController.updateGameBoard();

            updateCards = true;
        }
    }
}
