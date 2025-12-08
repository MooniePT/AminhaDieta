package app.persistence;

import app.model.AppState;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataStore {
    private final Path filePath;

    public DataStore(Path filePath) {
        this.filePath = filePath;
    }

    public AppState load() {
        try {
            if (!Files.exists(filePath)) return new AppState();
            try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(filePath))) {
                return (AppState) in.readObject();
            }
        } catch (Exception e) {
            // se estiver corrompido ou incompatível, não crashar
            return new AppState();
        }
    }

    public void save(AppState state) {
        try {
            Files.createDirectories(filePath.getParent());
            try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filePath))) {
                out.writeObject(state);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro a guardar dados: " + e.getMessage(), e);
        }
    }
}
