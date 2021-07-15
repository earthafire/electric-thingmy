package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.CreepyNoiseTask;

import java.util.ArrayList;

public class ScaryNoiseStarter extends TaskStarter{
    public ScaryNoiseStarter(){
        taskVerb = "spooked";
    }

    @Override
    public void run(ArrayList<Player> players, String name) {
        int delay = 0;
        for(Player next: players){
            new CreepyNoiseTask(next, name).runTaskLater(App.plugin, delay * 5);
            delay++;
        }
    }
}
