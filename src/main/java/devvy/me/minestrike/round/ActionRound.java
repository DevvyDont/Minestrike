package devvy.me.minestrike.round;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.game.CSTeam;
import devvy.me.minestrike.game.GameState;
import devvy.me.minestrike.game.TeamType;
import devvy.me.minestrike.player.CSPlayer;
import devvy.me.minestrike.timers.ExperienceTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ActionRound extends RoundBase {

    private ExperienceTimer timer;

    public ActionRound() {
        super();
    }

    @Override
    public void start() {

        plugin.getGameManager().setState(GameState.ROUND_IN_PROGRESS);

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.AQUA + "Starting Action Round...");

        timer = new ExperienceTimer(plugin, type().DEFAULT_TICK_LENGTH);
        timer.startTimer();
    }

    @Override
    public void end() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.RED + "Ending Action Round...");

        timer.endTimer();
    }

    public ExperienceTimer getTimer() {
        return timer;
    }

    @Override
    public void handlePlayerDeath(Player player) {

        CSPlayer csPlayer = plugin.getGameManager().getPlayerManager().getCSPlayer(player);

        // Get the player's team
        CSTeam victimsTeam = plugin.getGameManager().getTeamManager().getPlayerTeam(csPlayer);

        // Just in case
        if (victimsTeam.getType() == TeamType.SPECTATORS)
            return;

        CSTeam oppositeTeam = plugin.getGameManager().getTeamManager().getOppositeTeam(victimsTeam);

        //finds who killed the player
        Player p = player.getKiller();   //why is p an error?
        int moneyToGive;

        //suicide. literally
        if (p == null){     //TODO: handle bomb explosion logic
            moneyToGive = 300; //to a random player on the enemy team
            CSPlayer randomPlayer = oppositeTeam.getRandomMember();

            if (randomPlayer != null)
                oppositeTeam.getRandomMember().addMoney(moneyToGive);

        }else{
            CSPlayer killerCSPlayer = plugin.getGameManager().getPlayerManager().getCSPlayer(p);
            CSTeam killersTeam = plugin.getGameManager().getTeamManager().getPlayerTeam(killerCSPlayer);

            //teamkill penalty
            if (killersTeam == victimsTeam) {
                moneyToGive = -300;
                killerCSPlayer.addMoney(moneyToGive);

            }

        }

        player.setGameMode(GameMode.SPECTATOR);

        // Should we end the round?
        if (victimsTeam.getNumMembersAlive() <= 0) {
            plugin.getGameManager().setState(calculateCurrentState());
            plugin.getGameManager().getRoundManager().teamWonRound(plugin.getGameManager().getTeamManager().getOppositeTeam(victimsTeam));
        }

        Bukkit.getServer().broadcastMessage(player.getDisplayName() + " died!");

    }

    @Override
    public RoundType type() {
        return RoundType.ACTION;
    }

    @Override
    public RoundType next() {
        return RoundType.INTERMISSION;
    }

    private GameState calculateCurrentState() {

        CSTeam attackers = plugin.getGameManager().getTeamManager().getAttackers();
        CSTeam defenders = plugin.getGameManager().getTeamManager().getDefenders();


        // TODO: Analyze bomb plant status

        // is everyone dead on a team?
        if (attackers.getNumMembersAlive() == 0 || defenders.getNumMembersAlive() == 0)
            return GameState.TEAM_WAS_ELIMINATED;
        // Did the time run out?
        else if (timer.getPercentTimeCompleted() >= 1.0) {
            return GameState.TIME_RAN_OUT;
        }

        // Round is still running
        return GameState.ROUND_IN_PROGRESS;

    }
}
