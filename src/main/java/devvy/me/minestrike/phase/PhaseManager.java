package devvy.me.minestrike.phase;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.game.CSTeam;
import devvy.me.minestrike.player.CSPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

public class PhaseManager {

    private final Minestrike plugin;
    private PhaseBase currentPhase;
    private BukkitRunnable nextPhaseTask;

    public PhaseManager(Minestrike plugin) {
        this.plugin = plugin;
    }

    public PhaseBase getCurrentPhase() {
        return currentPhase;
    }

    public void startPhaseLoop(){

        if (nextPhaseTask != null){
            endPhaseLoop();
        }

        currentPhase = new BuyPhase();
        currentPhase.start();

        // Go to the next phase after DEFAULT_TICK_LENGTH ticks
        doNextPhaseLater(currentPhase.type().DEFAULT_TICK_LENGTH, false);

    }

    public void endPhaseLoop(){
        nextPhaseTask.cancel();
        currentPhase.end();
        currentPhase = null;
        nextPhaseTask = null;
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
        nextPhase();

        plugin.getGameManager().incrementRoundNumber();
    }

    public void nextPhase(){

        if (currentPhase == null)
            throw new IllegalStateException("There is no game running right now, can't go to the next phase!");

        Bukkit.getLogger().info("Ending the current phase");
        nextPhaseTask.cancel();
        currentPhase.end();

        try {
            currentPhase = currentPhase.next().CLAZZ.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException exception){
            exception.printStackTrace();
            Bukkit.getLogger().warning("Failed to create new phase. Ending the game...");
            endPhaseLoop();
            return;
        }

        Bukkit.getLogger().info("Starting the next phase");
        currentPhase.start();

        doNextPhaseLater(currentPhase.type().DEFAULT_TICK_LENGTH, currentPhase instanceof ActionPhase);
    }

    /**
     * Starts next round ticks ticks later
     *
     * @param ticks - ticks to run next round later
     */
    private void doNextPhaseLater(int ticks, boolean actionPhaseFlag){
        // Go to the next phase after DEFAULT_TICK_LENGTH ticks
        nextPhaseTask = new BukkitRunnable(){
            @Override
            public void run() {

                if (actionPhaseFlag)
                    teamWonRound(plugin.getGameManager().getTeamManager().getDefenders());
                 else
                    nextPhase();
            }
        };

        nextPhaseTask.runTaskLater(plugin, ticks);
    }

    /**
     * Called if for whatever reason we need to end the game timer, planting bombs needs to call this
     */
    public void endPhaseTimer() {
        nextPhaseTask.cancel();
    }

}
