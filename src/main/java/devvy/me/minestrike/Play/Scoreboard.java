package devvy.me.minestrike.Play;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

public class Scoreboard implements Listener {

    public Scoreboard(){

    }
    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = (Scoreboard) manager.getNewScoreboard();

        Objective o = ((org.bukkit.scoreboard.Scoreboard) scoreboard).registerNewObjective("Scoreboard", "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&d&lExmo&f&lPvP"));

        Score spacer1 = o.getScore(ChatColor.translateAlternateColorCodes('&', " "));
        spacer1.setScore(8);

        Score server = o.getScore(ChatColor.translateAlternateColorCodes('&', "&d&lServer"));
        server.setScore(7);

        Score servername = o.getScore(ChatColor.translateAlternateColorCodes('&', "&fHub"));
        servername.setScore(6);

        Score spacer2 = o.getScore(ChatColor.translateAlternateColorCodes('&', "  "));
        spacer2.setScore(5);

        Score players = o.getScore(ChatColor.translateAlternateColorCodes('&', "&d&lPlayers"));
        players.setScore(4);

        Score playercount = o.getScore(ChatColor.translateAlternateColorCodes('&', "&f" + Bukkit.getServer().getOnlinePlayers().size()));
        playercount.setScore(3);

        Score spacer3 = o.getScore(ChatColor.translateAlternateColorCodes('&', "   "));
        spacer3.setScore(2);

        Score ip = o.getScore(ChatColor.translateAlternateColorCodes('&', "&fplay.exmopvp.net"));
        ip.setScore(1);

        player.setScoreboard((org.bukkit.scoreboard.Scoreboard) scoreboard);
    }
}
