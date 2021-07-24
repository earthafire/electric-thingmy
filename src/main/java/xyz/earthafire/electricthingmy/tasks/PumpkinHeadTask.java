package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PumpkinHeadTask extends BukkitRunnable{
    
    private Player player;
    String name;
    
    public PumpkinHeadTask(Player player, String name) {
        this.player = player;
        this.name = name;
    }

    @Override
    public void run() {
        //player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " put a " + ChatColor.GOLD + "pumpkin" + ChatColor.WHITE + " on your head!");

        //check current helmet
        ItemStack item;
        item = player.getInventory().getHelmet();

        if(item != null){ //if player has helmet drop helmet on ground
            player.getWorld().dropItemNaturally(player.getLocation(), item);
            player.getInventory().setHelmet(null);
        }

        //give player pumpkin helmet
        item = new ItemStack(Material.CARVED_PUMPKIN);
        item.setAmount(1);
        player.getInventory().setHelmet(item);
    }
}