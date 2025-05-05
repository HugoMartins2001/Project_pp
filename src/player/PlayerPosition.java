package player;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

public enum PlayerPosition implements IPlayerPosition {
    GOALKEEPER("Goalkeeper"),
    DEFENDER("Defender"),
    MIDFIELDER("Midfielder"),
    FORWARD("Forward");

    private final String description;

    PlayerPosition(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}