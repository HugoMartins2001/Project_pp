/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */
package player;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

/**
 * Represents a football player with attributes like name, birthdate, nationality, position, and technical stats.
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
     * Constructs a Player with all necessary attributes.
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
     * @return the player's full name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the player's birthdate
     */
    @Override
    public LocalDate getBirthDate() {
        return birthdate;
    }

    /**
     * @return the player's age in years
     */
    @Override
    public int getAge() {
        LocalDate birthdate = getBirthDate();
        LocalDate todayDate = LocalDate.now();
        return Period.between(birthdate, todayDate).getYears();
    }

    /**
     * @return the player's nationality
     */
    @Override
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the player's field position.
     *
     * @param iPlayerPosition the player's position on the field
     * @throws IllegalArgumentException if the position is null
     */
    @Override
    public void setPosition(IPlayerPosition iPlayerPosition) {
        if (iPlayerPosition == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.position = (PlayerPosition) iPlayerPosition;
    }

    /**
     * @return path or URL to the player's photo
     */
    @Override
    public String getPhoto() {
        return photo;
    }

    /**
     * @return the player's shirt number
     */
    @Override
    public int getNumber() {
        return number;
    }

    /**
     * @return the player's shooting skill (0-100)
     */
    @Override
    public int getShooting() {
        return specShooting;
    }

    /**
     * @return the player's passing skill (0-100)
     */
    @Override
    public int getPassing() {
        return specPassing;
    }

    /**
     * @return the player's stamina (0-100)
     */
    @Override
    public int getStamina() {
        return specStamina;
    }

    /**
     * @return the player's speed (0-100)
     */
    @Override
    public int getSpeed() {
        return specSpeed;
    }

    /**
     * @return the player's position on the field
     */
    @Override
    public IPlayerPosition getPosition() {
        return position;
    }

    /**
     * @return the player's height in meters
     */
    @Override
    public float getHeight() {
        return height;
    }

    /**
     * @return the player's weight in kilograms
     */
    @Override
    public float getWeight() {
        return weight;
    }

    /**
     * @return the player's preferred foot
     */
    @Override
    public PreferredFoot getPreferredFoot() {
        return foot;
    }

    /**
     * @return the code of the club the player belongs to
     */
    public String getClub() {
        return clubCode;
    }

    /**
     * Exports player information to JSON format and prints it to the console.
     *
     * @throws IOException if an I/O error occurs
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
     * Returns a string representation of the player object.
     *
     * @return formatted string with player details
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
     * @return a clone of this player instance
     * @throws CloneNotSupportedException if cloning is not supported
     */
    @Override
    public Player clone() throws CloneNotSupportedException {
        return (Player) super.clone();
    }

    /**
     * Compares this player to another object for equality.
     *
     * @param obj the object to compare to
     * @return true if the objects represent the same player, false otherwise
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
