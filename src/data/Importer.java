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

import java.util.Random;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class Importer {

    private static final Random RANDOM = new Random();
    
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

    private static float generateRandomHeight() {
        return 1.50f + RANDOM.nextFloat() * (2.00f - 1.50f);
    }

    private static float generateRandomWeight() {
        return 60.00f + RANDOM.nextFloat() * (100.00f - 60.00f);
    }

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

    private static PreferredFoot generateRandomPreferredFoot() {
        int chance = RANDOM.nextInt(10) + 1; // 1 a 10

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

    public void importAllLeagues() {
        try {
            FileReader file = new FileReader("./files/leagues.json");
            JSONParser parser = new JSONParser();
            JSONArray leaguesArray = (JSONArray) parser.parse(file);

            ILeague[] leagues = ILeagueJSONtoArray(leaguesArray);
            Functions.setLeagues(leagues);
        }catch (Exception e) {
            System.out.println("Error reading club file: " + e.getMessage());
        }
    }

    private ILeague[] ILeagueJSONtoArray(JSONArray jsonArray){
        ILeague[] leagues = new ILeague[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            ILeague league = this.ILeagueJSONtoObject((JSONObject) jsonArray.get(i));
            leagues[i] = league;
        }

        return leagues;
    }

    private ILeague ILeagueJSONtoObject(JSONObject jsonObject) {
        String name = (String) jsonObject.get("name");
        ISeason[] seasons = ISeasonJSONtoArray((JSONArray) jsonObject.get("seasons"));

        return new League(name, seasons);
    }

    private ISeason[] ISeasonJSONtoArray(JSONArray jsonArray) {
        ISeason[] seasons = new ISeason[jsonArray.size()];

        for(int i = 0; i < jsonArray.size(); i++) {
            seasons[i] = this.ISeasonJSONtoObject((JSONObject) jsonArray.get(i));
        }
        return seasons;
    }

    private ISeason ISeasonJSONtoObject(JSONObject jsonObject) {
        String name = (String) jsonObject.get("name");
        int year = ((Long) jsonObject.get("year")).intValue();
        int currentRound = ((Long) jsonObject.get("current_round")).intValue();
        int maxTeams = ((Long) jsonObject.get("max_teams")).intValue();
        IClub[] clubs = IClubJSONtoArray((JSONArray) jsonObject.get("clubs"));
        IMatch[] matches = IMatchJSONtoArray((JSONArray) jsonObject.get("matches"));
        ISchedule schedule = IScheduleJSONtoObject((JSONObject) jsonObject.get("schedule"));
        IStanding[] standings = IStandingJSONtoArray((JSONArray) jsonObject.get("standings"));
        boolean isManager = (boolean) jsonObject.get("is_manager");

        int numberOfTeams = 0;
        for(IClub club : clubs){
            if(club != null){
                numberOfTeams++;
            }
        }
        return new Season(name, year, currentRound, maxTeams, clubs, matches, schedule, standings, numberOfTeams, isManager);
    }

    private IClub[] IClubJSONtoArray(JSONArray jsonArray) {
        IClub[] clubs = new IClub[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            clubs[i] = this.IClubJSONtoObject((JSONObject) jsonArray.get(i));
        }
        return clubs;
    }

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

    private IMatch[] IMatchJSONtoArray(JSONArray jsonArray) {
        IMatch[] matches = new IMatch[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            matches[i] = this.IMatchJSONtoObject((JSONObject) jsonArray.get(i));
        }
        return matches;
    }

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

    private ITeam[] ITeamJSONtoArray(JSONArray jsonArray) {
        ITeam[] teams = new ITeam[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            teams[i] = this.ITeamJSONtoObject((JSONObject) jsonArray.get(i));
        }
        return teams;
    }

    private ITeam ITeamJSONtoObject(JSONObject jsonObject) {
        IFormation formation = IFormationStringToObject((String) jsonObject.get("formation"));
        IClub club = this.IClubJSONtoObject((JSONObject) jsonObject.get("club"));
        IPlayer[] players = IPlayerJSONtoArray((JSONArray) jsonObject.get("players"));

        return new Team(club, formation, players);
    }

    private IPlayer[] IPlayerJSONtoArray(JSONArray jsonArray) {
        IPlayer[] players = new IPlayer[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            players[i] = this.IPlayerJSONtoObject((JSONObject) jsonArray.get(i));
        }
        return players;
    }

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
        return new Player(name, birthDate, nationality, photo, number, passing, shooting, speed, stamina, weight, height, playerPosition ,preferredFoot , clubCode);
    }


    private IFormation IFormationStringToObject(String formation) {
        String[] parts = formation.split("-");

        if (parts.length < 3) {
            throw new IllegalArgumentException("Formato inválido. Deve ser 'X-X-X'");
        }

        try {
            int defenders = Integer.parseInt(parts[0]);
            int midfielders = Integer.parseInt(parts[1]);
            int forwards = Integer.parseInt(parts[2]);

            return new Formation(formation, defenders, midfielders, forwards);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Os valores da formação devem ser números inteiros.");
        }
    }

    private ISchedule IScheduleJSONtoObject(JSONObject jsonObject) {
        IMatch[] matchesArray = IMatchJSONtoArray((JSONArray) jsonObject.get("matches"));
        IMatch[][] matches= organizeMatchesByRound(matchesArray);
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


    private IStanding[] IStandingJSONtoArray(JSONArray jsonArray) {
        IStanding[] standings = new IStanding[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); i++) {
            standings[i] = this.IStandingJSONtoObject((JSONObject) jsonArray.get(i));
        }
        return standings;
    }

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

    private IEventManager IEventManagerJSONtoArray(JSONArray jsonArray) {
        IEventManager eventManager = new EventManager();

        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject eventJson = (JSONObject) jsonArray.get(i);
            IEvent event = this.IEventJSONtoObject(eventJson);
            eventManager.addEvent(event);
        }

        return eventManager;
    }

    private IEvent IEventJSONtoObject(JSONObject eventJson) {
        String type = (String) eventJson.get("type");
        int minute = ((Long) eventJson.get("minute")).intValue();
        Player player = null;
        if(eventJson.containsKey("player")){
            player = (Player) this.IPlayerJSONtoObject((JSONObject) eventJson.get("player"));
        }

        switch(type){
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
            case "Start Event":
                return new StartEvent(minute);
            case "Substitution Card":
                return new SubstitutionEvent(player,(Player) this.IPlayerJSONtoObject((JSONObject) eventJson.get("player_in")), minute);
            case "Yellow Card":
                return new YellowCardEvent(player, minute);
            default:
                throw new IllegalStateException("Unknown Event: " + type);
        }
    }

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