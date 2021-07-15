package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.earthafire.electricthingmy.Tasks;
import xyz.earthafire.electricthingmy.webserver.HTTPQueries;

import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;

public class PollTask extends BukkitRunnable {

    Player target;
    int pollLength;
    Tasks tasks;

    public PollTask(Player player, int pollLength, Tasks tasks){
        this.tasks = tasks;
        this.pollLength = pollLength;
        target = player;
    }

    @Override
    public void run() {
        ConcurrentSkipListSet<String> array = tasks.random(new Random().nextInt(4) + 2);
        HTTPQueries.createPoll(target.getUniqueId(), "events", array, pollLength);
    }
}
