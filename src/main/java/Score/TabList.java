package Score;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.game.CSTeam;
import devvy.me.minestrike.game.TeamManager;
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
    private Scoreboard board;
    private CSTeam terrorists = new TeamManager().getAttackers();
    private CSTeam counterTerror = new TeamManager().getDefenders();
    private Player player;


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
        TeamManager teamManager = new TeamManager();
        if (teamManager.getDefenders().hasMember(player)){
            ct.addPlayer(player);
        }else if (teamManager.getAttackers().hasMember(player)){
            t.addPlayer(player);
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(terrorists.hasMember(player)){
            t.addPlayer(player);
        }else{
            ct.addPlayer(player);
        }
        player.setScoreboard(board);

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (terrorists.hasMember(player)) {
            terrorists.removeMember(player);
        } else if (counterTerror.hasMember(player)) {
            counterTerror.removeMember(player);

        }
    }

    public void updateScoreboard(Minestrike plugin){
        for (Player p : plugin.getServer().getOnlinePlayers()){
            p.setDisplayName(p.getName()  + " K/D: " + plugin.getGameManager().getPlayerManager().getCSPlayer(p).getKdTracker().getKDRation());
        }
        //Need to update scoreboard KD

    }
}

