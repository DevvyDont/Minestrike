package devvy.me.minestrike.game;


import devvy.me.minestrike.scoreboard.Sidebar;
import devvy.me.minestrike.player.CSPlayer;
import devvy.me.minestrike.player.PlayerManager;
import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.round.RoundManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GameManager implements Listener {

    private final RoundManager roundManager;
    private final TeamManager teamManager;
    private final PlayerManager playerManager;
    private Sidebar scoreboardManager;

    private GameState state;

    private int roundNumber = 1;

    public GameManager() {

        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);

        roundManager = new RoundManager(plugin);
        teamManager = new TeamManager();
        playerManager = new PlayerManager(plugin);
        scoreboardManager = new Sidebar();
        state = GameState.WAITING;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getPluginManager().registerEvents(teamManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(playerManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(scoreboardManager, plugin);
    }
    public void initializeTabList(){
        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);
        scoreboardManager = new Sidebar();
        for(Player player: Bukkit.getOnlinePlayers()){
            scoreboardManager.updatePlayerClanTag(player);
        }

        plugin.getServer().getPluginManager().registerEvents(scoreboardManager, plugin);

    }


    public RoundManager getRoundManager() {
        return roundManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void incrementRoundNumber(){
        roundNumber++;
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


    /**************************************************************************************
    EVENTS TO HANDLE
     *************************************************************************************/

    @EventHandler
    public void handlePlayerJoined(PlayerJoinEvent event){


        // Handle what we should do for their team
        CSPlayer player = getPlayerManager().getCSPlayer(event.getPlayer());
        CSTeam team = teamManager.getPlayerTeam(player);
        if (team.getType() == TeamType.SPECTATORS) {

            team.removeMember(player);  // Remove them as a spectator

            if (teamManager.getDefenders().size() < teamManager.getAttackers().size()) {
                teamManager.getDefenders().addMember(player);
                System.out.println("ct" + teamManager.getDefenders());
            } else {
                teamManager.getAttackers().addMember(player);
                System.out.println("t " + teamManager.getAttackers());
            }
        }

    }


}
