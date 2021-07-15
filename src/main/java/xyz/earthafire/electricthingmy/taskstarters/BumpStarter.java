package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.BumpTask;

import java.util.ArrayList;

public class BumpStarter extends TaskStarter{
    public BumpStarter(){
        taskVerb = "bumped";
    }

    @Override
    public void run(ArrayList<Player> players, String name) {
        int delay = 0;
        for(Player next: players){
            new BumpTask(next, name).runTaskLater(App.plugin, delay * 5);
            delay++;
        }
    }
}
