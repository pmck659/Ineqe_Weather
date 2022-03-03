package com.example.ineqe_weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //All views to be used
    EditText enterCity;
    TextView mainTemp, cityText, todaysDate, minTempText, maxTempText, sunriseText, sunsSetText, windText;
    ListView mListView;
    ImageView searchButton;

    //Variables that do not change, needed for Http Requests
    private static  final String TAG = "MainActivity";
    final private String getCityendPointUrl1 = "http://api.openweathermap.org/geo/1.0/direct?q=";
    final private String getCityendPointURL2 = "&limit=1&appid=cbfe5fc5b5b8df2ca72e686ed0650bba";
    final private String getResponseDataEndPoint1 = "https://api.openweathermap.org/data/2.5/onecall?lat=";
    final private String getResponseDataEndPoint2 = "&lon=";
    final private String getResponseDataEndPoint3 = "&units=metric&exclude=minutely,hourly,alerts,current&appid=cbfe5fc5b5b8df2ca72e686ed0650bba";


    //Intitial values on creation, these could be swapped out for current location values if I have time
    private String cityName = "Belfast";
    private String state = "Northern Ireland";
    private String lat = "54.596391";
    private String lon = "-5.9301829";
    private boolean validCity = true; //this flag will be used by main activity to determine whether entered city is valid
    private ArrayList<DailyWeather> dailyWeather = new ArrayList<DailyWeather>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        mainTemp = findViewById(R.id.tempText);
        cityText = findViewById(R.id.cityText);
        todaysDate = findViewById(R.id.todaysDate);
        minTempText = findViewById(R.id.minTempText);
        maxTempText = findViewById(R.id.maxTempText);
        windText = findViewById(R.id.windText);
        mListView = findViewById(R.id.listView);
        enterCity = findViewById(R.id.enterCity);
        sunriseText = findViewById(R.id.sunriseTime);
        sunsSetText = findViewById(R.id.sunsetTime);
        searchButton = findViewById(R.id.searchButton);

        //Onclick listener for the search button, once user clicks
        // the asyn validateCity task is called
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new validateCity().execute();
            }
        });

        new updateData().execute();

    }


    //This instantiates the custom ListView adapter which was created to populate
    // the 8 day weather forecast data
    private void updateList(){
        ListViewDataAdapter adapter = new ListViewDataAdapter(this, R.layout.adapter_view_layout, dailyWeather);
        mListView.setAdapter(adapter);
    }



    /*Ansynchronus Tasks, dont affect main UI thread*/

    //This async task is called in the onCreate method and when user enters a valid city,
    //HttpRequest is made to retrieve weather data, this is done asynchrously in the background
    //so it doesnt affect the UI
    class updateData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {

            String cityResponse = HttpRequestJSON.executeGET(
                    getResponseDataEndPoint1
                            + lat + getResponseDataEndPoint2 + lon + getResponseDataEndPoint3);

            return cityResponse;
        }

        //8 day forecast list is updated, the the UI is updated using the new data
        @Override
        protected void onPostExecute(String result) {

            try {
                dailyWeather = ResponseData.getFormattedResponseData(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            Log.d(TAG, "onPostExecute: " + dailyWeather.get(0).toString());
            mainTemp.setText(dailyWeather.get(0).getTemp() + "°C");
            cityText.setText(cityName + " , "+ state);
            todaysDate.setText(dailyWeather.get(0).getDay()+ " " +dailyWeather.get(0).getDate() + " " +dailyWeather.get(0).getMonth());
            minTempText.setText("Lowest Temp: " +dailyWeather.get(0).getMin());
            maxTempText.setText("Highest Temp: " +dailyWeather.get(0).getMax());
            sunriseText.setText(dailyWeather.get(0).getSunrise_time());
            sunsSetText.setText(dailyWeather.get(0).getSunset_time());
            windText.setText(dailyWeather.get(0).getWind_speed() + "MPH, " + dailyWeather.get(0).getWind_direction());


            updateList();
        }
    }





    //The AsyncTask is used to validate whether the user has entered a valid city
    //if the city is not valid and TOAST notification is shown to the user. If
    // the city is valid then lat and lon values are retrieved with a HttpRequest.
    //Those values are then used for another request which retrieves the updated
    //forecast for the specified city. DailyWeather forecast list is then updated
    // and updateData Asyn Task is called.

    class validateCity extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... args) {

            String cityResponse = HttpRequestJSON.executeGET(getCityendPointUrl1 + enterCity.getText() + getCityendPointURL2);

            if(cityResponse.equals("[]")){
                validCity = false;
            }else {
                validCity = true;
                String[] cityLocationData = new String[4];
                try {
                    cityLocationData = LatLonService.getLatLon(cityResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                lat = cityLocationData[0];
                lon = cityLocationData[1];
                cityName = cityLocationData[2];
                state = cityLocationData[3];
            }

            return cityResponse;
        }

        @Override
        protected void onPostExecute(String result) {

            if(validCity == false){
                Toast.makeText(MainActivity.this, "Invalid City", Toast.LENGTH_SHORT).show();
            } else{
                new updateData().execute();
            }


        }
    }






}