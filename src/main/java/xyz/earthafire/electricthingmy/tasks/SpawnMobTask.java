package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnMobTask extends BukkitRunnable{
    
    private Player player;
    private EntityType entity;
    private String name;
    public SpawnMobTask(Player player, String name, EntityType entity) {
        this.player = player;
        this.entity = entity;
        this.name = name;
    }

    @Override
    public void run() {
        MobSpawnLocation finder = new MobSpawnLocation();

        //player.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has summoned one " + entity.toString().toLowerCase());
        LivingEntity mob = (LivingEntity)player.getWorld().spawnEntity(finder.locationNearPlayer(player, 1), entity);
        mob.setCustomName(name);
        mob.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 15*20, 0));

    }
}