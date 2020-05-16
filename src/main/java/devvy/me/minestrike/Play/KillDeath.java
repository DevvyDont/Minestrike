package devvy.me.minestrike.Play;

import org.bukkit.event.Listener;


public class KillDeath implements Listener {
    private int playerKills;
    private int playerDeaths;

    public KillDeath(){
       playerDeaths = 0;
       playerKills = 0;
    }

    public void reset(){
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
