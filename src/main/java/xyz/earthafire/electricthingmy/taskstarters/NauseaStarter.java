package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.NauseaTask;

import java.util.ArrayList;

public class NauseaStarter extends TaskStarter{
    public NauseaStarter(){
        taskVerb = "infected";
    }

    @Override
    public void run(ArrayList<Player> players, String name) {
        int delay = 0;
        for(Player next: players){
            new NauseaTask(next, name).runTaskLater(App.plugin, delay * 5L);
            delay++;
        }
    }
}
