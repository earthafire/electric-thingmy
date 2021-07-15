package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.GiveItemTask;

import java.util.ArrayList;
import java.util.Random;

public class RandomItemStarter extends TaskStarter{
    public RandomItemStarter(){
        taskVerb = "gifted";
    }

    @Override
    public void run(ArrayList<Player> players, String name) {
        int delay = 0;
        Random r = new Random();
        for(Player next: players){
            ItemStack item = new ItemStack(Material.values()[r.nextInt(Material.values().length)]);
            new GiveItemTask(next, name, item).runTaskLater(App.plugin, delay * 5);
            delay++;
        }
    }
}
