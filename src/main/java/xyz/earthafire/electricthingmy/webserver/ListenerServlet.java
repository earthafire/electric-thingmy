package xyz.earthafire.electricthingmy.webserver;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.eclipse.jetty.http.HttpStatus;
import xyz.earthafire.electricthingmy.App;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ListenerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("TEST GET");
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println("TEST GET");
        System.out.println("get " + req.toString());
        System.out.println("Authorization: " + req.getHeader("Authorization"));
        System.out.println("Client-ID: " + req.getHeader("Client-ID"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("TEST POST");

        String type = req.getHeader("Twitch-Eventsub-Message-Type");
        System.out.println("Twitch-Eventsub-Message-Type: " + type);

        if(type.equalsIgnoreCase("webhook_callback_verification")){
            //return challenge token
            JsonObject data = extractRequestBodyToJson(req);
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(data.get("challenge").getAsString());
        } else if (type.equalsIgnoreCase("notification")){
            //respond quickly
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println("OK");

            //handle event asynchronously
            App.eventSub.handleNotification(extractRequestBodyToJson(req));
        } else {
            System.out.println("unknown post payload, type: " + type);
            System.out.println(resp.toString());

            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println("IDK");
        }
    }

    public JsonObject extractRequestBodyToJson(HttpServletRequest req){

        try {
            InputStreamReader stream = new InputStreamReader(req.getInputStream());
            BufferedReader reader = new BufferedReader(stream);
            StringBuffer stringBuffer = new StringBuffer();
            String temp;
            while((temp = reader.readLine()) != null){
                stringBuffer.append(temp);
            }
            temp = stringBuffer.toString();
            Gson gson = new Gson();
            System.out.println(temp);
            JsonObject jsonObject = gson.fromJson(temp, JsonObject.class);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
