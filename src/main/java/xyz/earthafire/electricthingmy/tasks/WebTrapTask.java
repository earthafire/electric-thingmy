package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class WebTrapTask extends BukkitRunnable{
    
    private Player player;
    String name;
    
    //assumes "target" is not in "onlinePlayers"
    public WebTrapTask(Player player, String name) {
        this.player = player;
        this.name = name;
    }

    @Override
    public void run() {
        //player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has set a web trap!");
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, .8F, 1.0F);

        Location temp;
        Random r = new Random();
        for(int x = -2; x <= 2; x++){
            for(int z = -2; z <= 2; z++){
                for(int y = -1; y <= 2; y++){
                    temp = player.getLocation();
                    if(temp.add(x, y, z).getBlock().getType() == Material.AIR){
                        if(r.nextInt(3) == 0){
                            temp.getBlock().setType(Material.COBWEB);
                        }
                    }
                }
            }
        }
    }
}