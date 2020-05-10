package devvy.me.minestrike.round;

import org.bukkit.Bukkit;

public class RoundManager {

    private Round currentRound;

    public Round getCurrentRound() {
        return currentRound;
    }

    public void startRoundLoop(){
        currentRound = new BuyRound();
        currentRound.start();
    }

    public void endRoundLoop(){
        currentRound.end();
        currentRound = null;
    }

    public void nextRound(){

        Bukkit.getLogger().info("Ending the current round");
        currentRound.end();

        try {
            currentRound = currentRound.next().CLAZZ.newInstance();
        } catch (InstantiationException | IllegalAccessException exception){
            Bukkit.getLogger().warning("Failed to create new round. Ending the game...");
            currentRound = null;
            return;
        }

        Bukkit.getLogger().info("Starting the next round");
        currentRound.start();

    }

}
