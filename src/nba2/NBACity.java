/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;

// Import JavaFX library 
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextFlow;

// Import Java Util library 
import java.util.*;

public class NBACity extends Application {

    // Adjacency matrix representing distances between cities
    private static final int[][] distances = {
         {0, 0, 0, 0, 0, 500, 1137, 0, 678, 983},// San Antonio (Spurs)
        {0, 0, 0, 0, 554, 0, 0, 1507, 2214, 0}, // Golden State (Warriors)
        {0, 0, 0, 3045, 0, 0, 0, 2845, 0, 2584}, // Boston (Celtics)
        {0, 0, 3045, 0, 0, 0, 268, 0, 0, 0}, // Miami (Heat)
        {0, 554, 0, 0, 0, 577, 0, 0, 1901, 0}, //Los Angeles (Lakers)
        {500, 0, 0, 0, 577, 0, 0, 0, 0, 0}, // Phoenix (Suns)
        {1137, 0, 0, 268, 0, 0, 0, 0, 0, 458}, //Orlando (Magic)
        {0, 1507, 2845, 0, 0, 0, 0, 0, 942, 0}, //Denver (Nuggets)
        {678, 2214, 0, 0, 1901, 0, 0, 0, 0, 778}, // Oklahoma City (Thunder)
        {983, 0, 0, 0, 0, 0, 458, 0, 778, 0} //Houston (Rockets)
    };

    private static final String[] cities = {
        "San Antonio", "Golden State", "Boston", "Miami", "Los Angeles",
        "Phoenix", "Orlando", "Denver", "Oklahoma City", "Houston"
    };

    private static final boolean[] visited = new boolean[10];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        openNBACity(primaryStage);
    }

    public void openNBACity(Stage primaryStage) {
        Stage cityStage = new Stage();

        cityStage.initOwner(primaryStage);
        cityStage.initModality(Modality.APPLICATION_MODAL);

        Text cityTitle = new Text("⭐️ NBA City Map  ⭐️");
        cityTitle.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-font-family: 'Impact';");

        Button btnBFS = new Button("Breadth First Search");
        Button btnDFS = new Button("Depth First Search");
        Button btnBestJourney = new Button("Overall Best Journey");
        Button btnDigitalMap = new Button("Digital Map");
        Button backButton = new Button("Back");
        
        btnBFS.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        btnDFS.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        btnBestJourney.setStyle("-fx-background-color:#FFA500 ;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        btnDigitalMap.setStyle("-fx-background-color:#FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold; ");
        backButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        

        // Set up the layout
        VBox cityLayout = new VBox(10);
        cityLayout.setAlignment(Pos.CENTER);
        cityLayout.setPadding(new Insets(20, 20, 20, 20));
        cityLayout.getChildren().addAll(cityTitle, btnDigitalMap, btnBFS, btnDFS, btnBestJourney,backButton);

        // Set up the scene
        Scene cityScene = new Scene(cityLayout, 400, 300);
        cityScene.getRoot().setStyle("-fx-background-color: #3b5998;");
        cityStage.setScene(cityScene);
        cityStage.setTitle("NBA - City Map 🌍");

        // Button actions
        btnBFS.setOnAction(e -> displayBFS(cityStage));
        btnDFS.setOnAction(e -> displayDFS(cityStage));
        btnBestJourney.setOnAction(e -> displayBestJourney(cityStage));
        btnDigitalMap.setOnAction(e -> displayDigitalMap(cityStage));
        backButton.setOnAction(e -> cityStage.close());
        cityStage.show();
    }


    private static int dfs(int currentNode, List<Integer> route, int currentDistance) {
        visited[currentNode] = true;
        route.add(currentNode);
        int totalDistance = currentDistance;

        int shortestDistance = Integer.MAX_VALUE;
        int nextNode = -1;

        for (int i = 0; i < distances[currentNode].length; i++) {
            if (!visited[i] && distances[currentNode][i] < shortestDistance && distances[currentNode][i] > 0) {
                shortestDistance = distances[currentNode][i];
                nextNode = i;
            }
        }

        if (nextNode != -1) {
            totalDistance += dfs(nextNode, route, distances[currentNode][nextNode]);
        }

        return totalDistance;
    }

    private static int bfs(int startNode, List<Integer> route) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);
        visited[startNode] = true;

        int totalDistance = 0;

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            route.add(currentNode);

            int shortestDistance = Integer.MAX_VALUE;
            int nextNode = -1;

            for (int i = 0; i < distances[currentNode].length; i++) {
                if (!visited[i] && distances[currentNode][i] < shortestDistance && distances[currentNode][i] > 0) {
                    shortestDistance = distances[currentNode][i];
                    nextNode = i;
                }
            }

            if (nextNode != -1) {
                queue.add(nextNode);
                visited[nextNode] = true;
                totalDistance += distances[currentNode][nextNode];
            }
        }

        return totalDistance;
    }
    private void displayBFS(Stage owner) {
        Arrays.fill(visited, false);
        List<Integer> bfsRoute = new ArrayList<>();
        int bfsDistance = bfs(0, bfsRoute);
        showPopup(owner, "Breadth First Search Path", bfsRoute, bfsDistance,"Breath-First Search");
    }

    private void displayDFS(Stage owner) {
        Arrays.fill(visited, false);
        List<Integer> dfsRoute = new ArrayList<>();
        int dfsDistance = dfs(0, dfsRoute, 0);
        showPopup(owner, "Depth First Search Path", dfsRoute, dfsDistance,"Depth-First Search");
    }

    private void displayBestJourney(Stage owner) {
        Arrays.fill(visited, false);
        List<Integer> dfsRoute = new ArrayList<>();
        int dfsDistance = dfs(0, dfsRoute, 0);

        Arrays.fill(visited, false);
        List<Integer> bfsRoute = new ArrayList<>();
        int bfsDistance = bfs(0, bfsRoute);

        if (dfsDistance < bfsDistance) {
            showPopup(owner, "Overall Best Journey", dfsRoute, dfsDistance,"Overall Best Journey: Depth-First Search");
        } else {
            showPopup(owner, "Overall Best Journey", bfsRoute, bfsDistance,"Overall Best Journey: Breath-First Search");
        }
    }

    private void displayDigitalMap(Stage owner) {
        Stage mapStage = new Stage();
        mapStage.initOwner(owner);
        mapStage.initModality(Modality.APPLICATION_MODAL);
        mapStage.setTitle("Digital Map");
        
        Text injuryTitle = new Text("Digital Map");
        injuryTitle.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;-fx-font-family: 'Impact';");


        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawMap(gc);
        
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        backButton.setOnAction(e -> {
            openNBACity(owner);
            mapStage.close();
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(injuryTitle,canvas,backButton);

        Scene scene = new Scene(layout, 900, 700);
        scene.getRoot().setStyle("-fx-background-color: #3b5998;");
        mapStage.setScene(scene);
        mapStage.show();
    }

    private void drawMap(GraphicsContext gc) {
    gc.setFill(Color.WHITE);// text colour
    gc.setLineWidth(3);
    gc.setFont(Font.font("Arial", FontWeight.NORMAL, 13)); // Change font for city labels

    // Coordinates for the cities
    double[][] coordinates = {
        {300, 500}, // San Antonio (Spurs)
        {100, 100}, // Golden State (Warriors)
        {700, 100}, // Boston (Celtics)
        {700, 500}, // Miami (Heat)
        {50, 200}, // Los Angeles (Lakers)
        {150, 300}, // Phoenix (Suns)
        {550, 200}, // Orlando (Magic)
        {400, 50},  // Denver (Nuggets)
        {400, 300}, // Oklahoma City (Thunder)
        {500, 500}  // Houston (Rockets)
    };
    
    
    // Draw edges between cities
    drawEdges(gc, coordinates, distances);

    // Draw cities
    drawCities(gc, coordinates, cities);


}
    private void drawEdges(GraphicsContext gc, double[][] coordinates, int[][] distances) {
    for (int i = 0; i < coordinates.length; i++) {
        for (int j = 0; j < coordinates.length; j++) {
            if (i != j && distances[i][j] > 0) {
                gc.setStroke(Color.BLACK); // Set color for edge
                gc.strokeLine(coordinates[i][0], coordinates[i][1], coordinates[j][0], coordinates[j][1]); // Draw edge
                double midX = (coordinates[i][0] + coordinates[j][0]) / 2;
                double midY = (coordinates[i][1] + coordinates[j][1]) / 2;
                gc.fillText(distances[i][j] + " km", midX, midY - 5); // Draw distance text
            }
        }
    }
}
    
    private void drawCities(GraphicsContext gc, double[][] coordinates, String[] cityNames) {
    for (int i = 0; i < coordinates.length; i++) {
        gc.setStroke(Color.BLACK); // Set color for city dot border
        gc.strokeOval(coordinates[i][0] - 6, coordinates[i][1] - 6, 12, 12); // Draw city dot border
        gc.setStroke(Color.ORANGE); // Set color for city dot
        gc.fillOval(coordinates[i][0] - 5, coordinates[i][1] - 5, 10, 10); // Draw city dot
        gc.setFill(Color.ORANGE); // Set color for city label
        gc.fillText(cityNames[i], coordinates[i][0] + 15, coordinates[i][1] - 10); // Draw city label with increased distance
    }
}

private void showPopup(Stage owner, String title, List<Integer> route, int totalDistance, String t) {
    Stage popupStage = new Stage();
    
    // Create the main text components
    Text bfsTitle = new Text(t + " 🔎\n");
    bfsTitle.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-font-family: 'Impact';");

    Text titleText = new Text("\nShortest Path:\n");
    titleText.setStyle("-fx-font-family: Arial; -fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: white;");

    // Create text components for the route
    StringBuilder routeInfo = new StringBuilder();
    for (int cityIndex : route) {
        routeInfo.append("--> ").append(cities[cityIndex]).append("\n");
    }
    Text routeText = new Text(routeInfo.toString());
    routeText.setStyle("-fx-font-family: Arial; -fx-font-size: 14px; -fx-fill: white;");

    // Create the text component for the total distance
    Text distanceText = new Text("\nTotal Distance: " + totalDistance + " km");
    distanceText.setStyle("-fx-font-family: Arial; -fx-font-size: 18px; -fx-font-weight: bold; -fx-fill: white;");

    // Combine all text components in a TextFlow
    TextFlow textFlow = new TextFlow(bfsTitle, titleText, routeText, distanceText);

    // Create the back button
    Button backButton = new Button("Back");
    backButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
    backButton.setOnAction(e -> {
        openNBACity(owner);
        popupStage.close();
    });

    // Create the layout
    VBox layout = new VBox(10);
    layout.setAlignment(Pos.CENTER); // Center the content vertically
    layout.setPadding(new Insets(20));
    layout.getChildren().addAll(textFlow, backButton);

    // Create the scene and set its properties
    Scene scene = new Scene(layout, 400, 350);
    scene.getRoot().setStyle("-fx-background-color: #3b5998;");

    // Setup and show the popup stage
    popupStage.initOwner(owner);
    popupStage.initModality(Modality.APPLICATION_MODAL);
    popupStage.setTitle(title);
    popupStage.setScene(scene);
    popupStage.show();
}

}