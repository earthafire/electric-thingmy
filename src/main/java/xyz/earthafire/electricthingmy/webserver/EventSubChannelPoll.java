package xyz.earthafire.electricthingmy.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.earthafire.electricthingmy.App;
import xyz.earthafire.electricthingmy.Tasks;

import java.util.ArrayList;
import java.util.UUID;

public class EventSubChannelPoll {

    private String[] previousEvents;

    private final static int MEMORY_LENGTH = App.settings.getMemoryLength();
    private int memoryIndex;
    private Tasks tasks;

    public EventSubChannelPoll(Tasks tasks){
        this.tasks = tasks;
        memoryIndex = 0;
        previousEvents = new String[MEMORY_LENGTH];
    }

    public void handleNotification(JsonObject data){
        JsonObject event = data.get("event").getAsJsonObject();
        String temp = event.get("id").getAsString();
        for(String eventID : previousEvents){
            if(eventID != null){
                if(eventID.equalsIgnoreCase(temp)){
                    return;
                }
            }
        }

        previousEvents[memoryIndex] = temp;
        memoryIndex++;

        if(memoryIndex >= MEMORY_LENGTH){
            memoryIndex = 0;
        }

        if(data.get("subscription").getAsJsonObject().get("type").getAsString().equalsIgnoreCase("channel.poll.end")){
            App.eventSub.channelPollNotification(event);
        }
    }

    public void channelPollNotification(JsonObject event){
        UUID playerUUID = App.twitchData.getUUIDfromTwitchID(event.get("broadcaster_user_id").getAsString());
        Player player = Bukkit.getPlayer(playerUUID);

        if(player == null){
            System.out.println("player must be offline, can't find " + playerUUID);
            return;
        }

        JsonArray choices = event.get("choices").getAsJsonArray();
        ArrayList<JsonObject> votedChoices = new ArrayList<JsonObject>();
        int currentHighest = -1;

        for(JsonElement temp : choices){
            //get as object
            JsonObject objectToCheck = temp.getAsJsonObject();

            //if no current object in list of winners
            if(votedChoices.size() == 0){
                //add object to winners
                votedChoices.add(objectToCheck);
                currentHighest = getVotesFromChoice(objectToCheck);

            //if there is at least one object in list of winners
            } else {
                //check object against current winners
                int objectVotes = getVotesFromChoice(objectToCheck);

                //if votes is higher in new object
                if(currentHighest < objectVotes){
                    //clear whole list
                    votedChoices.clear();
                    //replace highest winner data
                    votedChoices.add(objectToCheck);
                    currentHighest = objectVotes;

                //if votes are a tie
                } else if (currentHighest == objectVotes){
                    //we now have more than one winner
                    votedChoices.add(objectToCheck);
                }
            }
        }

        if(currentHighest > 0){ // only do votes if someone voted
            player.sendMessage(getResultText(votedChoices));
            for(JsonObject temp : votedChoices){
                initializeEvent(player, temp.get("title").getAsString());
            }
        }
    }

    public int getVotesFromChoice(JsonObject choice){
        return choice.get("bits_votes").getAsInt() + choice.get("channel_points_votes").getAsInt() + choice.get("votes").getAsInt();
    }

    public String getResultText(ArrayList<JsonObject> winners){
        String result = "Chat ";
        if(winners.size() == 1){
            //"Poll complete! Result: blank!
            result += tasks.getVerb(winners.get(0).get("title").getAsString()) + " you!";
        } else if (winners.size() == 2){
            //"Poll complete! Result: blank and blank!"
            result += tasks.getVerb(winners.get(0).get("title").getAsString()) + " and " + tasks.getVerb(winners.get(1).get("title").getAsString()) + " you!";
        } else {
            //"Poll complete! Result: blank, blank, ..., and blank!"
            for(int i = 0; i < winners.size() - 1; i++){
                result += tasks.getVerb(winners.get(i).get("title").getAsString()) + ", ";
            }
            result += "and " + tasks.getVerb(winners.get(winners.size() - 1).get("title").getAsString()) + " you!";
        }
        return result;
    }

    public void initializeEvent(Player player, String event){
        ArrayList<Player> playerList = new ArrayList<Player>();
        playerList.add(player);
        if(tasks.has(event)){
            tasks.startTask(event, playerList, "chat");
        } else {
            System.out.println("no such task in directory..");
        }
    }
}
