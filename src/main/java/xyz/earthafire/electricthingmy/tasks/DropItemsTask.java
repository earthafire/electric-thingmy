package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class DropItemsTask extends BukkitRunnable{
    
    private Player player;
    String name;
    
    //assumes "target" is not in "onlinePlayers"
    public DropItemsTask(Player players, String name) {
        this.player = players;
        this.name = name;
    }

    @Override
    public void run() {
        //player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has triggered drop items!");
        player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, .8F, 1.0F);
        ItemStack items;
        for(int i = 40; i >= 0; i--){
            items = player.getInventory().getItem(i);
            if(items != null){
                player.getWorld().dropItemNaturally(player.getLocation(), items);
                player.getInventory().clear(i);
            }
        }
    }
}