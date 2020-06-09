package Score;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.game.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import devvy.me.minestrike.player.PlayerManager;

public class Sidebar implements Listener {

    private Scoreboard mainScoreboard;
    private Objective mainScoreboardObjective;

    private Score blankLine1;
    private Team mapLine;
    private Score blankLine2;
    private Team scoreLabelLine;
    private Team actualScoreLine;
    private Score blankLine3;
    private Team timeRemLine;

    private Team t;
    private Team ct;
    private Team spec;

    public Sidebar() {

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        mainScoreboard = manager.getNewScoreboard();
        mainScoreboardObjective = mainScoreboard.registerNewObjective("Stats", "dummy", ChatColor.RED + "MINESTRIKE");
        mainScoreboardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        blankLine1 = mainScoreboardObjective.getScore("  ");
        mapLine = mainScoreboard.registerNewTeam("mapLine");
        blankLine2 = mainScoreboardObjective.getScore(" ");
        scoreLabelLine = mainScoreboard.registerNewTeam("scoreLabelLine");
        actualScoreLine = mainScoreboard.registerNewTeam("actualScoreLine");
        blankLine3 = mainScoreboardObjective.getScore("   ");
        timeRemLine = mainScoreboard.registerNewTeam("timeRemLine");

        blankLine1.setScore(14);

        mapLine.addEntry(ChatColor.RED.toString());
        mainScoreboardObjective.getScore(ChatColor.RED.toString()).setScore(13);

        blankLine2.setScore(12);

        scoreLabelLine.addEntry(ChatColor.GREEN.toString());
        mainScoreboardObjective.getScore(ChatColor.GREEN.toString()).setScore(11);

        actualScoreLine.addEntry(ChatColor.BLUE.toString());
        mainScoreboardObjective.getScore(ChatColor.BLUE.toString()).setScore(10);

        blankLine3.setScore(9);

        timeRemLine.addEntry(ChatColor.GRAY.toString());
        mainScoreboardObjective.getScore(ChatColor.GRAY.toString()).setScore(8);

        t = mainScoreboard.registerNewTeam("Terrorists");
        t.setPrefix(ChatColor.GOLD + "[T] " + ChatColor.RED);
        t.setAllowFriendlyFire(false);
        t.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);

        ct = mainScoreboard.registerNewTeam("Defenders");
        ct.setPrefix(ChatColor.BLUE + "[CT] " + ChatColor.LIGHT_PURPLE);
        ct.setAllowFriendlyFire(false);
        ct.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);

        spec = mainScoreboard.registerNewTeam("Spectators");
        spec.setPrefix(ChatColor.GRAY + "[SPEC] " + ChatColor.WHITE);
        spec.setNameTagVisibility(NameTagVisibility.ALWAYS);

        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);

        for (Player p : Bukkit.getOnlinePlayers())
            p.setScoreboard(mainScoreboard);

        updateScoreboard();

        new BukkitRunnable() {

            @Override
            public void run() {
                updateScoreboard();
            }

        }.runTaskTimer(plugin, 0, 20);
    }

    public void updatePlayerClanTag(Player player){

        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);
        TeamManager teamManager = plugin.getGameManager().getTeamManager();
        PlayerManager playerManager = plugin.getGameManager().getPlayerManager();

        if (teamManager.getDefenders().hasMember(playerManager.getCSPlayer(player)))
            ct.addPlayer(player);
        else if (teamManager.getAttackers().hasMember(playerManager.getCSPlayer(player)))
            t.addPlayer(player);
        else
            spec.addPlayer(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setScoreboard(mainScoreboard);
        updatePlayerClanTag(event.getPlayer());
    }

    public void updateScoreboard() {

        // TODO: implement these variables
        String mapName = "not implemented";
        int ctScore = 0;
        int tScore = 0;
        int timeRemaining = 0;
        int ctsAlive = 5;
        int tsAlive = 5;

        mapLine.setPrefix("Current Map: ");
        mapLine.setSuffix(mapName);
        scoreLabelLine.setPrefix("== Score ==");
        actualScoreLine.setPrefix(String.format("%d - %d", ctScore, tScore));
        timeRemLine.setPrefix(String.format("%ds ", timeRemaining));
        timeRemLine.setSuffix(String.format("[%d v %d]", ctsAlive, tsAlive));

    }

}
