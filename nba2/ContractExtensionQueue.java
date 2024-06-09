/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;

// Import OpenCSV 
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

// Import JavaFX library 
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

// Import Java I/O library 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Import Java Util library 
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class ContractExtensionQueue extends Application {
    //private instances
    private Queue<String[]> contractQueue = new LinkedList<>();
    private Set<String> rosterPlayersSet = new HashSet<>();
    private final String CSV_FILE = "roster.csv";
    private TextArea contractExtensionList = new TextArea();
    
    //method to open contractExtension
    public void openContract(Stage primaryStage) {
        
        Stage contractStage = new Stage();
        contractStage.initOwner(primaryStage);
        contractStage.initModality(Modality.APPLICATION_MODAL);

         // Create text and textField
        Text contractTitle = new Text("🏀 Contract Extension Queue 🏀");
        contractTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("First Name");
        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("Last Name");
        TextField removeFirstNameInput = new TextField();
        removeFirstNameInput.setPromptText("First Name");
        TextField removeLastNameInput = new TextField();
        removeLastNameInput.setPromptText("Last Name");
        
         // Create buttons
        Button addButton = new Button("Add Player");
        addButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        addButton.setOnAction(e -> {
            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            if (!firstName.isEmpty() && !lastName.isEmpty()) {
                if (isPlayerInRoster(firstName, lastName)) {
                    addPlayer(firstName, lastName, true); // Modify to include contract status
                    firstNameInput.clear();
                    lastNameInput.clear();
                } else {
                    showAlert("Player Not Found", "The player " + firstName + " " + lastName + " is not in the roster.");
                }
            }
        });

        Button removeButton = new Button("Remove Player");
        removeButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        removeButton.setOnAction(e -> {
            String firstName = removeFirstNameInput.getText();
            String lastName = removeLastNameInput.getText();
            if (!firstName.isEmpty() && !lastName.isEmpty()) {
                removePlayer(firstName, lastName);
                removeFirstNameInput.clear();
                removeLastNameInput.clear();
            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        backButton.setOnAction(e -> contractStage.close());

        // Initialize rosterPlayersSet and contractQueue from CSV
        updateRosterPlayersSet();
        updateContractQueueFromCSV();

        contractExtensionList.setEditable(false);
        updateContractExtensionList();

        //create layout and add
        VBox contractLayout = new VBox(10, contractTitle, firstNameInput, lastNameInput, addButton,
                removeFirstNameInput, removeLastNameInput, removeButton,  contractExtensionList,backButton);
        contractLayout.setAlignment(Pos.CENTER);
        contractLayout.setPadding(new Insets(20, 20, 20, 20));

        Scene contractScene = new Scene(contractLayout, 300, 500);
        contractScene.getRoot().setStyle("-fx-background-color: #3b5998;");
        contractStage.setScene(contractScene);
        contractStage.setTitle("NBA - Contract Extension Queue");

        contractStage.show();
    }

    // Method to check if player is in Roster
    private boolean isPlayerInRoster(String firstName, String lastName) {
        String playerName = (firstName + " " + lastName).toLowerCase();
        return rosterPlayersSet.contains(playerName);
    }

    // Method to show popup with customize message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
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

    // Method to add player to contractQueue
    public void addPlayer(String firstName, String lastName, boolean printMessage) {
        String playerName = firstName + " " + lastName;
        contractQueue.add(new String[]{playerName, String.valueOf(true)}); // Modify to include contract extension status
        updateCSV(firstName, lastName, true); // Update CSV method accordingly
        updateContractExtensionList();
        if (printMessage) {
            System.out.println("-- Adding Player into Contract Extension Queue --");
            System.out.println("Player: " + playerName);
            System.out.println("Status: Added to Contract Extension Queue\n");
        }
    }

    // Method to remove player from contractQueue
    public void removePlayer(String firstName, String lastName) {
        String playerName = firstName + " " + lastName;
        boolean playerFound = false;

        for (Iterator<String[]> iterator = contractQueue.iterator(); iterator.hasNext();) {
            String[] playerInfo = iterator.next();
            if (playerInfo[0].equalsIgnoreCase(playerName)) {
                iterator.remove();
                updateCSV(firstName, lastName, false); // Update CSV method accordingly
                updateContractExtensionList();

                System.out.println("-- Removing Player from Contract Extension Queue --");
                System.out.println("Player: " + playerInfo[0]);
                System.out.println("Status: Removed from Contract Extension Queue\n");
                playerFound = true;
                break;
            }
        }

        if (!playerFound) {
            showAlert("Player Not Found", "The player " + playerName + " is not in the contract extension queue.");
        }
    }

    // Method to update the contract extension queue list
    private void updateContractExtensionList() {
        if (contractQueue.isEmpty()) {
            contractExtensionList.setText("No players in the Contract Extension Queue.");
        } else {
            StringBuilder playersList = new StringBuilder();
            for (String[] playerInfo : contractQueue) {
                playersList.append("Player: ").append(playerInfo[0]).append("\n");
            }
            contractExtensionList.setText(playersList.toString());
        }
    }

    // Method to update the rosterPlayersSet from CSV file
    private void updateRosterPlayersSet() {
        rosterPlayersSet.clear(); // Clear existing set
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE))) {
            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                if (row.length >= 2) {
                    String playerName = row[0] + " " + row[1];
                    rosterPlayersSet.add(playerName.toLowerCase()); // Add player to set
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    // Method to update contractQueue from CSV file
    private void updateContractQueueFromCSV() {
        contractQueue.clear(); // Clear existing queue
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE))) {
            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                // Check if the row has at least 14 columns before accessing row[13]
                if (row.length >= 14 && "true".equalsIgnoreCase(row[13])) {
                    String playerName = row[0] + " " + row[1];
                    contractQueue.add(new String[]{playerName, "true"}); // Add player to contractQueue
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    // Method to update CSV file based on changes in contractQueue
    private synchronized void updateCSV(String firstName, String lastName, boolean isContractExtended) {
        CSVReader reader = null;
        CSVWriter writer = null;
        try {
            reader = new CSVReader(new FileReader(CSV_FILE));
            List<String[]> allData = reader.readAll(); // Read all data at once
            for (String[] row : allData) {
                // Check if the row has at least 14 columns before accessing row[13]
                if (row.length >= 14 && row[0].equals(firstName) && row[1].equals(lastName)) {
                    row[13] = String.valueOf(isContractExtended); // Update contract extension column
                }
            }
            reader.close(); // Close reader before opening writer

            writer = new CSVWriter(new FileWriter(CSV_FILE),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                   CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                      CSVWriter.DEFAULT_LINE_END);
            writer.writeAll(allData); // Write all data at once
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException ex) {
            Logger.getLogger(ContractExtensionQueue.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }

    // Start method to open the main contract extension queue
    @Override
    public void start(Stage primaryStage) {
        openContract(primaryStage);
    }
}