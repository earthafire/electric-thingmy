package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SlashHomeTask extends BukkitRunnable{
    
    private ArrayList<Player> players;
    String name;
    
    //assumes "target" is not in "onlinePlayers"
    public SlashHomeTask(ArrayList<Player> players, String name) {
        this.players = players;
        this.name = name;
    }

    @Override
    public void run() {
        for(Player nextPlayer : players){
            if(nextPlayer.getBedSpawnLocation() != null){
                nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " sent you back to your room!");
                nextPlayer.teleport(nextPlayer.getBedSpawnLocation());
            } else {
                nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " tried to send you home but your bed is missing");
            }
        }
    }
}