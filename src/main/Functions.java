/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */
package main;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import data.Importer;
import event.*;
import league.League;
import league.Season;
import league.Standing;
import match.Match;
import player.Player;
import simulation.MatchSimulatorStrat;
import team.Club;
import team.Formation;
import team.Team;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class that provides various core functionalities for managing
 * leagues, seasons, matches, clubs, and players within the football management
 * system.
 * <p>
 * This class acts as a static manager, storing and manipulating global objects
 * such as leagues and facilitating actions such as league creation, match
 * simulation, and data retrieval.
 */
public class Functions {

    private static ILeague[] leagues = new ILeague[10];
    private static int leagueCount = 0;

    /**
     * Returns the internal array of leagues. This method provides access to the
     * leagues stored in the application.
     *
     * @return An array of ILeague objects representing the leagues.
     */
    public static ILeague[] getLeagues() {
        return leagues;
    }

    /**
     * Sets the internal array of leagues with the provided leagues. It replaces
     * the existing leagues in the array.
     *
     * @param leagues1 An array of ILeague objects to set.
     */
    public static void setLeagues(ILeague[] leagues1) {
        for (int i = 0; i < leagues1.length; i++) {
            if (leagues1[i] != null) {
                leagues[leagueCount++] = leagues1[i];
            }
        }
    }

    /**
     * Adds a league to the internal array of leagues. If the array is full, it
     * prints an error message.
     *
     * @param league The league to be added.
     */
    public static void addLeague(ILeague league) {
        if (leagueCount < leagues.length) {
            leagues[leagueCount++] = league;
        } else {
            System.out.println("League array is full. Cannot add more leagues.");
        }
    }

    /**
     * Loads a league from the internal array of leagues. It prompts the user to
     * select a league by number and returns the selected league.
     *
     * @param input The Scanner object for user input.
     * @return The selected League object, or null if no valid league is
     * selected.
     */
    public static League loadLeague(Scanner input) {
        for (int i = 0; i < leagues.length; i++) {

            if (leagues[i] != null) {
                System.out.println("League " + (i + 1) + ": " + leagues[i].getName());
            }

        }
        System.out.println("Enter the number of the league you want to load (1-" + leagueCount + "): ");
        int leagueNumber = 0;
        int maxTries = 5;
        int currentTry = 0;
        boolean validInput = false;
        while (!validInput) {
            if (currentTry >= maxTries) {
                System.out.println("Maximum attempts reached. Exiting...");
                return null;
            }
            try {
                leagueNumber = input.nextInt();
                if (leagueNumber < 1 || leagueNumber > leagueCount) {
                    System.out.println("Invalid league number. Please enter a number between 1 and " + leagueCount + ".");
                    currentTry++;
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                input.next();
            }
        }
        ILeague selectedLeague = leagues[leagueNumber - 1];
        return (League) selectedLeague;
    }

    /**
     * Creates a new season based on user input. It prompts the user for the
     * season name, year, maximum number of teams, and whether they want to be
     * the manager.
     *
     * @param input The Scanner object for user input.
     * @return A new Season object with the provided details.
     */
    public static Season createSeason(Scanner input) {
        System.out.println("");
        System.out.println("Enter the name of the season: ");
        String seasonName = input.next();

        int year = 0, maxTeams = 0;
        boolean isManager = false;
        boolean validInput = false;

        String managerInput = "";

        while (!validInput) {
            try {
                System.out.println("");
                System.out.println("Enter the year of the season: ");
                year = input.nextInt();

                System.out.println("");
                System.out.println("Enter the maximum number of teams for the season: ");
                maxTeams = input.nextInt();

                System.out.println("");
                System.out.println("Do you want to be the manager of this season? (Y/N): ");

                do {
                    managerInput = input.next();
                } while (!managerInput.equalsIgnoreCase("Y") && !managerInput.equalsIgnoreCase("N"));

                isManager = managerInput.equalsIgnoreCase("Y");

                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("");
                System.out.println("Invalid input. Please enter numeric values.");
                input.next();
            }
        }
        return new Season(seasonName, year, maxTeams, isManager);
    }

    /**
     * Loads a season from the specified league. It prompts the user to enter
     * the year of the season they want to load.
     *
     * @param input  The Scanner object for user input.
     * @param league The League object from which to load the season.
     * @return The selected ISeason object, or null if no valid season is found.
     */
    public static ISeason loadSeason(Scanner input, League league) {
        ISeason[] seasons = league.getSeasons();
        if (seasons.length == 0) {
            System.out.println("");
            System.out.println("No seasons available in this league.");
            return null;
        }
        System.out.println("");
        System.out.println("All seasons avaiable: ");
        for (ISeason season : seasons) {
            if (season == null) {
                continue;
            }
            System.out.println("");
            System.out.println("Season: " + season.getName() + " | Year: " + season.getYear());
        }

        int year = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("");
                System.out.println("Enter the year of the season: ");
                year = input.nextInt();

                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("");
                System.out.println("Invalid input. Please enter numeric values.");
                input.next();
            }
        }
        return league.getSeason(year);
    }

    /**
     * Adds all clubs from a JSON file to the specified season. It imports clubs
     * and adds them to the season, handling exceptions if the maximum number of
     * clubs is reached.
     *
     * @param season The Season object to which clubs will be added.
     */
    public static void addAllClubsToSeason(Season season) {
        Importer importer = new Importer();
        try {
            Club[] clubs = importer.importClubs("files/clubs.json");
            for (Club club : clubs) {
                if (club != null) {
                    season.addClub(club);
                }
            }
            System.out.println("");
            System.out.println("All clubs have been added to the season: " + season.getName());
        } catch (Exception e) {
            System.out.println("");
            System.out.println("Max Clubs reached.");
        }
    }

    /**
     * Removes all clubs from the specified season. It iterates through the
     * current clubs in the season and removes each one.
     *
     * @param season The Season object from which clubs will be removed.
     */
    public static void removeAllClubsToSeason(Season season) {
        IClub[] clubs = season.getCurrentClubs();
        if (clubs.length == 0) {
            System.out.println("");
            System.out.println("No clubs available in this season.");
            return;
        }

        for (IClub club : clubs) {
            if (club != null) {
                season.removeClub(club);
            }
        }
        System.out.println("");
        System.out.println("All clubs have been removed from the season: " + season.getName());
    }

    /**
     * Adds a club to the specified season based on user input. It imports clubs
     * from a JSON file and allows the user to select a club to add.
     *
     * @param input  The Scanner object for user input.
     * @param season The Season object to which the club will be added.
     */
    public static void addClub(Scanner input, Season season) {
        try {
            Importer importer = new Importer();
            Club[] clubs = importer.importClubs("files/clubs.json");

            System.out.println("\nView Available Clubs:");
            System.out.println("---------------------------------------");

            for (Club club : clubs) {
                if (club != null) {
                    System.out.printf("Name: %-40s | Country: %-15s | Stadium: %s%n",
                            club.getName(),
                            club.getCountry(),
                            club.getStadiumName());
                }
            }
            System.out.println("Enter the name or the code of the club you want to add: ");
            String stringClub = input.next();
            IClub addClub = null;
            for (IClub club : clubs) {
                if (club.getCode().equalsIgnoreCase(stringClub) || club.getName().equalsIgnoreCase(stringClub)) {
                    addClub = club;
                }
            }
            season.addClub(addClub);

            System.out.println("---------------------------------------");
        } catch (Exception error) {
            System.out.println("Error while adding club: " + error.getMessage());
        }
    }

    /**
     * Removes a club from the specified season based on user input. It lists
     * the current clubs in the season and allows the user to select a club to
     * remove.
     *
     * @param input  The Scanner object for user input.
     * @param season The Season object from which the club will be removed.
     */
    public static void removeClub(Scanner input, Season season) {
        try {
            IClub[] seasonClubs = season.getCurrentClubs();
            if (seasonClubs.length == 0) {
                System.out.println("No clubs available in this season.");
                return;
            }

            System.out.println("\nView Current Clubs in Season:");
            System.out.println("---------------------------------------");

            for (IClub club : seasonClubs) {
                if (club != null) {
                    System.out.printf("Name: %-40s | Country: %-15s | Stadium: %s%n",
                            club.getName(),
                            club.getCountry(),
                            club.getStadiumName());
                }
            }

            System.out.println("Enter the name or the code of the club you want to remove: ");
            String stringClub = input.next();
            IClub removeClub = null;
            for (IClub club : seasonClubs) {
                if (club.getCode().equalsIgnoreCase(stringClub) || club.getName().equalsIgnoreCase(stringClub)) {
                    removeClub = club;
                }
            }
            if (removeClub != null) {
                season.removeClub(removeClub);
                System.out.println("Club removed successfully.");
            } else {
                System.out.println("Club not found.");
            }
        } catch (Exception error) {
            System.out.println("Error while removing club: " + error.getMessage());
        }
    }

    /**
     * Starts a friendly match between two clubs in the specified season. It
     * prompts the user to select home and away clubs, sets their formations,
     * and simulates the match.
     *
     * @param input  The Scanner object for user input.
     * @param season The Season object in which the friendly match will be
     *               played.
     */
    public static void startFriendlyMatch(Scanner input, Season season) {
        IClub[] seasonClubs = season.getCurrentClubs();
        int clubCounter = 0;
        for (IClub club : seasonClubs) {
            if (club != null) {
                System.out.printf("Name: %-40s | Country: %-15s | Stadium: %s%n",
                        club.getName(),
                        club.getCountry(),
                        club.getStadiumName());
                clubCounter++;
            }
        }
        if (clubCounter < 2) {
            System.out.println("Not enough clubs to play a friendly match. Please add more clubs to the season.");
            return;
        }

        IClub homeClub = null;
        while (homeClub == null) {
            System.out.println("Enter the code or name of the home club: ");
            String stringHomeClub = input.next();
            for (IClub club : seasonClubs) {
                if (club != null && (club.getCode().equalsIgnoreCase(stringHomeClub) || club.getName().equalsIgnoreCase(stringHomeClub))) {
                    homeClub = club;
                    break;
                }
            }
            if (homeClub == null) {
                System.out.println("Invalid club. Please enter a valid home club code or name.");
            }
        }

        Team homeTeam = new Team(homeClub);
        homeTeam.setFormation(readFormation(input, "home"));
        homeTeam.setAutomaticTeam(homeClub.getPlayers(), (Formation) homeTeam.getFormation());

        IClub awayClub = null;
        while (awayClub == null || awayClub.equals(homeClub)) {
            System.out.println("Enter the code or name of the away club (different from home club): ");
            String stringAwayClub = input.next();
            for (IClub club : seasonClubs) {
                if (club != null && (club.getCode().equalsIgnoreCase(stringAwayClub) || club.getName().equalsIgnoreCase(stringAwayClub))) {
                    awayClub = club;
                    break;
                }
            }
            if (awayClub == null || awayClub.equals(homeClub)) {
                System.out.println("Invalid club. Please enter a valid and different away club.");
                awayClub = null;
            }
        }

        Team awayTeam = new Team(awayClub);
        awayTeam.setFormation(readFormation(input, "away"));
        awayTeam.setAutomaticTeam(awayClub.getPlayers(), (Formation) awayTeam.getFormation());

        IMatch friendlyMatch = new Match(homeClub, awayClub, 0);
        friendlyMatch.setTeam(homeTeam);
        friendlyMatch.setTeam(awayTeam);
        MatchSimulatorStrat matchSimulator = new MatchSimulatorStrat();
        matchSimulator.simulate(friendlyMatch);

        System.out.println("Friendly match between " + homeClub.getName() + " and " + awayClub.getName() + " has been played.");
        if (friendlyMatch.getWinner() == null) {
            System.out.println("The match ended in a draw.");
        } else {
            System.out.println("The match winner is: " + friendlyMatch.getWinner().getClub().getName());
        }
        System.out.println("Final Score: "
                + friendlyMatch.getHomeClub().getName() + " "
                + friendlyMatch.getTotalByEvent(GoalEvent.class, homeClub) + " - "
                + friendlyMatch.getTotalByEvent(GoalEvent.class, awayClub) + " "
                + friendlyMatch.getAwayClub().getName());
        System.out.println("Match Events:");
        for (IEvent gameEvent : friendlyMatch.getEvents()) {
            if (gameEvent != null) {
                System.out.println(gameEvent);
            }
        }
    }

    /**
     * Reads the formation details for a team from user input. It prompts the
     * user for the display name, number of defenders, midfielders, and
     * forwards, ensuring that the total number of outfield players is 10.
     *
     * @param input     The Scanner object for user input.
     * @param teamLabel The label for the team (e.g., "home" or "away").
     * @return A Formation object with the specified details.
     */
    private static Formation readFormation(Scanner input, String teamLabel) {
        String displayName;
        int defenders = -1, midfielders = -1, forwards = -1;
        final int TOTAL_OUTFIELD_PLAYERS = 10;

        System.out.println("Select the formation for the " + teamLabel + " team: ");
        System.out.print("Displayed formation: ");
        displayName = input.next();

        while (true) {
            try {
                System.out.print("How many Defenders: ");
                defenders = input.nextInt();
                System.out.print("How many Midfielders: ");
                midfielders = input.nextInt();
                System.out.print("How many Forwards: ");
                forwards = input.nextInt();

                if (defenders < 0 || midfielders < 0 || forwards < 0) {
                    System.out.println("Numbers cannot be negative. Try again.");
                    continue;
                }

                if ((defenders + midfielders + forwards) != TOTAL_OUTFIELD_PLAYERS) {
                    System.out.println("The total number of outfield players must be " + TOTAL_OUTFIELD_PLAYERS + ". Try again.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid integers.");
                input.nextLine();
            }
        }
        return new Formation(displayName, defenders, midfielders, forwards);
    }

    /**
     * Lists the current clubs in the specified season. It prints the name,
     * country, and stadium of each club in the season.
     *
     * @param season The Season object containing the clubs to list.
     */
    public static void listSeasonClubs(Season season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null.");
        }
        IClub[] clubs = season.getCurrentClubs();
        if (clubs.length == 0) {
            System.out.println("No clubs available in this season.");
            return;
        }

        System.out.println("Current Clubs in Season " + season.getName() + ":");
        System.out.println("=======================================");
        for (IClub club : clubs) {
            if (club != null) {
                System.out.printf("Name: %-40s | Country: %-15s | Stadium: %s%n",
                        club.getName(),
                        club.getCountry(),
                        club.getStadiumName());
            }
        }
        System.out.println("=======================================");
    }

    /**
     * Displays all available clubs in the system. It imports clubs from a JSON
     * file and prints their details.
     */
    public static void viewAllClubs() {
        System.out.println("=======================================");
        System.out.println("PPFootballManager v1.0 - Season 24/25");
        System.out.println("=======================================");

        try {
            Importer importer = new Importer();
            Club[] clubs = importer.importClubs("files/clubs.json");

            System.out.println("\nView Available Clubs:");
            System.out.println("====================================================================================================================================");

            for (Club club : clubs) {
                if (club != null) {
                    System.out.printf("Name: %-40s | Country: %-15s | Stadium: %s%n",
                            club.getName(),
                            club.getCountry(),
                            club.getStadiumName());
                }
            }

            System.out.println("====================================================================================================================================");
        } catch (IOException error) {
            System.out.println("Error while loading the clubs: " + error.getMessage());
        }
    }

    /**
     * Views the details of a specific club based on user input. It lists all
     * clubs and allows the user to enter a club code or name to view its
     * details.
     *
     * @throws IOException If an error occurs while importing clubs.
     */
    public static void viewClub() throws IOException {
        int option;
        Scanner input = new Scanner(System.in);

        Importer importer = new Importer();
        Club[] clubs = importer.importClubs("files/clubs.json");

        System.out.println("=======================================");
        System.out.println(" PPFootballManager v1.0 - Season 24/25 ");
        System.out.println("=======================================");

        for (Club club : clubs) {
            System.out.println("Code: " + club.getCode() + " | Name: " + club.getName());
        }
        System.out.println("======================================================");
        do {
            System.out.println("");
            System.out.println("Enter the code of the club you want to view:");
            String clubCode = input.nextLine();
            System.out.println("");
            boolean found = false;
            for (Club club : clubs) {
                if (club.getCode().equalsIgnoreCase(clubCode) || club.getName().equalsIgnoreCase(clubCode)) {
                    System.out.println("Club Details:");
                    System.out.println("Name: " + club.getName());
                    System.out.println("Country: " + club.getCountry());
                    System.out.println("Stadium: " + club.getStadiumName());
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Club not found!");
            }

            System.out.println("\n1- Do you want to view another club?");
            System.out.println("0 - Back to the previous menu");
            System.out.println("Enter your option:");

            while (!input.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
            option = input.nextInt();
            input.nextLine();

            if (option == 0) {
                return;
            }

            if (option != 1) {
                System.out.println("Select a valid option!");
            }
        } while (option == 1);
    }

    /**
     * Views all players from a specific club based on user input. It lists
     * available clubs and allows the user to enter a club code to view its
     * players.
     */
    public static void viewAllPlayers() {
        Scanner input = new Scanner(System.in);
        System.out.println("=======================================");
        System.out.println("PPFootballManager v1.0 - Season 24/25");
        System.out.println("=======================================");

        try {
            Importer importer = new Importer();
            Club[] clubs = importer.importClubs("files/clubs.json");

            System.out.println("\nAvailable Clubs:");
            System.out.println("---------------------------------------");
            for (Club club : clubs) {
                System.out.println("Code: " + club.getCode() + " | Name: " + club.getName());
            }

            System.out.println("\nEnter your club code to see your players:");
            String clubCode = input.nextLine().toUpperCase();

            boolean found = false;
            for (Club club : clubs) {
                if (club.getCode().equalsIgnoreCase(clubCode)) {
                    found = true;
                    Player[] players = importer.importPlayers("./files/players/" + club.getCode() + ".json");
                    club.setPlayers(players);

                    System.out.println("\nPlayers From " + club.getName() + ":");
                    System.out.println("---------------------------------------");
                    for (IPlayer player : club.getPlayers()) {
                        if (player != null) {
                            System.out.printf("Name: %-20s | Position: %-12s | Number: %d%n",
                                    player.getName(),
                                    player.getPosition().getDescription(),
                                    player.getNumber());
                        }
                    }
                    System.out.println("---------------------------------------");
                    break;
                }
            }

            if (!found) {
                System.out.println("Club not found!");
            }

        } catch (IOException error) {
            error.printStackTrace();
            System.out.println("Error while loading data: " + error.getMessage());
        }
    }

    /**
     * Views the details of a specific player from a club based on user input.
     * It lists available clubs and allows the user to enter a club code to view
     * its players and their details.
     */
    public static void viewPlayer() {
        int option;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("=======================================");
            System.out.println("PPFootballManager v1.0 - Season 24/25");
            System.out.println("=======================================");
            System.out.println("\nAvailable Clubs:");
            System.out.println("---------------------------------------");

            try {
                Importer importer = new Importer();
                Club[] clubs = importer.importClubs("files/clubs.json");

                for (Club club : clubs) {
                    System.out.println("Code: " + club.getCode() + " | Name: " + club.getName());
                }

                System.out.println("\nEnter club code to view players:");
                String clubCode = input.nextLine().toUpperCase();

                boolean found = false;
                for (Club club : clubs) {
                    if (club.getCode().equalsIgnoreCase(clubCode)) {
                        found = true;
                        try {
                            Player[] players = importer.importPlayers("./files/players/" + club.getCode() + ".json");
                            club.setPlayers(players);

                            System.out.println("\nPlayers from " + club.getName() + ":");
                            System.out.println("---------------------------------------");
                            for (IPlayer player : club.getPlayers()) {
                                if (player != null) {
                                    System.out.printf("Name: %-20s | Number: %d%n",
                                            player.getName(),
                                            player.getNumber());
                                }
                            }

                            System.out.println("\nEnter player number to see details (0 to skip):");
                            int playerNumber = input.nextInt();
                            input.nextLine();

                            if (playerNumber != 0) {
                                boolean playerFound = false;
                                for (IPlayer player : club.getPlayers()) {
                                    if (player != null && player.getNumber() == playerNumber) {
                                        playerFound = true;
                                        System.out.println("\nPlayer Details:");
                                        System.out.println("---------------------------------------");
                                        System.out.println("Name: " + player.getName());
                                        System.out.println("Number: " + player.getNumber());
                                        System.out.println("Position: " + player.getPosition().getDescription());
                                        System.out.println("Age: " + player.getAge());
                                        System.out.println("Nationality: " + player.getNationality());
                                        System.out.println("Height: " + String.format("%.2f", player.getHeight()) + "m");
                                        System.out.println("Weight: " + String.format("%.2f", player.getWeight()) + "kg");
                                        System.out.println("Preferred Foot: " + player.getPreferredFoot());
                                        System.out.println("\nAttributes:");
                                        System.out.println("Shooting: " + player.getShooting());
                                        System.out.println("Passing: " + player.getPassing());
                                        System.out.println("Speed: " + player.getSpeed());
                                        System.out.println("Stamina: " + player.getStamina());
                                        System.out.println("---------------------------------------");
                                        break;
                                    }
                                }
                                if (!playerFound) {
                                    System.out.println("Player not found!");
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("Error loading players: " + e.getMessage());
                        }
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Club not found!");
                }
            } catch (IOException error) {
                System.out.println("Error while loading players: " + error.getMessage());
            }

            System.out.println("\n1 - See another player status");
            System.out.println("0 - Return to previous menu");
            System.out.print("Enter your option: ");

            while (!input.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
            option = input.nextInt();
            input.nextLine();

            if (option == 0) {
                return;
            }

            if (option != 1) {
                System.out.println("Invalid Option!");
            }
        } while (option == 1);
    }

    /**
     * Starts the season by generating the schedule and simulating matches. It
     * lists available clubs, generates the schedule, simulates matches, and
     * prints final standings.
     *
     * @param input  The Scanner object for user input.
     * @param season The Season object to start.
     */
    public static void startSeason(Scanner input, Season season) {

        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null.");
        }

        IClub[] seasonClubs = season.getCurrentClubs();
        if (seasonClubs == null || seasonClubs.length < 2) {
            System.out.println("=== Not enough clubs to start the season. Please add more clubs. ===");
            return;
        }

        // Listar clubes disponíveis
        System.out.println("\n=== AVAILABLE CLUBS IN THE SEASON ===");
        for (IClub club : seasonClubs) {
            if (club != null) {
                System.out.printf("Name: %-40s | Code: %-10s | Country: %-15s | Stadium: %s%n",
                        club.getName(), club.getCode(), club.getCountry(), club.getStadiumName());
            }
        }

        System.out.println("\n=== STARTING SEASON: " + season.getName().toUpperCase() + " (" + season.getYear() + ") ===");
        season.generateSchedule();

        season.printSchedule();

        IMatch[] matches = season.getMatches();
        if (matches == null || matches.length == 0) {
            System.out.println("=== No matches scheduled for this season. ===");
            return;
        }

        System.out.println("\n=== SEASON SCHEDULE GENERATED: " + season.getMaxRounds() + " rounds ===\n");

        MatchSimulatorStrat matchSimulator = new MatchSimulatorStrat();

        for (IMatch match : matches) {
            if (match == null) {
                continue;
            }

            Team homeTeam = new Team(match.getHomeClub());
            homeTeam.setFormation(generateRandomFormation());
            homeTeam.setAutomaticTeam(homeTeam.getClub().getPlayers(), (Formation) homeTeam.getFormation());
            match.setTeam(homeTeam);

            Team awayTeam = new Team(match.getAwayClub());
            awayTeam.setFormation(generateRandomFormation());
            awayTeam.setAutomaticTeam(awayTeam.getClub().getPlayers(), (Formation) awayTeam.getFormation());
            match.setTeam(awayTeam);

            // Simular jogo
            matchSimulator.simulate(match);
            System.out.println("Match: " + match.getHomeClub().getName() + " vs " + match.getAwayClub().getName());
            System.out.println("Final Score: "
                    + match.getHomeClub().getName() + " "
                    + match.getTotalByEvent(GoalEvent.class, match.getHomeClub()) + " - "
                    + match.getTotalByEvent(GoalEvent.class, match.getAwayClub()) + " "
                    + match.getAwayClub().getName() + "\n");

            // Atualizar classificações
            for (IStanding standing : season.getLeagueStandings()) {
                if (standing != null) {
                    ((Standing) standing).updateStandings(match);
                }
            }
        }

        // Ordenar classificações
        IStanding[] standings = season.getLeagueStandings();

        for (int i = 0; i < standings.length - 1; i++) {
            for (int j = 0; j < standings.length - i - 1; j++) {
                IStanding a = standings[j];
                IStanding b = standings[j + 1];

                if (a == null || b == null) {
                    continue;
                }

                int pointsDiff = b.getPoints() - a.getPoints();
                int goalDiffA = a.getGoalScored() - a.getGoalsConceded();
                int goalDiffB = b.getGoalScored() - b.getGoalsConceded();

                if (pointsDiff > 0
                        || (pointsDiff == 0 && goalDiffB > goalDiffA)
                        || (pointsDiff == 0 && goalDiffB == goalDiffA && b.getGoalScored() > a.getGoalScored())) {

                    standings[j] = b;
                    standings[j + 1] = a;
                }
            }
        }

        System.out.println("\n================================= FINAL STANDINGS =====================================");
        System.out.printf("%-3s | %-40s | PTS | PJ | W  | D  | L  | GM | GS | GD%n", "#", "Club");
        System.out.println("=======================================================================================");

        for (int i = 0; i < standings.length; i++) {
            IStanding s = standings[i];
            if (s != null) {
                int played = s.getWins() + s.getDraws() + s.getLosses();
                int gd = s.getGoalScored() - s.getGoalsConceded();

                System.out.printf("%-3d | %-40s | %-3d | %-2d | %-2d | %-2d | %-2d | %-2d | %-2d | %+3d%n",
                        i + 1,
                        s.getTeam().getClub().getName(),
                        s.getPoints(),
                        played,
                        s.getWins(),
                        s.getDraws(),
                        s.getLosses(),
                        s.getGoalScored(),
                        s.getGoalsConceded(),
                        gd);
            }
        }

        System.out.println("\n=== SEASON " + season.getName().toUpperCase() + " HAS CONCLUDED ===");
        System.out.println("Thank you for playing!");

    }

    /**
     * Generates a random football formation. It randomly selects a formation
     * from a predefined list and returns a Formation object.
     *
     * @return A Formation object with a random formation.
     */
    public static Formation generateRandomFormation() {
        String[] formations = {"4-4-2", "4-3-3", "3-5-2", "5-3-2", "4-2-3-1", "3-4-3"};
        int index = (int) (Math.random() * formations.length);
        String formationStr = formations[index];

        String[] parts = formationStr.split("-");

        int defenders = Integer.parseInt(parts[0]);
        int midfielders;
        int forwards;

        if (parts.length == 3) {
            midfielders = Integer.parseInt(parts[1]);
            forwards = Integer.parseInt(parts[2]);
        } else if (parts.length == 4) {
            midfielders = Integer.parseInt(parts[1]) + Integer.parseInt(parts[2]);
            forwards = Integer.parseInt(parts[3]);
        } else {
            return new Formation("4-4-2", 4, 4, 2);
        }

        return new Formation(formationStr, defenders, midfielders, forwards);
    }

    /**
     * Views the standings of a specific season. It sorts the standings based on
     * points and prints the details of each club in the standings.
     *
     * @param season The Season object containing the standings to view.
     */
    public static void viewSeasonStandings(Season season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null.");
        }

        IStanding[] standings = season.getLeagueStandings();
        if (standings.length == 0) {
            System.out.println("No standings available for this season.");
            return;
        }

        for (int i = 0; i < standings.length - 1; i++) {
            for (int j = 0; j < standings.length - i - 1; j++) {
                if (standings[j] != null && standings[j + 1] != null
                        && standings[j].getPoints() < standings[j + 1].getPoints()) {
                    IStanding temp = standings[j];
                    standings[j] = standings[j + 1];
                    standings[j + 1] = temp;
                }
            }
        }

        System.out.println("\n========================== SEASON STANDINGS: " + season.getName().toUpperCase() + " =================================\n");

        System.out.printf("%-3s | %-35s | PTS | PJ |  W |  D |  L | GM | GS | GD%n", "#", "Club");
        System.out.println("==================================================================================");

        for (int i = 0; i < standings.length; i++) {
            IStanding standing = standings[i];
            if (standing != null) {
                int played = standing.getWins() + standing.getDraws() + standing.getLosses();
                int goalDifference = standing.getGoalScored() - standing.getGoalsConceded();

                System.out.printf("%-3d | %-35s | %-3d | %-2d | %-2d | %-2d | %-2d | %-2d | %-2d | %+3d%n",
                        i + 1,
                        standing.getTeam().getClub().getName(),
                        standing.getPoints(),
                        played,
                        standing.getWins(),
                        standing.getDraws(),
                        standing.getLosses(),
                        standing.getGoalScored(),
                        standing.getGoalsConceded(),
                        goalDifference);
            }
        }

        System.out.println("==================================================================================\n");
    }

    /**
     * Lists events from a specific match in the given season. It allows the
     * user to select a match and view events of a specific type or all events.
     *
     * @param input  The Scanner object for user input.
     * @param season The Season object containing the matches.
     */
    public static void ListEventFromAMatch(Scanner input, Season season) {
        if (input == null || season == null) {
            throw new IllegalArgumentException("Input or Season cannot be null.");
        }

        IMatch[] matches = season.getMatches();
        if (matches == null || matches.length == 0) {
            System.out.println("No matches available in this season.");
            return;
        }

        System.out.println("\n=== AVAILABLE MATCHES ===");
        for (int i = 0; i < matches.length; i++) {
            IMatch m = matches[i];
            if (m != null) {
                System.out.printf("[%02d] %-20s vs %-20s (Round %d)%n",
                        i, m.getHomeClub().getName(), m.getAwayClub().getName(), m.getRound());
            }
        }
        System.out.println("=========================");

        // Escolher jogo
        int selectedIndex = -1;
        while (selectedIndex < 0 || selectedIndex >= matches.length) {
            System.out.print("Select a match by index: ");
            if (input.hasNextInt()) {
                selectedIndex = input.nextInt();
            } else {
                System.out.println("Invalid input.");
                input.next();
            }
        }

        IMatch selectedMatch = matches[selectedIndex];

        // Menu de eventos
        System.out.println("\n=== CHOOSE EVENT TYPE TO VIEW ===");
        System.out.println("[00] All Events");
        System.out.println("[01] Corner Kicks       [02] Free Kicks         [03] Fouls");
        System.out.println("[04] Goals              [05] Injuries           [06] Offsides");
        System.out.println("[07] Penalties          [08] Red Cards          [9] Shots");
        System.out.println("[10] Shots on Goal      [11] Substitutions      [2] Yellow Cards");
        System.out.println("================================");

        int option = -1;
        while (option < 0 || option > 13) {
            System.out.print("Enter your choice (0-13): ");
            if (input.hasNextInt()) {
                option = input.nextInt();
            } else {
                System.out.println("Invalid input.");
                input.next();
            }
        }

        Class selectedClass = null;
        switch (option) {
            case 1:
                selectedClass = CornerKickEvent.class;
                break;
            case 2:
                selectedClass = FreeKickEvent.class;
                break;
            case 3:
                selectedClass = FoulEvent.class;
                break;
            case 4:
                selectedClass = GoalEvent.class;
                break;
            case 5:
                selectedClass = InjuryEvent.class;
                break;
            case 6:
                selectedClass = OffSideEvent.class;
                break;
            case 7:
                selectedClass = PenaltiesEvent.class;
                break;
            case 8:
                selectedClass = RedCardEvent.class;
                break;
            case 9:
                selectedClass = ShotEvent.class;
                break;
            case 10:
                selectedClass = ShotOnGoalEvent.class;
                break;
            case 11:
                selectedClass = SubstitutionEvent.class;
                break;
            case 12:
                selectedClass = YellowCardEvent.class;
                break;
        }

        System.out.println("\n=== EVENTS FOR SELECTED MATCH ===");
        System.out.printf("%s vs %s (Round %d)%n",
                selectedMatch.getHomeClub().getName(),
                selectedMatch.getAwayClub().getName(),
                selectedMatch.getRound());
        System.out.println("=================================");

        boolean found = false;
        for (IEvent e : selectedMatch.getEvents()) {
            if (e != null && (option == 0 || e.getClass().equals(selectedClass))) {
                System.out.println("- " + e);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No events of that type were found.");
        }

        // Mostrar resultado
        System.out.println("\n=== MATCH SUMMARY ===");

        System.out.printf("Goals by Home Team (%s): %d%n",
                selectedMatch.getHomeClub().getName(),
                selectedMatch.getTotalByEvent(GoalEvent.class, selectedMatch.getHomeClub()));

        System.out.printf("Goals by Away Team (%s): %d%n",
                selectedMatch.getAwayClub().getName(),
                selectedMatch.getTotalByEvent(GoalEvent.class, selectedMatch.getAwayClub()));

        if (selectedMatch.getWinner() != null) {
            System.out.println("Match Winner: " + selectedMatch.getWinner().getClub().getName());
        } else {
            System.out.println("The match ended in a draw.");
        }

        System.out.println("======================\n");
    }

    /**
     * Resets the current season by calling the resetSeason method on the Season
     * object. It prints a confirmation message after resetting the season.
     *
     * @param season The Season object to reset.
     */
    public static void ResetSeason(Season season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null.");
        }
        season.resetSeason();
        System.out.println("Season " + season.getName() + " has been reset successfully.");
    }

    /**
     * Prompts the user to select a player from a club and retrieves their
     * statistics. It lists all clubs in the season, allows the user to select a
     * club, and then select a player by number.
     *
     * @param input  The Scanner object for user input.
     * @param season The Season object containing the clubs and players.
     */
    public static void inputPlayerToGetStats(Scanner input, Season season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null");
        }
        int counter = 0;
        for (IClub club : season.getCurrentClubs()) {
            if (club != null) {
                counter++;
                System.out.println("Club: " + club.getName());
            }
        }
        if (counter == 0) {
            System.out.println("There are no club's in the league!");
            return;
        }
        System.out.println("Choose the player club that you want to check stats:");
        input.nextLine();
        String selectedClub = input.nextLine();
        System.out.println("Enter the number of the player you want to get stats for:");
        for (IClub club : season.getCurrentClubs()) {
            if (club != null && club.getName().equalsIgnoreCase(selectedClub) || club.getCode().equalsIgnoreCase(selectedClub)) {
                for (IPlayer player : club.getPlayers()) {
                    if (player != null) {
                        System.out.println("Player: " + player.getName() + " | Number: " + player.getNumber());
                    }
                }
            }
        }
        int selectedPlayer = input.nextInt();
        for (IClub club : season.getCurrentClubs()) {
            if (club != null && club.getName().equalsIgnoreCase(selectedClub) || club.getCode().equalsIgnoreCase(selectedClub)) {
                for (IPlayer player : club.getPlayers()) {
                    if (player != null && player.getNumber() == selectedPlayer) {
                        season.getPlayerStatistics(player);
                    }
                }
            }
        }
    }

    /**
     * Views all player statistics for each club in the specified season. It
     * iterates through all clubs and players, printing their statistics.
     *
     * @param season The Season object containing the clubs and players.
     */
    public static void viewAllPlayerStats(Season season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null.");
        }
        int count = 0;
        for (IClub club : season.getCurrentClubs()) {
            if (club != null) {
                System.out.println("");
                System.out.println("=======================================");
                System.out.println("=======================================");
                System.out.println("Club: " + club.getName());
                System.out.println("");
                count++;
                for (IPlayer player : club.getPlayers()) {
                    if (player != null) {
                        System.out.println("");
                        season.getPlayerStatistics(player);
                    }
                }
            }
        }
        if (count == 0) {
            System.out.println("There are no players in the league!");
        }
    }

    /**
     * Starts the season manager by allowing the user to select a club and set
     * up their team. It prompts the user to choose a club, sets up the team
     * formation, and allows player selection.
     *
     * @param input  The Scanner object for user input.
     * @param season The Season object containing the clubs.
     */
    public static void startSeasonManager(Scanner input, Season season) {

        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null.");
        }

        IClub[] seasonClubs = season.getCurrentClubs();
        if (seasonClubs == null || seasonClubs.length < 2) {
            System.out.println("=== Not enough clubs to start the season. Please add more clubs. ===");
            return;
        }

        // Listar clubes disponíveis
        System.out.println("\n======================== AVAILABLE CLUBS IN THE SEASON ========================");
        for (IClub club : seasonClubs) {
            if (club != null) {
                System.out.printf("Name: %-40s | Code: %-10s | Country: %-15s | Stadium: %s%n",
                        club.getName(), club.getCode(), club.getCountry(), club.getStadiumName());
            }
        }

        // Escolher clube para gerir
        IClub selectedClub = null;
        while (selectedClub == null) {
            System.out.println("\n======================== Choose your club ========================");
            System.out.print("Enter the code or name of the club you want to manage this season: ");
            String inputClub = input.next();
            for (IClub club : seasonClubs) {
                if (club != null && (club.getCode().equalsIgnoreCase(inputClub) || club.getName().equalsIgnoreCase(inputClub))) {
                    selectedClub = club;
                    break;
                }
            }
            if (selectedClub == null) {
                System.out.println("Invalid club. Please try again.");
            }
        }

        // Criar equipa do utilizador com formação manual
        Team userTeam = new Team(selectedClub);
        userTeam.setFormation(readFormation(input, "your"));

        System.out.println("Enter the numbers of the 11 players you want to use for this formation:");

        IPlayer[] availablePlayers = selectedClub.getPlayers();
        for (IPlayer p : availablePlayers) {
            if (p != null) {
                System.out.printf("Name: %-20s | Number: %d | Position: %s%n",
                        p.getName(), p.getNumber(), p.getPosition().getDescription());
            }
        }

        IPlayer[] selectedPlayers = new IPlayer[11];
        int[] selectedNumbers = new int[11];
        int count = 0;

        int gkCount = 0, defCount = 0, midCount = 0, fwdCount = 0;

        int numDefs = ((Formation) userTeam.getFormation()).getDefenders();
        int numMids = ((Formation) userTeam.getFormation()).getMidfielders();
        int numFwds = ((Formation) userTeam.getFormation()).getForwards();
        int numGKs = 1;

        while (count < 11) {
            System.out.print("Enter player number #" + (count + 1) + ": ");
            int number = input.nextInt();

            // Verifica se o número já foi usado
            boolean alreadySelected = false;
            for (int i = 0; i < count; i++) {
                if (selectedNumbers[i] == number) {
                    alreadySelected = true;
                    break;
                }
            }
            if (alreadySelected) {
                System.out.println("This player has already been selected. Choose another.");
                continue;
            }

            boolean found = false;

            for (IPlayer p : availablePlayers) {
                if (p != null && p.getNumber() == number) {
                    String pos = p.getPosition().getDescription().toLowerCase();

                    if (pos.contains("goalkeeper")) {
                        if (gkCount < numGKs) {
                            selectedPlayers[count] = p;
                            selectedNumbers[count] = number;
                            gkCount++;
                            count++;
                            found = true;
                        } else {
                            System.out.println("Only one goalkeeper allowed.");
                        }
                    } else if (pos.contains("defender")) {
                        if (defCount < numDefs) {
                            selectedPlayers[count] = p;
                            selectedNumbers[count] = number;
                            defCount++;
                            count++;
                            found = true;
                        } else {
                            System.out.println("Defender limit reached (" + numDefs + ").");
                        }
                    } else if (pos.contains("midfielder")) {
                        if (midCount < numMids) {
                            selectedPlayers[count] = p;
                            selectedNumbers[count] = number;
                            midCount++;
                            count++;
                            found = true;
                        } else {
                            System.out.println("Midfielder limit reached (" + numMids + ").");
                        }
                    } else if (pos.contains("forward")) {
                        if (fwdCount < numFwds) {
                            selectedPlayers[count] = p;
                            selectedNumbers[count] = number;
                            fwdCount++;
                            count++;
                            found = true;
                        } else {
                            System.out.println("Forward limit reached (" + numFwds + ").");
                        }
                    } else {
                        System.out.println("Unknown position: " + pos);
                    }

                    break;
                }
            }

            if (!found) {
                System.out.println("Player not found, invalid number, or position quota full. Try again.");
            }
        }

        userTeam.setManualTeam(selectedPlayers);

        System.out.println("\n=== STARTING SEASON: " + season.getName().toUpperCase() + " (" + season.getYear() + ") ===");

        season.generateSchedule();
        season.printSchedule();

        MatchSimulatorStrat matchSimulator = new MatchSimulatorStrat();

        for (int i = 0; i < season.getMaxRounds(); i++) {

            int choice;
            while (true) {
                System.out.println("\n=== MANAGER MENU BEFORE ROUND " + (i + 1) + " ===");
                System.out.println("1 - Change formation");
                System.out.println("2 - View current standings");
                System.out.println("0 - Continue to matches");
                System.out.print("Select an option: ");
                if (input.hasNextInt()) {
                    choice = input.nextInt();
                    input.nextLine();
                    if (choice == 1) {
                        Formation newFormation = readFormation(input, "your (new)");
                        userTeam.setFormation(newFormation);

                        System.out.println("Reselect your new 11 players: ");
                        IPlayer[] availablePlayers1 = selectedClub.getPlayers();
                        for (IPlayer p : availablePlayers1) {
                            if (p != null) {
                                System.out.printf("Name: %-20s | Number: %d | Position: %s%n",
                                        p.getName(), p.getNumber(), p.getPosition().getDescription());
                            }
                        }

                        IPlayer[] selectedPlayers1 = new IPlayer[11];
                        int[] selectedNumbers1 = new int[11];
                        int count1 = 0;

                        int gkCount1 = 0, defCount1 = 0, midCount1 = 0, fwdCount1 = 0;

                        int numDefs1 = ((Formation) userTeam.getFormation()).getDefenders();
                        int numMids1 = ((Formation) userTeam.getFormation()).getMidfielders();
                        int numFwds1 = ((Formation) userTeam.getFormation()).getForwards();
                        int numGKs1 = 1;

                        while (count1 < 11) {
                            System.out.print("Enter player number #" + (count1 + 1) + ": ");
                            int number = input.nextInt();

                            boolean alreadySelected = false;
                            for (int n = 0; n < count1; n++) {
                                if (selectedNumbers1[n] == number) {
                                    alreadySelected = true;
                                    break;
                                }
                            }
                            if (alreadySelected) {
                                System.out.println("This player has already been selected. Choose another.");
                                continue;
                            }

                            boolean found = false;
                            for (IPlayer p : availablePlayers1) {
                                if (p != null && p.getNumber() == number) {
                                    String pos = p.getPosition().getDescription().toLowerCase();

                                    if (pos.contains("goalkeeper")) {
                                        if (gkCount1 < numGKs1) {
                                            selectedPlayers1[count1] = p;
                                            selectedNumbers1[count1] = number;
                                            gkCount1++;
                                            count1++;
                                            found = true;
                                        } else {
                                            System.out.println("Only one goalkeeper allowed.");
                                        }
                                    } else if (pos.contains("defender")) {
                                        if (defCount1 < numDefs1) {
                                            selectedPlayers1[count1] = p;
                                            selectedNumbers1[count1] = number;
                                            defCount1++;
                                            count1++;
                                            found = true;
                                        } else {
                                            System.out.println("Defender limit reached (" + numDefs1 + ").");
                                        }
                                    } else if (pos.contains("midfielder")) {
                                        if (midCount1 < numMids1) {
                                            selectedPlayers1[count1] = p;
                                            selectedNumbers1[count1] = number;
                                            midCount1++;
                                            count1++;
                                            found = true;
                                        } else {
                                            System.out.println("Midfielder limit reached (" + numMids1 + ").");
                                        }
                                    } else if (pos.contains("forward")) {
                                        if (fwdCount1 < numFwds1) {
                                            selectedPlayers1[count1] = p;
                                            selectedNumbers1[count1] = number;
                                            fwdCount1++;
                                            count1++;
                                            found = true;
                                        } else {
                                            System.out.println("Forward limit reached (" + numFwds1 + ").");
                                        }
                                    } else {
                                        System.out.println("Unknown position: " + pos);
                                    }

                                    break;
                                }
                            }

                            if (!found) {
                                System.out.println("Player not found, invalid number, or position quota full. Try again.");
                            }
                        }

                        userTeam.setManualTeam(selectedPlayers1);
                    }

                    if (choice == 2) {
                        viewSeasonStandings(season);
                    } else if (choice == 0) {
                        break;
                    } else {
                        System.out.println("Invalid choice.");
                    }
                } else {
                    System.out.println("Invalid input.");
                    input.next();
                }
            }

            System.out.println("Round " + (i + 1) + ":");
            for (IMatch match : season.getMatches(i)) {
                if (match == null) {
                    continue;
                }

                // Equipa da casa
                if (match.getHomeClub().equals(selectedClub)) {
                    match.setTeam(userTeam);
                } else {
                    Team autoTeam = new Team(match.getHomeClub());
                    autoTeam.setFormation(generateRandomFormation());
                    autoTeam.setAutomaticTeam(autoTeam.getClub().getPlayers(), (Formation) autoTeam.getFormation());
                    match.setTeam(autoTeam);
                }

                // Equipa visitante
                if (match.getAwayClub().equals(selectedClub)) {
                    match.setTeam(userTeam);
                } else {
                    Team autoTeam = new Team(match.getAwayClub());
                    autoTeam.setFormation(generateRandomFormation());
                    autoTeam.setAutomaticTeam(autoTeam.getClub().getPlayers(), (Formation) autoTeam.getFormation());
                    match.setTeam(autoTeam);
                }

                // Simular jogo
                matchSimulator.simulate(match);
                System.out.println("Match: " + match.getHomeClub().getName() + " vs " + match.getAwayClub().getName());
                System.out.println("Final Score: "
                        + match.getHomeClub().getName() + " "
                        + match.getTotalByEvent(GoalEvent.class, match.getHomeClub()) + " - "
                        + match.getTotalByEvent(GoalEvent.class, match.getAwayClub()) + " "
                        + match.getAwayClub().getName() + "\n");

                // Atualizar classificações
                for (IStanding standing : season.getLeagueStandings()) {
                    if (standing != null) {
                        ((Standing) standing).updateStandings(match);
                    }
                }
            }
        }

        // Ordenar classificações
        IStanding[] standings = season.getLeagueStandings();

        for (int i = 0; i < standings.length - 1; i++) {
            for (int j = 0; j < standings.length - i - 1; j++) {
                IStanding a = standings[j];
                IStanding b = standings[j + 1];

                if (a == null || b == null) {
                    continue;
                }

                int pointsDiff = b.getPoints() - a.getPoints();
                int goalDiffA = a.getGoalScored() - a.getGoalsConceded();
                int goalDiffB = b.getGoalScored() - b.getGoalsConceded();

                if (pointsDiff > 0
                        || (pointsDiff == 0 && goalDiffB > goalDiffA)
                        || (pointsDiff == 0 && goalDiffB == goalDiffA && b.getGoalScored() > a.getGoalScored())) {

                    standings[j] = b;
                    standings[j + 1] = a;
                }
            }
        }

        System.out.println("\n================================= FINAL STANDINGS =====================================");
        System.out.printf("%-3s | %-40s | PTS | PJ | W  | D  | L  | GM | GS | GD%n", "#", "Club");
        System.out.println("=======================================================================================");

        for (int i = 0; i < standings.length; i++) {
            IStanding s = standings[i];
            if (s != null) {
                int played = s.getWins() + s.getDraws() + s.getLosses();
                int gd = s.getGoalScored() - s.getGoalsConceded();

                System.out.printf("%-3d | %-40s | %-3d | %-2d | %-2d | %-2d | %-2d | %-2d | %-2d | %+3d%n",
                        i + 1,
                        s.getTeam().getClub().getName(),
                        s.getPoints(),
                        played,
                        s.getWins(),
                        s.getDraws(),
                        s.getLosses(),
                        s.getGoalScored(),
                        s.getGoalsConceded(),
                        gd);
            }
        }

        System.out.println("\n=== SEASON " + season.getName().toUpperCase() + " HAS CONCLUDED ===");
        System.out.println("Thank you for playing!");

    }

    public static Season createSeasonFromExisting(Scanner input, League league) {
        System.out.println("\nSelect the base season to copy clubs from:");
        ISeason currentSeason = loadSeason(input, league);

        if (currentSeason == null) {
            System.out.println("No season selected.");
            return null;
        }

        System.out.println("\nConfiguring new season:");
        Season season = createSeason(input);

        if (season == null) {
            System.out.println("Failed to create new season.");
            return null;
        }

        IClub[] existingClubs = currentSeason.getCurrentClubs();
        for (IClub club : existingClubs) {
            try {
                season.addClub(club);
            } catch (IllegalArgumentException error) {
                System.out.println("Error adding club " + club.getName() + ": " + error.getMessage());
            }
        }

        System.out.println("\nNew season created successfully!");
        System.out.println("Clubs copied: " + season.getNumberOfCurrentTeams());


        return season;
    }

}
