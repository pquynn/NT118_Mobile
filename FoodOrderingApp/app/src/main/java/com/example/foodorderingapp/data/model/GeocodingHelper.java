package com.example.foodorderingapp.data.model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class GeocodingHelper {

    private static final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    public static String getAddressFromPlusCode(String plusCode, String apiKey) {
        OkHttpClient client = new OkHttpClient();

        String url = GEOCODING_API_URL + "?address=" + plusCode + "&key=" + apiKey;
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray("results");
                if (results.length() > 0) {
                    JSONObject addressObject = results.getJSONObject(0);
                    return addressObject.getString("formatted_address");
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
