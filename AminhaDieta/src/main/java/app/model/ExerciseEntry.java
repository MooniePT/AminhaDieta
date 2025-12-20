package app.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa um registo de exercício físico realizado pelo utilizador.
 */
public class ExerciseEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID id = UUID.randomUUID();
    private LocalDateTime timestamp;
    private String type;
    private int durationMinutes;
    private int caloriesBurned;

    public ExerciseEntry(LocalDateTime timestamp, String type, int durationMinutes, int caloriesBurned) {
        this.timestamp = timestamp;
        this.type = type;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }
}
