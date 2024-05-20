/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author edaotiro
 */
public class NBACity {
    
    
    public NBACity(){
        
    }
    
    public void openNBACity(Stage primaryStage){
        Stage CityStage = new Stage();
        
        CityStage.initOwner(primaryStage);
        CityStage.initModality(Modality.APPLICATION_MODAL);
        
        // Create UI components for entering donation details
        Text CityTitle = new Text("NBA City Map");
        
        // Set up the layout for entering donation details
        VBox CityLayout = new VBox(10);
        CityLayout.setAlignment(Pos.CENTER);
        CityLayout.setPadding(new Insets(20, 20, 20, 20));

        CityLayout.getChildren().addAll(CityTitle);

        // Set up the scene for entering donation details
        Scene cityScene = new Scene(CityLayout, 300, 200);
        cityScene.getRoot().setStyle("-fx-background-color: #FFF3B0;");
        CityStage.setScene(cityScene);
        CityStage.setTitle("NBA- City Map");

        // Show the stage for entering donation details
        CityStage.show();

    }
}
