/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;

import java.util.Random;
public class Manager {
    private String name;
    private int Id;
    private String password;
    private Random r = new Random();//KIV
  
    
    public Manager(String name,int Id, String password ) {
        this.name = name;
        this.Id = Id;
        this.password = password;
        
    }
    
    //KIV JAPS
    public void setId() {
        Id = r.nextInt(10000+999);
    }
    
    public String getName() {
        return name;
    }
    
    public int getId() {
        return Id;
    }

    public String getPassword() {
        return password;
    }
     
}
