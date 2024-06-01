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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class InjuryReserve extends Application {
    private List<Player> injuryReserve = new ArrayList<>();
    private List<Player> activeRoster;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        openInjuryReserve(primaryStage);
    }

    public void setActiveRoster(List<Player> activeRoster) {
        this.activeRoster = activeRoster;
    }

    public void openInjuryReserve(Stage primaryStage) {
    primaryStage.setTitle("NBA - Injury Reserve");

    Text reserveTitle = new Text("Injury Reserve List");

    // Display existing players in the injury reserve list, or show a message if the list is null
    VBox playersList = new VBox(5);
    if (injuryReserve != null) {
        for (Player player : injuryReserve) {
            Label playerNameLabel = new Label(player.getName());
            playersList.getChildren().add(playerNameLabel);
        }
    } else {
        Text noPlayersText = new Text("No players in the injury reserve list.");
        playersList.getChildren().add(noPlayersText);
    }

    Button addButton = new Button("Add Player to Injury Reserve");
    addButton.setOnAction(e -> openAddToInjuryReserveDialog(primaryStage));

    Button removeButton = new Button("Remove Player from Injury Reserve");
    removeButton.setOnAction(e -> openRemoveFromInjuryReserveDialog(primaryStage));

    VBox reserveLayout = new VBox(10);
    reserveLayout.setAlignment(Pos.CENTER);
    reserveLayout.setPadding(new Insets(20, 20, 20, 20));

    reserveLayout.getChildren().addAll(reserveTitle, playersList, addButton, removeButton);

    Scene reserveScene = new Scene(reserveLayout, 300, 200);
    primaryStage.setScene(reserveScene);

    primaryStage.show();
}


    private void openAddToInjuryReserveDialog(Stage primaryStage) {
        Stage addToInjuryStage = new Stage();
        addToInjuryStage.initOwner(primaryStage);
        addToInjuryStage.initModality(Modality.APPLICATION_MODAL);
        addToInjuryStage.setTitle("Add Player to Injury Reserve");

        TextField nameField = new TextField();
        Label nameLabel = new Label("Name:");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            Player player = removePlayerFromRoster(name);
            if (player != null) {
                injuryReserve.add(player);
                addToInjuryStage.close();
            }
        });

        VBox addToInjuryLayout = new VBox(10);
        addToInjuryLayout.setAlignment(Pos.CENTER);
        addToInjuryLayout.setPadding(new Insets(20, 20, 20, 20));

        addToInjuryLayout.getChildren().addAll(nameLabel, nameField, addButton);

        Scene addToInjuryScene = new Scene(addToInjuryLayout, 300, 150);
        addToInjuryStage.setScene(addToInjuryScene);

        addToInjuryStage.show();
    }

    private Player removePlayerFromRoster(String name) {
        for (Player player : activeRoster) {
            if (player.getName().equals(name)) {
                activeRoster.remove(player);
                return player;
            }
        }
        return null; // Player not found
    }

    private void openRemoveFromInjuryReserveDialog(Stage primaryStage) {
        Stage removeFromInjuryStage = new Stage();
        removeFromInjuryStage.initOwner(primaryStage);
        removeFromInjuryStage.initModality(Modality.APPLICATION_MODAL);
        removeFromInjuryStage.setTitle("Remove Player from Injury Reserve");

        TextField nameField = new TextField();
        Label nameLabel = new Label("Name:");

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            String name = nameField.getText();
            Player player = removePlayerFromInjuryReserve(name);
            if (player != null) {
                activeRoster.add(player);
                removeFromInjuryStage.close();
            }
        });

        VBox removeFromInjuryLayout = new VBox(10);
        removeFromInjuryLayout.setAlignment(Pos.CENTER);
        removeFromInjuryLayout.setPadding(new Insets(20, 20, 20, 20));

        removeFromInjuryLayout.getChildren().addAll(nameLabel, nameField, removeButton);

        Scene removeFromInjuryScene = new Scene(removeFromInjuryLayout, 300, 150);
        removeFromInjuryStage.setScene(removeFromInjuryScene);

        removeFromInjuryStage.show();
    }

    private Player removePlayerFromInjuryReserve(String name) {
        for (Player player : injuryReserve) {
            if (player.getName().equals(name)) {
                injuryReserve.remove(player);
                return player;
            }
        }
        return null; // Player not found
    }
}
