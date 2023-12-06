/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Aaron
 * This class represents the weather API and contains the method to call on it
 */
import com.fasterxml.jackson.core.JsonParser;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class WeatherAPI {
    private static final String ENDPOINT = "https://weatherapi-com.p.rapidapi.com/current.json";
    private static final String API_KEY = "601c08e0e0msh1dc185f80ea6d59p13e2eajsnbc5f2f1e5341";
    
    /**
     * This method get the weather from the weather API
     * @param city represents the city to find weather in
     * @return it returns a string with the important weather details
     * @throws IOException
     * @throws InterruptedException 
     */
    public static String getWeather(String city) throws IOException, InterruptedException {
    String url = String.format("%s?q=%s", ENDPOINT, URLEncoder.encode(city, "UTF-8"));
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .header("X-RapidAPI-Key", API_KEY)
        .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();

    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    
    String responseString = response.body();
    Pattern iconPattern = Pattern.compile("\"icon\":\"(.*?)\"");
    Pattern conditionPattern = Pattern.compile("\"text\":\"(.*?)\"");
    Pattern tempPattern = Pattern.compile("\"temp_c\":(\\d+\\.?\\d*)");
    Pattern lastUpdatedPattern = Pattern.compile("\"last_updated\":\"(.*?)\"");
    Matcher iconMatcher = iconPattern.matcher(responseString);
    Matcher conditionMatcher = conditionPattern.matcher(responseString);
    Matcher tempMatcher = tempPattern.matcher(responseString);
    Matcher lastUpdatedMatcher = lastUpdatedPattern.matcher(responseString);
   
    if (iconMatcher.find() && conditionMatcher.find() && tempMatcher.find() && lastUpdatedMatcher.find()) {
        String iconUrl = "http:" + iconMatcher.group(1);
        String condition = conditionMatcher.group(1);
        double tempC = Double.parseDouble(tempMatcher.group(1));
        String lastUpdated = lastUpdatedMatcher.group(1);
        JSONObject json = new JSONObject(response.body());
        JSONObject current = json.getJSONObject("current");
        double temp_c = current.getDouble("temp_c");
        String last_updated = current.getString("last_updated").toString();
        JSONObject current1 = json.getJSONObject("current");

// Get the condition object from the current object
        JSONObject condition1 = current.getJSONObject("condition");

// Get the text value from the condition object
        String conditionText = condition1.getString("text");
        String conditionIcon = "https:" + condition1.getString("icon");
        
        // Create a URL object from the string
        URL url1 = new URL(conditionIcon);

        // Read the image from the URL
        BufferedImage image = ImageIO.read(url1);

        // Create a File object to store the image
        File file = new File("dataFiles/weatherImage.png");

        // Write the image to the file
        ImageIO.write(image, "png", file);
        return String.format("Temperature: %sC, Condition: %s,\n Last updated: %s", temp_c, conditionText, last_updated);
    } 
        return null;
    

}

   
}


    


