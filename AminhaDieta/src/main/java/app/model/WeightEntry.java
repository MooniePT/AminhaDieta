package app.model;

import java.io.Serializable;
import java.time.LocalDate;

public class WeightEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate date;
    private double weightKg;

    public WeightEntry(double weightKg) {
        this.date = LocalDate.now();
        this.weightKg = weightKg;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getWeightKg() {
        return weightKg;
    }
}
