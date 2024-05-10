/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;

import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;

/**
 *
 * @author edaotiro
 */
public class Roster {
    private UserManager userManager;
    
    public Roster(UserManager userManager){
        this.userManager = userManager;
    }
    
    public void openRoster(Stage primaryStage){
        Stage rosterStage = new Stage();
        
        rosterStage.initOwner(primaryStage);
        rosterStage.initModality(Modality.APPLICATION_MODAL);
        
        // Create UI components 
        Text rosterTitle = new Text("Active Roster Player");
       
        Button addButton = new Button("Add Player");
        Button searchButton = new Button("Search Player");
        
        // Set up the layout 
        VBox RosterLayout = new VBox(10);
        RosterLayout.setAlignment(Pos.CENTER);
        RosterLayout.setPadding(new Insets(20, 20, 20, 20));

        RosterLayout.getChildren().addAll(rosterTitle,addButton,searchButton);

        // Set up the scene 
        Scene rosterScene = new Scene(RosterLayout, 300, 200);
        rosterScene.getRoot().setStyle("-fx-background-color: #FFF3B0;");
        rosterStage.setScene(rosterScene);
        rosterStage.setTitle("NBA - Active Roster");

        // Show the stage 
        rosterStage.show();


    }
}
