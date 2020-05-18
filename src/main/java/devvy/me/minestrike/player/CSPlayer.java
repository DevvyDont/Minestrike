package devvy.me.minestrike.player;

import devvy.me.minestrike.game.PlayerKDTracker;
import org.bukkit.entity.Player;

public class CSPlayer {

    private Player spigotPlayer;
    private PlayerKDTracker kdTracker;


    public CSPlayer(Player player) {
        kdTracker = new PlayerKDTracker();
        this.spigotPlayer = player;
    }

    public Player getSpigotPlayer() {
        return spigotPlayer;
    }

    public void setSpigotPlayer(Player spigotPlayer) {
        this.spigotPlayer = spigotPlayer;
    }


    public void resetStats(){
        kdTracker.reset();
    }

    public PlayerKDTracker getKdTracker(){
        return kdTracker;
    }

}
