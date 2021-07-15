package xyz.earthafire.electricthingmy.json;

import com.google.gson.JsonObject;

public class Subscription {
    private String id;
    private String status;
    private String type;
    private JsonObject condition;
    private String created_at;
    private String cost;

    public Subscription(String id, String status, String type, JsonObject condition, String created_at, String cost) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.condition = condition;
        this.created_at = created_at;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public Boolean isEnabled() {
        if(status.equalsIgnoreCase("enabled")){
            return true;
        }
        return false;
    }

    public String getType() {
        return type;
    }

    public JsonObject getCondition() {
        return condition;
    }
}
