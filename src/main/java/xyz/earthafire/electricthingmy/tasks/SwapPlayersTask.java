package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class SwapPlayersTask extends BukkitRunnable{
    
    private ArrayList<UUID> onlinePlayersUUIDs;
    private Player target;
    
    //assumes "target" is not in "onlinePlayers"
    public SwapPlayersTask(Player target, ArrayList<UUID> onlinePlayersUUIDs) {
        this.onlinePlayersUUIDs = onlinePlayersUUIDs;
        this.target = target;
    }

    @Override
    public void run() {
        Random r = new Random();

        if(onlinePlayersUUIDs == null || onlinePlayersUUIDs.size() < 1){
            target.sendMessage("Swap failed, nobody to swap with!");
            return;
        }

        //get random player
        UUID randomPlayerUUID = onlinePlayersUUIDs.get(r.nextInt(onlinePlayersUUIDs.size()));
        Player playerToSwap = Bukkit.getPlayer(randomPlayerUUID);

        target.sendMessage("Swapping you and " + playerToSwap.getDisplayName() + "!");
        playerToSwap.sendMessage("Swapping you and " + target.getDisplayName() + "!");

        //teleporting
        Location temp =  target.getLocation();
        target.teleport(playerToSwap.getLocation());
        playerToSwap.teleport(temp);
    }
}