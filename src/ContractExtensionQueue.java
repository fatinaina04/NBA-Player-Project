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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Queue;

public class ContractExtensionQueue extends Application {
    private Queue<String> contractQueue; // Using a simple Queue of player names for demonstration
    private Label statusLabel;
    private TextField playerNameInput;
    private TextField performanceScoreInput;

    @Override
    public void init() {
        contractQueue = new LinkedList<>();
    }

    public void openContract(Stage primaryStage) {
        primaryStage.setTitle("NBA - Contract Extension Queue");

        // Create UI components
        Label contractextensionTitle = new Label("Contract Extension Queue");

        playerNameInput = new TextField();
        playerNameInput.setPromptText("Enter Player Name");

        performanceScoreInput = new TextField();
        performanceScoreInput.setPromptText("Enter Performance Score");

        Button addButton = new Button("Add Player");
        addButton.setOnAction(e -> addToQueue(playerNameInput.getText().trim()));

        Button removeButton = new Button("Remove Player");
        removeButton.setOnAction(e -> removeFromQueue());

        statusLabel = new Label();

        // Layout for input fields
        VBox inputLayout = new VBox(10);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));
        inputLayout.getChildren().addAll(playerNameInput, performanceScoreInput, addButton, removeButton, statusLabel);

        // Layout for entire scene
        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(contractextensionTitle, inputLayout);

        // Set up the scene
        Scene scene = new Scene(mainLayout, 400, 300);
        scene.getRoot().setStyle("-fx-background-color: #FFF3B0;");
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    private void addToQueue(String playerName) {
        if (!playerName.isEmpty()) {
            contractQueue.add(playerName);
            updateStatusLabel(playerName + " added to contract extension queue.");
        } else {
            updateStatusLabel("Please enter a valid player name.");
        }
        playerNameInput.clear();
    }

    private void removeFromQueue() {
        String player = contractQueue.poll();
        if (player != null) {
            updateStatusLabel(player + " removed from contract extension queue.");
        } else {
            updateStatusLabel("Contract extension queue is empty.");
        }
    }

    private void updateStatusLabel(String message) {
        statusLabel.setText(message);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}