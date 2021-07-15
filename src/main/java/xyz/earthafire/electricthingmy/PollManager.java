package xyz.earthafire.electricthingmy;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import xyz.earthafire.electricthingmy.tasks.PollTask;

import java.util.Hashtable;
import java.util.UUID;

public class PollManager {
    Hashtable<UUID, BukkitTask> currentPolls;
    Tasks tasks;

    public PollManager(Tasks tasks){
        this.tasks = tasks;
        this.currentPolls = new Hashtable<UUID, BukkitTask>();
    }

    public boolean startPolling(Player player, int seconds){
        if (currentPolls.contains(player.getUniqueId())){
            return false;
        }

        if(seconds < 30){
            seconds = 30;
        }

        BukkitTask newTask = new PollTask(player, seconds - 10, tasks).runTaskTimerAsynchronously(App.plugin, 20, 20 * seconds);
        currentPolls.put(player.getUniqueId(), newTask);
        return true;
    }

    public boolean stopPolling(Player player){
        if(!currentPolls.contains(player.getUniqueId())){
            return false;
        }

        currentPolls.remove(player.getUniqueId()).cancel();
        return true;
    }
}
