/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class InjuryReserve {
   
    
    public void openInjury(Stage primaryStage){
        
        Stage InjuryStage = new Stage();
        
        InjuryStage.initOwner(primaryStage);
        InjuryStage.initModality(Modality.APPLICATION_MODAL);
        
        // Create UI components for entering donation details
        Text InjuryTitle = new Text("Injury Reserve List");
        
        // Set up the layout for entering donation details
        VBox InjuryLayout = new VBox(10);
        InjuryLayout.setAlignment(Pos.CENTER);
        InjuryLayout.setPadding(new Insets(20, 20, 20, 20));

        InjuryLayout.getChildren().addAll(InjuryTitle);

        // Set up the scene for entering donation details
        Scene injuryScene = new Scene(InjuryLayout, 300, 200);
        injuryScene.getRoot().setStyle("-fx-background-color: #FFF3B0;");
        InjuryStage.setScene(injuryScene);
        InjuryStage.setTitle("NBA - Injury Reserve");

        // Show the stage for entering donation details
        InjuryStage.show();

    }
}
