package devvy.me.minestrike.phase;

import devvy.me.minestrike.GUI.BuyMenu;
import devvy.me.minestrike.game.BombSite;
import devvy.me.minestrike.game.GameState;
import devvy.me.minestrike.items.CustomItemType;
import devvy.me.minestrike.player.CSPlayer;
import devvy.me.minestrike.timers.ExperienceTimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class BuyPhase extends PhaseBase implements Listener {

    private ExperienceTimer timer;

    public BuyPhase() {
        super();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void start() {
        plugin.getGameManager().getTeamManager().getAttackers().getSpawn().teleportMembersToSpawnPoints();
        plugin.getGameManager().getTeamManager().getDefenders().getSpawn().teleportMembersToSpawnPoints();

        plugin.getGameManager().setState(GameState.BUY_IN_PROGRESS);
        plugin.getGameManager().broadcast(ChatColor.AQUA + "Starting Buy Phase...");

        // A loop for what we should do for all players playing
        for (CSPlayer p : plugin.getGameManager().getAllPlayers()) {
            p.getSpigotPlayer().setGameMode(GameMode.ADVENTURE);
            p.getSpigotPlayer().setHealth(p.getSpigotPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            p.getSpigotPlayer().getInventory().remove(plugin.getCustomItemManager().getCustomItemStack(CustomItemType.BOMB));

            // Give them the emerald
            if (p.getSpigotPlayer().getInventory().getItem(8) == null)
                p.getSpigotPlayer().getInventory().setItem(8, new ItemStack(Material.EMERALD));
            else
                p.getSpigotPlayer().getInventory().addItem(new ItemStack(Material.EMERALD));
        }

        timer = new ExperienceTimer(plugin, type().DEFAULT_TICK_LENGTH);
        timer.startTimer();

        // Randomly give an attacker a bomb
        CSPlayer randomAttacker = plugin.getGameManager().getTeamManager().getAttackers().getRandomMember();
        if (randomAttacker != null)
            randomAttacker.getSpigotPlayer().getInventory().addItem(plugin.getCustomItemManager().getCustomItemStack(CustomItemType.BOMB));

        // Reset all the bomb sites
        plugin.getGameManager().getBombs().forEach(BombSite::reset);
    }

    @Override
    public void end() {

        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(ChatColor.RED + "Ending Buy Phase...");

        timer.endTimer();

        for (CSPlayer csp : plugin.getGameManager().getAllPlayers()) {
            csp.getSpigotPlayer().getInventory().remove(Material.EMERALD);
            csp.getSpigotPlayer().closeInventory();
        }

        HandlerList.unregisterAll(new BuyMenu());
        HandlerList.unregisterAll(this);

    }

    @Override
    public void handlePlayerDeath(Player player) {
        plugin.getLogger().warning(player.getName() + " died during the buy phase. This shouldn't happen.");
    }

    @Override
    public PhaseType type() {
        return PhaseType.BUY;
    }

    @Override
    public PhaseType next() {
        return PhaseType.ACTION;
    }
    public ExperienceTimer getTimer() {
        return timer;
    }

    @EventHandler
    public void onAttemptMove(PlayerMoveEvent event) {

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR)
            return;

        int oldBlockX = event.getFrom().getBlockX();
        int oldBlockZ = event.getFrom().getBlockZ();
        int newBlockX = event.getTo().getBlockX();
        int newBlockZ = event.getTo().getBlockZ();

        boolean moved = oldBlockX != newBlockX || oldBlockZ != newBlockZ;

        if (moved)
            event.setCancelled(true);

    }

}
