package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.earthafire.electricthingmy.App;

import java.util.ArrayList;
import java.util.Random;

public class OreTrapTask extends BukkitRunnable{
    
    private ArrayList<Player> players;
    private ArrayList<Material> ores;
    String name;
    
    //assumes "target" is not in "onlinePlayers"
    public OreTrapTask(ArrayList<Player> players, String name) {
        this.players = players;
        this.name = name;

        ores = new ArrayList<Material>();

        ores.add(Material.COAL_ORE);
        ores.add(Material.COAL_ORE);
        ores.add(Material.COAL_ORE);
        ores.add(Material.IRON_ORE);
        ores.add(Material.IRON_ORE);
        ores.add(Material.COBBLESTONE);
        ores.add(Material.COBBLESTONE);
        ores.add(Material.COBBLESTONE);
        ores.add(Material.COBBLESTONE);
        ores.add(Material.COBBLESTONE);
        ores.add(Material.STONE);
        ores.add(Material.STONE);
        ores.add(Material.STONE);
        ores.add(Material.STONE);
        ores.add(Material.STONE);
        ores.add(Material.STONE);
        ores.add(Material.STONE);
        ores.add(Material.STONE);
        ores.add(Material.DIRT);
        ores.add(Material.DIRT);
        ores.add(Material.DIRT);
        ores.add(Material.COARSE_DIRT);
        ores.add(Material.COARSE_DIRT);
        ores.add(Material.GRANITE);
        ores.add(Material.ANDESITE);
        ores.add(Material.DIORITE);
        ores.add(Material.COARSE_DIRT);
        ores.add(Material.DIAMOND_ORE);
        ores.add(Material.GOLD_ORE);
        ores.add(Material.GOLD_ORE);
        ores.add(Material.REDSTONE_ORE);
        ores.add(Material.REDSTONE_ORE);
    }

    @Override
    public void run() {
        for(Player nextPlayer : players){
            nextPlayer.sendMessage(ChatColor.LIGHT_PURPLE + name + ChatColor.WHITE + " has encased you in stone.");
            nextPlayer.playSound(nextPlayer.getLocation(), Sound.BLOCK_ANVIL_FALL, .8F, 1.0F);

            Location temp;

            int x;
            int z;
            int y;

            //front
            z = 2;
            for(x = -1; x <= 1; x++){
                for(y = 0; y <= 2; y++){
                    temp = nextPlayer.getLocation().add(x, y, z);
                    if(temp.getBlock().getType() == Material.AIR){
                        temp.getBlock().setType(getRandomOre());
                    }
                }
            }

            //back
            z = -2;
            for(x = -1; x <= 1; x++){
                for(y = 0; y <= 2; y++){
                    temp = nextPlayer.getLocation().add(x, y, z);
                    if(temp.getBlock().getType() == Material.AIR){
                        temp.getBlock().setType(getRandomOre());
                    }
                }
            }

            //left
            x = 2;
            for(z = -1; z <= 1; z++){
                for(y = 0; y <= 2; y++){
                    temp = nextPlayer.getLocation().add(x, y, z);
                    if(temp.getBlock().getType() == Material.AIR){
                        temp.getBlock().setType(getRandomOre());
                    }
                }
            }
            //right
            x = -2;
            for(z = -1; z <= 1; z++){
                for(y = 0; y <= 2; y++){
                    temp = nextPlayer.getLocation().add(x, y, z);
                    if(temp.getBlock().getType() == Material.AIR){
                        temp.getBlock().setType(getRandomOre());
                    }
                }
            }

            App.log.info("5");
            //top
            y = 3;
            for(z = -1; z <= 1; z++){
                for(x = -1; x <= 1; x++){
                    temp = nextPlayer.getLocation().add(x, y, z);
                    if(temp.getBlock().getType() == Material.AIR){
                        temp.getBlock().setType(getRandomOre());
                    }
                }
            }

            App.log.info("6");
            //bottom
            y = -1;
            for(z = -1; z <= 1; z++){
                for(x = -1; x <= 1; x++){
                    temp = nextPlayer.getLocation().add(x, y, z);
                    if(temp.getBlock().getType() == Material.AIR){
                        temp.getBlock().setType(getRandomOre());
                    }
                }
            }
        }
    }

    public Material getRandomOre(){
        Random r = new Random();
        return ores.get(r.nextInt(ores.size()));
    }
}