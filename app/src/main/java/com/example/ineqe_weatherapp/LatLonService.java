package com.example.ineqe_weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*This class utilises openweathermaps GeoCoding API which gives latitude and longitude
* values for a given city name. These can than be used openweathermaps other  APIs
* */
public class LatLonService {

    public static String[] getLatLon(String responseBody) throws JSONException {

        String[] latLon = new String[4];

        JSONArray values  = new JSONArray(responseBody);
        JSONObject val = values.getJSONObject(0);
        latLon[0] = String.valueOf(val.getDouble("lat"));
        latLon[1] = String.valueOf(val.getDouble("lon"));
        latLon[2] = val.getString("name");
        latLon[3] = val.getString("country");

        return latLon;
    }




}
