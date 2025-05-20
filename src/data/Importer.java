package data;

import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import player.Player;
import player.PlayerPosition;
import team.Club;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Locale;
import java.util.Random;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class Importer {

    public Player[] importPlayers(String filePath) throws IOException {
        try {
            FileReader file = new FileReader(filePath);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(file);

            JSONArray squadArray = (JSONArray) json.get("squad");
            Player[] players = new Player[squadArray.size()];

            for (int i = 0; i < squadArray.size(); i++) {
                JSONObject p = (JSONObject) squadArray.get(i);
                String name = (String) p.get("name");
                LocalDate birthDate = LocalDate.parse((String) p.get("birthDate"));
                String nationality = (String) p.get("nationality");
                String position = (String) p.get("basePosition");
                String photo = (String) p.get("photo");
                int number = ((Number) p.get("number")).intValue();

                int passing;
                int shooting;
                int speed;
                int stamina;
                float weight;
                float height;
                PreferredFoot foot;

                PlayerPosition playerPosition = PlayerPosition.valueOf(position.toUpperCase());

                if (p.containsKey("height") && p.get("height") instanceof Number) {
                    height = ((Number) p.get("height")).floatValue();
                } else {
                    height = generateRandomHeight();
                }

                if (p.containsKey("weight") && p.get("weight") instanceof Number) {
                    weight = ((Number) p.get("weight")).floatValue();
                } else {
                    weight = generateRandomWeight();
                }

                if (p.containsKey("stamina") && p.get("stamina") instanceof Number) {
                    stamina = ((Number) p.get("stamina")).intValue();
                } else {
                    stamina = generateRandomStamina(playerPosition);
                }

                if (p.containsKey("speed") && p.get("speed") instanceof Number) {
                    speed = ((Number) p.get("speed")).intValue();
                } else {
                    speed = generateRandomSpeed(playerPosition);
                }

                if (p.containsKey("shooting") && p.get("shooting") instanceof Number) {
                    shooting = ((Number) p.get("shooting")).intValue();
                } else {
                    shooting = generateRandomShooting(playerPosition);
                }

                if (p.containsKey("passing") && p.get("passing") instanceof Number) {
                    passing = ((Number) p.get("passing")).intValue();
                } else {
                    passing = generateRandomPassing(playerPosition);
                }

                if (p.containsKey("preferredFoot") && p.get("preferredFoot") instanceof String) {
                    foot = PreferredFoot.fromString((String) p.get("preferredFoot"));
                } else {
                    foot = generateRandomPreferredFoot();
                }

                players[i] = new Player(name, birthDate, nationality, photo, number,
                        passing, shooting, speed, stamina, weight, height, playerPosition, foot);
            }

            file.close();
            return players;

        } catch (Exception error) {
            throw new IOException("Error reading player file:" + error.getMessage());
        }
    }

    private static float generateRandomHeight() {
        Random random = new Random();
        return 1.50f + random.nextFloat() * (2.00f - 1.50f);
    }

    private static float generateRandomWeight() {
        Random random = new Random();
        return 60.00f + random.nextFloat() * (100.00f - 60.00f);
    }

    private static int generateRandomSpeed(PlayerPosition position) {
        Random random = new Random();
        switch (position.getDescription().toUpperCase()) {
            case "GOALKEEPER":
                return random.nextInt(20) + 30;
            case "DEFENDER":
                return random.nextInt(40) + 30;
            case "MIDFIELDER":
                return random.nextInt(60) + 30;
            case "FORWARD":
                return random.nextInt(70) + 30;
            default:
                return 0;
        }
    }

    private static int generateRandomStamina(PlayerPosition position) {
        Random random = new Random();
        switch (position.getDescription().toUpperCase()) {
            case "GOALKEEPER":
                return random.nextInt(30) + 30;
            case "DEFENDER":
                return random.nextInt(60) + 30;
            case "MIDFIELDER":
                return random.nextInt(70) + 30;
            case "FORWARD":
                return random.nextInt(70) + 30;
            default:
                return 0;
        }
    }

    private static int generateRandomShooting(PlayerPosition position) {
        Random random = new Random();
        switch (position.getDescription().toUpperCase()) {
            case "GOALKEEPER":
                return random.nextInt(20) + 30;
            case "DEFENDER":
                return random.nextInt(50) + 30;
            case "MIDFIELDER":
                return random.nextInt(70) + 30;
            case "FORWARD":
                return random.nextInt(70) + 30;
            default:
                return 0;
        }
    }

    private static int generateRandomPassing(PlayerPosition position) {
        Random random = new Random();
        switch (position.getDescription().toUpperCase()) {
            case "GOALKEEPER":
                return random.nextInt(60) + 30;
            case "DEFENDER":
                return random.nextInt(60) + 30;
            case "MIDFIELDER":
                return random.nextInt(60) + 30;
            case "FORWARD":
                return random.nextInt(60) + 30;
            default:
                return 0;
        }
    }

    private static PreferredFoot generateRandomPreferredFoot() {
        Random random = new Random();
        int chance = random.nextInt(10) + 1; // 1 a 10

        if (chance <= 2) {
            return PreferredFoot.Both;
        } else if (chance <= 5) {
            return PreferredFoot.Left;
        } else {
            return PreferredFoot.Right;
        }
    }

    public Club[] importClubs(String filePath) throws IOException {
        try {
            FileReader file = new FileReader(filePath);
            JSONParser parser = new JSONParser();
            JSONArray clubArray = (JSONArray) parser.parse(file);

            Club[] clubs = new Club[clubArray.size()];

            for (int i = 0; i < clubArray.size(); i++) {
                JSONObject c = (JSONObject) clubArray.get(i);
                String name = (String) c.get("name");
                String code = (String) c.get("code");
                String clubNationality = (String) c.get("country");
                long foundedLong = (Long) c.get("founded");
                int dateOfFoundation = (int) foundedLong;
                String stadiumName = (String) c.get("stadium");
                String clubLogo = (String) c.get("logo");


                clubs[i] = new Club(name, code, clubNationality, stadiumName, clubLogo, dateOfFoundation);
            }

            file.close();
            return clubs;

        } catch (Exception error) {
            throw new IOException("Error reading club file: " + error.getMessage());
        }
    }


    public Club[] importData() throws IOException {
        try {
            Club[] clubs = importClubs("./files/clubs.json");

            for (Club club : clubs) {
                Player[] players = importPlayers("./files/players/"+ club.getCode()+".json");
                club.setPlayers(players);
            }
            return clubs;

        }catch (Exception error) {
            throw new IOException("Error reading club file: " + error.getMessage());
        }
    }
}