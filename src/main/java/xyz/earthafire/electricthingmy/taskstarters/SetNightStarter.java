package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.ChangeTimeTask;

import java.util.ArrayList;

public class SetNightStarter extends TaskStarter{
    public SetNightStarter(){
        taskVerb = "darkened the sky";
    }

    @Override
    public void run(ArrayList<Player> players, String name) {
        int delay = 0;
        for(Player next: players){
            new ChangeTimeTask(next, name, 13000).runTaskLater(App.plugin, delay * 5L);
            delay++;
        }
    }
}
