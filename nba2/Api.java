/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;

// Import Java I/O library 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

// Imports for making HTTP requests
import java.net.HttpURLConnection;
import java.net.URL;

// Imports for handling JSON data
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Api {

    //private instances
    private static int[] idArray = new int[1000000]; 
    private static int idCount = 0; // To keep track of the number of IDs

    // Main method to fetch player data and stats from API
    public static void main(String[] args) throws JSONException {
        try {
            Integer nextCursor = 0; // Initially set to 0
            boolean hasNext = true;

            // Create a new CSV file and write the headers
            FileWriter writer = new FileWriter("players.csv");
            writer.write("ID,FirstName,LastName,Position,Height,Weight,Points,Assist,Rebound,Steal,Block\n");
            writer.close();

            while (hasNext) {
                // Create URL object with the API endpoint and cursor
                URL url = new URL("https://api.balldontlie.io/v1/players?cursor=" + nextCursor);

                // Create HttpURLConnection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Set request method and headers
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "feb57b30-2519-4a28-9c3c-d0fa226b500f");

                // Get response code
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read response from input stream
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONObject meta = jsonResponse.getJSONObject("meta");
                    nextCursor = meta.optInt("next_cursor", -1);

                    JSONArray data = jsonResponse.getJSONArray("data");

                    // Append response data to CSV file
                    appendDataToCSV(data);

                    System.out.println("Cursor " + nextCursor + " processed");

                    // Close connection
                    conn.disconnect();

                    // Check if there is a next cursor
                    hasNext = nextCursor != -1;
                } else {
                    System.out.println("Failed to retrieve data with cursor " + nextCursor);
                    break;
                }
            }

            // Fetch and append stats for each player ID in idArray
            getStats();

            System.out.println("All data processed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch and append stats for players
    private static void getStats() throws JSONException {
        try {
            for (int i = 0; i < idCount; i++) {
                int playerId = idArray[i];
                
                // Create URL object with the API endpoint and palayer id
                URL url = new URL("https://api.balldontlie.io/v1/season_averages?season=2023&player_ids[]=" + playerId);

                // Create HttpURLConnection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Set request method
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "feb57b30-2519-4a28-9c3c-d0fa226b500f");

                // Get response code
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read response from input stream
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray statsData = jsonResponse.getJSONArray("data");

                    // If stats are available, append to CSV
                    if (statsData.length() > 0) {
                        JSONObject stats = statsData.getJSONObject(0);
                        appendStatsToCSV(playerId, stats);
                    } else {
                        removePlayerFromCSV(playerId); // Remove player if no stats are available
                    }

                    System.out.println("Stats for player ID " + playerId + " processed");

                    // Close connection
                    conn.disconnect();
                } else {
                    System.out.println("Failed to retrieve stats for player ID " + playerId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to append player data to CSV file
    private static void appendDataToCSV(JSONArray data) throws IOException, JSONException {
        FileWriter writer = new FileWriter("players.csv", true); // Append mode

        for (int i = 0; i < data.length(); i++) {
            JSONObject player = data.getJSONObject(i);

            int id = player.getInt("id"); // Extracting player's id

            String firstName = player.getString("first_name");
            String lastName = player.getString("last_name");
            String weight = player.optString("weight", ""); // Use optString to avoid JSONException
            String position = player.optString("position", "");
            String height = player.optString("height", "");

            // Check for missing values
            if (firstName.isEmpty() || lastName.isEmpty() || weight.isEmpty() || position.isEmpty() || height.isEmpty()) {
                continue; // Skip this player if any value is missing
            }
        
            // Convert height from feet-inches to inches
            String[] heightParts = height.split("-");
            if (heightParts.length != 2) {
                continue; // Skip this player if height format is invalid
            }

            // Parse feet and inches
            int feet,inches;
            try {
                feet = Integer.parseInt(heightParts[0]);
                inches = Integer.parseInt(heightParts[1]);
            } catch (NumberFormatException e) {
                continue; // Skip this player if height cannot be parsed as integers
            }
        
            // convert inches to meters
            int totalHeightInInches = feet * 12 + inches;
            double heightInMeters = totalHeightInInches * 0.0254;
            heightInMeters = Math.round(heightInMeters * 100.0) / 100.0;


            if (position.length() > 1) {
                position = String.valueOf(position.charAt(0));
            }

            // Add ID to idArray and increment idCount
            idArray[idCount++] = id; 

            // Append player information to CSV file
            writer.write(id + "," + firstName + "," + lastName + "," + position + "," + heightInMeters + "," + weight);
            writer.write("\n"); // Add newline for next entry
        }

        writer.close();
    }

    

    // Method to append player statistics to the CSV file
    private static void appendStatsToCSV(int playerId, JSONObject stats) throws IOException, JSONException {
        FileReader fileReader = new FileReader("players.csv");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder updatedContent = new StringBuilder();
        String line;
        boolean found = false;

        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith(playerId + ",")) {
                
                // Parse player statistics from JSON object
                double reb = stats.optDouble("reb", -1);
                double ast = stats.optDouble("ast", -1);
                double stl = stats.optDouble("stl", -1);
                double blk = stats.optDouble("blk", -1);
                double pts = stats.optDouble("pts", -1);

                // Check for missing stats
                if (reb == -1 || ast == -1 || stl == -1 || blk == -1 || pts == -1) {
                    continue; // Skip this player if any stat is missing
                }

                // Append stats to the line
                line = line + "," + pts + "," + ast + "," + reb + "," + stl + "," + blk;
                found = true;
            }
            updatedContent.append(line).append("\n");
        }

        bufferedReader.close();
        fileReader.close();

        // Write updated content back to the CSV file
        FileWriter writer = new FileWriter("players.csv");
        writer.write(updatedContent.toString());
        writer.close();

        // If no valid stats were found for the player, remove them from the CSV
        if (!found) {
            removePlayerFromCSV(playerId);
        }
    }

    // Method to remove player from the CSV file
    private static void removePlayerFromCSV(int playerId) throws IOException {
        FileReader fileReader = new FileReader("players.csv");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder updatedContent = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            if (!line.startsWith(playerId + ",")) {
                updatedContent.append(line).append("\n");
            }
        }

        bufferedReader.close();
        fileReader.close();

        // Write updated content back to the CSV file
        FileWriter writer = new FileWriter("players.csv");
        writer.write(updatedContent.toString());
        writer.close();
    }
}
