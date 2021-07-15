package xyz.earthafire.electricthingmy.taskstarters;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public abstract class TaskStarter {
    protected String taskVerb;
    protected final static String pluginName = "electricthingmy";

    public String getTaskVerb(){
        return taskVerb;
    }

    public abstract void run(ArrayList<Player> players, String name);
}
