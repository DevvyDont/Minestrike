package Players;

import devvy.me.minestrike.Play.KillDeath;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class csPlayer {
    private int playerKills;
    private int playerDeaths;
    private Player player;
    private KillDeath kdTracker;



    public csPlayer(Player player) {
        kdTracker = new KillDeath();
        this.player = player;
        playerKills = 0;
        playerDeaths = 0;
    }

    public KillDeath getKdTracker(){
        return kdTracker;
    }

}
