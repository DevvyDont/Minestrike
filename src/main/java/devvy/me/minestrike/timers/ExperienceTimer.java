package devvy.me.minestrike.timers;

import devvy.me.minestrike.Minestrike;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ExperienceTimer {

    private class ExperienceTimerTask extends BukkitRunnable {

        @Override
        public void run() {

            currentTick++;
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.setLevel(getSecondsLeft());
                p.setExp(1 - getPercentTimeCompleted());
            }

            if (currentTick >= targetTick)
                endTimer();

        }
    }

    Minestrike plugin;
    BukkitRunnable task;
    private int currentTick = 0;
    private int targetTick;

    public ExperienceTimer(Minestrike plugin, int duration) {

        this.plugin = plugin;
        this.targetTick = duration;

    }

    public void startTimer(){

        task = new ExperienceTimerTask();
        task.runTaskTimer(plugin, 0, 1);

    }

    public void endTimer(){

        task.cancel();

        // Set everyone's xp and levels back to 0
        for (Player player : Bukkit.getOnlinePlayers()){
            player.setLevel(0);
            player.setExp(0);
        }

    }

    private float getPercentTimeCompleted() {
        return (float) currentTick / targetTick;
    }

    private int getSecondsLeft() {
        int totalSeconds = Math.round((float)targetTick / 20);
        int currentSecond = Math.round((float)currentTick / 20);
        return totalSeconds - currentSecond;
    }
}
