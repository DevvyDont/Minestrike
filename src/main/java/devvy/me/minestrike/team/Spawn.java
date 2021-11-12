package devvy.me.minestrike.team;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.player.CSPlayer;
import devvy.me.minestrike.team.CSTeam;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;

public class Spawn implements Listener {

    private CSTeam team;
    private Location origin;

    public Spawn(Location origin, CSTeam team){
        this.origin = origin;
        this.team = team;


        new BukkitRunnable() {
            @Override
            public void run() {
                Location random = randomSpawnLocation();
                Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0, 127, 255), 1);
                origin.getWorld().spawnParticle(Particle.REDSTONE, random, 50, dustOptions);
            }
        }.runTaskTimer(Minestrike.getPlugin(Minestrike.class), 0, 1);
    }

    public Location randomSpawnLocation() {
        return randomSpawnLocation(Math.random());
    }

    public Location randomSpawnLocation(double t) {
        Location loc = origin.clone();
        double newX = 3 * Math.sin(t*4*Math.PI-(2*Math.PI)) + loc.getX();
        double newZ = 3 * Math.cos(t*2*Math.PI*-1) + loc.getZ();
        loc.set(newX, loc.getY(), newZ);
        return loc;
    }

    public void teleportMembersToSpawnPoints(){
        ArrayList<CSPlayer> players = new ArrayList<>(team.getMembers());
        Collections.shuffle(players);


        double t = 0;
        double progress = 0;
        double playersProcessed = 0;

        for(CSPlayer member : players){
            member.getSpigotPlayer().teleport(randomSpawnLocation(progress));
            playersProcessed++;
            progress = playersProcessed / team.getMembers().size();
        }

    }

}
