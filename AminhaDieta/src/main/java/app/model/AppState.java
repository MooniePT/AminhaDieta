package app.model;

import java.io.Serializable;
import java.util.*;

public class AppState implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<UserProfile> profiles = new ArrayList<>();
    private UUID activeProfileId; // Identificador do perfil atualmente ativo

    public List<UserProfile> getProfiles() {
        if (profiles == null) {
            profiles = new ArrayList<>();
        }
        return profiles;
    }

    public UserProfile getActiveProfile() {
        if (activeProfileId == null)
            return null;
        return profiles.stream()
                .filter(p -> p.getId().equals(activeProfileId))
                .findFirst().orElse(null);
    }

    public void setActiveProfile(UUID id) {
        this.activeProfileId = id;
    }

    public UUID getActiveProfileId() {
        return activeProfileId;
    }

    public void addProfile(UserProfile p) {
        profiles.add(p);
        activeProfileId = p.getId(); // Define automaticamente como ativo ao criar
    }

    public void removeProfile(UUID id) {
        profiles.removeIf(p -> p.getId().equals(id));
        if (Objects.equals(activeProfileId, id))
            activeProfileId = null;
    }
}
