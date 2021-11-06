package devvy.me.minestrike.map;

import devvy.me.minestrike.Minestrike;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class MapConfig {

    private List<BoundingBox> bombSites;

    public MapConfig() {

        Minestrike plugin = Minestrike.getPlugin(Minestrike.class);

        this.bombSites = new ArrayList<>();
        this.bombSites.add(new BoundingBox(-19, 64, 0, -28, 70, -9));
        this.bombSites.add(new BoundingBox(-29, 64, -21, 20, 70, -12));

        this.bombSites.get(0).contains(Bukkit.getPlayer("test").getBoundingBox());
    }
}
