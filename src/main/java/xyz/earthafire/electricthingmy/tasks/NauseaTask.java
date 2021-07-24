package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class NauseaTask extends BukkitRunnable{
    
    private Player player;
    private static final int SECONDSPERTICK = 20;
    String name;
    
    //assumes "target" is not in "onlinePlayers"
    public NauseaTask(Player player, String name) {
        this.player = player;
        this.name = name;
    }

    @Override
    public void run() {
        //player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " made you sick!");
        player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 12 * SECONDSPERTICK, 0));
    }
}