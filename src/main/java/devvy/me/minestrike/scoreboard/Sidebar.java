package devvy.me.minestrike.scoreboard;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.game.CSTeam;
import devvy.me.minestrike.game.GameManager;
import devvy.me.minestrike.game.TeamManager;
import devvy.me.minestrike.round.ActionRound;
import devvy.me.minestrike.round.BuyRound;
import devvy.me.minestrike.round.IntermissionRound;
import devvy.me.minestrike.round.RoundType;
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

    private final Scoreboard mainScoreboard;

    private final Team mapLine;
    private final Team scoreLabelLine;
    private final Team actualScoreLine;
    private final Team timeRemLine;

    private final Team t;
    private final Team ct;
    private final Team spec;
    private CSTeam defenderWins;
    private CSTeam attackerWins;
    private int tsAlive = 0;
    private int ctsAlive = 0;
    private int timeRemaining = 0;

    public Sidebar() {


        ScoreboardManager manager = Bukkit.getScoreboardManager();
        mainScoreboard = manager.getNewScoreboard();
        Objective mainScoreboardObjective = mainScoreboard.registerNewObjective("Stats", "dummy", ChatColor.RED + "MINESTRIKE");
        mainScoreboardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score blankLine1 = mainScoreboardObjective.getScore("  ");
        mapLine = mainScoreboard.registerNewTeam("mapLine");
        Score blankLine2 = mainScoreboardObjective.getScore(" ");
        scoreLabelLine = mainScoreboard.registerNewTeam("scoreLabelLine");
        actualScoreLine = mainScoreboard.registerNewTeam("actualScoreLine");
        Score blankLine3 = mainScoreboardObjective.getScore("   ");
        Score blankLine4 = mainScoreboardObjective.getScore("    ");
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
        blankLine4.setScore(8);

        timeRemLine.addEntry(ChatColor.GRAY.toString());
        mainScoreboardObjective.getScore(ChatColor.GRAY.toString()).setScore(7);

        t = mainScoreboard.registerNewTeam("Terrorists");
        t.setPrefix(ChatColor.GOLD + "[T] " + ChatColor.RED);
        t.setAllowFriendlyFire(false);
        t.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);  // TODO: use Team#setOption() instead since what we have is deprecated, not sure how this new method functions atm

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
                timeLeft();
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


    public void timeLeft(){

        //pre-round, buy, action, planted, halftime = 15s, intermission = 7s
        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);
        if (plugin.getGameManager().getRoundManager().getCurrentRound() == null){
            return;
        }

        if (plugin.getGameManager().getRoundManager().getCurrentRound().type() == RoundType.ACTION){
            timeRemaining = ((ActionRound) plugin.getGameManager().getRoundManager().getCurrentRound()).getTimer().getSecondsLeft();

        }else if (plugin.getGameManager().getRoundManager().getCurrentRound().type() == RoundType.BUY){
            timeRemaining = ((BuyRound) plugin.getGameManager().getRoundManager().getCurrentRound()).getTimer().getSecondsLeft();
        }else if (plugin.getGameManager().getRoundManager().getCurrentRound().type() == RoundType.INTERMISSION){
            timeRemaining = ((IntermissionRound) plugin.getGameManager().getRoundManager().getCurrentRound()).getTimer().getSecondsLeft();
        }else{
            timeRemaining = 69;
        }
    }


    public void updateScoreboard() {
        timeLeft();

        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);
        // TODO: implement these variables
        String mapName = "Ram Ranch";
        int ctScore = plugin.getGameManager().getTeamManager().getDefenders().getRoundsWon();
        int tScore = plugin.getGameManager().getTeamManager().getAttackers().getRoundsWon();

        ctsAlive = plugin.getGameManager().getTeamManager().getDefenders().getNumMembersAlive();
        tsAlive = plugin.getGameManager().getTeamManager().getAttackers().getNumMembersAlive();

        mapLine.setPrefix("Current Map: ");
        mapLine.setSuffix(mapName);
        scoreLabelLine.setPrefix(ChatColor.YELLOW + "            ✖ Score ✖      ");
        actualScoreLine.setPrefix(String.format("               %s%d -%s- %s%d    ", ChatColor.AQUA, ctScore, ChatColor.RED, ChatColor.RED, tScore));
        timeRemLine.setSuffix(String.format(ChatColor.GREEN + "    %ds ", timeRemaining));
        timeRemLine.setPrefix(String.format(ChatColor.AQUA + "            ♠ [%s%d %s✗ %s%d] ♦", ChatColor.AQUA, ctsAlive, ChatColor.WHITE, ChatColor.RED, tsAlive));

    }

}
