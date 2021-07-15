package xyz.earthafire.electricthingmy;

import org.bukkit.configuration.file.YamlConfiguration;
import xyz.earthafire.electricthingmy.webserver.HTTPQueries;

import java.io.File;

public class Settings {
    private final static String LOCATION = "plugins\\electric-thingmy\\settings.yml";

    private YamlConfiguration settings;

    public Settings(){
        File settingsFile = new File(LOCATION);

        settings = YamlConfiguration.loadConfiguration(settingsFile);
        boolean hasClientIDandSecret = true;
        if(!settings.isSet("clientid")){
            System.out.println("missing clientid");
            hasClientIDandSecret = false;
            settings.set("clientid", "SET THIS VALUE");
        }
        if(!settings.isSet("clientsecret")){
            System.out.println("missing clientsecret");
            hasClientIDandSecret = false;
            settings.set("clientsecret", "SET THIS VALUE");
        }
        if(!settings.isSet("appaccesstoken")){
            if(hasClientIDandSecret){
                settings.set("appaccesstoken", HTTPQueries.getClientAccessToken(getClientID(), getString("clientsecret")));
            } else {
                settings.set("appaccesstoken", "MISSING CLIENT ID OR SECRET");
            }
        }
        if(!settings.isSet("keystore_password")){
            settings.set("keystore_password", "SET THIS VALUE");
        }
        if(!settings.isSet("redirecturi")){
            settings.set("redirecturi", "SET THIS VALUE");
        }
        if(!settings.isSet("webhookurl")){
            settings.set("webhookurl", "SET THIS VALUE");
        }
        if(!settings.isSet("notificationmemorylength")){
            settings.set("notificationmemorylength", 7);
        }

        saveAll();
    }

    public void saveAll(){
        File settingsFile = new File(LOCATION);
        try{
            settings.save(settingsFile);
        } catch (Exception e){
            System.out.println("Failed to save settings to file");
            e.printStackTrace();
        }
    }

    public String getString(String key){
        return settings.getString(key);
    }

    public String getClientID(){
        return getString("clientid");
    }
    public String getAppToken(){
        return getString("appaccesstoken");
    }
    public int getMemoryLength(){
        return settings.getInt("notificationmemorylength");
    }
}
