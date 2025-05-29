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

import com.ppstudios.footballmanager.api.contracts.data.IExporter;

import com.ppstudios.footballmanager.api.contracts.data.htmlgenerators.ClubHtmlGenerator;
import com.ppstudios.footballmanager.api.contracts.data.htmlgenerators.SeasonHtmlGenerator;
import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISchedule;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import event.Event;
import event.PlayerEvent;
import event.SubstitutionEvent;
import league.Season;
import main.Functions;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import player.Player;
import team.Club;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Exporter class responsible for exporting league data.
 * Implements the IExporter interface and provides functionality
 * to export league-related information to JSON files.
 * <p>
 * Authors:
 * - Rúben Tiago Martins Pereira (8230162, LsircT2)
 * - Hugo Leite Martins (8230273, LsircT2)
 */
public class Exporter implements IExporter {

    /**
     * Exports all loaded leagues to a JSON file.
     * <p>
     * The method retrieves the list of leagues using the {@code Functions.getLeagues()}
     * utility method, converts them into a JSON array, and writes the result to
     * {@code ./files/leagues.json}.
     * <p>
     * If an I/O error occurs during file writing, it logs an error message to the console.
     */
    @Override
    public void exportToJson() {
        JSONArray leaguesJson = leaguesToJsonArray(Functions.getLeagues());

        try (FileWriter file = new FileWriter("./files/leagues.json")) {
            file.write(leaguesJson.toJSONString());
            file.flush();
            System.out.println("League exported successfully to JSON file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Converts an array of ILeague objects into a JSON array.
     *
     * @param leagues An array of leagues to convert.
     * @return A JSONArray representing the list of leagues.
     */
    private JSONArray leaguesToJsonArray(ILeague[] leagues) {
        JSONArray leaguesArray = new JSONArray();
        for (ILeague league : leagues) {
            if (league != null) {
                leaguesArray.add(leagueToJsonObject(league));
            }
        }
        return leaguesArray;
    }

    /**
     * Converts a single ILeague object into a JSONObject.
     *
     * @param league The league to convert.
     * @return A JSONObject representing the league with its name and seasons.
     */
    private JSONObject leagueToJsonObject(ILeague league) {
        JSONObject leagueJson = new JSONObject();
        leagueJson.put("name", league.getName());
        leagueJson.put("seasons", seasonToJsonArray(league.getSeasons()));
        return leagueJson;
    }

    /**
     * Converts an array of ISeason objects into a JSON array.
     *
     * @param seasons An array of seasons to convert.
     * @return A JSONArray representing the list of seasons.
     */
    private JSONArray seasonToJsonArray(ISeason[] seasons) {
        JSONArray seasonsArray = new JSONArray();
        for (ISeason season : seasons) {
            if (season != null) {
                seasonsArray.add(seasonToJsonObject(season));
            }
        }
        return seasonsArray;
    }
    /**
     * Converts an ISeason object into a JSONObject.
     * <p>
     * Includes details such as name, year, current round, maximum teams,
     * list of clubs, matches, schedule, standings, and manager status.
     *
     * @param season The season to convert.
     * @return A JSONObject representing the season with all its properties.
     */
    private JSONObject seasonToJsonObject(ISeason season) {
        JSONObject seasonJson = new JSONObject();
        seasonJson.put("name", season.getName());
        seasonJson.put("year", season.getYear());
        seasonJson.put("current_round", season.getCurrentRound());
        seasonJson.put("max_teams", season.getMaxTeams());
        seasonJson.put("clubs", clubsToJsonArray(season.getCurrentClubs()));
        seasonJson.put("matches", matchesToJsonArray(season.getMatches()));
        seasonJson.put("schedule", scheduleToJsonObject(season.getSchedule()));
        seasonJson.put("standings", standingsToJsonArray(season.getLeagueStandings()));
        seasonJson.put("is_manager", ((Season) season).isManager());

        return seasonJson;
    }

    /**
     * Converts an array of IStanding objects into a JSON array.
     *
     * @param standings An array of league standings to convert.
     * @return A JSONArray representing the standings of a league.
     */
    private JSONArray standingsToJsonArray(IStanding[] standings) {
        JSONArray standingsArray = new JSONArray();
        for (IStanding standing : standings) {
            if (standing != null) {
                standingsArray.add(standingToJsonObject(standing));
            }
        }
        return standingsArray;
    }

    /**
     * Converts an IStanding object into a JSONObject.
     * <p>
     * Includes team information and statistics such as points, wins,
     * draws, losses, total matches, goals scored, and goals conceded.
     *
     * @param standing The standing object to convert.
     * @return A JSONObject representing the team's standing.
     */
    private JSONObject standingToJsonObject(IStanding standing) {
        JSONObject standingJson = new JSONObject();
        standingJson.put("team", teamToJsonObject(standing.getTeam()));
        standingJson.put("points", standing.getPoints());
        standingJson.put("wins", standing.getWins());
        standingJson.put("draws", standing.getDraws());
        standingJson.put("losses", standing.getLosses());
        standingJson.put("total_matches", standing.getTotalMatches());
        standingJson.put("goals_scored", standing.getGoalScored());
        standingJson.put("goals_conceded", standing.getGoalsConceded());

        return standingJson;
    }

    /**
     * Converts an array of IMatch objects into a JSON array.
     *
     * @param matches The list of matches to convert.
     * @return A JSONArray containing all the match data.
     */
    private JSONArray matchesToJsonArray(IMatch[] matches) {
        JSONArray matchesJsonArray = new JSONArray();
        for (IMatch match : matches) {
            if (match != null) {
                matchesJsonArray.add(matchToJsonObject(match));
            }
        }
        return matchesJsonArray;
    }

    /**
     * Converts a single IMatch object into a JSONObject.
     * <p>
     * Includes home and away clubs/teams, round number, match events,
     * and whether the match was played.
     *
     * @param match The match to convert.
     * @return A JSONObject representing the match details.
     */
    private JSONObject matchToJsonObject(IMatch match) {
        JSONObject matchJsonObject = new JSONObject();
        matchJsonObject.put("home_club", clubToJsonObject((Club) match.getHomeClub()));
        matchJsonObject.put("away_club", clubToJsonObject((Club) match.getAwayClub()));
        matchJsonObject.put("home_team", teamToJsonObject(match.getHomeTeam()));
        matchJsonObject.put("away_team", teamToJsonObject(match.getAwayTeam()));
        matchJsonObject.put("round", match.getRound());
        matchJsonObject.put("events", eventsToJsonArray(match.getEvents()));
        matchJsonObject.put("is_played", match.isPlayed());
        return matchJsonObject;
    }

    /**
     * Converts an ISchedule object into a JSONObject.
     * <p>
     * Includes all scheduled matches within the season.
     *
     * @param schedule The schedule to convert.
     * @return A JSONObject containing the list of matches in the schedule.
     */
    private Object scheduleToJsonObject(ISchedule schedule) {
        JSONObject scheduleJson = new JSONObject();
        scheduleJson.put("matches", matchesToJsonArray(schedule.getAllMatches()));
        return scheduleJson;
    }

    /**
     * Converts an ITeam object into a JSONObject.
     * <p>
     * Includes team formation, associated club, and list of players.
     * If the formation is not properly initialized, a default "4-4-2" is used.
     *
     * @param team The team to convert.
     * @return A JSONObject representing the team's structure and players.
     */
    private JSONObject teamToJsonObject(ITeam team) {
        JSONObject teamJson = new JSONObject();
        String formation;
        try {
            formation = team.getFormation().toString();
        } catch (IllegalStateException e) {
            formation = "4-4-2";
        }

        teamJson.put("formation", formation);
        teamJson.put("club", clubToJsonObject((Club) team.getClub()));
        teamJson.put("players", playersToJsonArray(team.getPlayers()));

        return teamJson;
    }

    /**
     * Converts an array of IClub objects into a JSON array.
     *
     * @param clubs An array of clubs to convert.
     * @return A JSONArray containing all club data.
     */
    private JSONArray clubsToJsonArray(IClub[] clubs) {
        JSONArray clubsJson = new JSONArray();

        for (IClub club : clubs) {
            if (club != null) {
                clubsJson.add(clubToJsonObject((Club) club));
            }
        }
        return clubsJson;
    }

    /**
     * Converts a Club object into a JSONObject.
     * <p>
     * Includes club metadata such as name, code, stadium, logo, country,
     * foundation year, and associated players.
     *
     * @param club The club to convert.
     * @return A JSONObject representing the club.
     */
    private JSONObject clubToJsonObject(Club club) {
        JSONObject clubJson = new JSONObject();
        clubJson.put("name", club.getName());
        clubJson.put("code", club.getCode());
        clubJson.put("stadium", club.getStadiumName());
        clubJson.put("logo", club.getLogo());
        clubJson.put("country", club.getCountry());
        clubJson.put("foundedYear", club.getFoundedYear());
        clubJson.put("players", playersToJsonArray(club.getPlayers()));
        clubJson.put("playerCount", club.getPlayers().length);

        return clubJson;
    }

    /**
     * Converts an array of IPlayer objects into a JSON array.
     *
     * @param players An array of players to convert.
     * @return A JSONArray containing all player data.
     */
    private JSONArray playersToJsonArray(IPlayer[] players) {
        JSONArray playersJson = new JSONArray();

        for (IPlayer player : players) {
            if (player == null) continue;
            playersJson.add(playerToJsonObject((Player) player));
        }
        return playersJson;
    }

    /**
     * Converts a Player object into a JSONObject.
     * <p>
     * Includes personal and performance attributes such as name, club, position,
     * age, number, shooting, passing, stamina, speed, height, weight, nationality,
     * preferred foot, photo, birth date, and club code.
     *
     * @param player The player to convert.
     * @return A JSONObject representing the player.
     */
    private JSONObject playerToJsonObject(Player player) {
        JSONObject playerJson = new JSONObject();
        playerJson.put("name", player.getName());
        playerJson.put("club", player.getClub());
        playerJson.put("position", player.getPosition().getDescription());
        playerJson.put("age", player.getAge());
        playerJson.put("number", player.getNumber());
        playerJson.put("shooting", player.getShooting());
        playerJson.put("passing", player.getPassing());
        playerJson.put("stamina", player.getStamina());
        playerJson.put("speed", player.getSpeed());
        playerJson.put("height", player.getHeight());
        playerJson.put("weight", player.getWeight());
        playerJson.put("nationality", player.getNationality());
        playerJson.put("preferredFoot", player.getPreferredFoot().getPreferredFoot());
        playerJson.put("photo", player.getPhoto());
        playerJson.put("birthDate", player.getBirthDate().toString());
        playerJson.put("clubCode", player.getClub());

        return playerJson;
    }

    /**
     * Converts an array of IEvent objects into a JSON array.
     * <p>
     * Each event is cast to a concrete Event type and processed individually.
     *
     * @param events An array of events to convert.
     * @return A JSONArray containing all event data.
     */
    public JSONArray eventsToJsonArray(IEvent[] events) {
        JSONArray eventsJson = new JSONArray();
        for (IEvent event : events) {
            if (event != null) {
                eventsJson.add(eventToJsonObject((Event) event));
            }
        }
        return eventsJson;
    }

    /**
     * Converts a single Event object into a JSONObject.
     * <p>
     * Identifies the event type, minute, and associated players (if applicable).
     * Handles different event subtypes such as PlayerEvent and SubstitutionEvent.
     *
     * @param event The event to convert.
     * @return A JSONObject representing the event details.
     */
    private JSONObject eventToJsonObject(Event event) {
        JSONObject eventJson = new JSONObject();
        eventJson.put("type", event.getClass().getSimpleName());
        eventJson.put("minute", event.getMinute());

        if (event instanceof PlayerEvent) {
            eventJson.put("player", playerToJsonObject((Player) ((PlayerEvent) event).getPlayer()));
        } else if (event instanceof SubstitutionEvent) {
            eventJson.put("player", playerToJsonObject((Player) ((SubstitutionEvent) event).getPlayerOut()));
            eventJson.put("player_in", playerToJsonObject((Player) ((SubstitutionEvent) event).getPlayerIn()));
        }

        return eventJson;
    }

    /**
     * Exports HTML reports for all loaded leagues, including individual seasons and clubs.
     * <p>
     * Generates output files in the directories:
     * <ul>
     *   <li>{@code output/html/seasons/} - for season reports</li>
     *   <li>{@code output/html/clubs/} - for club reports</li>
     * </ul>
     * If no leagues are loaded, the method will log a message and stop execution.
     * Errors during the generation process are logged individually.
     */
    public void exportHtmlReports() {
        ILeague[] leagues = Functions.getLeagues();

        if (leagues == null || leagues.length == 0) {
            System.out.println("There are no leagues available to export.");
            return;
        }

        File seasonDir = new File("output/html/seasons/");
        File clubDir = new File("output/html/clubs/");
        if (!seasonDir.exists()) seasonDir.mkdirs();
        if (!clubDir.exists()) clubDir.mkdirs();

        for (ILeague league : leagues) {
            if (league == null) continue;

            for (ISeason season : league.getSeasons()) {
                if (season == null) continue;

                String seasonPath = "output/html/seasons/" + season.getName().replace(" ", "_") + "_" + season.getYear() + ".html";
                try {
                    SeasonHtmlGenerator.generate(season, seasonPath);
                } catch (Exception e) {
                    System.out.println("Error generating HTML for the season: " + season.getName());
                    e.printStackTrace();
                }

                for (IClub club : season.getCurrentClubs()) {
                    if (club == null) continue;

                    String clubPath = "output/html/clubs/" + club.getName().replace(" ", "_") + ".html";
                    try {
                        ClubHtmlGenerator.generate(club, clubPath);
                    } catch (Exception e) {
                        System.out.println("Error generating HTML for club: " + club.getName());
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println("Exportation HTML concluded successfully.");
    }

}