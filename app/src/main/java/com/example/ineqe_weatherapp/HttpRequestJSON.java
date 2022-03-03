package com.example.ineqe_weatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpRequestJSON {

    public static String executeGET(String apiEndPoint){

        HttpURLConnection connection;
        BufferedReader reader;
        String line;
        StringBuffer responseData = new StringBuffer(); //used to append each line of the response data

        URL url;

        {
            try {
                url = new URL(apiEndPoint);
                //open connection to API endpoint
                connection = (HttpURLConnection) url.openConnection();

                //Setup for Request
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000); //if connection not successful under 5s it will timeout
                connection.setReadTimeout(5000);

                //Determines whether request has been successful, returns 200 for success and 401 for not.
                int responseStatus = connection.getResponseCode();

                //Handle unsuccessful connection
                if (responseStatus > 300) {
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    while ((line = reader.readLine()) != null) {
                        responseData.append(line);
                    }
                    reader.close();
                } else {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        responseData.append(line);
                    }
                    reader.close();
                }

                //System.out.println(responseData.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return responseData.toString();
    }
}
