package player;

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;

public class PlayerPosition implements IPlayerPosition {

    private String description;

    @Override
    public String getDescription() {
        return description;
    }
}
