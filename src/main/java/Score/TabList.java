package Score;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.game.TeamManager;
import devvy.me.minestrike.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class TabList implements Listener {
    private org.bukkit.scoreboard.Team t;
    private org.bukkit.scoreboard.Team ct;
    private org.bukkit.scoreboard.Team spec;
    private Scoreboard board;



    public TabList() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();

        t = board.registerNewTeam("Terrorists");
        t.setPrefix(ChatColor.GOLD + "[T] " + ChatColor.RED);
        t.setAllowFriendlyFire(true);
        t.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);

        ct = board.registerNewTeam("CT");
        ct.setPrefix(ChatColor.BLUE + "[CT] " + ChatColor.LIGHT_PURPLE);
        ct.setAllowFriendlyFire(false);
        ct.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);

        spec = board.registerNewTeam("Spectators");
        spec.setPrefix(ChatColor.GRAY + "[SPEC] ");

    }

    public void updatePlayerClanTag(Player player){
        player.setScoreboard(board);
        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);
        TeamManager teamManager = plugin.getGameManager().getTeamManager();
        PlayerManager playerManager = plugin.getGameManager().getPlayerManager();


        if (teamManager.getDefenders().hasMember(playerManager.getCSPlayer(player))){
            ct.addPlayer(player);

        }else if (teamManager.getAttackers().hasMember(playerManager.getCSPlayer(player))){
            t.addPlayer(player);

        }else {
            spec.addPlayer(player);
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        updatePlayerClanTag(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if(t.hasPlayer(event.getPlayer())){
            t.removePlayer(event.getPlayer());
        }else if (ct.hasPlayer(event.getPlayer())){
            ct.removePlayer(event.getPlayer());
        }else{
            spec.removePlayer(event.getPlayer());
        }
    }

    public void updateScoreboard(){
        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);
        for (Player p : plugin.getServer().getOnlinePlayers()){
            p.setDisplayName(p.getName()  + ChatColor.RED + " K/D: " + plugin.getGameManager().getPlayerManager().getCSPlayer(p).getKdTracker().getKDRation() + ChatColor.WHITE);
        }
        //Need to update scoreboard KD

    }
}

