package app.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa um registo de uma refeição consumida pelo utilizador.
 */
public class MealEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id = UUID.randomUUID();
    private LocalDateTime timestamp;
    private String description;
    private int calories;
    private double protein;
    private double carbs;
    private double fat;

    // Construtor simples (retrocompatibilidade)
    public MealEntry(String description, int calories) {
        this(description, calories, 0, 0, 0);
    }

    public MealEntry(String description, int calories, double protein, double carbs, double fat) {
        this.timestamp = LocalDateTime.now();
        this.description = description;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }
}
