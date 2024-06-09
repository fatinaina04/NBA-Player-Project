/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;

// Import Java Util library 
import java.util.Map;

// Import JavaFX library 
import javafx.beans.property.*;

class Player {
    
    //private instances
    private String FirstName;
    private String LastName;
    private String position;
    private double pts;
    private double reb;
    private double stl;
    private double ast;
    private double blk;
    private double compositeScore;
    private double salary;
    private double height;
    private int weight;
    private boolean injured;
    private String injuryDetail;
    private boolean extend;
    private final StringProperty name;
    private final DoubleProperty Height;
    private final DoubleProperty Weight;
    private final StringProperty Position;
    private final DoubleProperty points;
    private final DoubleProperty rebound;
    private final DoubleProperty steal;
    private final DoubleProperty assist;
    private final DoubleProperty block;
    private final DoubleProperty Salary;

    public Player(String firstName, String lastName, String position, double pts, double reb, double stl, double ast, double blk, double salary, double height, int weight,boolean injured, String injuryDetail,boolean extend) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.position = position;
        this.height = height;
        this.weight = weight;
        this.pts = pts;
        this.reb = reb;
        this.stl = stl;
        this.ast = ast;
        this.blk = blk;
        this.salary = salary;
        this.injured = injured;
        this.injuryDetail = injuryDetail;
        this.extend=extend;
        this.name = new SimpleStringProperty(firstName+" "+lastName);
        this.Height = new SimpleDoubleProperty(height);
        this.Weight = new SimpleDoubleProperty(weight);
        this.Position = new SimpleStringProperty(position);
        this.points = new SimpleDoubleProperty(pts);
        this.rebound = new SimpleDoubleProperty(reb);
        this.steal = new SimpleDoubleProperty(stl);
        this.assist = new SimpleDoubleProperty(ast);
        this.block = new SimpleDoubleProperty(blk);
        this.Salary = new SimpleDoubleProperty(salary);
    }
    
    public String getName() {
    return getFirstName() + " " + getLastName();
}


    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPosition() {
        return position;
    }

    public double getPoints() {
        return pts;
    }

    public double getRebounds() {
        return reb;
    }

    public double getSteals() {
        return stl;
    }

    public double getAssists() {
        return ast;
    }

    public double getBlocks() {
        return blk;
    }

    public double getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
     public StringProperty nameProperty() {
        return name;
    }

    public StringProperty positionProperty() {
        return Position;
    }

    public DoubleProperty ptsProperty() {
        return points;
    }

    public DoubleProperty rebProperty() {
        return rebound;
    }

    public DoubleProperty stlProperty() {
        return steal;
    }

    public DoubleProperty astProperty() {
        return assist;
    }

    public DoubleProperty blkProperty() {
        return block;
    }

    public DoubleProperty salaryProperty() {
        return Salary;
    }

    public DoubleProperty heightProperty() {
        return Height;
    }

    public DoubleProperty weightProperty() {
        return Weight;
    }
    public String getInjuryDetails() {
        return injuryDetail;
    }
    
    public boolean isInjured(){
        return injured;
    }
    public boolean iscontractExtend(){
        return extend;
    }
    
    public double getCompositeScore() {
        return compositeScore;
    }
    
    
    public void setCompositeScore(double compositeScore) {
        this.compositeScore = compositeScore;
    }

    //method to calculate composite score for Performance Ranking class
    public void calculateCompositeScore(Map<String, Map<String, Double>> weights) {
        Map<String, Double> weight = weights.get(this.position);
        this.compositeScore = this.pts * weight.get("PTS") +
                this.reb * weight.get("REB") +
                this.stl * weight.get("STL") +
                this.ast * weight.get("AST") +
                this.blk * weight.get("BLK");
        
         setCompositeScore(compositeScore);
    }

    @Override
    public String toString() {
        return String.format("%s %s (Position: %s, PPG: %.1f, Salary: $%.2f, Height: %d, Weight: %d)",
                FirstName, LastName, position, pts, salary, height, weight);
    }
}
