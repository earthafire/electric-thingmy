package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.CreeperTask;

import java.util.ArrayList;

public class CreeperStarter extends TaskStarter{
    public CreeperStarter(){
        taskVerb = "creepered";
    }

    @Override
    public void run(ArrayList<Player> players, String name) {
        int delay = 0;
        for(Player next: players){
            new CreeperTask(next, name).runTaskLater(App.plugin, delay * 5);
            delay++;
        }
    }
}


