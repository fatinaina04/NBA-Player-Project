/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class PerformanceRanking extends Application {
    private List<Player> activeRoster = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("NBA - Active Roster");

        // Create UI components
        Button performanceRankButton = new Button("Performance Rank");
        performanceRankButton.setOnAction(e -> openPerformanceRanking(primaryStage));

        VBox rosterLayout = new VBox(10);
        rosterLayout.setAlignment(Pos.CENTER);
        rosterLayout.setPadding(new Insets(20, 20, 20, 20));

        rosterLayout.getChildren().addAll(performanceRankButton);

        Scene rosterScene = new Scene(rosterLayout, 300, 200);
        primaryStage.setScene(rosterScene);

        primaryStage.show();
    }

    private void openPerformanceRanking(Stage primaryStage) {
        Stage rankingStage = new Stage();

        rankingStage.initOwner(primaryStage);
        rankingStage.initModality(Modality.APPLICATION_MODAL);

        // Calculate composite scores
        Map<String, Map<String, Double>> weights = getWeights();
        for (Player player : activeRoster) {
            player.calculateCompositeScore(weights);
        }

        // Sort players by composite score in descending order
        activeRoster.sort((p1, p2) -> Double.compare(p2.compositeScore, p1.compositeScore));

        // Create UI components
        VBox rankingLayout = new VBox(10);
        rankingLayout.setAlignment(Pos.CENTER);
        rankingLayout.setPadding(new Insets(20, 20, 20, 20));

        for (Player player : activeRoster) {
            rankingLayout.getChildren().add(new javafx.scene.control.Label(player.toString()));
        }

        // Set up the scene
        Scene rankingScene = new Scene(rankingLayout, 300, 200);
        rankingScene.getRoot().setStyle("-fx-background-color: #FFF3B0;");
        rankingStage.setScene(rankingScene);
        rankingStage.setTitle("NBA - Performance Ranking");

        // Show the stage
        rankingStage.show();
    }

    private Map<String, Map<String, Double>> getWeights() {
        Map<String, Map<String, Double>> weights = new HashMap<>();

        Map<String, Double> guardWeights = new HashMap<>();
        guardWeights.put("PTS", 0.3);
        guardWeights.put("REB", 0.1);
        guardWeights.put("STL", 0.25);
        guardWeights.put("AST", 0.3);
        guardWeights.put("BLK", 0.05);
        weights.put("G", guardWeights);

        Map<String, Double> forwardWeights = new HashMap<>();
        forwardWeights.put("PTS", 0.25);
        forwardWeights.put("REB", 0.25);
        forwardWeights.put("STL", 0.15);
        forwardWeights.put("AST", 0.2);
        forwardWeights.put("BLK", 0.15);
        weights.put("F", forwardWeights);

        Map<String, Double> centerWeights = new HashMap<>();
        centerWeights.put("PTS", 0.2);
        centerWeights.put("REB", 0.35);
        centerWeights.put("STL", 0.05);
        centerWeights.put("AST", 0.1);
        centerWeights.put("BLK", 0.3);
        weights.put("C", centerWeights);

        return weights;
    }
}
