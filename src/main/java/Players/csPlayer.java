package Players;

import devvy.me.minestrike.Play.KillDeath;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class csPlayer {

    private Player player;
    private KillDeath kdTracker;



    public csPlayer(Player player) {
        kdTracker = new KillDeath();
        this.player = player;
    }

    public void resetStats(){
        kdTracker.reset();
    }

    public KillDeath getKdTracker(){
        return kdTracker;
    }

}
