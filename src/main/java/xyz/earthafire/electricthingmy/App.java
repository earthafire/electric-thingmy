package xyz.earthafire.electricthingmy;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.earthafire.electricthingmy.commands.CommandThingmy;
import xyz.earthafire.electricthingmy.listeners.PlayerDrinkLacroixListener;
import xyz.earthafire.electricthingmy.listeners.PlayerJoinListener;
import xyz.earthafire.electricthingmy.taskstarters.*;
import xyz.earthafire.electricthingmy.webserver.EventSubChannelPoll;
import xyz.earthafire.electricthingmy.webserver.ServerHandler;

import java.io.File;
import java.util.logging.Logger;

public class App extends JavaPlugin {

    private final static String HTTPTOKENSYML = "plugins\\electric-thingmy\\data\\tokens.yml";
    private final static String KEYSTOREPATH = "plugins\\electric-thingmy\\keystore";

    private ServerHandler serverHandler;

    public static Logger log;
    public static Settings settings;
    public static TwitchUserData twitchData;
    public static EventSubChannelPoll eventSub;
    public static PollManager pollManager;
    public static Plugin plugin;
    public static SubscriptionManager subscriptionManager;

    private Tasks taskStarters;

    @Override
    public void onEnable(){


        getLogger().info("ElectricThingmy is here!");
        log = getLogger();

        // init
        taskStarters = makeTaskStarters();
        settings = new Settings();
        twitchData = new TwitchUserData(HTTPTOKENSYML);
        eventSub = new EventSubChannelPoll(taskStarters);
        serverHandler = new ServerHandler();
        pollManager = new PollManager(taskStarters);
        plugin = getServer().getPluginManager().getPlugin("electric-thingmy");
        subscriptionManager = new SubscriptionManager();
        subscriptionManager.getCurrentSubscriptions();

        // check ssl key and encryption
        File keyStore = new File(KEYSTOREPATH);
        boolean keyExists = keyStore.exists();
        if (keyExists) {
            try {
                serverHandler.StartHttpsServer(settings.getString("keystore_password"), keyStore);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("key does not exist");
        }

        // register listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDrinkLacroixListener(), this);

        // init commands
        this.getCommand("thingmy").setExecutor(new CommandThingmy());
    }

    @Override
    public void onDisable() {
        getLogger().info("ElectricThingmy signing off!");
        try {
            serverHandler.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tasks makeTaskStarters(){
        Tasks temp = new Tasks();
        temp.addTask("bump", new BumpStarter(), 1);
        temp.addTask("awww man!", new CreeperStarter(), 1);
        temp.addTask("creepy noise", new ScaryNoiseStarter(), 1);
        temp.addTask("drop items", new DropItemsStarter(), 1);
        temp.addTask("garbage", new FillInvWithGarbageStarter(), 1);
        temp.addTask("lacroix", new LaCroixStarter(), 1);
        temp.addTask("random item", new RandomItemStarter(), 1);

        return temp;
    }
}