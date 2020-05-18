package devvy.me.minestrike.player;

import devvy.me.minestrike.game.PlayerKDTracker;
import org.bukkit.entity.Player;

public class CSPlayer {

    private Player player;
    private PlayerKDTracker kdTracker;



    public CSPlayer(Player player) {
        kdTracker = new PlayerKDTracker();
        this.player = player;
    }


    public void resetStats(){
        kdTracker.reset();
    }

    public PlayerKDTracker getKdTracker(){
        return kdTracker;
    }

}
