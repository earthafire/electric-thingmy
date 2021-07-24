package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ChangeTimeTask extends BukkitRunnable{

    Player target;
    String name;
    int time;
    //assumes "target" is not in "onlinePlayers"
    public ChangeTimeTask(Player target, String name, int time) {
        this.target = target;
        this.name = name;
        this.time = time;
    }

    @Override
    public void run() {
        target.getWorld().setTime(time);
        target.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has triggered night!");
    }
}