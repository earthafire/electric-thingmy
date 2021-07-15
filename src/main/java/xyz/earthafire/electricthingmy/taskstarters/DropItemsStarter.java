package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.DropItemsTask;

import java.util.ArrayList;

public class DropItemsStarter extends TaskStarter{
    public DropItemsStarter(){
        taskVerb = "mugged";
    }

    @Override
    public void run(ArrayList<Player> players, String name) {
        int delay = 0;
        for(Player next: players){
            new DropItemsTask(next, name).runTaskLater(App.plugin, delay * 5);
            delay++;
        }
    }
}
