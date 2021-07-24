package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.earthafire.electricthingmy.App;

import java.util.ArrayList;
import java.util.Random;

public class SwapPlayersTask extends BukkitRunnable{

    private Player target;
    private String name;
    
    //assumes "target" is not in "onlinePlayers"
    public SwapPlayersTask(Player target, String name) {
        this.target = target;
        this.name = name;
    }

    @Override
    public void run() {
        ArrayList<Player> onlinePlayers = (ArrayList<Player>) App.plugin.getServer().getOnlinePlayers();

        if(onlinePlayers == null || onlinePlayers.size() < 2){
            target.sendMessage("Swap failed, nobody to swap with!");
            return;
        }

        Random r = new Random();
        onlinePlayers.remove(target);

        //get random player
        Player playerToSwap = onlinePlayers.get(r.nextInt(onlinePlayers.size()));

        target.sendMessage("Swapping you and " + playerToSwap.getDisplayName() + "!");
        playerToSwap.sendMessage("Swapping you and " + target.getDisplayName() + "!");

        //teleporting
        Location temp =  target.getLocation();
        target.teleport(playerToSwap.getLocation());
        playerToSwap.teleport(temp);
    }
}