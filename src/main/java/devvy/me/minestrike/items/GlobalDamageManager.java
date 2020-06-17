package devvy.me.minestrike.items;

import devvy.me.minestrike.Minestrike;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;

// This class simply just overwrites any damage and resistance done by custom items
public class GlobalDamageManager implements Listener {

    @EventHandler
    public void onPlayerShootBow(EntityShootBowEvent event) {

        if (!(event.getEntity() instanceof Player))
            return;

        if (event.getBow() == null)
            return;

        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);
        CustomItemType weaponType = plugin.getCustomItemManager().getCustomItemType(event.getBow());

        double newDmg = weaponType != null ? weaponType.DAMAGE : 1;
        newDmg *= event.getForce();

        // Minecraft does some weird shit to calculate arrow damage, we are going to do our own math in the below event
        if (event.getProjectile() instanceof Arrow)
            event.getProjectile().setMetadata("damage", new FixedMetadataValue(plugin, Math.round(newDmg)));

    }

    @EventHandler
    public void onPlayerTookDamageFromPlayer(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player))
            return;

        Player playerHit = (Player) event.getEntity();

        if (!(event.getDamager() instanceof Arrow || event.getDamager() instanceof Player))
            return;

        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);

        if (event.getDamager() instanceof Player) {

            Player damager = (Player) event.getDamager();

            CustomItemType weaponType = plugin.getCustomItemManager().getCustomItemType(damager.getInventory().getItemInMainHand());
            double newDmg = weaponType != null && weaponType.CATEGORY != CustomItemType.CustomItemCategory.RANGED ? weaponType.DAMAGE : 1;  // 1 damage if the item isn't custom, all of our items should be custom
            event.setDamage(newDmg);

        } else if (event.getDamager() instanceof Arrow) {

            //TODO: Optimize to loop through plugins instead of assuming it's going to be the first metadata val in the list
            // Its possible that some other plugin uses this metadata tag and that can either crash/fuck up our plugin
            if (event.getDamager().hasMetadata("damage"))
                event.setDamage(event.getDamager().getMetadata("damage").get(0).asInt());

        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDamaged(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player) {
            int maxHP = (int)((Player)event.getEntity()).getMaxHealth();
            int currHP = (int)(((Player)event.getEntity()).getHealth() - event.getFinalDamage());
            ((Player)event.getEntity()).sendActionBar(currHP + " / " + maxHP);
        }

    }

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
