package xyz.earthafire.electricthingmy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.earthafire.electricthingmy.App;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("This server has twitch events. Type '/thingmy help' for more commands, '/thingmy optout' to not participate");
        App.twitchData.addPlayer(player);
    }
}
