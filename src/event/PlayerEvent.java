package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import player.Player;

import java.io.IOException;

public class PlayerEvent extends Event {
    private IPlayer player;

    public PlayerEvent(IPlayer player, int minute, String description) {
        super(description, minute);
        this.player = player;
    }


    public IPlayer getPlayer() {
        return this.player;
    }


    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void exportToJson() throws IOException {
        String json = "event : {\n" +
                "  \"type\": \"PlayerEvent\",\n" +
                "  \"description\": \"" + getDescription() + "\",\n" +
                "  \"minute\": " + getMinute() + ",\n" +
                "  \"player\": {\n" +
                "    \"name\": \"" + player.getName() + "\",\n" +
                "    \"clubCode\": \"" + ((Player) player).getClub() + "\"\n" +
                "  }\n" +
                "}";

        System.out.println(json);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PlayerEvent)) {
            return false;
        }
        PlayerEvent playerEvent = (PlayerEvent) obj;
        return super.equals(obj) && player.equals(playerEvent.player);
    }
}
