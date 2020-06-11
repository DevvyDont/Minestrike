package devvy.me.minestrike.round;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.items.CustomItemType;
import devvy.me.minestrike.player.CSPlayer;
import devvy.me.minestrike.timers.ExperienceTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class BuyRound extends RoundBase {

    private ExperienceTimer timer;
    private Minestrike plugin;

    public BuyRound(Minestrike plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public void start() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.AQUA + "Starting Buy Round...");

        for (CSPlayer p : plugin.getGameManager().getTeamManager().getAttackers().getMembers()) {
            p.getSpigotPlayer().setGameMode(GameMode.ADVENTURE);
            // Remove any bombs from the t's
            p.getSpigotPlayer().getInventory().remove(plugin.getCustomItemManager().getCustomItemStack(CustomItemType.BOMB));
        }

        for (CSPlayer p : plugin.getGameManager().getTeamManager().getDefenders().getMembers())
            p.getSpigotPlayer().setGameMode(GameMode.ADVENTURE);

        timer = new ExperienceTimer(plugin, type().DEFAULT_TICK_LENGTH);
        timer.startTimer();

        CSPlayer randomAttacker = plugin.getGameManager().getTeamManager().getAttackers().getRandomMember();
        if (randomAttacker != null)
            randomAttacker.getSpigotPlayer().getInventory().addItem(plugin.getCustomItemManager().getCustomItemStack(CustomItemType.BOMB));

    }

    @Override
    public void end() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.RED + "Ending Buy Round...");

        timer.endTimer();

    }

    @Override
    public void handlePlayerDeath(Player player) {
        throw new IllegalStateException("Players cannot die during the buy round!");
    }

    @Override
    public RoundType type() {
        return RoundType.BUY;
    }

    @Override
    public RoundType next() {
        return RoundType.ACTION;
    }
}
