package app.test;

import app.model.AppState;
import app.model.ExerciseEntry;
import app.model.UserProfile;
import app.persistence.DataStore;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

public class ExerciseTest {
    public static void main(String[] args) {
        try {
            System.out.println("Starting Exercise Test...");

            // Setup
            AppState state = new AppState();
            UserProfile profile = new UserProfile("Test User", 30, 70.0, 175.0, UserProfile.Gender.MALE,
                    UserProfile.PhysicalActivityLevel.MODERATELY_ACTIVE);
            state.addProfile(profile);

            System.out.println("Profile created. Initial exercises: " + profile.getExercises().size());

            // Simulate Add Exercise
            String type = "Running";
            int duration = 30;
            int calories = 300;

            ExerciseEntry entry = new ExerciseEntry(LocalDateTime.now(), type, duration, calories);
            profile.getExercises().add(entry);

            System.out.println("Exercise added. Count: " + profile.getExercises().size());

            // Verify Data
            ExerciseEntry saved = profile.getExercises().get(0);
            if (!saved.getType().equals(type))
                throw new RuntimeException("Type mismatch");
            if (saved.getDurationMinutes() != duration)
                throw new RuntimeException("Duration mismatch");
            if (saved.getCaloriesBurned() != calories)
                throw new RuntimeException("Calories mismatch");

            System.out.println("Data verification passed.");

            // Simulate Persistence
            Path tempPath = Path.of("test_appstate.dat");
            DataStore store = new DataStore(tempPath);
            store.save(state);
            System.out.println("State saved.");

            AppState loadedState = store.load();
            UserProfile loadedProfile = loadedState.getActiveProfile();

            System.out.println("State loaded. Exercises: " + loadedProfile.getExercises().size());

            if (loadedProfile.getExercises().isEmpty()) {
                throw new RuntimeException("Persistence failed: No exercises found.");
            }

            System.out.println("Test SUCCESS.");

            // Cleanup
            java.nio.file.Files.deleteIfExists(tempPath);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test FAILED: " + e.getMessage());
        }
    }
}
