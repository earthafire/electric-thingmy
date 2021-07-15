package xyz.earthafire.electricthingmy.tasks;


import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class CreeperTask extends BukkitRunnable{
    
    private Player player;
    private String name;
    private static final int DISTANCE = 6;

    public CreeperTask(Player player, String name) {
        this.player = player;
        this.name = name;
    }

    @Override
    public void run() {
        //player.sendMessage(ChatColor.GREEN + "aw-w-w man!");
        MobSpawnLocation finder = new MobSpawnLocation();
        Creeper creep = (Creeper) player.getWorld().spawnEntity(finder.locationNearPlayer(player, DISTANCE), EntityType.CREEPER);
        creep.setCustomName(name);
        creep.setTarget(player);
        creep.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 30*10, 0));
    }
}