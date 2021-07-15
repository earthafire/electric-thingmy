package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.FillInvWithGarbageTask;

import java.util.ArrayList;

public class FillInvWithGarbageStarter extends TaskStarter{
    public FillInvWithGarbageStarter(){
        taskVerb = "garbage'd";
    }

    @Override
    public void run(ArrayList<Player> players, String name) {
        int delay = 0;
        for(Player next: players){
            new FillInvWithGarbageTask(next, name).runTaskLater(App.plugin, delay * 5);
            delay++;
        }
    }
}
