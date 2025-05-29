/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */

package data;

import com.ppstudios.footballmanager.api.contracts.data.htmlgenerators.ClubHtmlGenerator;
import com.ppstudios.footballmanager.api.contracts.data.htmlgenerators.SeasonHtmlGenerator;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;
import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import event.*;
import league.League;
import league.Schedule;
import league.Season;
import league.Standing;
import main.Functions;
import match.Match;
import player.Player;
import player.PlayerPosition;
import team.Club;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import team.Formation;
import team.Team;

import java.io.File;
import java.util.Random;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Importer class responsible for loading player data from JSON files.
 * <p>
 * This class reads player attributes from structured JSON and converts them into
 * Player objects with fallbacks for missing or invalid fields using random generators.
 */
public class Importer {

    private static final Random RANDOM = new Random();

    /**
     * Imports a list of players from a JSON file.
     * <p>
     * The JSON file is expected to have a "squad" array containing player details.
     * If certain attributes are missing or not valid (e.g., height, weight, stats),
     * the method assigns them with random but contextually appropriate values.
     *
     * @param filePath The path to the JSON file containing player data.
     * @return An array of {@link Player} objects parsed from the file.
     * @throws IOException If the file cannot be read or parsed.
     */
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

                String clubCode = filePath.split("/")[3].replace(".json", "");

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
                        passing, shooting, speed, stamina, weight, height, playerPosition, foot, clubCode);
            }

            file.close();
            return players;

        } catch (Exception error) {
            throw new IOException("Error reading player file:" + error.getMessage());
        }
    }

    /**
     * Generates a random height for a player.
     * <p>
     * The height will be a float value between 1.50m and 2.00m.
     *
     * @return A randomly generated height in meters.
     */
    private static float generateRandomHeight() {
        return 1.50f + RANDOM.nextFloat() * (2.00f - 1.50f);
    }

    /**
     * Generates a random weight for a player.
     * <p>
     * The weight will be a float value between 60kg and 100kg.
     *
     * @return A randomly generated weight in kilograms.
     */
    private static float generateRandomWeight() {
        return 60.00f + RANDOM.nextFloat() * (100.00f - 60.00f);
    }

    /**
     * Generates a random speed value for a player, based on position.
     * <p>
     * Goalkeepers tend to have lower speed values, while forwards can reach the highest.
     *
     * @param position The player's position.
     * @return A randomly generated speed value appropriate to the player's position.
     */
    private static int generateRandomSpeed(PlayerPosition position) {
        switch (position.getDescription().toUpperCase()) {
            case "GOALKEEPER":
                return RANDOM.nextInt(20) + 30;
            case "DEFENDER":
                return RANDOM.nextInt(40) + 30;
            case "MIDFIELDER":
                return RANDOM.nextInt(60) + 30;
            case "FORWARD":
                return RANDOM.nextInt(70) + 30;
            default:
                return 0;
        }
    }

    /**
     * Generates a random stamina value for a player, based on position.
     * <p>
     * Stamina values are tailored to position, with midfielders and forwards typically having higher ranges.
     *
     * @param position The player's position.
     * @return A randomly generated stamina value appropriate to the player's position.
     */
    private static int generateRandomStamina(PlayerPosition position) {
        switch (position.getDescription().toUpperCase()) {
            case "GOALKEEPER":
                return RANDOM.nextInt(30) + 30;
            case "DEFENDER":
                return RANDOM.nextInt(60) + 30;
            case "MIDFIELDER":
                return RANDOM.nextInt(70) + 30;
            case "FORWARD":
                return RANDOM.nextInt(70) + 30;
            default:
                return 0;
        }
    }

    /**
     * Generates a random shooting value for a player, based on position.
     * <p>
     * Forwards and midfielders tend to have higher shooting capabilities,
     * while defenders and goalkeepers have lower values.
     *
     * @param position The player's position.
     * @return A randomly generated shooting stat appropriate to the position.
     */
    private static int generateRandomShooting(PlayerPosition position) {
        switch (position.getDescription().toUpperCase()) {
            case "GOALKEEPER":
                return RANDOM.nextInt(20) + 30;
            case "DEFENDER":
                return RANDOM.nextInt(50) + 30;
            case "MIDFIELDER":
                return RANDOM.nextInt(70) + 30;
            case "FORWARD":
                return RANDOM.nextInt(70) + 30;
            default:
                return 0;
        }
    }

    /**
     * Generates a random passing value for a player, based on position.
     * <p>
     * Passing stats are moderately high across all positions except goalkeepers,
     * who may have slightly less accurate passing abilities.
     *
     * @param position The player's position.
     * @return A randomly generated passing stat appropriate to the position.
     */
    private static int generateRandomPassing(PlayerPosition position) {
        switch (position.getDescription().toUpperCase()) {
            case "GOALKEEPER":
                return RANDOM.nextInt(60) + 30;
            case "DEFENDER":
                return RANDOM.nextInt(60) + 30;
            case "MIDFIELDER":
                return RANDOM.nextInt(60) + 30;
            case "FORWARD":
                return RANDOM.nextInt(60) + 30;
            default:
                return 0;
        }
    }

    /**
     * Generates a random preferred foot for a player.
     * <p>
     * Uses a weighted probability:
     * <ul>
     *   <li>20% chance of being both-footed</li>
     *   <li>30% chance of being left-footed</li>
     *   <li>50% chance of being right-footed</li>
     * </ul>
     *
     * @return A randomly selected {@link PreferredFoot}.
     */
    private static PreferredFoot generateRandomPreferredFoot() {
        int chance = RANDOM.nextInt(10) + 1; // 1 to 10

        if (chance <= 2) {
            return PreferredFoot.Both;
        } else if (chance <= 5) {
            return PreferredFoot.Left;
        } else {
            return PreferredFoot.Right;
        }
    }

    /**
     * Imports an array of Club objects from a JSON file.
     * <p>
     * For each club in the JSON array, this method also loads its players
     * from a corresponding file in the path: {@code ./files/players/{code}.json}.
     *
     * @param filePath The path to the JSON file containing the list of clubs.
     * @return An array of {@link Club} objects fully populated with player data.
     * @throws IOException If the file cannot be read or parsed.
     */
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
                IPlayer[] players = importPlayers("./files/players/" + code + ".json");
                for (IPlayer player : players) {
                    if (player != null) {
                        clubs[i].addPlayer(player);
                    }
                }
            }

            file.close();
            return clubs;

        } catch (Exception error) {
            throw new IOException("Error reading club file: " + error.getMessage());
        }
    }

    /**
     * Imports all leagues from a predefined JSON file and registers them
     * into the system using the {@link Functions#setLeagues(ILeague[])} method.
     * <p>
     * This method expects the file {@code ./files/leagues.json} to exist and be correctly formatted.
     * If an error occurs during parsing, it prints the stack trace and an error message.
     */
    public void importAllLeagues() {
        try {
            FileReader file = new FileReader("./files/leagues.json");
            JSONParser parser = new JSONParser();
            JSONArray leaguesArray = (JSONArray) parser.parse(file);

            ILeague[] leagues = ILeagueJSONtoArray(leaguesArray);
            Functions.setLeagues(leagues);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading club file: " + e.getMessage());
        }
    }

    /**
     * Converts a JSON array of leagues into an array of {@link ILeague} objects.
     *
     * @param jsonArray The JSON array containing league data.
     * @return An array of {@link ILeague} instances created from the JSON.
     */
    private ILeague[] ILeagueJSONtoArray(JSONArray jsonArray) {
        ILeague[] leagues = new ILeague[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            ILeague league = this.ILeagueJSONtoObject((JSONObject) jsonArray.get(i));
            leagues[i] = league;
        }

        return leagues;
    }

    /**
     * Converts a single JSON object into an {@link ILeague} instance.
     * <p>
     * Includes the league name and an array of associated seasons.
     *
     * @param jsonObject The JSON object representing a league.
     * @return A new {@link ILeague} created from the JSON data.
     */
    private ILeague ILeagueJSONtoObject(JSONObject jsonObject) {
        String name = (String) jsonObject.get("name");
        ISeason[] seasons = ISeasonJSONtoArray((JSONArray) jsonObject.get("seasons"));

        return new League(name, seasons);
    }

    /**
     * Converts a JSON array of seasons into an array of {@link ISeason} objects.
     *
     * @param jsonArray The JSON array containing season data.
     * @return An array of {@link ISeason} instances.
     */
    private ISeason[] ISeasonJSONtoArray(JSONArray jsonArray) {
        ISeason[] seasons = new ISeason[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            seasons[i] = this.ISeasonJSONtoObject((JSONObject) jsonArray.get(i));
        }

        return seasons;
    }

    /**
     * Converts a JSON object into an {@link ISeason} instance.
     * <p>
     * Includes season name, year, current round, max teams, club list,
     * schedule, standings, and manager mode flag. After creation,
     * the season simulates rounds to reach the stored round number.
     *
     * @param jsonObject The JSON object representing a season.
     * @return A fully constructed {@link ISeason} object.
     */
    private ISeason ISeasonJSONtoObject(JSONObject jsonObject) {
        String name = (String) jsonObject.get("name");
        int year = ((Long) jsonObject.get("year")).intValue();
        int currentRound = ((Long) jsonObject.get("current_round")).intValue();
        int maxTeams = ((Long) jsonObject.get("max_teams")).intValue();
        IClub[] clubs = IClubJSONtoArray((JSONArray) jsonObject.get("clubs"));
        ISchedule schedule = IScheduleJSONtoObject((JSONObject) jsonObject.get("schedule"));
        IStanding[] standings = IStandingJSONtoArray((JSONArray) jsonObject.get("standings"));
        boolean isManager = (boolean) jsonObject.get("is_manager");

        Season season = new Season(name, year, maxTeams, isManager);

        for (IClub club : clubs) {
            season.addClub(club);
        }

        season.setShedule(schedule);
        season.setStandings(standings);

        while (season.getCurrentRound() < currentRound) {
            season.simulateRound();
        }

        return season;
    }

    /**
     * Converts a JSON array of clubs into an array of {@link IClub} objects.
     *
     * @param jsonArray The JSON array containing club data.
     * @return An array of {@link IClub} instances.
     */
    private IClub[] IClubJSONtoArray(JSONArray jsonArray) {
        IClub[] clubs = new IClub[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            clubs[i] = this.IClubJSONtoObject((JSONObject) jsonArray.get(i));
        }
        return clubs;
    }

    /**
     * Converts a JSON object into an {@link IClub} instance.
     * <p>
     * Includes name, code, stadium, logo, country, foundation year,
     * and an array of players.
     *
     * @param jsonObject The JSON object representing a club.
     * @return A fully populated {@link IClub} object.
     */
    private IClub IClubJSONtoObject(JSONObject jsonObject) {
        String name = (String) jsonObject.get("name");
        String code = (String) jsonObject.get("code");
        String stadium = (String) jsonObject.get("stadium");
        String logo = (String) jsonObject.get("logo");
        String country = (String) jsonObject.get("country");
        int founded = ((Long) jsonObject.get("foundedYear")).intValue();
        IPlayer[] players = IPlayerJSONtoArray((JSONArray) jsonObject.get("players"));

        return new Club(name, code, country, stadium, logo, founded, players);
    }

    /**
     * Converts a JSON array of matches into an array of {@link IMatch} objects.
     *
     * @param jsonArray The JSON array representing match data.
     * @return An array of {@link IMatch} instances.
     */
    private IMatch[] IMatchJSONtoArray(JSONArray jsonArray) {
        IMatch[] matches = new IMatch[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            matches[i] = this.IMatchJSONtoObject((JSONObject) jsonArray.get(i));
        }
        return matches;
    }

    /**
     * Converts a JSON object into an {@link IMatch} instance.
     * <p>
     * Includes information about home and away clubs/teams, the round,
     * whether the match was played, and its events.
     *
     * @param jsonObject The JSON object representing a match.
     * @return A fully constructed {@link IMatch} object.
     */
    private IMatch IMatchJSONtoObject(JSONObject jsonObject) {
        IClub homeClub = this.IClubJSONtoObject((JSONObject) jsonObject.get("home_club"));
        IClub awayClub = this.IClubJSONtoObject((JSONObject) jsonObject.get("away_club"));
        ITeam homeTeam = this.ITeamJSONtoObject((JSONObject) jsonObject.get("home_team"));
        ITeam awayTeam = this.ITeamJSONtoObject((JSONObject) jsonObject.get("away_team"));

        int round = ((Long) jsonObject.get("round")).intValue();
        IEventManager eventManager = IEventManagerJSONtoArray((JSONArray) jsonObject.get("events"));
        boolean isPlayed = (boolean) jsonObject.get("is_played");

        return new Match(homeClub, awayClub, homeTeam, awayTeam, round, eventManager, isPlayed);
    }

    /**
     * Converts a JSON array of teams into an array of {@link ITeam} objects.
     *
     * @param jsonArray The JSON array representing team data.
     * @return An array of {@link ITeam} instances.
     */
    private ITeam[] ITeamJSONtoArray(JSONArray jsonArray) {
        ITeam[] teams = new ITeam[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            teams[i] = this.ITeamJSONtoObject((JSONObject) jsonArray.get(i));
        }
        return teams;
    }

    /**
     * Converts a JSON object into an {@link ITeam} instance.
     * <p>
     * Includes team formation, associated club, and player list.
     *
     * @param jsonObject The JSON object representing a team.
     * @return A {@link ITeam} object populated with the given data.
     */
    private ITeam ITeamJSONtoObject(JSONObject jsonObject) {
        IFormation formation = IFormationStringToObject((String) jsonObject.get("formation"));
        IClub club = this.IClubJSONtoObject((JSONObject) jsonObject.get("club"));
        IPlayer[] players = IPlayerJSONtoArray((JSONArray) jsonObject.get("players"));

        return new Team(club, formation, players);
    }

    /**
     * Converts a JSON array of players into an array of {@link IPlayer} objects.
     *
     * @param jsonArray The JSON array representing player data.
     * @return An array of {@link IPlayer} instances.
     */
    private IPlayer[] IPlayerJSONtoArray(JSONArray jsonArray) {
        IPlayer[] players = new IPlayer[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            players[i] = this.IPlayerJSONtoObject((JSONObject) jsonArray.get(i));
        }
        return players;
    }

    /**
     * Converts a JSON object into an {@link IPlayer} instance.
     * <p>
     * Includes player attributes such as name, number, stats, nationality,
     * preferred foot, photo, birth date, and club code.
     *
     * @param jsonObject The JSON object representing a player.
     * @return A {@link IPlayer} object populated with the given data.
     */
    private IPlayer IPlayerJSONtoObject(JSONObject jsonObject) {
        String name = (String) jsonObject.get("name");
        String stringPosition = (String) jsonObject.get("position");
        int number = ((Long) jsonObject.get("number")).intValue();
        int shooting = ((Long) jsonObject.get("shooting")).intValue();
        int passing = ((Long) jsonObject.get("passing")).intValue();
        int stamina = ((Long) jsonObject.get("stamina")).intValue();
        int speed = ((Long) jsonObject.get("speed")).intValue();
        float height = ((Double) jsonObject.get("height")).floatValue();
        float weight = ((Double) jsonObject.get("weight")).floatValue();
        String nationality = (String) jsonObject.get("nationality");
        PreferredFoot preferredFoot = PreferredFoot.fromString((String) jsonObject.get("preferredFoot"));
        String photo = (String) jsonObject.get("photo");
        LocalDate birthDate = LocalDate.parse((String) jsonObject.get("birthDate"));
        String clubCode = (String) jsonObject.get("clubCode");

        PlayerPosition playerPosition = stringToPlayerPosition(stringPosition);
        return new Player(name, birthDate, nationality, photo, number, passing, shooting, speed, stamina, weight, height, playerPosition, preferredFoot, clubCode);
    }

    /**
     * Converts a string representation of a formation into an {@link IFormation} object.
     * <p>
     * The format must follow the pattern "X-X-X", where each part represents the
     * number of defenders, midfielders, and forwards respectively.
     *
     * @param formation The string representation of the formation (e.g. "4-4-2").
     * @return A new {@link IFormation} object based on the parsed values.
     * @throws IllegalArgumentException If the format is invalid or values are not integers.
     */
    private IFormation IFormationStringToObject(String formation) {
        String[] parts = formation.split("-");

        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid format. Expected format: 'X-X-X'");
        }

        try {
            int defenders = Integer.parseInt(parts[0]);
            int midfielders = Integer.parseInt(parts[1]);
            int forwards = Integer.parseInt(parts[2]);

            return new Formation(formation, defenders, midfielders, forwards);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formation values must be integers.");
        }
    }

    /**
     * Converts a JSON object into an {@link ISchedule} instance.
     * <p>
     * Extracts the list of matches, organizes them by round, and returns
     * a structured {@link Schedule} object.
     *
     * @param jsonObject The JSON object representing a schedule.
     * @return An {@link ISchedule} instance with matches grouped by round.
     */
    private ISchedule IScheduleJSONtoObject(JSONObject jsonObject) {
        IMatch[] matchesArray = IMatchJSONtoArray((JSONArray) jsonObject.get("matches"));
        IMatch[][] matches = organizeMatchesByRound(matchesArray);
        return new Schedule(matches);
    }


    /**
     * Organizes matches into a 2D array [round][matchIndex] using only arrays.
     *
     * @param matches the flat array of matches
     * @return a 2D array grouped by round
     */
    private IMatch[][] organizeMatchesByRound(IMatch[] matches) {
        // Primeiro, descobrir o número de rondas e quantos jogos há em cada ronda
        int maxRound = 0;
        for (IMatch match : matches) {
            if (match.getRound() > maxRound) {
                maxRound = match.getRound();
            }
        }

        // Contar quantos jogos existem por ronda
        int[] countPerRound = new int[maxRound + 1];
        for (IMatch match : matches) {
            countPerRound[match.getRound()]++;
        }

        // Criar o array de saída com o tamanho correto por ronda
        IMatch[][] result = new IMatch[maxRound + 1][];
        for (int i = 0; i <= maxRound; i++) {
            result[i] = new IMatch[countPerRound[i]];
        }

        // Índices auxiliares para preencher cada ronda
        int[] currentIndex = new int[maxRound + 1];

        // Distribuir os jogos para o array bidimensional
        for (IMatch match : matches) {
            int round = match.getRound();
            result[round][currentIndex[round]++] = match;
        }

        return result;
    }

    /**
     * Converts a JSON array into an array of {@link IStanding} objects.
     *
     * @param jsonArray The JSON array representing league standings.
     * @return An array of {@link IStanding} instances.
     */
    private IStanding[] IStandingJSONtoArray(JSONArray jsonArray) {
        IStanding[] standings = new IStanding[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            standings[i] = this.IStandingJSONtoObject((JSONObject) jsonArray.get(i));
        }
        return standings;
    }

    /**
     * Converts a JSON object into an {@link IStanding} instance.
     * <p>
     * Includes team statistics such as points, wins, draws, losses,
     * total matches, goals scored, and goals conceded.
     *
     * @param jsonObject The JSON object representing a standing.
     * @return A {@link IStanding} object populated with the given data.
     */
    private IStanding IStandingJSONtoObject(JSONObject jsonObject) {
        ITeam team = ITeamJSONtoObject((JSONObject) jsonObject.get("team"));
        int points = ((Long) jsonObject.get("points")).intValue();
        int wins = ((Long) jsonObject.get("wins")).intValue();
        int draws = ((Long) jsonObject.get("draws")).intValue();
        int losses = ((Long) jsonObject.get("losses")).intValue();
        int totalMatches = ((Long) jsonObject.get("total_matches")).intValue();
        int goalsScored = ((Long) jsonObject.get("goals_scored")).intValue();
        int goalsConceded = ((Long) jsonObject.get("goals_conceded")).intValue();

        return new Standing(points, wins, draws, losses, totalMatches, goalsScored, goalsConceded, team);
    }

    /**
     * Converts a JSON array of events into an {@link IEventManager}.
     * <p>
     * Each event is parsed and added to the event manager.
     *
     * @param jsonArray The JSON array representing match events.
     * @return An {@link IEventManager} instance populated with events.
     */
    private IEventManager IEventManagerJSONtoArray(JSONArray jsonArray) {
        IEventManager eventManager = new EventManager();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject eventJson = (JSONObject) jsonArray.get(i);
            IEvent event = this.IEventJSONtoObject(eventJson);
            eventManager.addEvent(event);
        }

        return eventManager;
    }

    /**
     * Converts a JSON object into an {@link IEvent} instance based on the event type.
     * <p>
     * Supports all predefined football match events such as goals, fouls, injuries,
     * and substitutions. Each event is mapped from its "type" string to the corresponding class.
     * If the event type is unknown, an exception is thrown.
     *
     * @param eventJson The JSON object representing an event.
     * @return A concrete {@link IEvent} instance.
     * @throws IllegalStateException If the event type is not recognized.
     */
    private IEvent IEventJSONtoObject(JSONObject eventJson) {
        String type = (String) eventJson.get("type");
        int minute = ((Long) eventJson.get("minute")).intValue();
        Player player = null;

        if (eventJson.containsKey("player")) {
            player = (Player) this.IPlayerJSONtoObject((JSONObject) eventJson.get("player"));
        }

        switch (type) {
            case "CornerKickEvent":
                return new CornerKickEvent(player, minute);
            case "EndEvent":
                return new EndEvent(minute);
            case "FoulEvent":
                return new FoulEvent(player, minute);
            case "FreeKickEvent":
                return new FreeKickEvent(player, minute);
            case "GoalEvent":
                return new GoalEvent(player, minute);
            case "InjuryEvent":
                return new InjuryEvent(player, minute);
            case "OffSideEvent":
                return new OffSideEvent(player, minute);
            case "PenaltiesEvent":
                return new PenaltiesEvent(player, minute);
            case "RedCardEvent":
                return new RedCardEvent(player, minute);
            case "ShotEvent":
                return new ShotEvent(player, minute);
            case "ShotOnGoalEvent":
                return new ShotOnGoalEvent(player, minute);
            case "StartEvent":
                return new StartEvent(minute);
            case "SubstitutionEvent":
                return new SubstitutionEvent(player, (Player) this.IPlayerJSONtoObject((JSONObject) eventJson.get("player_in")), minute);
            case "YellowCardEvent":
                return new YellowCardEvent(player, minute);
            default:
                throw new IllegalStateException("Unknown Event: " + type);
        }
    }

    /**
     * Converts a string representation of a player position into a {@link PlayerPosition} enum.
     *
     * @param position The string representing the position (e.g. "GOALKEEPER").
     * @return The corresponding {@link PlayerPosition} enum value.
     * @throws IllegalArgumentException If the string does not match a known position.
     */
    private PlayerPosition stringToPlayerPosition(String position) {
        switch (position.toUpperCase()) {
            case "GOALKEEPER":
                return PlayerPosition.GOALKEEPER;
            case "DEFENDER":
                return PlayerPosition.DEFENDER;
            case "MIDFIELDER":
                return PlayerPosition.MIDFIELDER;
            case "FORWARD":
                return PlayerPosition.FORWARD;
            default:
                throw new IllegalArgumentException("Unknown player position: " + position);
        }
    }

}