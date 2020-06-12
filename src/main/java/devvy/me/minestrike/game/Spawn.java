package devvy.me.minestrike.game;

import devvy.me.minestrike.Minestrike;
import devvy.me.minestrike.player.CSPlayer;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import java.util.ArrayList;
import java.util.Collections;

public class Spawn implements Listener {

    private ArrayList<Location> ctSpawnPoints = new ArrayList<>();
    private CSTeam team;
    private int modifier = 1;

    public Spawn(Location origin, CSTeam team){
        this.team = team;
        int originX = origin.getBlockX();
        int originY = origin.getBlockY();
        int originZ = origin.getBlockZ();
        Location ctSpawn = new Location(origin.getWorld(), originX, originY, originZ);
        Location ctSpawn2 = new Location(origin.getWorld(), originX - 1 , originY, originZ - 2 * modifier);
        Location ctSpawn3 = new Location(origin.getWorld(), originX - 3 , originY, originZ  - 2 * modifier);
        Location ctSpawn4 = new Location(origin.getWorld(), originX - 4 , originY, originZ - 2 * modifier);
        Location ctSpawn5 = new Location(origin.getWorld(), originX - 5 , originY, originZ);

        ctSpawnPoints.add(ctSpawn);
        ctSpawnPoints.add(ctSpawn2);
        ctSpawnPoints.add(ctSpawn3);
        ctSpawnPoints.add(ctSpawn4);
        ctSpawnPoints.add(ctSpawn5);


    }
    public void setModifier(int modifier){
        int h = modifier == 1 ? 0: 180;
        for(Location spawn : ctSpawnPoints){
            spawn.setYaw(h);
        }
        this.modifier = modifier;
    }


    public void teleportMembersToSpawnPoints(){
        ArrayList<CSPlayer> players = new ArrayList<>(team.getMembers());
        Collections.shuffle(players);



        int i = 0;

        for(CSPlayer member : players){
            member.getSpigotPlayer().teleport(ctSpawnPoints.get(i));

        }

    }

}
