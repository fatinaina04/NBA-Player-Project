package nba2;

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

import java.util.*;

public class NBACity extends Application {

    // Adjacency matrix representing distances between cities
    private static final int[][] distances = {
         {0, 0, 0, 0, 0, 500, 1137, 0, 678, 983},
        {0, 0, 0, 0, 554, 0, 0, 1507, 2214, 0},
        {0, 0, 0, 3045, 0, 0, 0, 2845, 0, 2584},
        {0, 0, 3045, 0, 0, 0, 268, 0, 0, 0},
        {0, 554, 0, 0, 0, 577, 0, 0, 1901, 0},
        {500, 0, 0, 0, 577, 0, 0, 0, 0, 0},
        {1137, 0, 0, 268, 0, 0, 0, 0, 0, 458},
        {0, 1507, 2845, 0, 0, 0, 0, 0, 942, 0},
        {678, 2214, 0, 0, 1901, 0, 0, 942, 0, 778},
        {983, 0, 2584, 0, 0, 0, 458, 0, 778, 0}
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
        primaryStage.setTitle("NBA Travel Planner");
        Button openCityMapButton = new Button("Open NBA City Map");

        openCityMapButton.setOnAction(e -> openNBACity(primaryStage));

        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().add(openCityMapButton);

        Scene mainScene = new Scene(mainLayout, 300, 200);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public void openNBACity(Stage primaryStage) {
        Stage cityStage = new Stage();

        cityStage.initOwner(primaryStage);
        cityStage.initModality(Modality.APPLICATION_MODAL);

        Text cityTitle = new Text("NBA City Map");

        Button btnBFS = new Button("Breadth First Search");
        Button btnDFS = new Button("Depth First Search");
        Button btnBestJourney = new Button("Overall Best Journey");
        Button btnDigitalMap = new Button("Digital Map");

        // Set up the layout
        VBox cityLayout = new VBox(10);
        cityLayout.setAlignment(Pos.CENTER);
        cityLayout.setPadding(new Insets(20, 20, 20, 20));
        cityLayout.getChildren().addAll(cityTitle, btnDigitalMap, btnBFS, btnDFS, btnBestJourney);

        // Set up the scene
        Scene cityScene = new Scene(cityLayout, 300, 200);
        cityScene.getRoot().setStyle("-fx-background-color: #FFF3B0;");
        cityStage.setScene(cityScene);
        cityStage.setTitle("NBA City Map");

        // Button actions
        btnBFS.setOnAction(e -> displayBFS(primaryStage));
        btnDFS.setOnAction(e -> displayDFS(primaryStage));
        btnBestJourney.setOnAction(e -> displayBestJourney(primaryStage));
        btnDigitalMap.setOnAction(e -> displayDigitalMap(primaryStage));

        cityStage.show();
    }

    private void displayBFS(Stage owner) {
        Arrays.fill(visited, false);
        List<Integer> bfsRoute = new ArrayList<>();
        int bfsDistance = bfs(0, bfsRoute);
        showPopup(owner, "Breadth First Search Path", bfsRoute, bfsDistance);
    }

    private void displayDFS(Stage owner) {
        Arrays.fill(visited, false);
        List<Integer> dfsRoute = new ArrayList<>();
        int dfsDistance = dfs(0, dfsRoute, 0);
        showPopup(owner, "Depth First Search Path", dfsRoute, dfsDistance);
    }

    private void displayBestJourney(Stage owner) {
        Arrays.fill(visited, false);
        List<Integer> dfsRoute = new ArrayList<>();
        int dfsDistance = dfs(0, dfsRoute, 0);

        Arrays.fill(visited, false);
        List<Integer> bfsRoute = new ArrayList<>();
        int bfsDistance = bfs(0, bfsRoute);

        if (dfsDistance < bfsDistance) {
            showPopup(owner, "Overall Best Journey (DFS)", dfsRoute, dfsDistance);
        } else {
            showPopup(owner, "Overall Best Journey (BFS)", bfsRoute, bfsDistance);
        }
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

    private void displayDigitalMap(Stage owner) {
        Stage mapStage = new Stage();
        mapStage.initOwner(owner);
        mapStage.initModality(Modality.APPLICATION_MODAL);
        mapStage.setTitle("Digital Map");

        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawMap(gc);

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().add(canvas);

        Scene scene = new Scene(layout, 800, 600);
        mapStage.setScene(scene);
        mapStage.show();
    }

    private void drawMap(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);

        // Coordinates for the cities
        double[][] coordinates = {
            {300, 450},  // San Antonio
            {50, 100},   // Golden State
            {700, 50},   // Boston
            {700, 500}   // Miami
            {100, 300},  // Los Angeles
            {250, 350},  // Phoenix
            {600, 400},  // Orlando
            {400, 75},  // Denver
            {300, 300},  // Oklahoma City
            {500, 350},  // Houston
        };

        // Draw cities
        for (int i = 0; i < coordinates.length; i++) {
            gc.fillOval(coordinates[i][0] - 5, coordinates[i][1] - 5, 10, 10);
            gc.fillText(cities[i], coordinates[i][0] + 10, coordinates[i][1]);
        }

        // Draw edges
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances[i].length; j++) {
                if (distances[i][j] > 0) {
                    gc.strokeLine(coordinates[i][0], coordinates[i][1], coordinates[j][0], coordinates[j][1]);
                    double midX = (coordinates[i][0] + coordinates[j][0]) / 2;
                    double midY = (coordinates[i][1] + coordinates[j][1]) / 2;
                    gc.fillText(distances[i][j] + " km", midX, midY);
                }
            }
        }
    }

    private void showPopup(Stage owner, String title, List<Integer> route, int totalDistance) {
        StringBuilder routeInfo = new StringBuilder("Shortest Path:\n");
        for (int cityIndex : route) {
            routeInfo.append("--> ").append(cities[cityIndex]).append("\n");
        }
        routeInfo.append("\nTotal Distance: ").append(totalDistance).append(" km");

        Stage popupStage = new Stage();
        popupStage.initOwner(owner);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        Text routeText = new Text(routeInfo.toString());
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(routeText);

        Scene scene = new Scene(layout, 500, 600); // Increase the size of the popup
        scene.getRoot().setStyle("-fx-background-color: #FFF3B0;");
        popupStage.setScene(scene);
        popupStage.show();
    }

    private void showPopup(Stage owner, String title, int totalDistance) {
        String distanceInfo = "Total Distance: " + totalDistance + " km";

        Stage popupStage = new Stage();
        popupStage.initOwner(owner);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        Text distanceText = new Text(distanceInfo);
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(distanceText);

        Scene scene = new Scene(layout, 500, 600); // Increase the size of the popup
        scene.getRoot().setStyle("-fx-background-color: #FFF3B0;");
        popupStage.setScene(scene);
        popupStage.show();
    }
}
