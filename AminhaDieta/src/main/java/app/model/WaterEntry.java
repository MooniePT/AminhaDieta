package app.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa um registo de consumo de água.
 */
public class WaterEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime timestamp;
    private double amountMl;

    public WaterEntry(double amountMl) {
        this.timestamp = LocalDateTime.now();
        this.amountMl = amountMl;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getAmountMl() {
        return amountMl;
    }
}
