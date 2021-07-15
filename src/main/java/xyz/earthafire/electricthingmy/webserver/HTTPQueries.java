package xyz.earthafire.electricthingmy.webserver;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import xyz.earthafire.electricthingmy.App;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

public class HTTPQueries {
    public static HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    private final static String scopes = "&scope=channel:read:redemptions%20channel:manage:redemptions%20channel:read:subscriptions%20channel:read:polls%20channel:manage:polls%20bits:read";

    public static String getOAuthRequestURL() {
        return "https://id.twitch.tv/oauth2/authorize" +
                "?client_id=" + App.settings.getClientID() +
                "&redirect_uri=" + App.settings.getString("redirecturi") +
                "&response_type=token" +
                scopes;
    }

    public static String getClientAccessToken(String clientid, String clientsecret){
        URI uri = URI.create(
                "https://id.twitch.tv/oauth2/token" +
                        "?client_id=" + clientid +
                        "&client_secret=" + clientsecret +
                        "&grant_type=client_credentials" +
                        scopes);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(uri)
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request,  HttpResponse.BodyHandlers.ofString());

            int code = response.statusCode();

            System.out.println("code: " + code + "responed with: " + response.body());

            if(code >= 200 && code < 300){
                Gson gson = new Gson();
                JsonObject data = gson.fromJson(response.body(), JsonObject.class);
                System.out.println(data.get("access_token").getAsString());
                return data.get("access_token").getAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JsonObject getUserFromToken(String token){
        URI uri = URI.create(
                "https://api.twitch.tv/helix/users");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .setHeader("Client-ID", App.settings.getClientID())
                .header("Authorization", "Bearer " + token)
                .build();
        try {

            HttpResponse<String> response = httpClient.send(request,  HttpResponse.BodyHandlers.ofString());

            int code = response.statusCode();

            if(code >= 200 && code < 300){
                Gson gson = new Gson();
                JsonObject data = gson.fromJson(response.body(), JsonObject.class);
                System.out.println("getUserFromToken request successful");
                return data;
            } else {
                System.out.println("getUserFromToken request failed");
                System.out.println("code: " + code + "responed with: " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean eventSubFollows(UUID player){
        String twitchid = App.twitchData.getTwitchID(player);
        String webhookurl = App.settings.getString("webhookurl");
        if(webhookurl == null || webhookurl.length() < 1){
            System.out.println("invalid webhookurl");
            return false;
        }
        if(twitchid == null){
            return false;
        }

        //target uri
        URI uri = URI.create(
                "https://api.twitch.tv/helix/eventsub/subscriptions");

        //payload
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "channel.follow");
        jsonObject.addProperty("version", "1");

        JsonObject condition = new JsonObject();
        condition.addProperty("broadcaster_user_id", twitchid);
        jsonObject.add("condition", condition);

        JsonObject transport = new JsonObject();
        transport.addProperty("method", "webhook");
        transport.addProperty("callback", webhookurl);
        transport.addProperty("secret", "bests3creteverprobably");
        jsonObject.add("transport", transport);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .uri(uri)
                .setHeader("Client-ID", App.settings.getClientID())
                .header("Authorization", "Bearer " + App.settings.getAppToken())
                .header("Content-Type", "application/json")
                .build();

        try {
            //get response
            HttpResponse<String> response = httpClient.send(request,  HttpResponse.BodyHandlers.ofString());

            int code = response.statusCode();

            if(code >= 200 && code < 300){
                System.out.println("listenForFollowsFromUUID request successful");
                return true;
            } else {
                System.out.println("listenForFollowsFromUUID request failed");
                System.out.println("code: " + code + "responed with: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean eventSub(UUID player, String type){
        String twitchid = App.twitchData.getTwitchID(player);
        String webhookurl = App.settings.getString("webhookurl");
        if(webhookurl == null || webhookurl.length() < 1){
            System.out.println("invalid webhookurl");
            return false;
        }
        if(twitchid == null){
            return false;
        }

        //target uri
        URI uri = URI.create(
                "https://api.twitch.tv/helix/eventsub/subscriptions");

        //payload
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", type);
        jsonObject.addProperty("version", "1");

        JsonObject condition = new JsonObject();
        condition.addProperty("broadcaster_user_id", twitchid);
        jsonObject.add("condition", condition);

        JsonObject transport = new JsonObject();
        transport.addProperty("method", "webhook");
        transport.addProperty("callback", webhookurl);
        transport.addProperty("secret", "bests3creteverprobably");
        jsonObject.add("transport", transport);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .uri(uri)
                .setHeader("Client-ID", App.settings.getClientID())
                .header("Authorization", "Bearer " + App.settings.getAppToken())
                .header("Content-Type", "application/json")
                .build();

        try {
            //get response
            HttpResponse<String> response = httpClient.send(request,  HttpResponse.BodyHandlers.ofString());

            int code = response.statusCode();

            if(code >= 200 && code < 300){
                System.out.println("eventSub request successful");
                return true;
            } else {
                System.out.println("eventSub request failed");
                System.out.println("code: " + code + "responed with: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean createPoll(UUID player, String title, ConcurrentSkipListSet<String> args, int duration){
        String twitchid = App.twitchData.getTwitchID(player);
        if(twitchid == null){
            System.out.println("missing twitch id");
            return false;
        }
        String userOauthToken = App.twitchData.getToken(player);
        if(userOauthToken == null){
            System.out.println("missing player oauth token");
            return false;
        }

        //target uri
        URI uri = URI.create(
                "https://api.twitch.tv/helix/polls");

        //payload
        JsonObject payload = new JsonObject();
        payload.addProperty("broadcaster_id", twitchid);
        payload.addProperty("title", title);
        JsonArray choices = new JsonArray();
        for(String arg : args){
            JsonObject temp = new JsonObject();
            temp.addProperty("title", arg);
            choices.add(temp);
        }
        payload.add("choices", choices);
        payload.addProperty("duration", duration);

        System.out.println(payload.toString());

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .uri(uri)
                .setHeader("Client-ID", App.settings.getClientID())
                .header("Authorization", "Bearer " + userOauthToken)
                .header("Content-Type", "application/json")
                .build();

        try {
            //get response
            HttpResponse<String> response = httpClient.send(request,  HttpResponse.BodyHandlers.ofString());

            int code = response.statusCode();

            if(code >= 200 && code < 300){
                System.out.println("create poll request successful");
                return true;
            } else {
                System.out.println("create poll request failed");
                System.out.println("code: " + code + "responed with: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static JsonArray getCurrentSubscriptionsList(){
        URI uri = URI.create(
                "https://api.twitch.tv/helix/eventsub/subscriptions");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .setHeader("Client-ID", App.settings.getClientID())
                .header("Authorization", "Bearer " + App.settings.getAppToken())
                .build();
        try {

            HttpResponse<String> response = httpClient.send(request,  HttpResponse.BodyHandlers.ofString());

            int code = response.statusCode();

            if(code >= 200 && code < 300){
                Gson gson = new Gson();
                JsonObject data = gson.fromJson(response.body(), JsonObject.class);
                System.out.println("getCurrentSubscriptions request successful");
                return data.get("data").getAsJsonArray();
            } else {

                System.out.println("code: " + code + "responed with: " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("getCurrentSubscriptions request failed");
        return null;
    }

    public static void endSubscription(String id) {
        URI uri = URI.create(
                "https://api.twitch.tv/helix/eventsub/subscriptions?id=" + id);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .setHeader("Client-ID", App.settings.getClientID())
                .header("Authorization", "Bearer " + App.settings.getAppToken())
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request,  HttpResponse.BodyHandlers.ofString());

            int code = response.statusCode();

            if(code >= 200 && code < 300){
                System.out.println("deleteSubscription request successful");
            } else {
                System.out.println("code: " + code + "responed with: " + response.body());
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("deleteSubscription request failed");
    }
}
