package xyz.earthafire.electricthingmy;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.webserver.HTTPQueries;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class TwitchUserData {
    private YamlConfiguration tokens;
    private String TOKENSFILELOCATION;

    public TwitchUserData(String fileLocation){
        this.TOKENSFILELOCATION = fileLocation;
        File tokensFile = new File(TOKENSFILELOCATION);
        this.tokens = YamlConfiguration.loadConfiguration(tokensFile);
        saveAll();
    }

    public void saveAll(){
        File tokensFile = new File(TOKENSFILELOCATION);
        try{
            tokens.save(tokensFile);
        } catch (Exception e){
            System.out.println("Failed to save tokens to file");
            e.printStackTrace();
        }
    }

    public String getTokenForUUID(){
        String clientid = tokens.getString("clientid");
        if(clientid == null){
            return null;
        }
        return clientid;
    }

    public void addToken(UUID player, String token) {
        tokens.set(player.toString() + ".token", token);
        saveAll();
        getTwitchID(player);
    }

    public String getToken(UUID player){
        return tokens.getString(player.toString() + ".token");
    }

    public String getTwitchID(UUID player){
        String answer;

        //if it is not saved yet, get it from the twitch servers
        if(!tokens.isSet(player.toString() + ".twitch.id")){
            pullTwitchDataFromServer(player);
        }

        answer = tokens.getString(player.toString() + ".twitch.id");
        return answer;
    }

    public boolean pullTwitchDataFromServer(UUID player){
        String token = getToken(player);
        if(token == null){
            System.out.println("Failed to get twitch token from uuid");
            return false;
        }
        JsonObject data = HTTPQueries.getUserFromToken(token);

        if(data == null){
            System.out.println("Failed to get twitch data from token");
            return false;
        }

        JsonArray arrayofusers = data.get("data").getAsJsonArray();
        JsonObject twitchuser = arrayofusers.get(0).getAsJsonObject();

        tokens.set(player+".twitch.id", twitchuser.get("id").getAsString());
        tokens.set(player+".twitch.display_name", twitchuser.get("display_name").getAsString());
        tokens.set(player+".twitch.broadcaster_type", twitchuser.get("broadcaster_type").getAsString());
        saveAll();
        return true;
    }

    public ArrayList<UUID> getParticipants(){
        ArrayList<UUID> answer = new ArrayList<UUID>();
        for(String uuid : tokens.getKeys(false)){
            if(!tokens.getBoolean(uuid + ".optedout")){
                answer.add(UUID.fromString(uuid));
            }
        }
        return answer;
    }

    public void optOutOfEvents(UUID uuid){
        tokens.set(uuid + ".optedout", true);
    }

    public void optInToEvents(UUID uuid){
        tokens.set(uuid + ".optedout", false);
    }

    public void addPlayer(Player player) {
        if(!tokens.contains(player.getUniqueId().toString())){
            optInToEvents(player.getUniqueId());
        }
    }

    public UUID getUUIDfromTwitchID(String twitchid){
        for(String uuid : tokens.getKeys(false)){
            if(tokens.getString(uuid + ".twitch.id").equalsIgnoreCase(twitchid)){
                return UUID.fromString(uuid);
            }
        }
        return null;
    }
}
