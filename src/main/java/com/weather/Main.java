package com.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Şehir adını girin : ");
            String city = scanner.nextLine();
            scanner.close();

            String apiKey = "your_api_key";
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" +
                    city + "&appid=" + apiKey + "&units=metric&lang=tr";

            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();

            // 1- Sunucudan veriyi oku ve response'a ekle
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();

            // 2- Artık veriler toplandı, JSON olarak işleyelim
            String jsonString = response.toString();
            JSONObject jsonObject = new JSONObject(jsonString);

            // 3- İstediğimiz verileri alalım

            // Şehir adı
            String cityName = jsonObject.getString("name");

            // Sıcaklık bilgileri
            JSONObject main = jsonObject.getJSONObject("main");
            double temperature = main.getDouble("temp");
            double feelsLike = main.getDouble("feels_like");
            double tempMin = main.getDouble("temp_min");
            double tempMax = main.getDouble("temp_max");
            int humidity = main.getInt("humidity");

            // Hava durumu açıklaması
            JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
            String description = weather.getString("description");

            // Rüzgar bilgisi
            JSONObject wind = jsonObject.getJSONObject("wind");
            double windSpeed = wind.getDouble("speed");

            // 4- Verileri yazdıralım
            System.out.println("Şehir: " + cityName);
            System.out.println("Sıcaklık: " + temperature + " °C");
            System.out.println("Hissedilen: " + feelsLike + " °C");
            System.out.println("Minimum Sıcaklık: " + tempMin + " °C");
            System.out.println("Maximum Sıcaklık: " + tempMax + " °C");
            System.out.println("Nem: %" + humidity);
            System.out.println("Hava Durumu: " + description);
            System.out.println("Rüzgar Hızı: " + windSpeed + " m/s");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
