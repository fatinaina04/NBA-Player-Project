/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<Manager> managerList;
    private List<Integer> idList;
    
    public UserManager() {
        managerList = new ArrayList<>();
        idList = loadManagerId();
        loadManager();
    }
    public boolean isIdTaken(int Id) {
        return idList.contains(Id);
    }
    public void registerUser(Manager manager) {
        managerList.add(manager);
        idList.add(manager.getId());
    }
    public void updateManager(Manager manager) {
        saveManager(manager);
    }
    
    public List<Manager> getAllManagerId() {
        // Return a copy of the user list to avoid modifying the original list outside this class
        return new ArrayList<>(managerList);
    }
    public List<Integer> getManagerId() {
        return idList;
    }
    
    public void saveManager(Manager manager) {
        String fileName = manager.getId() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Manager ID: " + manager.getId() + "\n");
            writer.write("Name: " + manager.getName() + "\n");
            writer.write("Password: " + manager.getPassword() + "\n");
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private List<Integer> loadManagerId() {
    List<Integer> id = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader("ManagerId.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Skip empty lines
            if (line.trim().isEmpty()) {
                continue;
            }
            // Try parsing the line as an integer
            try {
                id.add(Integer.parseInt(line.trim()));
            } catch (NumberFormatException e) {
                // Handle cases where the line cannot be parsed as an integer
                System.err.println("Invalid ID found in ManagerId.txt: " + line);
            }
        }
    } catch (IOException e) {
        // Ignore if the file doesn't exist yet
    }
    return id;
}
    private Manager loadManagerFromFile(File file) {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        int Id = 0;
        String name = null;
        String password = null;
        

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("EXP: ")) {
                Id = Integer.parseInt(line.substring("EXP: ".length()).trim());
            } else if (line.startsWith("Name: ")) {
                name = line.substring("Name: ".length()).trim();
            }else if (line.startsWith("Password: ")) {
                password = line.substring("Password: ".length()).trim();
            }

        if (Id != 0  && password != null && name != null) {
            return new Manager(name,Id, password);
        }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
    }

    private void loadManager() {
    File folder = new File(".");
    File[] listOfFiles = folder.listFiles();

    for (File file : listOfFiles) {
        if (file.isFile() && file.getName().endsWith(".txt")) {
            Manager loadedManager = loadManagerFromFile(file);
            if (loadedManager != null) {
                managerList.add(loadedManager);
            }
        }
    }
}
    public boolean isValidLogin(int id, String password) {
        for (Manager manager : managerList) {
            if (manager.getId()==id && getPasswordById(id).equals(password) ) {
                return true;
            }
        }
        return false;
    }
    
    public static String getPasswordById(int Id) {
        String fileName = Id + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Password: ")) {
                    return line.substring("Password: ".length()).trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Return null if the username is not found or there's an error
    }
}
