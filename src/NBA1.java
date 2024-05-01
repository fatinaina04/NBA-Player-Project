/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package nba1;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class NBA1 extends Application {
    
    
    @Override
    public void start(Stage primaryStage) {
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20)); //margine
        grid.setVgap(10); //vertical spacing
        grid.setHgap(10); //horizontal spacing
        grid.setAlignment(Pos.CENTER); //allignment
        
        //Add Text Field
        TextField usernameField = new TextField();
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        
        //Add Button
        Button registerButton = new Button("Register");
        Button loginButton = new Button("Login");
        
        //Change bg colour for button
        registerButton.setStyle("-fx-background-color: #B2DFDB; -fx-text-fill: black;");
        loginButton.setStyle("-fx-background-color: #B2DFDB; -fx-text-fill: black;");
        
        //determine the position of children
        grid.add(new Label("Username:"), 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Password:"), 0, 3);
        grid.add(passwordField, 1, 3);
        grid.add(registerButton, 0, 4);
        grid.add(loginButton, 1, 4);
        
        GridPane.setHalignment(registerButton, HPos.CENTER);
        GridPane.setHalignment(loginButton, HPos.CENTER);
       
       //set Scene
        Scene scene = new Scene(grid, 300, 250);
        
        primaryStage.setTitle("🏀 NBA-Login Page 🏀");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

 
    public static void main(String[] args) {
        launch(args);
    }
    
}
