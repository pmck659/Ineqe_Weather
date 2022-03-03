package com.example.ineqe_weatherapp;


/*
* This class is used to stored weather information for a particular day.
* */
public class DailyWeather {
    
    private String day;
    private String date;
    private String month;
    private String temp;
    private String max;
    private String min;
    private String wind_speed;
    private String wind_direction;
    private String sunrise_time;
    private String sunset_time;


    public DailyWeather(String[] values) {
        this.day = values[0];
        this.date = values[1];
        this.month = values[2];
        this.temp = values[3];
        this.max = values[4];
        this.min = values[5];
        this.wind_speed = values[6];
        this.wind_direction = values[7];
        this.sunrise_time = values[8];
        this.sunset_time = values[9];
    }

    public DailyWeather(String day, String date, String month, String temp, String max, String min, String wind_speed, String wind_direction, String sunrise_time, String sunset_time) {
        this.day = day;
        this.date = date;
        this.month = month;
        this.temp = temp;
        this.max = max;
        this.min = min;
        this.wind_speed = wind_speed;
        this.wind_direction = wind_direction;
        this.sunrise_time = sunrise_time;
        this.sunset_time = sunset_time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getSunrise_time() {
        return sunrise_time;
    }

    public void setSunrise_time(String sunrise_time) {
        this.sunrise_time = sunrise_time;
    }

    public String getSunset_time() {
        return sunset_time;
    }

    public void setSunset_time(String sunset_time) {
        this.sunset_time = sunset_time;
    }

    @Override
    public String toString() {
        return "DailyWeather{" +
                "day='" + day + '\'' +
                ", date='" + date + '\'' +
                ", month='" + month + '\'' +
                ", temp='" + temp + '\'' +
                ", max='" + max + '\'' +
                ", min='" + min + '\'' +
                ", wind_speed='" + wind_speed + '\'' +
                ", wind_direction='" + wind_direction + '\'' +
                ", sunrise_time='" + sunrise_time + '\'' +
                ", sunset_time='" + sunset_time + '\'' +
                '}';
    }
}
