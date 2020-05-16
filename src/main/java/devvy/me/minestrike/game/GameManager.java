package devvy.me.minestrike.game;

import Players.PlayerManager;
import Players.csPlayer;
import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.Play.Team;
import devvy.me.minestrike.Play.TeamManager;
import devvy.me.minestrike.Play.TeamType;
import devvy.me.minestrike.round.RoundManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameManager implements Listener {

    private Minestrike plugin;
    private final RoundManager roundManager;
    private final TeamManager teamManager;
    private final PlayerManager playerManager;

    public GameManager(Minestrike plugin) {
        this.plugin = plugin;

        roundManager = new RoundManager(plugin);
        teamManager = new TeamManager();
        playerManager = new PlayerManager(plugin);

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getPluginManager().registerEvents(teamManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(playerManager, plugin);
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

    @EventHandler
    public void handlePlayerJoined(PlayerJoinEvent event){

        // Handle what we should do for their team
        Player player = event.getPlayer();
        Team team = teamManager.getPlayerTeam(player);
        if (team.getType() == TeamType.SPECTATORS) {
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
