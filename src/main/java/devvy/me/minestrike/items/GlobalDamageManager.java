package devvy.me.minestrike.items;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

// This class simply just overwrites any damage and resistance done by custom items
public class GlobalDamageManager implements Listener {

    @EventHandler
    public void onPlayerTookExplosiveDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
            event.setDamage(event.getDamage()*5);
    }

    @EventHandler
    public void onPlayerTookFireDamage(EntityDamageEvent event) {
        // Fire damage is 3x damage
        if (event.getEntity() instanceof Player && (event.getCause() == EntityDamageEvent.DamageCause.LAVA ||
                                                    event.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                                                    event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK))
            event.setDamage(event.getDamage() * 3);
    }

    @EventHandler
    public void onPlayerTookFallDamage(EntityDamageEvent event) {
        // Fall damage is simply just 5x damage
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL)
            event.setDamage(event.getDamage() * 5);

    }


}
