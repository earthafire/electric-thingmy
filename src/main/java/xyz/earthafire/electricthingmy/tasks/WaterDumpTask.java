package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WaterDumpTask extends BukkitRunnable{
    
    private static final int RADIUS = 1;
    private Player player;
    String name;
    
    //assumes "target" is not in "onlinePlayers"
    public WaterDumpTask(Player players, String name) {
        this.player = players;
        this.name = name;
    }

    @Override
    public void run() {
        Location temp;

        //try head location first
        temp = player.getLocation().add(0.0, 1, 0.0);
        if(temp.getBlock().getType() == Material.AIR){
            temp.getBlock().setType(Material.WATER);
            player.playSound(temp, Sound.ENTITY_GENERIC_SPLASH, 1.0F, 1.0F);
            //player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has dumped water on you!");
            return;
        }

        //if head location blocked try area around player
        for(int x = -RADIUS; x <= RADIUS; x++){
            for(int z = -RADIUS; z <= RADIUS; z++){
                for(int y = 0; y <= 2; y++){
                    temp = player.getLocation().add(x, y, z);
                    if(temp.getBlock().getType() == Material.AIR){
                        temp.getBlock().setType(Material.WATER);
                        player.playSound(temp, Sound.ENTITY_GENERIC_SPLASH, 1.0F, 1.0F);
                        //player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has dumped water on you!");
                        return;
                    }
                }
            }
        }
        player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " couldn't find a bucket..");
    }
}