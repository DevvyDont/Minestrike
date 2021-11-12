package devvy.me.minestrike.player;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class CSPlayer {

    private Player spigotPlayer;
    private final PlayerKDTracker kdTracker;
    private int money;


    public CSPlayer(Player player) {
        kdTracker = new PlayerKDTracker();
        this.spigotPlayer = player;
        this.money = 0;
    }

    public Player getSpigotPlayer() {
        return spigotPlayer;
    }

    public void setSpigotPlayer(Player spigotPlayer) {
        this.spigotPlayer = spigotPlayer;
    }

    public boolean isAlive() {
        return spigotPlayer.isValid() && (spigotPlayer.getGameMode() == GameMode.SURVIVAL || spigotPlayer.getGameMode() == GameMode.ADVENTURE);
    }

    public int getMoney() {
        return money;
    }

    public boolean spendMoney(int amount) {
        if (amount > money)
            return false;
        money -= amount;
        return true;
    }

    public void addMoney(int amount, String reason){
        money += amount;
        spigotPlayer.sendMessage(reason + ChatColor.GREEN + "+$"+amount);
        spigotPlayer.sendActionBar(ChatColor.GREEN + "$" + money + "");
//        spigotPlayer.sendActionBar(reason + ChatColor.GREEN + " - $" + amount + ChatColor.DARK_GREEN + " ($" + money + ")");
    }

    public void addMoney(int amount) {
        addMoney(amount, "");
    }

    public void resetStats(){
        kdTracker.reset();
    }

    public PlayerKDTracker getKdTracker(){
        return kdTracker;
    }

}
