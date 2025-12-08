package app.model;

import java.io.Serializable;

public class Food implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int caloriesPer100g;
    private double proteinPer100g;
    private double carbsPer100g;
    private double fatPer100g;

    public Food(String name, int caloriesPer100g, double proteinPer100g, double carbsPer100g, double fatPer100g) {
        this.name = name;
        this.caloriesPer100g = caloriesPer100g;
        this.proteinPer100g = proteinPer100g;
        this.carbsPer100g = carbsPer100g;
        this.fatPer100g = fatPer100g;
    }

    public String getName() {
        return name;
    }

    public int getCaloriesPer100g() {
        return caloriesPer100g;
    }

    public double getProteinPer100g() {
        return proteinPer100g;
    }

    public double getCarbsPer100g() {
        return carbsPer100g;
    }

    public double getFatPer100g() {
        return fatPer100g;
    }
}
