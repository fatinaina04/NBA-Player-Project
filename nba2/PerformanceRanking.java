
package nba2;

// Import JavaFX library 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;

// Import Java Util library 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformanceRanking extends Application {
    private List<Player> players;
    private Map<String, Map<String, Double>> weights;

    public PerformanceRanking() {
        this.players = new ArrayList<>();
        initializeWeights();
    }

    @Override
    public void start(Stage primaryStage) {
        showPerformanceRanking(primaryStage);
    }

    public void showPerformanceRanking(Stage primaryStage) {
        Stage performanceStage = new Stage();
        
        performanceStage.initOwner(primaryStage);
        performanceStage.initModality(Modality.APPLICATION_MODAL);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Load player data, calculate scores, and get ranked players
        String filePath = "roster.csv"; // Replace with the actual path to your CSV file
        loadPlayersFromCSV(filePath);
        calculateCompositeScores();
        List<Player> rankedPlayers = getRankedPlayers();

        // Create headers
        Label header = new Label("Performance Ranking 🏆");
        header.setStyle("-fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 24px; -fx-text-fill: black;");
        header.setAlignment(Pos.CENTER);

        Label rankHeader = new Label("Rank");
        rankHeader.setStyle("-fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white;");
        rankHeader.setAlignment(Pos.CENTER);

        Label nameHeader = new Label("Player Name");
        nameHeader.setStyle("-fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white;");
        nameHeader.setAlignment(Pos.CENTER);

        Label scoreHeader = new Label("Composite Score");
        scoreHeader.setStyle("-fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: white;");
        scoreHeader.setAlignment(Pos.CENTER);

        gridPane.add(header, 0, 0, 3, 1);
        GridPane.setColumnSpan(header, 3); // Span the header across three columns
        gridPane.add(rankHeader, 0, 1);
        gridPane.add(nameHeader, 1, 1);
        gridPane.add(scoreHeader, 2, 1);

        // Add ranked players to the grid
        for (int i = 0; i < rankedPlayers.size(); i++) {
            Player player = rankedPlayers.get(i);

            Label rankLabel = new Label(String.valueOf(i + 1));
            rankLabel.setStyle("-fx-font-family: Arial; -fx-font-weight: bold; -fx-text-fill: White;");
            rankLabel.setAlignment(Pos.CENTER);

            Button nameButton = new Button(player.getFirstName() + " " + player.getLastName());
            nameButton.setOnAction(e -> showPlayerProfile(player,performanceStage));
            nameButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");

            Label scoreLabel = new Label(String.format("            %.2f", player.getCompositeScore()));
            scoreLabel.setStyle("-fx-font-family: Arial; -fx-font-weight: bold;  -fx-text-fill: White;");
            scoreLabel.setAlignment(Pos.CENTER);

            gridPane.add(rankLabel, 0, i + 2);
            gridPane.add(nameButton, 1, i + 2);
            gridPane.add(scoreLabel, 2, i + 2);
        }

        // Create and add the Back button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        backButton.setOnAction(e -> performanceStage.close());

        gridPane.add(backButton, 1, rankedPlayers.size() + 2);
        GridPane.setColumnSpan(backButton, 2);
        GridPane.setHalignment(backButton, javafx.geometry.HPos.CENTER);
        

        // Create and set the scene
        Scene scene = new Scene(gridPane, 400, 500);
        scene.getRoot().setStyle("-fx-background-color: #3b5998;");
        performanceStage.setScene(scene);
        performanceStage.setTitle("NBA - Performance Ranking");
        
        performanceStage.show();
    }

    private void showPlayerProfile(Player player,Stage performanceStage) {
        Stage profileStage = new Stage();
        profileStage.setTitle(player.getFirstName() + " " + player.getLastName() + " Profile");
        
        Button nameTitle = new Button(player.getFirstName() + " " + player.getLastName() + " Profile");
        nameTitle.setStyle("-fx-background-color: #FFA500;-fx-font-weight: bold;-fx-font-family: 'Arial';-fx-font-size: 20px");

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);

        // Create labels with white text color
        Label nameLabel = new Label("Name: " + player.getFirstName() + " " + player.getLastName());
        nameLabel.setStyle("-fx-text-fill: white;-fx-font-family: Arial; -fx-font-weight: bold;");
        Label positionLabel = new Label("Position: " + player.getPosition());
        positionLabel.setStyle("-fx-text-fill: white;-fx-font-family: Arial; -fx-font-weight: bold;");
        Label pointsLabel = new Label("Points: " + player.getPoints());
        pointsLabel.setStyle("-fx-text-fill: white;-fx-font-family: Arial; -fx-font-weight: bold;");
        Label reboundsLabel = new Label("Rebounds: " + player.getRebounds());
        reboundsLabel.setStyle("-fx-text-fill: white;-fx-font-family: Arial; -fx-font-weight: bold;");
        Label stealsLabel = new Label("Steals: " + player.getSteals());
        stealsLabel.setStyle("-fx-text-fill: white;-fx-font-family: Arial; -fx-font-weight: bold;");
        Label assistsLabel = new Label("Assists: " + player.getAssists());
        assistsLabel.setStyle("-fx-text-fill: white;-fx-font-family: Arial; -fx-font-weight: bold;");
        Label blocksLabel = new Label("Blocks: " + player.getBlocks());
        blocksLabel.setStyle("-fx-text-fill: white;-fx-font-family: Arial; -fx-font-weight: bold;");
        Label salaryLabel = new Label("Salary: " + player.getSalary());
        salaryLabel.setStyle("-fx-text-fill: white;-fx-font-family: Arial; -fx-font-weight: bold;");
        Label heightLabel = new Label("Height: " + player.getHeight());
        heightLabel.setStyle("-fx-text-fill: white;-fx-font-family: Arial; -fx-font-weight: bold;");
        Label weightLabel = new Label("Weight: " + player.getWeight());
        weightLabel.setStyle("-fx-text-fill: white;-fx-font-family: Arial; -fx-font-weight: bold;");

        // Add labels to the VBox
        vbox.getChildren().addAll(
            nameTitle, nameLabel, positionLabel, pointsLabel, reboundsLabel, stealsLabel,
            assistsLabel, blocksLabel, salaryLabel, heightLabel, weightLabel
        );
        // Create back button and position it lower
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        backButton.setOnAction(e -> {
            profileStage.close();
            performanceStage.show();
        });
        
        vbox.getChildren().add(backButton);
        VBox.setMargin(backButton, new Insets(20, 0, 0, 0)); // Add margin to the back button

        Scene scene = new Scene(vbox, 400, 400);
        scene.getRoot().setStyle("-fx-background-color: #3b5998;");
        profileStage.setScene(scene);
        profileStage.show();
    }

    private void initializeWeights() {
        this.weights = new HashMap<>();

        // Define weights for Guard position
        Map<String, Double> guardWeights = new HashMap<>();
        guardWeights.put("PTS", 0.2);
        guardWeights.put("REB", 0.1);
        guardWeights.put("STL", 0.3);
        guardWeights.put("AST", 0.3);
        guardWeights.put("BLK", 0.1);
        weights.put("G", guardWeights); // Using "G" for Guard

        // Define weights for Center position
        Map<String, Double> centerWeights = new HashMap<>();
        centerWeights.put("PTS", 0.2);
        centerWeights.put("REB", 0.3);
        centerWeights.put("STL", 0.1);
        centerWeights.put("AST", 0.1);
        centerWeights.put("BLK", 0.3);
        weights.put("C", centerWeights); // Using "C" for Center

        // Define weights for Forward position
        Map<String, Double> forwardWeights = new HashMap<>();
        forwardWeights.put("PTS", 0.2);
        forwardWeights.put("REB", 0.2);
        forwardWeights.put("STL", 0.3);
        forwardWeights.put("AST", 0.1);
        forwardWeights.put("BLK", 0.2);
        weights.put("F", forwardWeights); // Using "F" for Forward
    }
    
    public void addPlayer(Player player) {
        players.add(player);
    }

    public void loadPlayersFromCSV(String filePath) {
        players.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Player player = new Player(
                        values[0], // FirstName
                        values[1], // LastName
                        values[2], // Position
                        Double.parseDouble(values[3]), // Points
                        Double.parseDouble(values[4]), // Rebounds
                        Double.parseDouble(values[5]), // Steals
                        Double.parseDouble(values[6]), // Assists
                        Double.parseDouble(values[7]), // Blocks
                        Double.parseDouble(values[8]), // Salary
                        Double.parseDouble(values[9]), // Height
                        Integer.parseInt(values[10]), // Weight
                        Boolean.parseBoolean(values[11]), // Injured
                        values[12], // InjuryDetails
                        Boolean.parseBoolean(values[13]) //Contract Extension
                );
                addPlayer(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void calculateCompositeScores() {
        for (Player player : players) {
            player.calculateCompositeScore(weights);
        }
    }

    public List<Player> getRankedPlayers() {
        // Sort players in descending order of their composite scores
        Collections.sort(players, (p1, p2) -> Double.compare(p2.getCompositeScore(), p1.getCompositeScore()));
        return players;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
