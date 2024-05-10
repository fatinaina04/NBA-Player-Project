/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package nba2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class UserManagement extends Application {
    private UserManager userManager;
    
    @Override
    public void start(Stage primaryStage) {
        userManager = new UserManager();
        LoginRegister logreg = new LoginRegister(userManager, this);
        GridPane grid = new GridPane();
        
        //set Scene
        Scene scene = new Scene(grid, 400, 350);
        scene.getRoot().setStyle("-fx-background-color: #FFF3B0;");
      
        grid.setPadding(new Insets(20, 20, 20, 20)); //margine
        grid.setVgap(10); //vertical spacing
        grid.setHgap(5); //horizontal spacing
        grid.setAlignment(Pos.CENTER); //allignment
        
        //Add Title Text
        Label StartTitle = new Label("NBA GENERAL MANAGER");
        StartTitle.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 24));
        StartTitle.setTextFill(Color.GREEN);  
        grid.add(StartTitle, 0, 0, 2, 1);  

        //Add Button
        Button registerButton = new Button("Register");
        Button loginButton = new Button("Login");
        
        //Change bg colour for button
        registerButton.setStyle("-fx-background-color: #B2DFDB; -fx-text-fill: black;");
        loginButton.setStyle("-fx-background-color: #B2DFDB; -fx-text-fill: black;");
        
        //determine the position of children
        GridPane.setHalignment(registerButton, HPos.CENTER);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setHalignment(StartTitle, HPos.CENTER);
        grid.add(registerButton, 1, 4);
        grid.add(loginButton, 1, 5);
        // Event handling for register button
        registerButton.setOnAction(e -> logreg.openRegister(primaryStage));

        // Event handling for login button
        loginButton.setOnAction(e ->logreg.openLogin(primaryStage) );

        primaryStage.setTitle("🏀 NBA-Login Page 🏀");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

 
    public static void main(String[] args) {
        launch(args);
    }
    public void registerUser(String name,int Id, String password) {
        if (userManager.isIdTaken(Id)) {
            System.out.println("Sorry! Username taken. Please choose another username.");
        } else {
            
            Manager newUser = new Manager(name,Id, password);
            userManager.registerUser(newUser);
            System.out.println("Registration successful!");
            saveId();
            userManager.saveManager(newUser);
            
        }
    }
    public void loginUser(int Id, String password, Stage primaryStage) {
        if (userManager.isValidLogin(Id, password)) {
            System.out.println("Login successful! Welcome, ");
            LogInOut.login(Id);
            openMainAppWindow(primaryStage);
            
        } else {
            System.out.println("Invalid username, email or password. Please try again.");
        }
    }
    private void openMainAppWindow(Stage primaryStage) {
        MainAppWindow mainAppWindow = new MainAppWindow();
        mainAppWindow.start(primaryStage);
    }
    
    private void saveId() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("ManagerId.txt"))) {
        for (Integer managerId : userManager.getManagerId()) {
            writer.write(managerId.toString()); // Convert integer to string before writing
            writer.newLine();
            writer.close();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
