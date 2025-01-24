package com.live;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherApp {
    public static void main(String[] args) {
        String apiKey = "eee1860ebab34ef3b7b162616251101"; 
        String location = "ahmedabad"; 
        String apiUrl = "http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + location;

        try {
           
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            
            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

              
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());

                String cityName = rootNode.get("location").get("name").asText();
                String temperature = rootNode.get("current").get("temp_c").asText() + "°C";
                String condition = rootNode.get("current").get("condition").get("text").asText();
                String humidity = rootNode.get("current").get("humidity").asText() + "%";
                String windSpeed = rootNode.get("current").get("wind_kph").asText() + " km/h";
                String feelsLike = rootNode.get("current").get("feelslike_c").asText() + "°C";

             
                System.out.println("Weather Information for " + cityName + ":");
                System.out.println("--------------------------------");
                System.out.println("Temperature: " + temperature);
                System.out.println("Condition: " + condition);
                System.out.println("Humidity: " + humidity);
                System.out.println("Wind Speed: " + windSpeed);
                System.out.println("Feels Like: " + feelsLike);
                System.out.println("--------------------------------");
            } else {
                System.out.println("Error: Unable to fetch weather data. HTTP Code: " + connection.getResponseCode());
            }

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
