package xyz.earthafire.electricthingmy.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.tasks.GiveItemTask;
import xyz.earthafire.electricthingmy.webserver.HTTPQueries;

public class CommandThingmy implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player)sender;
            if(args.length == 0){
                help(player);
                return true;
            } else {
                if(args[0].equalsIgnoreCase("link")){
                    register(player);
                    return true;
                } else if(args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")){
                    help(player);
                    return true;
                } else if(args[0].equalsIgnoreCase("poll")){
                    if(!App.subscriptionManager.subscribeToEvent(player.getUniqueId(), "channel.poll.end")){
                        player.sendMessage("Error talking to twitch.. Try '/thingmy link' to get new authorization token");
                    } else {
                        int temp = 60;
                        if(args.length > 1){

                            try{
                                temp = Integer.parseInt(args[1]);
                            } catch (Exception e){
                                player.sendMessage("2nd argument not a number? try '/thingmy poll 60'");
                            }

                        }
                        App.pollManager.startPolling(player, temp);
                    }
                    return true;
                } else if(args[0].equalsIgnoreCase("stop")){
                    App.pollManager.stopPolling(player);
                    return true;
                } else if(args[0].equalsIgnoreCase("oauth")){
                    oauth(player,args);
                    return true;
                } else if(args[0].equalsIgnoreCase("optout")){
                    App.twitchData.optOutOfEvents(player.getUniqueId());
                    return true;
                } else if(args[0].equalsIgnoreCase("optin")){
                    App.twitchData.optInToEvents(player.getUniqueId());
                    return true;
                } else if(args[0].equalsIgnoreCase("id")){
                    player.sendMessage("your id is: " + App.twitchData.getTwitchID(player.getUniqueId()));
                    return true;
                } else if(args[0].equalsIgnoreCase("test")){
                    new GiveItemTask(player, "hi", "lacroix").runTaskLater(App.plugin, 0);
                } else if(args[0].equalsIgnoreCase("test2")){
                    App.subscriptionManager.trimDeadSubscriptions();
                }
            }
        }
        return false;
    }

    public void help(Player player){
        player.sendMessage("Thingmy Commands:");
        player.sendMessage("- '/Thingmy link' link your twitch to your minecraft account");
        player.sendMessage("- '/Thingmy on' turn on events related to this plugin");
        player.sendMessage("- '/Thingmy off' turn off events related to this plugin");
    }

    public void register(Player player){
        //generate oauth URL
        String targetURL = HTTPQueries.getOAuthRequestURL();
        System.out.println(targetURL);

        //offer URL to user as link
        TextComponent message = Component.text( "Click me to authorize" )
                .color(NamedTextColor.BLUE)
                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, targetURL))
                .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,  Component.text( "https://id.twitch.tv/" )));
        player.sendMessage(message);
    }

    public void oauth(Player player, String[] args){
        if(args.length > 2 && args[1].equalsIgnoreCase("set")){
            App.twitchData.addToken(player.getUniqueId(), args[2]);
            player.sendMessage("Accounts successfully linked! try '/thingmy on' to turn on events");
        } else {
            player.sendMessage("Failed to set link twitch, try '/thingmy link' to get new code");
        }
    }
}
