/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;

import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.util.ArrayList;
import java.util.List;

public class Roster extends Application {
    private List<Player> activeRoster = new ArrayList<>();
    private Team team = new Team();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        openRoster(primaryStage);
    }

    public void openRoster(Stage primaryStage) {
        primaryStage.setTitle("NBA - Active Roster");

        Text rosterTitle = new Text("Active Roster Players");

        Button addButton = new Button("Add Player");
        addButton.setOnAction(e -> openAddPlayerDialog(primaryStage));

        Button removeButton = new Button("Remove Player");
        removeButton.setOnAction(e -> openRemovePlayerDialog(primaryStage));

        Button searchButton = new Button("Search Player");
        searchButton.setOnAction(e -> openSearchPlayerDialog(primaryStage));

        VBox rosterLayout = new VBox(10);
        rosterLayout.setAlignment(Pos.CENTER);
        rosterLayout.setPadding(new Insets(20, 20, 20, 20));

        rosterLayout.getChildren().addAll(rosterTitle, addButton, removeButton, searchButton);

        Scene rosterScene = new Scene(rosterLayout, 300, 200);
        primaryStage.setScene(rosterScene);

        primaryStage.show();
    }

    private void openAddPlayerDialog(Stage primaryStage) {
        Stage addPlayerStage = new Stage();
        addPlayerStage.initOwner(primaryStage);
        addPlayerStage.initModality(Modality.APPLICATION_MODAL);
        addPlayerStage.setTitle("Add Player");

        // Create UI components for adding a player
        TextField nameField = new TextField();
        TextField positionField = new TextField();
        TextField ptsField = new TextField();
        TextField rebField = new TextField();
        TextField stlField = new TextField();
        TextField astField = new TextField();
        TextField blkField = new TextField();
        TextField salaryField = new TextField();
        TextField heightField = new TextField();
        TextField weightField = new TextField();

        Label nameLabel = new Label("Name:");
        Label positionLabel = new Label("Position:");
        Label ptsLabel = new Label("Points:");
        Label rebLabel = new Label("Rebounds:");
        Label stlLabel = new Label("Steals:");
        Label astLabel = new Label("Assists:");
        Label blkLabel = new Label("Blocks:");
        Label salaryLabel = new Label("Salary:");
        Label heightLabel = new Label("Height:");
        Label weightLabel = new Label("Weight:");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String position = positionField.getText();
            double pts = Double.parseDouble(ptsField.getText());
            double reb = Double.parseDouble(rebField.getText());
            double stl = Double.parseDouble(stlField.getText());
            double ast = Double.parseDouble(astField.getText());
            double blk = Double.parseDouble(blkField.getText());
            double salary = Double.parseDouble(salaryField.getText());
            int height = Integer.parseInt(heightField.getText());
            int weight = Integer.parseInt(weightField.getText());
            Player player = new Player(name, position, pts, reb, stl, ast, blk, salary, height, weight);
            activeRoster.add(player);
            addPlayerStage.close();
        });

        VBox addPlayerLayout = new VBox(10);
        addPlayerLayout.setAlignment(Pos.CENTER);
        addPlayerLayout.setPadding(new Insets(20, 20, 20, 20));

        addPlayerLayout.getChildren().addAll(
                nameLabel, nameField,
                positionLabel, positionField,
                ptsLabel, ptsField,
                rebLabel, rebField,
                stlLabel, stlField,
                astLabel, astField,
                blkLabel, blkField,
                salaryLabel, salaryField,
                heightLabel, heightField,
                weightLabel, weightField,
                addButton
        );

        Scene addPlayerScene = new Scene(addPlayerLayout, 400, 900);
        addPlayerStage.setScene(addPlayerScene);

        addPlayerStage.show();
    }


    private void openRemovePlayerDialog(Stage primaryStage) {
        Stage removePlayerStage = new Stage();
        removePlayerStage.initOwner(primaryStage);
        removePlayerStage.initModality(Modality.APPLICATION_MODAL);
        removePlayerStage.setTitle("Remove Player");

        // Create UI components for removing a player
        TextField nameField = new TextField();
        Label nameLabel = new Label("Name:");
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            String name = nameField.getText();
            team.removePlayer(name);
            removePlayerStage.close();
        });

        VBox removePlayerLayout = new VBox(10);
        removePlayerLayout.setAlignment(Pos.CENTER);
        removePlayerLayout.setPadding(new Insets(20, 20, 20, 20));

        removePlayerLayout.getChildren().addAll(nameLabel, nameField, removeButton);

        Scene removePlayerScene = new Scene(removePlayerLayout, 300, 150);
        removePlayerStage.setScene(removePlayerScene);

        removePlayerStage.show();
    }

    private void openSearchPlayerDialog(Stage primaryStage) {
        Stage searchPlayerStage = new Stage();
        searchPlayerStage.initOwner(primaryStage);
        searchPlayerStage.initModality(Modality.APPLICATION_MODAL);
        searchPlayerStage.setTitle("Search Player");

        // Create UI components for searching a player
        TextField heightField = new TextField();
        TextField weightField = new TextField();
        TextField positionField = new TextField();
        TextField minDefenseStatField = new TextField();

        Label heightLabel = new Label("Height:");
        Label weightLabel = new Label("Weight:");
        Label positionLabel = new Label("Position:");
        Label minDefenseStatLabel = new Label("Min Defense Stat:");

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            int height = Integer.parseInt(heightField.getText());
            int weight = Integer.parseInt(weightField.getText());
            String position = positionField.getText();
            double minDefenseStat = Double.parseDouble(minDefenseStatField.getText());
            team.dynamicSearch(height, weight, position, minDefenseStat);
            searchPlayerStage.close();
        });

        VBox searchPlayerLayout = new VBox(10);
        searchPlayerLayout.setAlignment(Pos.CENTER);
        searchPlayerLayout.setPadding(new Insets(20, 20, 20, 20));

        searchPlayerLayout.getChildren().addAll(heightLabel, heightField, weightLabel, weightField,
                positionLabel, positionField, minDefenseStatLabel, minDefenseStatField, searchButton);

        Scene searchPlayerScene = new Scene(searchPlayerLayout, 400, 250);
        searchPlayerStage.setScene(searchPlayerScene);
        searchPlayerScene.getRoot().setStyle("-fx-background-color: #FFF3B0;");

        searchPlayerStage.show();
    }
}
