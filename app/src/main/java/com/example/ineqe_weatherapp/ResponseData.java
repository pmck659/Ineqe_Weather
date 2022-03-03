package com.example.ineqe_weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ResponseData {
    /*
     * Helper function to converts unix UTC timestamps to a presentable format
     * */

    private static String[] getDateTime(int timeStamp, String timeZone, boolean sunTimeOnly){

        // convert seconds to milliseconds
        Date date = new java.util.Date(timeStamp*1000L);
        // the format of your date
        SimpleDateFormat sdf;

        if(sunTimeOnly == true){
            sdf = new java.text.SimpleDateFormat("hh:mm a");
        }else{
            sdf = new java.text.SimpleDateFormat("EEE dd MMM yyyy hh:mm a");
        }
        // give a timezone reference for formatting
        sdf.setTimeZone(java.util.TimeZone.getTimeZone(timeZone));

        String formattedDate = sdf.format(date);

        return formattedDate.split(" ");
    }

    /*Helper Function used to get wind direction, openweathermaps api only gives direction in degrees
    which is not helpful to the average user.
     */
    private static String getWindDirection(int degree) {
        int val = (int) ((degree / 22.5) + .5);
        String[] directions = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};

        return directions[(val % 16)];
    }

    /*The API returns wind speed in metres/second. This helper function converts it to
     * Miles Per Hour
     * */
    private static String covertWindSpeed(double speedMPS) {

        int speedMPH = (int) (speedMPS * 2.237);

        return String.valueOf(speedMPH);
    }

/*Formats response data and returns to list of dailyWeather objects which contains weather forecast information
for the next 8 days
*/
    public static ArrayList<DailyWeather> getFormattedResponseData(String responseBody) throws JSONException{


        ArrayList<DailyWeather> dailyForecasts = new ArrayList<DailyWeather>();
        JSONObject values = new JSONObject(responseBody);
        JSONArray dailyWeather = values.getJSONArray("daily");

        String[] weatherReport = new String[10];

        for (int i = 0; i < dailyWeather.length(); i++) {
            JSONObject daily = dailyWeather.getJSONObject(i);
            String[] dateTime = getDateTime(daily.getInt("dt"), values.getString("timezone"), false);

            //inserting day, date, month
            weatherReport[0] = dateTime[0];
            weatherReport[1] = dateTime[1];
            weatherReport[2] = dateTime[2];

            //inserting daily, max & min temperatures
            JSONObject temp = daily.getJSONObject("temp");
            weatherReport[3] = String.valueOf((int)temp.getDouble("day"));
            weatherReport[4] = String.valueOf((int)temp.getDouble("max"));
            weatherReport[5] = String.valueOf((int)temp.getDouble("min"));

            //inserting wind speed & direction
            weatherReport[6] = covertWindSpeed(daily.getDouble("wind_speed"));
            weatherReport[7] = getWindDirection(daily.getInt("wind_deg"));

            //inserting sunrise and sunset time in AM/PM format
            dateTime = getDateTime(daily.getInt("sunrise"), values.getString("timezone"), true);
            weatherReport[8] = dateTime[0] + dateTime[1];


            dateTime = getDateTime(daily.getInt("sunset"), values.getString("timezone"), true);
            weatherReport[9] = dateTime[0] + dateTime[1];

            dailyForecasts.add(new DailyWeather(weatherReport));
        }

        return dailyForecasts;
    }
}