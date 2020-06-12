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
    private int roundNumber;

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

        int moneyToGive;    // However much money you want to give
        int tLosses;        //team losses
        int ctLosses;
        CSPlayer p = null;

        //gives every player their money for the round
        for (Player player : Bukkit.getOnlinePlayers()){

            //conversts player to CSPlayer
            p = plugin.getGameManager().getPlayerManager().getCSPlayer(player);
            CSTeam pTeam = plugin.getGameManager().getTeamManager().getPlayerTeam(p);
            CSTeam oppositeTeam = plugin.getGameManager().getTeamManager().getOppositeTeam(pTeam);

            //makes sure someone doesnt get LOADED
            moneyToGive = 0;

            //team money management
            switch (team.getType()) {

                //t side money management
                case ATTACKERS:

                    //TODO: win by eliminating money managing
                    if (oppositeTeam.getNumMembersAlive() == 0) {
                        moneyToGive = moneyToGive + 3250;
                        tLosses = 0;
                    }

                    //TODO: win by bomb exploding money managing
                    /*
                    if ( round is won by bomb exploding ) {
                        moneyToGive = moneyToGive + 3500;
                        tLosses = 0;
                    }
                    */

                    //TODO: T side round loss
                    /*
                    if ( round is lost ) {
                        //adds 1 loss to t side
                        tLosses = tLosses + 1;      //TODO: max loss counter at 5

                        //t side money calculated for losing.. imagine losing lol
                        //TODO: make sure living terrorists don't get any of this cash money
                        moneyToGive = moneyToGive + 900 + 500 * tLosses;

                        //TODO: if bomb is planted add 800
                        //player lives and bomb is not planted
                        if (player is alive and no bomb was planted)
                            moneyToGive = 0;
                    }
                    */
                    //done with t-side
                    break;

                case DEFENDERS:

                    //TODO: win by eliminating money managing
                    if (oppositeTeam.getNumMembersAlive() == 0) {
                        moneyToGive = moneyToGive + 3250;
                        ctLosses = 0;
                    }

                    //TODO: win by defusing bomb money managing
                    /*
                    if ( bomb is defused ) {
                        moneyToGive = moneyToGive + 3500;   //to the team
                        moneyToGive = moneyToGive + 300;    //to the player
                        ctLosses = 0;
                    }
                    */

                    //TODO: Win by running out of time
                    /*
                    if ( time runs out ) {
                        moneyToGive = moneyToGive + 3250;
                        ctLosses = 0;
                    }
                    */

                    //TODO: CT side round loss
                    /*
                    if ( round is lost ) {
                        //adds 1 loss to t side
                        ctLosses = ctLosses + 1;    //TODO: max loss counter at 5

                        //ct side money calculated for losing
                        moneyToGive = moneyToGive + 900 + 500 * ctLosses;
                    }
                    */
                    //done with T side
                    break;

            }

            //pays the player
            p.addMoney(moneyToGive);

        }

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
            currentRound = currentRound.next().CLAZZ.getConstructor(Minestrike.class).newInstance(plugin);
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
