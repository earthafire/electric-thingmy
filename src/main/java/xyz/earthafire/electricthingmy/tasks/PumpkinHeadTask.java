package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PumpkinHeadTask extends BukkitRunnable{
    
    private ArrayList<Player> players;
    String name;
    
    public PumpkinHeadTask(ArrayList<Player> players, String name) {
        this.players = players;
        this.name = name;
    }

    @Override
    public void run() {
        for(Player nextPlayer : players){
            nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " put a " + ChatColor.GOLD + "pumpkin" + ChatColor.WHITE + " on your head!");
            
            //check current helmet
            ItemStack item;
            item = nextPlayer.getInventory().getHelmet();

            if(item != null){ //if player has helmet drop helmet on ground
                nextPlayer.getWorld().dropItemNaturally(nextPlayer.getLocation(), item);
                nextPlayer.getInventory().setHelmet(null);
            }

            //give player pumpkin helmet
            item = new ItemStack(Material.CARVED_PUMPKIN);
            item.setAmount(1);
            nextPlayer.getInventory().setHelmet(item);
        }
    }
}