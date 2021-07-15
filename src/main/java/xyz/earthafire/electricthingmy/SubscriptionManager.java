package xyz.earthafire.electricthingmy;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import xyz.earthafire.electricthingmy.json.Subscription;
import xyz.earthafire.electricthingmy.webserver.HTTPQueries;

import java.util.ArrayList;
import java.util.UUID;

public class SubscriptionManager {
    ArrayList<Subscription> currentSubscriptions;

    public SubscriptionManager(){
        this.currentSubscriptions = new ArrayList<Subscription>();
    }

    public void getCurrentSubscriptions(){
        JsonArray dataArray = HTTPQueries.getCurrentSubscriptionsList();
        for(JsonElement temp: dataArray){
            JsonObject data = temp.getAsJsonObject();

            Subscription newSubscription =
                    new Subscription(
                            data.get("id").getAsString(),
                            data.get("status").getAsString(),
                            data.get("type").getAsString(),
                            data.get("condition").getAsJsonObject(),
                            data.get("created_at").getAsString(),
                            data.get("cost").getAsString());

            currentSubscriptions.add(newSubscription);
            System.out.println(newSubscription.getId());
        }
    }

    public void trimDeadSubscriptions(){
        if(!(currentSubscriptions.size() < 1)){
            for(Subscription next: currentSubscriptions){
                if(!next.isEnabled()){
                    HTTPQueries.endSubscription(next.getId());
                }
                currentSubscriptions.remove(next);
            }
        }
    }

    public boolean subscribeToEvent(UUID uniqueId, String event) {
        for(Subscription next: currentSubscriptions) {
            //if message is a poll end type
            if(next.getType().equalsIgnoreCase(event)){
                if(next.getCondition().get("broadcaster_user_id").getAsString()
                        .equalsIgnoreCase(App.twitchData.getTwitchID(uniqueId))){
                    //if it is for this user
                    System.out.println("there is already a subscription for this user and event!");
                    return true;
                }
            }
        }
        return HTTPQueries.eventSub(uniqueId, event);
    }
}
