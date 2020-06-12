package devvy.me.minestrike.game;


import devvy.me.minestrike.items.GlobalDamageManager;
import devvy.me.minestrike.scoreboard.Sidebar;
import devvy.me.minestrike.player.CSPlayer;
import devvy.me.minestrike.player.PlayerManager;
import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.phase.PhaseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GameManager implements Listener {

    private final PhaseManager phaseManager;
    private final TeamManager teamManager;
    private final PlayerManager playerManager;
    private final GlobalDamageManager globalDamageManager;
    private Sidebar scoreboardManager;

    private GameState state;

    private int roundNumber = 1;

    public GameManager() {

        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);

        phaseManager = new PhaseManager(plugin);
        teamManager = new TeamManager();
        playerManager = new PlayerManager(plugin);
        globalDamageManager = new GlobalDamageManager();
        state = GameState.WAITING;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getPluginManager().registerEvents(teamManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(playerManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(globalDamageManager, plugin);
    }

    public void initializeTabList(){
        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);
        scoreboardManager = new Sidebar();
        for(Player player: Bukkit.getOnlinePlayers()){
            scoreboardManager.updatePlayerClanTag(player);
        }

        plugin.getServer().getPluginManager().registerEvents(scoreboardManager, plugin);

    }


    public PhaseManager getPhaseManager() {
        return phaseManager;
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public GlobalDamageManager getGlobalDamageManager() {
        return globalDamageManager;
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
        phaseManager.startPhaseLoop();
    }

    public void nextPhase(){
        phaseManager.nextPhase();
    }

    public void endGame(){
        phaseManager.endPhaseLoop();
    }

    public boolean isGameRunning(){
        return phaseManager.getCurrentPhase() != null;
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
