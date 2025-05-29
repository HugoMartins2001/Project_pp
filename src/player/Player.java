package player;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import event.PlayerEvent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

/**
 * Represents a football (soccer) player with personal information, physical attributes,
 * skill ratings, and club association.
 * <p>
 * The {@code Player} class encapsulates all relevant data for a player, such as:
 * <ul>
 *   <li>Name</li>
 *   <li>Birthdate</li>
 *   <li>Nationality</li>
 *   <li>Photo</li>
 *   <li>Jersey number</li>
 *   <li>Skill ratings (passing, shooting, speed, stamina)</li>
 *   <li>Physical characteristics (weight, height)</li>
 *   <li>Playing position</li>
 *   <li>Preferred foot</li>
 *   <li>Club code</li>
 * </ul>
 * </p>
 * <p>
 * This class implements the {@link IPlayer} interface and supports cloning via the {@link Cloneable} interface.
 * </p>
 *
 * <h2>Example Usage</h2>
 * <pre>{@code
 * Player player = new Player(
 *     "Lionel Messi",
 *     LocalDate.of(1987, 6, 24),
 *     "Argentina",
 *     "messi.jpg",
 *     10,
 *     95, 98, 90, 85,
 *     72.0f, 1.70f,
 *     PlayerPosition.FORWARD,
 *     PreferredFoot.LEFT,
 *     "FCB"
 * );
 * }</pre>
 *
 * <p>
 * Authors:
 * <ul>
 *   <li>Rúben Tiago Martins Pereira (8230162) - LsircT2</li>
 *   <li>Hugo Leite Martins (8230273) - LsircT2</li>
 * </ul>
 * </p>
 *
 * @see IPlayer
 * @see PlayerPosition
 * @see PreferredFoot
 */
public class Player implements IPlayer, Cloneable {

    private String name;
    private LocalDate birthdate;
    private String nationality;
    private String photo;
    private int number;
    private int specPassing;
    private int specShooting;
    private int specSpeed;
    private int specStamina;
    private float weight;
    private float height;
    private PlayerPosition position;
    private PreferredFoot foot;
    private String clubCode;

    /**
     * Constructs a new Player with the specified attributes.
     *
     * @param name         the player's full name
     * @param birthdate    the player's date of birth
     * @param nationality  the player's nationality
     * @param photo        the URL or path to the player's photo
     * @param number       the player's jersey number
     * @param specPassing  the player's passing skill rating
     * @param specShooting the player's shooting skill rating
     * @param specSpeed    the player's speed rating
     * @param specStamina  the player's stamina rating
     * @param weight       the player's weight in kilograms
     * @param height       the player's height in meters
     * @param position     the player's position on the field
     * @param foot         the player's preferred foot
     * @param clubCode     the code representing the player's club
     */
    public Player(String name, LocalDate birthdate, String nationality,
                  String photo, int number, int specPassing, int specShooting,
                  int specSpeed, int specStamina, float weight, float height,
                  PlayerPosition position, PreferredFoot foot, String clubCode) {
        this.name = name;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.photo = photo;
        this.number = number;
        this.specPassing = specPassing;
        this.specShooting = specShooting;
        this.specSpeed = specSpeed;
        this.specStamina = specStamina;
        this.weight = weight;
        this.height = height;
        this.position = position;
        this.foot = foot;
        this.clubCode = clubCode;
    }


    /**
     * Returns the player's full name.
     *
     * @return the name of the player
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the player's date of birth.
     *
     * @return the player's birth date as a {@link LocalDate}
     */
    @Override
    public LocalDate getBirthDate() {
        return birthdate;
    }

    /**
     * Calculates and returns the player's current age in years.
     * <p>
     * The age is computed based on the difference between the player's birth date
     * and the current date.
     *
     * @return the player's age in years
     */
    @Override
    public int getAge() {
        LocalDate birthdate = getBirthDate();
        LocalDate todayDate = LocalDate.now();
        return Period.between(birthdate, todayDate).getYears();
    }


    /**
     * Returns the player's nationality.
     *
     * @return the nationality of the player
     */
    @Override
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the player's position on the field.
     *
     * @param iPlayerPosition the new position to assign to the player; must not be {@code null}
     * @throws IllegalArgumentException if {@code iPlayerPosition} is {@code null}
     */
    @Override
    public void setPosition(IPlayerPosition iPlayerPosition) {
        if (iPlayerPosition == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.position = (PlayerPosition) iPlayerPosition;
    }

    /**
     * Returns the URL or file path to the player's photo.
     *
     * @return the player's photo as a {@code String}
     */
    @Override
    public String getPhoto() {
        return photo;
    }

    /**
     * Returns the player's jersey number.
     *
     * @return the player's number
     */
    @Override
    public int getNumber() {
        return number;
    }


    /**
     * Returns the player's shooting skill rating.
     *
     * @return the shooting skill value of the player
     */
    @Override
    public int getShooting() {
        return specShooting;
    }

    /**
     * Returns the player's passing skill rating.
     *
     * @return the passing skill value of the player
     */
    @Override
    public int getPassing() {
        return specPassing;
    }

    /**
     * Returns the player's stamina rating.
     *
     * @return the stamina value of the player
     */
    @Override
    public int getStamina() {
        return specStamina;
    }

    /**
     * Returns the player's speed rating.
     *
     * @return the speed value of the player
     */
    @Override
    public int getSpeed() {
        return specSpeed;
    }

    /**
     * Returns the player's position on the field.
     *
     * @return the player's position as an {@link IPlayerPosition}
     */
    @Override
    public IPlayerPosition getPosition() {
        return position;
    }


    /**
     * Returns the player's height in meters.
     *
     * @return the height of the player, in meters
     */
    @Override
    public float getHeight() {
        return height;
    }

    /**
     * Returns the player's weight in kilograms.
     *
     * @return the weight of the player, in kilograms
     */
    @Override
    public float getWeight() {
        return weight;
    }

    /**
     * Returns the player's preferred foot (e.g., left or right).
     *
     * @return the player's preferred foot as a {@link PreferredFoot}
     */
    @Override
    public PreferredFoot getPreferredFoot() {
        return foot;
    }

    /**
     * Returns the code representing the player's club.
     *
     * @return the club code associated with the player
     */
    public String getClub() {
        return clubCode;
    }

    /**
     * Exports the player's data to a JSON-formatted string and prints it to the standard output.
     * <p>
     * The JSON contains all main player attributes, including name, birthdate, nationality,
     * photo, number, skill ratings, physical attributes, position, preferred foot, and club code.
     * </p>
     *
     * @throws IOException if an I/O error occurs during export (not thrown in current implementation)
     */
    @Override
    public void exportToJson() throws IOException {
        String json = "Player :{\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"birthdate\": \"" + birthdate.toString() + "\",\n" +
                "  \"nationality\": \"" + nationality + "\",\n" +
                "  \"photo\": \"" + photo + "\",\n" +
                "  \"number\": " + number + ",\n" +
                "  \"specPassing\": " + specPassing + ",\n" +
                "  \"specShooting\": " + specShooting + ",\n" +
                "  \"specSpeed\": " + specSpeed + ",\n" +
                "  \"specStamina\": " + specStamina + ",\n" +
                "  \"weight\": " + weight + ",\n" +
                "  \"height\": " + height + ",\n" +
                "  \"position\": \"" + position.getDescription() + "\",\n" +
                "  \"foot\": \"" + foot + "\",\n" +
                "  \"clubCode\": \"" + clubCode + "\"\n" +
                "}";
        System.out.println(json);
    }

    /**
     * Returns a string representation of the player, including all main attributes.
     *
     * @return a formatted string containing player details
     */
    @Override
    public String toString() {
        String s = "Player: " + "\nName: " + name + "\n";
        s += "Birthdate: " + birthdate + "\n";
        s += "Age: " + getAge() + "\n";
        s += "Nationality: " + nationality + "\n";
        s += "Photo: " + photo + "\n";
        s += "Number: " + number + "\n";
        s += "Shooting: " + specShooting + "\n";
        s += "Passing: " + specPassing + "\n";
        s += "Stamina: " + specStamina + "\n";
        s += "Speed: " + specSpeed + "\n";
        s += "Position: " + position + "\n";
        s += "Height: " + height + "\n";
        s += "Weight: " + weight + "\n";
        s += "Prefered foot: " + foot + "\n";
        s += "Club: " + clubCode + "\n";
        s += "----------------------------------------\n";
        return s;
    }

    /**
     * Creates and returns a copy of this player.
     *
     * @return a clone of this player
     * @throws CloneNotSupportedException if the player's class does not support the {@code Cloneable} interface
     */
    @Override
    public Player clone() throws CloneNotSupportedException {
        return (Player) super.clone();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * <p>
     * Two players are considered equal if they have the same name, birthdate, and club code.
     * </p>
     *
     * @param obj the reference object with which to compare
     * @return {@code true} if this object is the same as the {@code obj} argument; {@code false} otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Player)) {
            return false;
        }
        Player player = (Player) obj;
        return name.equals(player.name) && birthdate.equals(player.birthdate) && clubCode.equals(player.clubCode);
    }

}