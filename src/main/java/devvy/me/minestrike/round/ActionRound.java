package devvy.me.minestrike.round;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.game.CSTeam;
import devvy.me.minestrike.game.TeamType;
import devvy.me.minestrike.timers.ExperienceTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class ActionRound extends RoundBase {

    private ExperienceTimer timer;
    private Minestrike plugin;

    public ActionRound(Minestrike plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void start() {

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

    @Override
    public void handlePlayerDeath(Player player) {

        // Get the player's team
        CSTeam victimsTeam = plugin.getGameManager().getTeamManager().getPlayerTeam(player);

        // Just in case
        if (victimsTeam.getType() == TeamType.SPECTATORS)
            return;

        player.setGameMode(GameMode.SPECTATOR);

        // Should we end the round?
        if (victimsTeam.getNumMembersAlive() <= 0)
            plugin.getGameManager().getRoundManager().teamWonRound(plugin.getGameManager().getTeamManager().getOppositeTeam(victimsTeam));

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
}
