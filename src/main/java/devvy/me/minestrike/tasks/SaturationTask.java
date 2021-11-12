package devvy.me.minestrike.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SaturationTask extends BukkitRunnable {

    private final int SATURATION_LEVEL = 20;


    // Simple task to keep everyones saturation at a certain level
    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setSaturation(SATURATION_LEVEL);
            p.setFoodLevel(20);
        }
    }
}
