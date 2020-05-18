package Score;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.game.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Sidebar implements Listener {
    private Scoreboard board;
    private Objective objective;
    private Score kd;
    private Score rounds;
    private Score teams;
    private Score currency;

    public Sidebar() {

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        updateScoreboard(player);
    }



    public void updateScoreboard(Player player) {
        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);
        double ratio = plugin.getGameManager().getPlayerManager().getCSPlayer(player).getKdTracker().getKDRation();
        int roundScore;
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        objective = board.registerNewObjective("Stats", "test");
        objective.setDisplayName("Scoreboard");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);


        rounds = objective.getScore(ChatColor.GOLD + "ct rounds " + ChatColor.LIGHT_PURPLE + ": t rounds");
        rounds.setScore(4);

        teams = objective.getScore(ChatColor.GOLD + "ct " + ChatColor.LIGHT_PURPLE + "t");
        teams.setScore(3);

        currency = objective.getScore(ChatColor.GREEN + "currency");
        currency.setScore(2);

        kd = objective.getScore(ChatColor.YELLOW + "K / D : " + ratio);
        kd.setScore(1);
        player.setScoreboard(board);
    }
}
