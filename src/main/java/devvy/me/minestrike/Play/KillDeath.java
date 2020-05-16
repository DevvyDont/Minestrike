package devvy.me.minestrike.Play;

import devvy.me.minestrike.Minestrike;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;



public class KillDeath implements Listener {
    private int playerKills;
    private int playerDeaths;

    public KillDeath(){
       playerDeaths = 0;
       playerKills = 0;
    }


    public double getKDRation(){
        int tempDeath = playerDeaths;
        if (tempDeath == 0){
            tempDeath = 1;
        }
        return (double) playerKills / (double) tempDeath;
    }
    public int getPlayerKills(){
        return playerKills;
    }
    public int getPlayerDeaths(){
        return playerDeaths;
    }
    public void increaseKillCount(){
        playerKills++;
    }
    public void increaseDeathCount(){
        playerDeaths++;
    }

    

}
