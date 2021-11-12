package devvy.me.minestrike.phase;

import devvy.me.minestrike.game.BombSite;
import devvy.me.minestrike.team.CSTeam;
import devvy.me.minestrike.game.GameState;
import devvy.me.minestrike.team.TeamType;
import devvy.me.minestrike.player.CSPlayer;
import devvy.me.minestrike.tasks.ExperienceTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ActionPhase extends PhaseBase {

    private final int SUICIDE_MONEY_BONUS = 300;
    private final int TEAMKILL_MONEY_PENALTY = -300;

    private ExperienceTimer timer;

    public ActionPhase() {
        super();
    }

    @Override
    public void start() {

        plugin.getGameManager().setState(GameState.ROUND_IN_PROGRESS);

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.AQUA + "Starting Action Phase...");

        timer = new ExperienceTimer(plugin, type().DEFAULT_TICK_LENGTH);
        timer.startTimer();

        plugin.getGameManager().getBombs().forEach(BombSite::show);

        for (CSPlayer csp : plugin.getGameManager().getAllPlayers()) {
            csp.getSpigotPlayer().setInvulnerable(false);
            csp.getSpigotPlayer().setInvisible(false);
        }
    }

    @Override
    public void end() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.RED + "Ending Action Phase...");

        timer.endTimer();

        for (CSPlayer csp : plugin.getGameManager().getAllPlayers())
            csp.getSpigotPlayer().setInvulnerable(true);

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
            CSPlayer randomPlayer = oppositeTeam.getRandomMember();
            if (randomPlayer != null)
                randomPlayer.addMoney(SUICIDE_MONEY_BONUS, ChatColor.AQUA + "ENEMY SUICIDE!");

        }else{
            CSPlayer killerCSPlayer = plugin.getGameManager().getPlayerManager().getCSPlayer(p);
            CSTeam killersTeam = plugin.getGameManager().getTeamManager().getPlayerTeam(killerCSPlayer);

            //teamkill penalty
            if (killersTeam == victimsTeam)
                killerCSPlayer.addMoney(TEAMKILL_MONEY_PENALTY, ChatColor.DARK_RED + "TEAMKILL PEANLTY!");

        }

        player.setGameMode(GameMode.SPECTATOR);

        // Should we end the round?
        if (victimsTeam.getNumMembersAlive() <= 0) {
            plugin.getGameManager().setState(calculateCurrentState());
            plugin.getGameManager().getPhaseManager().teamWonRound(plugin.getGameManager().getTeamManager().getOppositeTeam(victimsTeam));
        }

        Bukkit.getServer().broadcastMessage(player.getDisplayName() + " died!");

    }

    @Override
    public PhaseType type() {
        return PhaseType.ACTION;
    }

    @Override
    public PhaseType next() {
        return PhaseType.INTERMISSION;
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
