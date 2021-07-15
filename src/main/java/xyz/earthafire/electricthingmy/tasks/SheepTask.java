package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class SheepTask extends BukkitRunnable{
    
    private final JavaPlugin plugin;
    private Player streamer;
    private String name;
    
    public SheepTask(JavaPlugin plugin, Player streamer, String name) {
        this.streamer = streamer;
        this.name = name;
        this.plugin = plugin;
    }

    @Override
    public void run() {

        //pick random color for sheep
        Random r = new Random();
        int colorSelector = r.nextInt(16);
        DyeColor sheepColor;
        switch(colorSelector){
            case 0:
                sheepColor = DyeColor.WHITE;
                break;
            case 1:
                sheepColor = DyeColor.ORANGE;
                break;
            case 2:
                sheepColor = DyeColor.MAGENTA;
                break;
            case 3:
                sheepColor = DyeColor.LIGHT_BLUE;
                break;
            case 4:
                sheepColor = DyeColor.YELLOW;
                break;
            case 5:
                sheepColor = DyeColor.LIME;
                break;
            case 6:
                sheepColor = DyeColor.PINK;
                break;
            case 7:
                sheepColor = DyeColor.GRAY;
                break;
            case 8:
                sheepColor = DyeColor.LIGHT_GRAY;
                break;
            case 9:
                sheepColor = DyeColor.CYAN;
                break;
            case 10:
                sheepColor = DyeColor.PURPLE;
                break;
            case 11:
                sheepColor = DyeColor.BLUE;
                break;
            case 12:
                sheepColor = DyeColor.BROWN;
                break;
            case 13:
                sheepColor = DyeColor.GREEN;
                break;
            case 14:
                sheepColor = DyeColor.RED;
                break;
            case 15:
                sheepColor = DyeColor.BLACK;
                break;
            default:
                sheepColor = DyeColor.PURPLE;
                plugin.getLogger().warning("something went wrong, defaulted to purple. Random: " + colorSelector);
                break;
        }

        // What you want to schedule goes here
        Sheep sheep = (Sheep) streamer.getWorld().spawnEntity(streamer.getLocation(), EntityType.SHEEP);
        sheep.setColor(sheepColor);
        sheep.setCustomName(name);
        sheep.setCustomNameVisible(true);
    }
}