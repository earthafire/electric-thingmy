package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class WaterDumpTask extends BukkitRunnable{
    
    private static final int RADIUS = 1;
    private ArrayList<Player> players;
    String name;
    
    //assumes "target" is not in "onlinePlayers"
    public WaterDumpTask(ArrayList<Player> players, String name) {
        this.players = players;
        this.name = name;
    }

    @Override
    public void run() {
        Location temp;
        for(Player nextPlayer : players){

            //try head location first
            temp = nextPlayer.getLocation().add(0.0, 1, 0.0);
            if(temp.getBlock().getType() == Material.AIR){
                temp.getBlock().setType(Material.WATER);
                nextPlayer.playSound(temp, Sound.ENTITY_GENERIC_SPLASH, 1.0F, 1.0F);
                nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has dumped water on you!");
                return;
            }

            //if head location blocked try area around player
            for(int x = -RADIUS; x <= RADIUS; x++){
                for(int z = -RADIUS; z <= RADIUS; z++){
                    for(int y = 0; y <= 2; y++){
                        temp = nextPlayer.getLocation().add(x, y, z);
                        if(temp.getBlock().getType() == Material.AIR){
                            temp.getBlock().setType(Material.WATER);
                            nextPlayer.playSound(temp, Sound.ENTITY_GENERIC_SPLASH, 1.0F, 1.0F);
                            nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has dumped water on you!");
                            return;
                        }
                    }
                }
            }
            nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " couldn't find his bucket..");
        }
    }
}