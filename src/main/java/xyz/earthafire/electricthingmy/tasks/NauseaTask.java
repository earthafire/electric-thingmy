package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class NauseaTask extends BukkitRunnable{
    
    private ArrayList<Player> players;
    private static final int SECONDSPERTICK = 20;
    String name;
    
    //assumes "target" is not in "onlinePlayers"
    public NauseaTask(ArrayList<Player> players, String name) {
        this.players = players;
        this.name = name;
    }

    @Override
    public void run() {
        for(Player nextPlayer : players){
            nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " made you sick!");
            nextPlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 12 * SECONDSPERTICK, 0));
        }
    }
}