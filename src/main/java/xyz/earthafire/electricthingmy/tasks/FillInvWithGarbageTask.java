package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class FillInvWithGarbageTask extends BukkitRunnable{
    
    private Player player;
    String name;
    
    public FillInvWithGarbageTask(Player player, String name) {
        this.player = player;
        this.name = name;
    }

    @Override
    public void run() {

        //player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " filled your inventory with garbage!");
        ItemStack items;
        Random r = new Random();
        int emptySlot = player.getInventory().firstEmpty();
        ItemMeta meta;
        while(emptySlot != -1){
            // What you want to schedule goes here
            switch(r.nextInt(8 /*number of cases*/)){
                case 0:
                    items = new ItemStack(Material.DIRT);
                    items.setAmount(1);
                    break;
                case 1:
                    items = new ItemStack(Material.WOODEN_PICKAXE);
                    items.setAmount(1);
                    meta = items.getItemMeta();
                    if (meta instanceof Damageable){
                        ((Damageable) meta).setDamage(Material.WOODEN_PICKAXE.getMaxDurability() - 1);
                    }
                    items.setItemMeta(meta);
                    break;
                case 2:
                    items = new ItemStack(Material.OAK_LEAVES);
                    items.setAmount(1);
                    break;
                case 3:
                    items = new ItemStack(Material.LEATHER_BOOTS);
                    items.setAmount(1);
                    meta = items.getItemMeta();
                    if (meta instanceof Damageable){
                        ((Damageable) meta).setDamage(Material.LEATHER_BOOTS.getMaxDurability() - 1);
                    }
                    items.setItemMeta(meta);
                    break;
                case 4:
                    items = new ItemStack(Material.LEATHER_HELMET);
                    items.setAmount(1);
                    meta = items.getItemMeta();
                    if (meta instanceof Damageable){
                        ((Damageable) meta).setDamage(Material.LEATHER_HELMET.getMaxDurability() - 1);
                    }
                    items.setItemMeta(meta);
                    break;
                case 5:
                    items = new ItemStack(Material.LEATHER_CHESTPLATE);
                    items.setAmount(1);
                    meta = items.getItemMeta();
                    if (meta instanceof Damageable){
                        ((Damageable) meta).setDamage(Material.LEATHER_BOOTS.getMaxDurability() - 1);
                    }
                    items.setItemMeta(meta);
                    break;
                case 6:
                    items = new ItemStack(Material.COARSE_DIRT);
                    items.setAmount(1);
                    break;
                default:
                    items = new ItemStack(Material.POISONOUS_POTATO);
                    items.setAmount(1);
            }
            player.getInventory().setItem(emptySlot, items);
            emptySlot = player.getInventory().firstEmpty();
        }

    }
}