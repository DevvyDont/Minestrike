package devvy.me.minestrike.round;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.game.CSTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

public class RoundManager {

    private final Minestrike plugin;
    private RoundBase currentRound;
    private BukkitRunnable nextRoundTask;

    public RoundManager(Minestrike plugin) {
        this.plugin = plugin;
    }

    public RoundBase getCurrentRound() {
        return currentRound;
    }

    public void startRoundLoop(){

        if (nextRoundTask != null){
            endRoundLoop();
        }

        currentRound = new BuyRound();
        currentRound.start();

        // Go to the next round after DEFAULT_TICK_LENGTH ticks
        doNextRoundLater(currentRound.type().DEFAULT_TICK_LENGTH, false);

    }

    public void endRoundLoop(){
        nextRoundTask.cancel();
        currentRound.end();
        currentRound = null;
        nextRoundTask = null;
    }

    public void teamWonRound(CSTeam team){
        team.addRoundWin();
        Bukkit.broadcastMessage(ChatColor.GRAY + team.getName() + " won the round!");
        nextRound();
    }

    public void nextRound(){

        if (currentRound == null)
            throw new IllegalStateException("There is no game running right now, can't go to the next round!");

        Bukkit.getLogger().info("Ending the current round");
        nextRoundTask.cancel();
        currentRound.end();

        try {
            currentRound = currentRound.next().CLAZZ.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException exception){
            exception.printStackTrace();
            Bukkit.getLogger().warning("Failed to create new round. Ending the game...");
            endRoundLoop();
            return;
        }

        Bukkit.getLogger().info("Starting the next round");
        currentRound.start();

        doNextRoundLater(currentRound.type().DEFAULT_TICK_LENGTH, currentRound instanceof ActionRound);
    }

    /**
     * Starts next round ticks ticks later
     *
     * @param ticks - ticks to run next round later
     */
    private void doNextRoundLater(int ticks, boolean actionRoundFlag){
        // Go to the next round after DEFAULT_TICK_LENGTH ticks
        nextRoundTask = new BukkitRunnable(){
            @Override
            public void run() {

                if (actionRoundFlag)
                    teamWonRound(plugin.getGameManager().getTeamManager().getDefenders());
                 else
                    nextRound();
            }
        };

        nextRoundTask.runTaskLater(plugin, ticks);
    }

}
