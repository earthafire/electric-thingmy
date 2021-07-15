package xyz.earthafire.electricthingmy.tasks;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class SpawnMobTask extends BukkitRunnable{
    
    private ArrayList<Player> players;
    private EntityType entity;
    private String name;
    public SpawnMobTask(ArrayList<Player> players, String name, EntityType entity) {
        this.players = players;
        this.entity = entity;
        this.name = name;
    }

    @Override
    public void run() {
        MobSpawnLocation finder = new MobSpawnLocation();
        for(Player nextPlayer : players){
            nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has summoned one " + entity.toString().toLowerCase());
            LivingEntity mob = (LivingEntity)nextPlayer.getWorld().spawnEntity(finder.locationNearPlayer(nextPlayer, 1), entity);
            mob.setCustomName(name);
            mob.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 15*20, 0));
        }
    }
}