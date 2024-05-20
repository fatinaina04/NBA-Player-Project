/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author edaotiro
 */
public class MainAppWindow extends Application{
    private Stage primaryStage;
   
  
    
    public static void main(String[] args) {
        MainAppWindow mainApp = new MainAppWindow();
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("🏀 NBA- Main Window 🏀");
        
        Roster roster = new Roster();
        NBACity NBA = new NBACity();
        InjuryReserve IR = new InjuryReserve();
        PerformanceRanking ranking = new PerformanceRanking();
        
        Button ActiveRoster = new Button("Roster");
        Button NBACities = new Button("NBA Cities");
        Button InjuryReserve = new Button("Injury Reserve");
        Button PerRanking = new Button("Performance Ranking");
        
        
        
        ActiveRoster.setOnAction(e -> roster.openRoster(primaryStage));
        NBACities.setOnAction(e -> NBA.openNBACity(primaryStage));
        InjuryReserve.setOnAction(e -> IR.openInjury(primaryStage));
        PerRanking.setOnAction(e -> ranking.openRanking(primaryStage));
        
        // Set up the layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(ActiveRoster,NBACities, InjuryReserve, PerRanking);
        
        // Set up the scene
        Scene scene = new Scene(layout, 400, 400);
        scene.getRoot().setStyle("-fx-background-color: #FFF3B0;");
        primaryStage.setScene(scene);

        primaryStage.show();

    }
    
    
}
