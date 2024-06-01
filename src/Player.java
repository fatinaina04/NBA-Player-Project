/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nba2;
import java.util.*;

class Player {
String name;
    String position;
    double pts;
    double reb;
    double stl;
    double ast;
    double blk;
    double compositeScore;
    double salary;
    int height;
    int weight;

    public Player(String name, String position, double pts, double reb, double stl, double ast, double blk, double salary, int height, int weight) {
        this.name = name;
        this.position = position;
        this.pts = pts;
        this.reb = reb;
        this.stl = stl;
        this.ast = ast;
        this.blk = blk;
        this.salary = salary;
        this.height = height;
        this.weight = weight;
    }

    public void calculateCompositeScore(Map<String, Map<String, Double>> weights) {
        Map<String, Double> weight = weights.get(this.position);
        this.compositeScore = this.pts * weight.get("PTS") +
                              this.reb * weight.get("REB") +
                              this.stl * weight.get("STL") +
                              this.ast * weight.get("AST") +
                              this.blk * weight.get("BLK");
    }

    @Override
    public String toString() {
        return String.format("%s (Position: %s, PPG: %.1f, Salary: %.2f, Height: %d, Weight: %d)", name, position, pts, salary, height, weight);
    }
}
