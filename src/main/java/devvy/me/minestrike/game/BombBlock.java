package devvy.me.minestrike.game;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.player.CSPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BombBlock {

    public enum BombState {
        HIDDEN,
        IDLE,
        PLANTED
    }

    private class BombTickTask extends BukkitRunnable {

        private int tick = 0;
        private int target;

        public BombTickTask(int targetTick) {
            this.target = targetTick;
        }

        @Override
        public void run() {
            tick++;
            setText(((target - tick) / 20) + "s");
            if (tick >= target)
                this.cancel();
        }

        @Override
        public synchronized void cancel() throws IllegalStateException {
            super.cancel();

            CSPlayer winner = tick >= target ? planter : defuser;

            // Prob will only happen using admin commands and ending round early, otherwise a bug that this is null
            if (winner == null) {
                plugin.getLogger().warning("Player in charge of winning this round is null. Defaulting to CT win");
                plugin.getGameManager().getPhaseManager().teamWonRound(plugin.getGameManager().getTeamManager().getDefenders());
                return;
            }

            plugin.getGameManager().getPhaseManager().teamWonRound(plugin.getGameManager().getTeamManager().getPlayerTeam(winner));
            explode();
        }
    }

    private Minestrike plugin;
    private BombTickTask task;
    // Origin coordinates
    private Location location;
    private ArmorStand text;
    private BombState state;
    private CSPlayer planter;
    private CSPlayer defuser;


    public BombBlock(Location location) {
        this.plugin = Minestrike.getPlugin(Minestrike.class);
        this.location = location;
        this.text = location.getWorld().spawn(location, ArmorStand.class);
        this.text.setMarker(true);
        this.text.teleport(location.clone().add(.5, 1, .5));
        this.text.setVisible(false);
        this.state = BombState.HIDDEN;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {

        if (state == BombState.PLANTED)
            throw new IllegalStateException("Bomb cannot be moved while planted!");

        this.location.getWorld().getBlockAt(location).setType(Material.AIR);
        this.location = location;
        this.text.teleport(location.clone().add(.5, 1, .5));
        if (state != BombState.HIDDEN)
            location.getWorld().getBlockAt(location).setType(Material.TNT);
    }

    public BombState getState() {
        return state;
    }

    public CSPlayer getPlanter() {
        return planter;
    }

    public CSPlayer getDefuser() {
        return defuser;
    }

    public void generate() {

    }

    // Generally called when a round resets
    public void reset() {
        this.setText("Waiting to be planted...");
        location.getWorld().getBlockAt(location).setType(Material.TNT);
        this.state = BombState.IDLE;
    }

    public void show() {
        this.setText("Waiting to be planted...");
        location.getWorld().getBlockAt(location).setType(Material.TNT);
        this.state = BombState.IDLE;
    }

    public void setText(String text) {

        if (text.isEmpty()) {
            this.text.setCustomNameVisible(false);
            return;
        }

        this.text.setCustomName(text);
        this.text.setCustomNameVisible(true);
    }

    public void hide() {
        location.getWorld().getBlockAt(location).setType(Material.AIR);
        this.setText("");
        this.state = BombState.HIDDEN;
    }

    public void destroy() {
        location.getWorld().getBlockAt(location).setType(Material.AIR);
        this.text.remove();
    }

    public void plant(Player planter) {

        if (this.state == BombState.PLANTED || this.state == BombState.HIDDEN)
            throw new IllegalStateException("Bomb cannot be planted unless it is idle!");

        this.planter = plugin.getGameManager().getPlayerManager().getCSPlayer(planter);

        if (plugin.getGameManager().getTeamManager().getPlayerTeam(this.planter).getType() != TeamType.ATTACKERS)
            throw new IllegalStateException("Bomb cannot be planted by a team that isn't attacking!");

        this.state = BombState.PLANTED;
        this.task = new BombTickTask(20*45);  // 45 second bomb fuse
        this.task.runTaskTimer(plugin, 0, 1);
        this.plugin.getGameManager().setState(GameState.BOMB_IS_PLANTED);
        this.plugin.getGameManager().getPhaseManager().endPhaseTimer();

    }

    public void defuse(Player defuser) {

        if (this.state == BombState.IDLE || this.state == BombState.HIDDEN)
            throw new IllegalStateException("Bomb cannot be defused unless it is planted!");

        this.defuser = plugin.getGameManager().getPlayerManager().getCSPlayer(defuser);
        this.state = BombState.IDLE;
        this.task.cancel();
        this.setText("DEFUSED!!!");
    }

    public void explode() {

        if (this.task == null)
            throw new IllegalStateException("Bomb cannot explode if it's not planted!");

        this.task = null;
        this.state = BombState.IDLE;
        this.setText("BOOM!");
        this.location.getWorld().createExplosion(location, 0);
    }

}
