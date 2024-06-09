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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

// Import Java I/O library 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Import Java Util library 
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;


public class InjuryReserve {
    //private instances
    private Stack<String[]> injuryReserveStack = new Stack<>();
    private TextArea injuredPlayersList = new TextArea();
    private final String CSV_FILE = "roster.csv";
    private Set<String> rosterPlayersSet = new HashSet<>();

    //method to open injury list
    public void openInjury(Stage primaryStage) {
        
        Stage injuryStage = new Stage();
        injuryStage.initOwner(primaryStage);
        injuryStage.initModality(Modality.APPLICATION_MODAL);

        // Create text and text fields
        Text injuryTitle = new Text("🚨 Injury Reserve List 🚨");
        injuryTitle.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;-fx-font-family: 'Impact';");

        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("Enter player's first name");

        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("Enter player's last name");

        TextField injuryDetailInput = new TextField();
        injuryDetailInput.setPromptText("Enter injury details");
        
        TextField removeFirstNameInput = new TextField();
        removeFirstNameInput.setPromptText("Enter player's first name to remove");

        TextField removeLastNameInput = new TextField();
        removeLastNameInput.setPromptText("Enter player's last name to remove");
        
        // Create buttons
        Button addButton = new Button("Add Player");
        addButton.setStyle("-fx-background-color: #FFA500;-fx-font-family: 'Arial';-fx-font-weight: bold;");
        addButton.setOnAction(e -> {
            String firstName = firstNameInput.getText();
            String lastName = lastNameInput.getText();
            String injuryDetail = injuryDetailInput.getText();
            if (!firstName.isEmpty() && !lastName.isEmpty() && !injuryDetail.isEmpty()) {
                if (isPlayerInRoster(firstName, lastName)) {
                    addPlayer(firstName, lastName, injuryDetail, true);
                    firstNameInput.clear();
                    lastNameInput.clear();
                    injuryDetailInput.clear();
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
        backButton.setOnAction(e -> injuryStage.close());

        // Clear the injuryReserveStack before loading data
        injuryReserveStack.clear();
        rosterPlayersSet.clear();

        try {
            // Read data from the CSV file and populate injuryReserveStack and rosterPlayersSet
            CSVReader reader = new CSVReader(new FileReader(CSV_FILE));
            List<String[]> allData = reader.readAll();
            for (String[] row : allData) {
                if (row.length >= 13) {
                    String playerName = row[0] + " " + row[1];
                    rosterPlayersSet.add(playerName.toLowerCase());
                    if (row[11].equalsIgnoreCase("true")) {
                        String injuryDetail = row[12];
                        addPlayer(row[0], row[1], injuryDetail, false);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException ex) {
            Logger.getLogger(InjuryReserve.class.getName()).log(Level.SEVERE, null, ex);
        }

        injuredPlayersList.setEditable(false); // to ensure the text field is not editable
        updateInjuredPlayersList();

        VBox injuryLayout = new VBox(10, injuryTitle, firstNameInput, lastNameInput, injuryDetailInput, addButton, removeFirstNameInput, removeLastNameInput, removeButton, injuredPlayersList, backButton);
        injuryLayout.setAlignment(Pos.CENTER);
        injuryLayout.setPadding(new Insets(20, 20, 20, 20));
        
        Scene injuryScene = new Scene(injuryLayout, 300, 500);
        injuryScene.getRoot().setStyle("-fx-background-color: #3b5998;");
        injuryStage.setScene(injuryScene);
        injuryStage.setTitle("NBA - Injury Reserve");

        injuryStage.show();
    }

    // Method to check if player is already added
    private boolean isPlayerInRoster(String firstName, String lastName) {
        String playerName = (firstName + " " + lastName).toLowerCase();
        return rosterPlayersSet.contains(playerName);
    }

    //Method to show popup with customize message
    private void showAlert(String title, String message) {
       Alert alert = new Alert(Alert.AlertType.ERROR);
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

    // Method to add player for injury
    public void addPlayer(String firstName, String lastName, String injuryDetail, boolean printMessage) {
        String playerName = firstName + " " + lastName;
        injuryReserveStack.push(new String[]{playerName, injuryDetail});// add to stack
        updateCSV(firstName, lastName, true, injuryDetail);//update to csv
        updateInjuredPlayersList();
        if (printMessage) {
            System.out.println("-- Adding Player into Injury Reserve --");
            System.out.println("Player: " + playerName);
            System.out.println("Injury: " + injuryDetail);
            System.out.println("Status: Added to Injury Reserve\n");
        }
    }

    //Method to remove player from injury
    public void removePlayer(String firstName, String lastName) {
        
        String playerName = firstName + " " + lastName;
        boolean playerFound = false;

        for (String[] playerInfo : injuryReserveStack) {
            if (playerInfo[0].equalsIgnoreCase(playerName)) {
                injuryReserveStack.remove(playerInfo);// remove from stack
                updateCSV(firstName, lastName, false, " ");// update csv
                updateInjuredPlayersList();
                System.out.println("-- Removing Player from Injury Reserve --");
                System.out.println("Player: " + playerInfo[0]);
                System.out.println("Status: Cleared to Play\n");
                playerFound = true;
                break;
            }
        }

        if (!playerFound) { // if player not found
            showAlert("Player Not Found", "The player " + playerName + " is not in the injury reserve.");
        }
    }

    //Method to update player list
    private void updateInjuredPlayersList() {
        if (injuryReserveStack.isEmpty()) {
            injuredPlayersList.setText("No players in the injury reserve.");
        } else {
            StringBuilder playersList = new StringBuilder();
            for (String[] playerInfo : injuryReserveStack) {
                playersList.append("Player: ").append(playerInfo[0]).append("\n");
                playersList.append("Injury: ").append(playerInfo[1]).append("\n\n");
            }
            injuredPlayersList.setText(playersList.toString());
        }
    }

    /**
 * Updates the CSV file with the injury status and details of a player.
 * 
 * @param firstName The first name of the player.
 * @param lastName The last name of the player.
 * @param isInjured The injury status of the player (true if injured, false otherwise).
 * @param injuryDetail The details of the injury (if injured).
 */
    private synchronized void updateCSV(String firstName, String lastName, boolean isInjured, String injuryDetail) {
        CSVReader reader = null;
        CSVWriter writer = null;
        try {
            reader = new CSVReader(new FileReader(CSV_FILE));
            List<String[]> allData = reader.readAll(); // Read all data at once
            for (String[] row : allData) {
                if (row[0].equals(firstName) && row[1].equals(lastName)) {
                    row[11] = String.valueOf(isInjured); // Update Injured column
                    row[12] = isInjured ? injuryDetail : " "; // Update InjuryDetails column
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
            Logger.getLogger(InjuryReserve.class.getName()).log(Level.SEVERE, null, ex);
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
}
