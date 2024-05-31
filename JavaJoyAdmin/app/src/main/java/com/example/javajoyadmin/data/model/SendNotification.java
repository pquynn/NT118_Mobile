package com.example.javajoyadmin.data.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.javajoyadmin.utils.AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendNotification {
    private final String userFcmToken;
    private final String title;
    private final String orderId;
    private final String body;

    private final Context context;

    private final String postUrl = "https://fcm.googleapis.com/v1/projects/javajoy-mobileapp/messages:send";
    public SendNotification(String userFcmToken, String title, String body, String orderId, Context context) {
        this.userFcmToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.orderId = orderId;
        this.context = context;
    }

    public void SendNotifications(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject mainObj = new JSONObject();
        try{
            JSONObject messageObject = new JSONObject(); 
            JSONObject notificationObject = new JSONObject();
//            JSONObject dataObject = new JSONObject();
//            dataObject.put("orderId", orderId);
            notificationObject.put( "body", body);
            notificationObject.put( "title", title);
            messageObject. put( "token", userFcmToken); 
            messageObject.put( "notification", notificationObject);
//            messageObject.put("data", dataObject);
            mainObj.put( "message", messageObject); 
            JsonObjectRequest request = new JsonObjectRequest(Request. Method. POST, postUrl, mainObj, response -> {
            // code run got response
            }, volleyError -> { // code run error
            }){
                @NonNull
                @Override
                public Map<String, String> getHeaders () {
                    AccessToken accessToken = new AccessToken();
                    String accessKey = accessToken.getAccessToken();
                    Log.d("fcm", "getHeaders: " + accessKey);
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "Bearer " + accessKey);
                    return header;
                }
            };
            requestQueue. add(request);
        }catch (JSONException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
