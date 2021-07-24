package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.LightFireTask;

import java.util.ArrayList;

public class StartFiresStarter extends TaskStarter{
    public StartFiresStarter(){
        taskVerb = "burned";
    }

    @Override
    public void run(ArrayList<Player> players, String name) {
        int delay = 0;
        for(Player next: players){
            new LightFireTask(next, name).runTaskLater(App.plugin, delay * 5L);
            delay++;
        }
    }
}
