package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.SwapPlayersTask;

import java.util.ArrayList;

public class SwapPlayersStarter extends TaskStarter{
    public SwapPlayersStarter(){
        taskVerb = "bumped";
    }

    @Override
    public void run(ArrayList<Player> players, String name) {
        int delay = 0;

        for(Player next: players){
            new SwapPlayersTask(next, name).runTaskLater(App.plugin, delay * 5L);
            delay++;
        }
    }
}
