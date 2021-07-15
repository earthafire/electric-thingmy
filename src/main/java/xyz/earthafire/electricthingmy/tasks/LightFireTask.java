package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class LightFireTask extends BukkitRunnable{
    
    private static final int MAX_FIRES = 7;
    private ArrayList<Player> players;
    String name;
    
    //assumes "target" is not in "onlinePlayers"
    public LightFireTask(ArrayList<Player> players, String name) {
        this.players = players;
        this.name = name;
    }

    @Override
    public void run() {
        for(Player nextPlayer : players){

            Location temp = nextPlayer.getLocation();
            Random r = new Random();
            int fireCount = 0;
            for(int x = -3; x <= 3; x++){
                for(int z = -3; z <= 3; z++){
                    for(int y = -1; y <= 1; y++){
                        temp = nextPlayer.getLocation();
                        if(temp.add(x, y, z).getBlock().getType() == Material.AIR){
                            if(isValidFireLocation(temp)){
                                if(r.nextInt(7) == 0){
                                    temp.getBlock().setType(Material.FIRE);
                                    nextPlayer.playSound(temp, Sound.ITEM_FLINTANDSTEEL_USE, 1.0F, 1.0F);
                                    fireCount++;
                                    if(fireCount >= MAX_FIRES){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(fireCount >= MAX_FIRES){
                        break;
                    }
                }
                if(fireCount >= MAX_FIRES){
                    break;
                }
            }
            if(fireCount == 0){
                nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has failed to light any fires..");
            } else if(fireCount == 1){
                nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has lit a fire!");
            } else {
                nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has lit " + fireCount + " fires!");
            }
            
        }
    }

    private Boolean isValidFireLocation(Location temp){
        
        if(!temp.getBlock().getType().isAir()){
            return false;
        }

        //check below block
        Location checker = new Location(temp.getWorld(), temp.getX(), temp.getY(), temp.getZ());

        checker.add(0, -1.0f, 0); //check block below
        
        if(checker.getBlock().getType().isOccluding()){
            return true;
        }
        
        checker.add(0, 2.0f, 0); //check block above

        if(checker.getBlock().getType().isOccluding()){
            return true;
        }

        checker.add(-1.0f, -1.0f, 0); //check block -x

        if(checker.getBlock().getType().isOccluding()){
            return true;
        }

        checker.add(2.0f, 0, 0); //check block +x

        if(checker.getBlock().getType().isOccluding()){
            return true;
        }

        checker.add(-1.0f, 0, -1.0f); //check block -z

        if(checker.getBlock().getType().isOccluding()){
            return true;
        }
        
        checker.add(0, 0, 2.0f); //check block +z

        if(checker.getBlock().getType().isOccluding()){
            return true;
        }

        return false;
    }
}