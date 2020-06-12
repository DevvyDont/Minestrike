package devvy.me.minestrike.round;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.game.CSTeam;
import devvy.me.minestrike.player.CSPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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

    public void teamLostRound(CSTeam team){

        //checks if round 1 or 16
        switch (plugin.getGameManager().getRoundNumber()){

            case 1:
            case 16:
                team.incrimentLossStreak();

                break;

        }

        //adds 1 to loss streak
        team.incrimentLossStreak();
        CSTeam oppositeTeam = plugin.getGameManager().getTeamManager().getOppositeTeam(team);

        //we know 'team' lost the round
        switch (team.getType()){

            //case where defending team lost the round
            case DEFENDERS:

                switch (plugin.getGameManager().getState()){

                    //case where defending team lost the round
                    //team was eliminated or bomb went boom
                    case TEAM_WAS_ELIMINATED:
                    case BOMB_EXPLODED:

                        //gives loss money
                        team.addMoneyToAllMembers(900 + 500 * team.getLossStreak());

                        break;

                }

                break;

            //case where attacking team lost the round
            case ATTACKERS:

                switch (plugin.getGameManager().getState()){

                    //case where attacking team lost the round
                    //time ran out
                    case TIME_RAN_OUT:
                        for (CSPlayer player : team.getMembers()){

                            if (!(player.isAlive()))
                                player.addMoney(900 + 500 * team.getLossStreak());

                        }

                        break;


                    //case where attacking team lost the round
                    //team was eliminated
                    case TEAM_WAS_ELIMINATED:

                        //gives loss money
                        team.addMoneyToAllMembers(900 + 500 * team.getLossStreak());

                        break;

                    //case where attacking team lost the round
                    //bomb is defused
                    case BOMB_IS_DEFUSED:

                        //gives loss money
                        team.addMoneyToAllMembers(1700 + 500 * team.getLossStreak());
                }

                break;

        }

    }

    public void teamWonRound(CSTeam team){

        //resets team loss streak
        team.resetLossStreak();
        CSTeam oppositeTeam = plugin.getGameManager().getTeamManager().getOppositeTeam(team);
        teamLostRound(oppositeTeam);

        //we know 'team' won the round
        switch (team.getType()){

            //case where defending team won the round
            case DEFENDERS:

                switch (plugin.getGameManager().getState()){

                    //case where defending team won the round
                    //bomb was defused
                    case BOMB_IS_DEFUSED:

                        //gives loss money
                        team.addMoneyToAllMembers(3500);

                        break;


                    //case where defending team won
                    //Ts eliminated, or time ran out
                    case TIME_RAN_OUT:
                    case TEAM_WAS_ELIMINATED:

                        //gives loss money
                        team.addMoneyToAllMembers(3250);

                        break;

                }

                break;

            //case where attacking team won the round
            case ATTACKERS:

                switch (plugin.getGameManager().getState()){

                    //case where attacking team won
                    //CTs eliminated
                    case TEAM_WAS_ELIMINATED:

                        //gives loss money
                        team.addMoneyToAllMembers(3500);

                    //case where defending team won
                    //bomb exploded
                    case BOMB_EXPLODED:

                        //gives loss money
                        team.addMoneyToAllMembers(3250);

                        break;
                }

                break;
        }

        team.addRoundWin();
        Bukkit.broadcastMessage(ChatColor.GRAY + team.getName() + " won the round!");
        nextRound();

        plugin.getGameManager().incrementRoundNumber();
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
