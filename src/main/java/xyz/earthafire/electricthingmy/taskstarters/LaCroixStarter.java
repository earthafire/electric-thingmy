package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.GiveItemTask;

import java.util.ArrayList;

public class LaCroixStarter extends TaskStarter{
    public LaCroixStarter(){
        taskVerb = "lacroix'd'";
    }

    @Override
    public void run(ArrayList<Player> players, String name) {
        int delay = 0;
        for(Player next: players){
            new GiveItemTask(next, name, "lacroix").runTaskLater(App.plugin, delay * 5);
            delay++;
        }
    }
}
