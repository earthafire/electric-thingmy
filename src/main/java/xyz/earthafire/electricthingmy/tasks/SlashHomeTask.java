package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SlashHomeTask extends BukkitRunnable{
    
    private Player player;
    String name;
    
    //assumes "target" is not in "onlinePlayers"
    public SlashHomeTask(Player player, String name) {
        this.player = player;
        this.name = name;
    }

    @Override
    public void run() {
        if(player.getBedSpawnLocation() != null){
            //player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " sent you back to your room!");
            player.teleport(player.getBedSpawnLocation());
        } else {
           // player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " tried to send you home but your bed is missing");
        }
    }
}