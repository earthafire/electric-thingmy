package xyz.earthafire.electricthingmy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PlayerDrinkLacroixListener implements Listener{

    @EventHandler
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event)
    {
        System.out.println(event.getItem().getI18NDisplayName());
        System.out.println(event.getItem().getItemMeta().getDisplayName());
        if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("lacroix")) {
            System.out.println("he drank lacroix2!");
            Random r = new Random();
            Player player = event.getPlayer();

            int cases = 8;

            //set random effect
            switch (r.nextInt(cases)) {
                case 0:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10 * 20, 0));
                    break;
                case 1:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10 * 20, 0));
                    break;
                case 2:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * 20, 0));
                    break;
                case 3:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 15 * 20, 0));
                    break;
                case 4:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HARM, 1, 0));
                    break;
                case 5:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 10 * 20, 0));
                    break;
                case 6:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 10 * 20, 0));
                    break;
                default:
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15 * 20, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 20, 2));
                    break;
            }
        }
    }
}