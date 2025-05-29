package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import player.Player;

import java.io.IOException;

/**
 * Represents a general player-related event during a football match.
 * <p>
 * This class extends {@link Event} and includes information about the player involved
 * in the event. It also overrides the JSON export logic to include player-specific data.
 * </p>
 *
 * <p>
 * Example description: {@code "-> Foul committed by John Doe at 35 minutes"}
 * </p>
 *
 * <p>
 * Authors:
 * <ul>
 *   <li>Rúben Tiago Martins Pereira (8230162) - LsircT2</li>
 *   <li>Hugo Leite Martins (8230273) - LsircT2</li>
 * </ul>
 * </p>
 *
 * @see Event
 * @see IPlayer
 */
public class PlayerEvent extends Event {
    private IPlayer player;

    /**
     * Constructs a new PlayerEvent with the given player, match minute and description.
     *
     * @param player The player involved in the event.
     * @param minute The minute in the match when the event occurred.
     * @param description The description of the event.
     */
    public PlayerEvent(IPlayer player, int minute, String description) {
        super(description, minute);
        this.player = player;
    }

    /**
     * Returns the player involved in this event.
     *
     * @return The player object.
     */
    public IPlayer getPlayer() {
        return this.player;
    }

    /**
     * Returns the description of the event.
     *
     * @return The event description.
     */
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    /**
     * Exports this player event in a JSON-like format to the console.
     *
     * @throws IOException If there is an error during export.
     */
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

    /**
     * Checks if this PlayerEvent is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if equal, false otherwise.
     */
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
