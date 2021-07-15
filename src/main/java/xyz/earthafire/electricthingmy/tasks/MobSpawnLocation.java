package xyz.earthafire.electricthingmy.tasks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;

public class MobSpawnLocation {
    public Location locationNearPlayer(Player p, int distance){

        Location temp = p.getLocation().add(p.getLocation().getDirection().multiply(distance));
        temp.setY(p.getLocation().getY());

        Location base = temp;
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 5; x++){
                for(int z = 0; z < 5; z++){
                    base = new Location(p.getWorld(), temp.getX(), temp.getY(), temp.getZ());
                    base.add(x, y, z);

                    if(isEmpty(base)){
                        return base;
                    }
                }
            }
        }

        for(int y = -4; y < 0; y++){
            for(int x = 0; x < 7; x++){
                for(int z = 0; z < 7; z++){
                    base = new Location(p.getWorld(), temp.getX(), temp.getY(), temp.getZ());
                    base.add(x, y, z);

                    if(isEmpty(base)){
                        return base;
                    }
                }
            }
        }

        temp = p.getLocation().add(p.getLocation().getDirection().multiply(-distance));
        temp.setY(p.getLocation().getY());
        for(int y = -1; y < 6; y++){
            for(int x = 0; x < 8; x++){
                for(int z = 0; z < 8; z++){
                    base = new Location(p.getWorld(), temp.getX(), temp.getY(), temp.getZ());
                    base.add(x, y, z);

                    if(isEmpty(base)){
                        return base;
                    }
                }
            }
        }

        App.log.warning("!!!couldn't find proper spawn!!!");

        base.add(0, 6, 1);
        return base;
    }

    private Boolean isEmpty(Location p){
        if((p.getBlock().getType() == Material.AIR || p.getBlock().getType() == Material.WATER) && (p.add(0, 1, 0).getBlock().getType() == Material.AIR || p.add(0, 1, 0).getBlock().getType() == Material.WATER))
            return true;
        return false;
    }
}
