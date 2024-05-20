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
public class PerformanceRanking {
    
    
    public PerformanceRanking(){
        
    }
    
    public void openRanking(Stage primaryStage){
        Stage rankingStage = new Stage();
        
        rankingStage.initOwner(primaryStage);
        rankingStage.initModality(Modality.APPLICATION_MODAL);
        
        // Create UI components 
        
        
        
        // Set up the layout 
        VBox rankingLayout = new VBox(10);
        rankingLayout.setAlignment(Pos.CENTER);
        rankingLayout.setPadding(new Insets(20, 20, 20, 20));

        rankingLayout.getChildren().addAll();

        // Set up the scene 
        Scene rankingScene = new Scene(rankingLayout, 300, 200);
        rankingScene.getRoot().setStyle("-fx-background-color: #FFF3B0;");
        rankingStage.setScene(rankingScene);
        rankingStage.setTitle("NBA - Performance Ranking");

        // Show the stage 
        rankingStage.show();
    }
}
