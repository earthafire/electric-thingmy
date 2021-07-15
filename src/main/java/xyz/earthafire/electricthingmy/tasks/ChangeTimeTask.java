package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ChangeTimeTask extends BukkitRunnable{

    Player target;
    String name;
    //assumes "target" is not in "onlinePlayers"
    public ChangeTimeTask(Player target, String name) {
        this.target = target;
        this.name = name;
    }

    @Override
    public void run() {
        target.getWorld().setTime(13000);
        target.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has triggered night!");
    }
}