package devvy.me.minestrike.Play;

import org.apache.commons.lang.ObjectUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;



public class KillDeath implements Listener {
    private int playerKills;
    private int playerDeaths;

    public double getKDRation(int playerKills, int playerDeaths){
        return (double) playerKills / (double) playerDeaths;
    }
    public int getPlayerKills(){
        return playerKills;
    }
    public int getPlayerDeaths(){
        return getPlayerDeaths();
    }

    

}
