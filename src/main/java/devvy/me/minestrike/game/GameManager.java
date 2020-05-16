package devvy.me.minestrike.game;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.round.RoundManager;

public class GameManager {

    private Minestrike plugin;
    private final RoundManager roundManager;

    public GameManager(Minestrike plugin) {
        this.plugin = plugin;
        roundManager = new RoundManager(plugin);
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
