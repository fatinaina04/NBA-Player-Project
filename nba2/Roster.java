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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.stage.Window;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;

// Import Java I/O library 
import java.io.*;

// Import Java new I/O library 
import java.nio.file.Files;
import java.nio.file.Paths;

// Import Java Util library
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;



public class Roster extends Application {
    
    //private instances
    private final List<Player> rosterPlayers = new ArrayList<>();//list for players in active roster
    private final String rosterFile = "roster.csv"; //csv file that save the information of players in the active roster
    private VBox rosterPlayersVBox; 
    private final int MIN_PLAYERS = 10;
    private final int MAX_PLAYERS = 15;
    private final int MIN_GUARDS = 2;
    private final int MIN_FORWARDS = 2;
    private final int MIN_CENTERS = 2;
    private final double MAX_SALARY_CAP = 20000;
    
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        openRoster(primaryStage);
    }
    
    //method to open roster
    public void openRoster(Stage primaryStage) {
        primaryStage.setTitle("NBA - Active Roster");
        
        Stage rosterStage= new Stage();
        rosterStage.initOwner(primaryStage);
        rosterStage.initModality(Modality.APPLICATION_MODAL);


        Text rosterTitle = new Text("🏀 Active Roster Players  🏀‍");
        rosterTitle.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;-fx-font-family: 'Impact';");
        rosterPlayersVBox = new VBox(5);
        
        // Check if the roster file exists
        if (Files.exists(Paths.get(rosterFile))) {
            loadRosterDataFromCSV();
            updateRosterUI(rosterPlayersVBox);
        } else {
            Text noPlayersText = new Text("No players in the active roster list");
            HBox noPlayersBox = new HBox(noPlayersText);
            noPlayersBox.setAlignment(Pos.CENTER);
            rosterPlayersVBox.getChildren().add(noPlayersBox);
        }
        
        // Create buttons
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        addButton.setOnAction(e -> openAddPlayerDialog(primaryStage, rosterPlayersVBox));

        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        removeButton.setOnAction(e -> openRemovePlayerDialog(primaryStage,rosterPlayersVBox));

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        searchButton.setOnAction(e -> openSearchPlayerDialog(primaryStage));

        Button validateButton = new Button("Validate Roster");
        validateButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        validateButton.setOnAction(e -> validateRoster());
        
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        backButton.setOnAction(e -> rosterStage.close());
        
        // Create button layout
        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(addButton, removeButton, searchButton, validateButton, backButton);
        
        VBox backButtonLayout = new VBox(10);
        backButtonLayout.setAlignment(Pos.BOTTOM_CENTER);
        backButtonLayout.getChildren().add(backButton);
        
        // Create roster layout
        VBox rosterLayout = new VBox(10);
        rosterLayout.setAlignment(Pos.CENTER);
        rosterLayout.setPadding(new Insets(20, 20, 20, 20));
        
        // Add the elements to the roster layout VBox
        rosterLayout.getChildren().addAll(rosterTitle, rosterPlayersVBox, buttonLayout, backButtonLayout);
        
        // Set the scene to the roster stage and set the title
        Scene rosterScene = new Scene(rosterLayout, 500, 500);
        rosterScene.getRoot().setStyle("-fx-background-color: #3b5998;");
        rosterStage.setScene(rosterScene);
        rosterStage.setTitle("NBA - Active Roster");
        
        rosterStage.show();
    }
    
    // Method to update the UI of the roster players VBox
    private void updateRosterUI(VBox rosterPlayersVBox) {
        
        // Clear the existing children of the rosterPlayersVBox
        rosterPlayersVBox.getChildren().clear();

        double totalSalary = 0; 

        if (!rosterPlayers.isEmpty()) {
            for (Player player : rosterPlayers) {
                totalSalary += player.getSalary(); // Accumulate the salary

                // Display player's name and check if injured
                String injured = "";
                if (player.isInjured()) {
                    injured = " (Injured)";
                }
                
                // Create labels for player's name, position, salary, and injured status
                Label playerNameLabel = new Label(player.getName());
                Label playerPositionLabel = new Label(" | Position: " + player.getPosition());
                Label playerSalaryLabel = new Label(" | Salary: $" + player.getSalary());
                Label injuredLabel = new Label(injured);
                
                // Set styles for the labels
                playerNameLabel.setStyle("-fx-font-size: 12px;-fx-font-family: 'Arial'; -fx-text-fill: white;");
                playerPositionLabel.setStyle("-fx-font-size: 12px; -fx-font-family: 'Arial';-fx-text-fill: white;");
                playerSalaryLabel.setStyle("-fx-font-size: 12px;-fx-font-family: 'Arial'; -fx-text-fill: white;");
                injuredLabel.setStyle("-fx-font-weight: bold;-fx-font-family: 'Arial'; -fx-text-fill: red;"); // injured player will have "(Injured)" in bold and red in color at the active roster UI
                
                // Create an HBox and add the player's information labels
                HBox playerBox = new HBox(playerNameLabel, playerPositionLabel, playerSalaryLabel, injuredLabel);
                playerBox.setAlignment(Pos.CENTER);
                rosterPlayersVBox.getChildren().add(playerBox);
            }

                // Add a label at the bottom to display the total salary
                Label totalSalaryLabel = new Label("\nTotal Salary: $" + totalSalary+ "💰");
                totalSalaryLabel.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial';-fx-font-weight: bold;-fx-text-fill: black;");
                HBox totalSalaryBox = new HBox(totalSalaryLabel);
                totalSalaryBox.setAlignment(Pos.CENTER);
                rosterPlayersVBox.getChildren().add(totalSalaryBox);
            
        } else { //if roster is empty display no player
            
            Text noPlayersText = new Text("No players in the active roster list");
            noPlayersText.setStyle("-fx-font-size: 16px;-fx-font-family: 'Arial'; -fx-font-weight: bold;");
            HBox noPlayersBox = new HBox(noPlayersText);
            noPlayersBox.setAlignment(Pos.CENTER);
            rosterPlayersVBox.getChildren().add(noPlayersBox);
        }
    }

    // Method to open a dialog window for adding a new player
    private void openAddPlayerDialog(Stage primaryStage, VBox rosterPlayersVBox) {
        
        // Create a new stage for the add player dialog
        Stage addPlayerStage = new Stage();
        addPlayerStage.initOwner(primaryStage);
        addPlayerStage.initModality(Modality.APPLICATION_MODAL);
        addPlayerStage.setTitle("Add Player");
        
        // Create text fields for entering the player's first name, last name, and salary
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter player's first name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter player's last name");

        TextField salaryField = new TextField();
        salaryField.setPromptText("Enter player's salary");


        // Create labels for the text fields
        Label firstNameLabel = new Label("First Name:");
        firstNameLabel.setStyle("-fx-font-weight: bold; -fx-font-family: 'Arial';-fx-text-fill: black;");

        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.setStyle("-fx-font-weight: bold;-fx-font-family: 'Arial'; -fx-text-fill: black;");

        Label salaryLabel = new Label("Salary:");
        salaryLabel.setStyle("-fx-font-weight: bold;-fx-font-family: 'Arial'; -fx-text-fill: black;");

        // Create buttons for adding a player and closing the dialog
        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #FFA500;-fx-font-weight: bold;");
        addButton.setOnAction(e -> {
            // Retrieve input from text fields and call fetchPlayerData method
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            fetchPlayerData(firstName, lastName, salary, rosterPlayersVBox, addPlayerStage);
        });
        
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        backButton.setOnAction(e -> addPlayerStage.close());
        
        // Create a layout for button and dialog
        VBox backButtonLayout = new VBox(10);
        backButtonLayout.setAlignment(Pos.BOTTOM_CENTER);
        backButtonLayout.getChildren().add(backButton);

        VBox addPlayerLayout = new VBox(10);
        addPlayerLayout.setAlignment(Pos.CENTER);
        addPlayerLayout.setPadding(new Insets(20, 20, 20, 20));

        // Add components to the add player layout
        addPlayerLayout.getChildren().addAll(
                firstNameLabel, firstNameField,
                lastNameLabel, lastNameField,
                salaryLabel, salaryField,
                addButton, backButtonLayout
        );

        // Create a scene for the add player dialog and player stage
        Scene addPlayerScene = new Scene(addPlayerLayout, 300, 300);
        addPlayerScene.getRoot().setStyle("-fx-background-color: #3b5998;");
        addPlayerStage.setScene(addPlayerScene);

        addPlayerStage.show();
    }

    // Method to fetch player data from a CSV file asynchronously
    private void fetchPlayerData(String firstName, String lastName, double salary, VBox rosterPlayersVBox, Stage addPlayerStage) {
        
        // Start a new thread to perform the data fetching and processing asynchronously
        new Thread(() -> {
            
            try {
                // Read player data from CSV file based on first name and last name
                Optional<Player> playerOptional = readPlayerFromCSV(firstName, lastName, "players.csv");

                //if player is found
                if (playerOptional.isPresent()) {
                    Player player = playerOptional.get();
                    player.setSalary(salary);

                    // Check if the player already exists in the list
                    if (isPlayerInList(player)) {
                        Platform.runLater(() -> showAlert("Player already exists in roster!"));
                        return;
                    }
                    // Check if adding the player violates salary requirements
                    if (checkSalaryRequirement(player)) {
                        return;
                    }
                    if(checkSize()){
                        return;
                    }

                    // Add the player to the roster if requirements are met
                    rosterPlayers.add(player);
                    
                    Platform.runLater(() -> {
                        addPlayerStage.close();
                        updateRosterUI(rosterPlayersVBox);
                        printPlayerDetails(player); //print player details in the console
                    });
                    
                  //if player is not found
                } else {
                    Platform.runLater(() -> showAlert("Player not found: " + firstName + " " + lastName));
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }).start();// Start the thread
    }
    
    // Method to print player details to the console
    private void printPlayerDetails(Player player) {
        
        System.out.println("Player added: ");
        System.out.println("Name: " + player.getFirstName() + " " + player.getLastName());
        System.out.println("Position: " + player.getPosition());
        System.out.println("Salary: " + player.getSalary());
        System.out.println("Height: " + player.getHeight());
        System.out.println("Weight: " + player.getWeight());
        System.out.println("Points: " + player.getPoints());
        System.out.println("Rebounds: " + player.getRebounds());
        System.out.println("Steals: " + player.getSteals());
        System.out.println("Assists: " + player.getAssists());
        System.out.println("Blocks: " + player.getBlocks());
    }

    // Method to check whether player in already in the list or not
    private boolean isPlayerInList(Player player) {
        
        String name = player.getName();
        for (Player p : rosterPlayers) {
            if (p.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    
    // Method to check for Salary Requirements
    private boolean checkSalaryRequirement(Player Player) {
        
        // Calculate the current total salary of all players
        double totalSalary = 0; 
        for (Player p : rosterPlayers) {
            totalSalary += p.getSalary();
        }
        
        double PlayerSalary = Player.getSalary();

        // Check if adding the new player's salary exceeds the total salary cap
        if (totalSalary + PlayerSalary > MAX_SALARY_CAP) {
            Platform.runLater(() -> showAlert("Unable to add player! Max Salary Cap"));
            return true;
        }
        
        // Validate salary based on superstar status
        if(Player.getPoints() >= 20.0){
            if(PlayerSalary<3000){ //superstar
                Platform.runLater(() -> showAlert("Unable to add player! Salary requirement for superstar player was not met"));
                return true;
            }
        }else if(PlayerSalary<1000 || PlayerSalary>=3000){ //non-superstar
             Platform.runLater(() -> showAlert("Unable to add player! Salary requirement for non-superstar player was not met"));
             return true;
        }
        
        return false;
    }
    
    private boolean checkSize(){
        
        if (rosterPlayers.size()+1 > MAX_PLAYERS) {
            Platform.runLater(() ->showAlert("Maximum number of players reached!"));
            return true;
        }
        return false;
    }

    // Method to validate the positional requirements and size of the roster
    private void validateRoster() {
         int guardsCount = 0, forwardsCount = 0, centersCount = 0;
         
        //check if roster less that 15 player(max)  and more than 10 player(min)
        if (rosterPlayers.size() < MIN_PLAYERS) {
            showAlert("Not enough players! Please add more players.");
            return;
        }
        
        for (Player p : rosterPlayers) {
            if (p.getPosition().equalsIgnoreCase("C")) {
                centersCount++;
            }else if(p.getPosition().equalsIgnoreCase("G")){
                guardsCount++;
            }else if(p.getPosition().equalsIgnoreCase("F")){
                forwardsCount++;
            }
        }

        //check if the roster contains at least 2 player for each position
        if(guardsCount>= MIN_GUARDS && forwardsCount >= MIN_FORWARDS && centersCount >= MIN_CENTERS){
            saveRosterToCSV(rosterPlayers, rosterFile);
            showAlert("Roster validated and saved successfully!");
        }else{
            showAlert("Roster Invalid! Please check positional requirements again :(");
        }
    }

    // Method to open a dialog for removing a player
    private void openRemovePlayerDialog(Stage primaryStage,VBox rosterPlayersVBox) {
        
        Stage removePlayerStage = new Stage();
        removePlayerStage.initOwner(primaryStage);
        removePlayerStage.initModality(Modality.APPLICATION_MODAL);
        removePlayerStage.setTitle("Remove Player");

        // Text fields for entering player's first and last name
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter player's first name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter player's last name");

        // Labels for text fields
        Label firstNameLabel = new Label("First Name:");
        firstNameLabel.setStyle("-fx-font-weight: bold;-fx-font-family: 'Arial'; -fx-text-fill: black;");
        Label lastNameLabel = new Label("Last Name:");
        lastNameLabel.setStyle("-fx-font-weight: bold;-fx-font-family: 'Arial'; -fx-text-fill: black;");
        
        // Button to remove player
        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        removeButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            removePlayer(firstName, lastName, rosterPlayersVBox);
            removePlayerStage.close();
        });
        
        // Button to back 
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        backButton.setOnAction(e -> removePlayerStage.close());
        
        // Layout 
        VBox backButtonLayout = new VBox(10);
        backButtonLayout.setAlignment(Pos.BOTTOM_CENTER);
        backButtonLayout.getChildren().add(backButton);

        VBox removePlayerLayout = new VBox(10);
        removePlayerLayout.setAlignment(Pos.CENTER);
        removePlayerLayout.setPadding(new Insets(20, 20, 20, 20));

        removePlayerLayout.getChildren().addAll(firstNameLabel, firstNameField, lastNameLabel, lastNameField, removeButton,backButtonLayout);

        Scene removePlayerScene = new Scene(removePlayerLayout, 300, 200);
        removePlayerScene.getRoot().setStyle("-fx-background-color: #3b5998;");
        removePlayerStage.setScene(removePlayerScene);

        removePlayerStage.show();
    }

    // Method to remove a player from roster
    private void removePlayer(String firstName, String lastName, VBox rosterVBox) {
        for (Player player : rosterPlayers) {
            if (player.getFirstName().equalsIgnoreCase(firstName) && player.getLastName().equalsIgnoreCase(lastName)) {
                rosterPlayers.remove(player);//remove player from list
                updateRosterUI(rosterVBox);// update the UI roster after remove
                return;
            }
        }
        showAlert("Player does not exist!");
    }

    // Method to open a dialog for dynamically searching a player
    private void openSearchPlayerDialog(Stage primaryStage) {
        
        Stage searchStage = new Stage(); // Create a new stage for the search dialog
        searchStage.initOwner(primaryStage);
        searchStage.initModality(Modality.APPLICATION_MODAL);
        searchStage.setTitle("Search Players");

        // Create input fields
        ComboBox<String> heightOperatorField = new ComboBox<>();
        heightOperatorField.getItems().addAll("=", "<=", ">=");
        heightOperatorField.setValue("=");

        TextField heightField = new TextField();
        heightField.setPromptText("Height in meters (e.g., 2.0)");

        ComboBox<String> weightOperatorField = new ComboBox<>();
        weightOperatorField.getItems().addAll("=", "<=", ">=");
        weightOperatorField.setValue("=");

        TextField weightField = new TextField();
        weightField.setPromptText("Weight in lbs (e.g., 150)");

        TextField positionField = new TextField();
        positionField.setPromptText("Position");

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
    
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        backButton.setOnAction(e -> searchStage.close());
        
        VBox backButtonLayout = new VBox(10);
        backButtonLayout.setAlignment(Pos.BOTTOM_CENTER);
        backButtonLayout.getChildren().add(backButton);

        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Label heightOperatorLabel = new Label("Height Operator:");
        Label heightLabel = new Label("Height:");
        Label weightOperatorLabel = new Label("Weight Operator:");
        Label weightLabel = new Label("Weight:");
        Label positionLabel = new Label("Position:");

        // Set styles for labels
        Stream.of(heightOperatorLabel, heightLabel, weightOperatorLabel, weightLabel, positionLabel)
              .forEach(label -> {
                  label.setStyle("-fx-font-family: 'Arial';-fx-font-weight: bold; -fx-text-fill: black;");
              });

        grid.add(heightOperatorLabel, 0, 0);
        grid.add(heightOperatorField, 1, 0);
        grid.add(heightLabel, 0, 1);
        grid.add(heightField, 1, 1);
        grid.add(weightOperatorLabel, 0, 2);
        grid.add(weightOperatorField, 1, 2);
        grid.add(weightLabel, 0, 3);
        grid.add(weightField, 1, 3);
        grid.add(positionLabel, 0, 4);
        grid.add(positionField, 1, 4);
        grid.add(searchButton, 1, 5);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(grid,backButtonLayout);

        // Event handler for search button
        searchButton.setOnAction(e -> {
            String heightOperator = heightOperatorField.getValue();
            double height = Double.parseDouble(heightField.getText());
            String weightOperator = weightOperatorField.getValue();
            double weight = Double.parseDouble(weightField.getText());
            String position = positionField.getText();

            List<Player> result = searchPlayers(rosterPlayers, heightOperator, height, weightOperator, weight, position);

            if (result.isEmpty()) {// if no player found
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Results");
                alert.setHeaderText(null);
                alert.setContentText("No players found.");
              
                // Access the dialog pane and set the custom background color
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle("-fx-background-color: #3b5998;");

                // Set the text color to white
                Label contentLabel = (Label) dialogPane.lookup(".content.label");
                contentLabel.setStyle("-fx-font-size: 18px;-fx-font-family: 'Arial';-fx-text-fill: white;-fx-font-weight: bold;");
                alert.showAndWait();
                
            } else {// player met the requirements found
            
            // Create a new stage for the popup window
                Stage popupStage = new Stage();
                popupStage.setTitle("Search Results");

                // Create a new TableView for the popup window
                TableView<Player> popupTable = new TableView<>();
                
                // Set up columns for the popup table
                TableColumn<Player, String> popupNameColumn = new TableColumn<>("Name");
                popupNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

                TableColumn<Player, Double> popupHeightColumn = new TableColumn<>("Height");
                popupHeightColumn.setCellValueFactory(cellData -> cellData.getValue().heightProperty().asObject());

                TableColumn<Player, Double> popupWeightColumn = new TableColumn<>("Weight");
                popupWeightColumn.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());

                TableColumn<Player, String> popupPositionColumn = new TableColumn<>("Position");
                popupPositionColumn.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
                
                TableColumn<Player, Double> popupPointsColumn = new TableColumn<>("Points");
                popupPointsColumn.setCellValueFactory(cellData -> cellData.getValue().ptsProperty().asObject());
                
                TableColumn<Player, Double> popuprebColumn = new TableColumn<>("Rebounds");
                popuprebColumn.setCellValueFactory(cellData -> cellData.getValue().rebProperty().asObject());
                
                TableColumn<Player, Double> popupStealsColumn = new TableColumn<>("Steals");
                popupStealsColumn.setCellValueFactory(cellData -> cellData.getValue().stlProperty().asObject());
                
                TableColumn<Player, Double> popupSalaryColumn = new TableColumn<>("Salary");
                popupSalaryColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());
                
                TableColumn<Player, Double> popupblkColumn = new TableColumn<>("Blocks");
                popupblkColumn.setCellValueFactory(cellData -> cellData.getValue().blkProperty().asObject());
                
                TableColumn<Player, Double> popupAstColumn = new TableColumn<>("Assists");
                popupAstColumn.setCellValueFactory(cellData -> cellData.getValue().astProperty().asObject());
                


                popupTable.getColumns().addAll(popupNameColumn, popupHeightColumn, popupWeightColumn, popupPositionColumn,popupSalaryColumn,popupPointsColumn,popuprebColumn,popupAstColumn,popupStealsColumn,popupblkColumn);


                // Set the items in the popup table
                popupTable.getItems().setAll(result);

                // Create a VBox to hold the popup table
                VBox popupLayout = new VBox();
                popupLayout.getChildren().addAll(popupTable);

                // Set the scene of the popup stage
                Scene popupScene = new Scene(popupLayout, 650, 200);
                popupScene.getRoot().setStyle("-fx-background-color: #3b5998;");

                popupStage.setScene(popupScene);

                // Show the popup stage
                popupStage.show();
            }
        });

        Scene scene = new Scene(vbox, 260, 255);
        scene.getRoot().setStyle("-fx-background-color: #3b5998;");
        searchStage.setScene(scene);
        searchStage.showAndWait(); // Show the search dialog and wait for it to be closed
    }

    //method to search the player by comparing the attributes  
    private List<Player> searchPlayers(List<Player> players, String heightOperator, double height,String weightOperator, double weight, String position) {
        List<Player> result = new ArrayList<>();

        //iterates for each player in the roster
        for (Player player : players) {
            boolean matches = true;

            // Height comparison
            if (heightOperator.equals(">=") && !(player.getHeight() >= height)) matches = false;
            else if (heightOperator.equals("<=") && !(player.getHeight() <= height)) matches = false;
            else if (heightOperator.equals("=") && !(player.getHeight() == height)) matches = false;

            // Weight comparison
            if (weightOperator.equals(">=") && !(player.getWeight() >= weight)) matches = false;
            else if (weightOperator.equals("<=") && !(player.getWeight() <= weight)) matches = false;
            else if (weightOperator.equals("=") && !(player.getWeight() == weight)) matches = false;

            // Position comparison
            if (!player.getPosition().equalsIgnoreCase(position)) matches = false;

            if (matches) {
                result.add(player);
            }
        }

        return result;
    }


    // Method to show popup with customize message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("🚨");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Access the dialog pane and set the custom background color
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #3b5998;");

        // Set the text color to white
        Label contentLabel = (Label) dialogPane.lookup(".content.label");
        contentLabel.setStyle("-fx-font-size: 18px;-fx-font-family: 'Arial';-fx-text-fill: white;-fx-font-weight: bold;");

        alert.showAndWait();
    }

    // Method to load roster data from a CSV file
    private void loadRosterDataFromCSV() {
        String line;
        String cvsSplitBy = ",";
        
        // Clear the existing players in the roster
        rosterPlayers.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(rosterFile))) {
            // Skip the header
            br.readLine();

            // Extract player data from CSV fields
            while ((line = br.readLine()) != null) {
                String[] playerData = line.split(cvsSplitBy);

                String firstName = playerData[0];
                String lastName = playerData[1];
                String position = playerData[2];
                double points = Double.parseDouble(playerData[3]);
                double rebounds = Double.parseDouble(playerData[4]);
                double steals = Double.parseDouble(playerData[5]);
                double assists = Double.parseDouble(playerData[6]);
                double blocks = Double.parseDouble(playerData[7]);
                double salary = Double.parseDouble(playerData[8]);
                double height = Double.parseDouble(playerData[9]);
                int weight = Integer.parseInt(playerData[10]);
                boolean injured = Boolean.parseBoolean(playerData[11]);
                String injuryDetail = playerData[12];
                boolean extend = Boolean.parseBoolean(playerData[13]);

                // Create a Player object and add it to the roster
                Player player = new Player(firstName, lastName, position, points, rebounds, steals, assists, blocks, salary, height, weight,injured, injuryDetail, extend);
                rosterPlayers.add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to read player data from a CSV file based on first and last name
    private Optional<Player> readPlayerFromCSV(String firstName, String lastName, String csvFile) {
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] playerData = line.split(cvsSplitBy);
                
                // Check if first and last name match the provided name
                String first = playerData[1];
                String last = playerData[2];
                if (first.equalsIgnoreCase(firstName) && last.equalsIgnoreCase(lastName)) {
                    // Extract player data from CSV fields
                    String position = playerData[3];
                    double points = Double.parseDouble(playerData[6]);
                    double assists = Double.parseDouble(playerData[7]);
                    double rebounds = Double.parseDouble(playerData[8]);
                    double steals = Double.parseDouble(playerData[9]);
                    double blocks = Double.parseDouble(playerData[10]);
                    double height = Double.parseDouble(playerData[4]);
                    int weight = Integer.parseInt(playerData[5]);

                    // Create and return a Player object
                    return Optional.of(new Player(firstName, lastName, position, points, rebounds, steals, assists, blocks, 0, height, weight,false, null,false));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    

    // Method to save roster data to a CSV file
    public static void saveRosterToCSV(List<Player> roster, String fileName) {
        String csvHeader = "FirstName,LastName,Position,Points,Rebounds,Steals,Assists,Blocks,Salary,Height,Weight,Injured,InjuryDetails,ContractExtension";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.append(csvHeader);
            fileWriter.append("\n");

            // Write player data to CSV format
            for (Player player : roster) {
                fileWriter.append(player.getFirstName()).append(",");
                fileWriter.append(player.getLastName()).append(",");
                fileWriter.append(player.getPosition()).append(",");
                fileWriter.append(String.valueOf(player.getPoints())).append(",");
                fileWriter.append(String.valueOf(player.getRebounds())).append(",");
                fileWriter.append(String.valueOf(player.getSteals())).append(",");
                fileWriter.append(String.valueOf(player.getAssists())).append(",");
                fileWriter.append(String.valueOf(player.getBlocks())).append(",");
                fileWriter.append(String.valueOf(player.getSalary())).append(",");
                fileWriter.append(String.valueOf(player.getHeight())).append(",");
                fileWriter.append(String.valueOf(player.getWeight())).append(",");
                fileWriter.append("false").append(","); // no injury initially
                fileWriter.append(" ").append(",");// nno injury details initially
                fileWriter.append("false").append("\n"); // no contract extension initially
            }

            System.out.println("Roster saved successfully to: " + fileName);
        } catch (IOException e) {
            System.out.println("Error occurred while saving roster to CSV: " + e.getMessage());
        }
    }
}