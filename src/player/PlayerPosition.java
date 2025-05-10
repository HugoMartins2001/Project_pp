package player;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

public enum PlayerPosition implements IPlayerPosition {
    GOALKEEPER,
    DEFENDER,
    MIDFIELDER,
    FORWARD;


    @Override
    public String getDescription() {
        switch (this) {
            case GOALKEEPER:
                return "Goalkeeper";
            case DEFENDER:
                return "Defender";
            case MIDFIELDER:
                return "Midfielder";
            case FORWARD:
                return "Forward";
            default:
                return "Unknown Position";
        }
    }
}