package devvy.me.minestrike.game;

import devvy.me.minestrike.round.RoundManager;

public class GameManager {

    private final RoundManager roundManager;

    public GameManager() {
        roundManager = new RoundManager();
    }

    public RoundManager getRoundManager() {
        return roundManager;
    }

    public void startGame(){
        roundManager.startRoundLoop();
    }

    public void nextRound(){
        roundManager.nextRound();
    }

    public void endGame(){
        roundManager.endRoundLoop();
    }

    public boolean isGameRunning(){
        return roundManager.getCurrentRound() != null;
    }


}
