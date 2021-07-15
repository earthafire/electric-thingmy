package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class CreepyNoiseTask extends BukkitRunnable{
    
    private Player player;
    private String name;
    
    public CreepyNoiseTask(Player players, String name) {
        this.player = players;
        this.name = name;
    }

    @Override
    public void run() {
        Random r = new Random();

        Sound arrayofsound[] = Sound.values();

        //player.sendMessage(ChatColor.WHITE + "did you hear that, " + ChatColor.LIGHT_PURPLE + name + "?");
        player.playSound(player.getLocation(), arrayofsound[(r.nextInt(21)+1)], 1.0F, 1.0F);
    }
}