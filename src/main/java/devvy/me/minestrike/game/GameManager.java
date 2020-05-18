package devvy.me.minestrike.game;

import Score.TabList;
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

    private Minestrike plugin;
    private final RoundManager roundManager;
    private final TeamManager teamManager;
    private final PlayerManager playerManager;
    private final TabList tabList;

    public GameManager(Minestrike plugin) {
        this.plugin = plugin;

        roundManager = new RoundManager(plugin);
        teamManager = new TeamManager();
        playerManager = new PlayerManager(plugin);
        tabList = new TabList();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getPluginManager().registerEvents(teamManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(playerManager, plugin);
        plugin.getServer().getPluginManager().registerEvents(tabList, plugin);
    }
    public void initializeTabList(){
        for(Player player: Bukkit.getOnlinePlayers()){
            tabList.updatePlayerClanTag(player);
        }
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
