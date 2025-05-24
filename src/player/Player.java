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
import com.ppstudios.footballmanager.api.contracts.team.IClub;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthdate;
    }

    @Override
    public int getAge() {
        LocalDate birthdate = getBirthDate();
        LocalDate todayDate = LocalDate.now();
        return Period.between(birthdate, todayDate).getYears();
    }

    @Override
    public String getNationality() {
        return nationality;
    }

    @Override
    public void setPosition(IPlayerPosition iPlayerPosition) {
        if(iPlayerPosition == null){
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.position = (PlayerPosition) iPlayerPosition;
    }

    @Override
    public String getPhoto() {
        return photo;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int getShooting() {
        return specShooting;
    }

    @Override
    public int getPassing() {
        return specPassing;
    }

    @Override
    public int getStamina() {
        return specStamina;
    }

    @Override
    public int getSpeed() {
        return specSpeed;
    }

    @Override
    public IPlayerPosition getPosition() {
        return position;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getWeight() {
        return weight;
    }

    @Override
    public PreferredFoot getPreferredFoot() {
        return foot;
    }

    public String getClub() {
        return clubCode;
    }

    //TODO: Fazer no final
    @Override
    public void exportToJson() throws IOException {

    }

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

    @Override
    public Player clone() throws CloneNotSupportedException {
        return (Player) super.clone();
    }
}