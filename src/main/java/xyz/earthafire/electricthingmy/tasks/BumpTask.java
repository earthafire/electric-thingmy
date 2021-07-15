package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class BumpTask extends BukkitRunnable{
    
    private Player player;
    private String name;
    
    //assumes "target" is not in "onlinePlayers"
    public BumpTask(Player player, String name) {
        this.player = player;
        this.name = name;
    }

    @Override
    public void run() {
        Random r = new Random();
        Vector v;

        v = new Vector();
        v.setZ((r.nextDouble() * 3.0) - 1.5);
        v.setX((r.nextDouble() * 3.0) - 1.5);
        v.setY((r.nextDouble() * .75));
        player.setVelocity(v);
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, .8F, 1.0F);
        //player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " bumped you!");
    }
}