package main;

import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import league.League;
import league.Season;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class providing static methods to display and manage user menus
 * for the Football Manager application.
 * <p>
 * This class contains methods for displaying the main menu and processing
 * user input, as well as other menus for league, club, and player management.
 * </p>
 * <p>
 * Authors:
 * <ul>
 *   <li>Rúben Tiago Martins Pereira (8230162) - LsircT2</li>
 *   <li>Hugo Leite Martins (8230273) - LsircT2</li>
 * </ul>
 * </p>
 */
public class Menus {


    /**
     * Displays the main menu to the user and prompts for an option.
     * <p>
     * The menu includes options to start the game, view all clubs,
     * view all players per club, view credits, or exit the application.
     * Handles invalid input by prompting the user until a valid option is entered.
     * </p>
     *
     * @param input the {@link Scanner} object to read user input from the console
     * @return the selected menu option as an integer
     */
    public static int mainMenu(Scanner input) {
        int option = 0;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println("PPFootballManager v1.0 - Season 24/25");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##-------------Main Menu-------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - Start game              |");
            System.out.println("|  Option 2 - All Clubs               |");
            System.out.println("|  Option 3 - All Players per Club    |");
            System.out.println("|  Option 4 - Credits                 |");
            System.out.println("|  Option 0 - Exit                    |");
            System.out.println("|-------------------------------------|");
            System.out.println("                                       ");
            System.out.println("Enter an option :");
            try {
                option = input.nextInt();
                verifyInput = true;
            } catch (InputMismatchException exception) {
                System.out.println("Select a valid option!");
                input.next();
            }
        } while (!verifyInput);
        return option;
    }

    /**
     * Displays and manages the main menu for starting or loading a football league.
     * <p>
     * This method presents options to the user for creating a new league, loading an existing league,
     * or returning to the previous menu. It handles user input and validates the selected option.
     * </p>
     * <ul>
     *   <li><b>Option 1:</b> Prompts for a league name and creates a new {@link League} instance, then opens the league menu.</li>
     *   <li><b>Option 2:</b> (Placeholder) Intended for loading a saved league.</li>
     *   <li><b>Option 0:</b> Returns to the previous menu.</li>
     * </ul>
     * <p>
     * The method ensures robust input handling by catching {@link InputMismatchException} and prompting the user again
     * if invalid input is entered.
     * </p>
     *
     * @param input the {@link Scanner} object used to read user input from the console
     */
    public static void startLeagueMenu(Scanner input) {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println(" PPFootballManager v1.0 - Season 24/25 ");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##-----------Start League------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - New League              |");
            System.out.println("|  Option 2 - Load League             |");
            System.out.println("|  option 0 - Back                    |");
            System.out.println("|-------------------------------------|");
            System.out.println("                                       ");
            try {
                option = input.nextInt();
                verifyInput = true;
                switch (option) {
                    case 1:
                        System.out.print("Insert a League Name:  ");
                        String leagueName = input.next();
                        League newLeague = new League(leagueName);
                        leagueMenu(input, newLeague);
                        verifyInput = false;
                        break;
                    case 2:
                        //loadGame(input);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Select a valid option!");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Select a valid option!");
                input.next();
            }
        } while (!verifyInput);

    }

    /**
     * Displays the "Start League" menu and processes user input for league management options.
     * <p>
     * This menu allows the user to:
     * <ul>
     *   <li>Create a new league by entering a league name.</li>
     *   <li>Load an existing league (feature placeholder).</li>
     *   <li>Return to the previous menu.</li>
     * </ul>
     * Handles invalid input by prompting the user until a valid option is entered.
     * </p>
     *
     * @param input the {@link Scanner} object used to read user input from the console
     */
    public static void leagueMenu(Scanner input, League league) {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println("PPFootballManager v1.0 - Season 24/25");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##------------Menu League------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - New Season              |");
            System.out.println("|  Option 2 - Load Season             |");
            System.out.println("|  option 0 - Back                    |");
            System.out.println("|-------------------------------------|");
            try {
                option = input.nextInt();
                verifyInput = true;
                switch (option) {
                    case 0:
                        return;
                    case 1:
                        Season newSeason = Functions.createSeason(input);
                        league.createSeason(newSeason);
                        System.out.println("New season created: " + newSeason.getName());
                        seasonMenu(input, newSeason);
                        verifyInput = false;
                        break;
                    case 2:
                        ISeason loadedSeason = Functions.loadSeason(input, league);
                        if (loadedSeason == null) {
                            break;
                        }
                        seasonMenu(input, (Season) loadedSeason);
                        verifyInput = false;
                        break;
                    default:
                        System.out.println("Select a valid option!");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Select a valid option!");
                input.next();
            }
        } while (!verifyInput);
    }

    /**
     * Displays the Season menu and processes user input for managing and interacting with a football season.
     * <p>
     * This menu offers a wide range of season management features, including:
     * <ul>
     *   <li>Starting friendly matches</li>
     *   <li>Starting the season (with or without manager mode)</li>
     *   <li>Adding or removing clubs (manually or automatically)</li>
     *   <li>Listing clubs in the season</li>
     *   <li>Listing events from a match</li>
     *   <li>Viewing season standings</li>
     *   <li>Resetting the season</li>
     *   <li>Viewing player statistics (individual or all players)</li>
     *   <li>Exiting to the previous menu</li>
     * </ul>
     * Handles invalid input by prompting the user until a valid option is entered.
     * </p>
     *
     * @param input  the {@link Scanner} object used to read user input from the console
     * @param season the {@link Season} object representing the current football season
     */
    public static void seasonMenu(Scanner input, Season season) {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("========================================");
            System.out.println(" PPFootballManager v1.0 - Season 24/25  ");
            System.out.println("========================================");
            System.out.println("                                        ");
            System.out.println("##-------------Start Game-------------##");
            System.out.println("|--------------------------------------|");
            System.out.println("|  Option 1 - Friendly Match           |");
            System.out.println("|  Option 2 - Start Season             |");
            System.out.println("|  Option 3 - Add Clubs (Manually)     |");
            System.out.println("|  Option 4 - Add Clubs (Automatic)    |");
            System.out.println("|  Option 5 - Remove Clubs (Manually)  |");
            System.out.println("|  Option 6 - Remove Clubs (Automatic) |");
            System.out.println("|  Option 7 - List Season Clubs        |");
            System.out.println("|  Option 8 - List a event form a match|");
            System.out.println("|  Option 9 - View Season Standings    |");
            System.out.println("|  Option 10 - Reset season            |");
            System.out.println("|  Option 11 - Get stats by players    |");
            System.out.println("|  Option 12 - Get stats all players   |");
            System.out.println("|  Option 0 - Exit                     |");
            System.out.println("|--------------------------------------|");
            System.out.println("                                        ");
            try {
                option = input.nextInt();
                verifyInput = true;
                switch (option) {
                    case 1:
                        Functions.startFriendlyMatch(input, season);
                        verifyInput = false;
                        break;
                    case 2:
                        if (season.isManager()) {
                            Functions.startSeasonManager(input, season);
                        }
                        Functions.startSeason(input, season);
                        verifyInput = false;
                        break;
                    case 3:
                        Functions.addClub(input, season);
                        verifyInput = false;
                        break;
                    case 4:
                        Functions.addAllClubsToSeason(season);
                        verifyInput = false;
                        break;
                    case 5:
                        Functions.removeClub(input, season);
                        verifyInput = false;
                        break;
                    case 6:
                        Functions.removeAllClubsToSeason(season);
                        verifyInput = false;
                        break;
                    case 7:
                        Functions.listSeasonClubs(season);
                        verifyInput = false;
                        break;
                    case 8:
                        Functions.ListEventFromAMatch(input, season);
                        verifyInput = false;
                        break;
                    case 9:
                        Functions.viewSeasonStandings(season);
                        verifyInput = false;
                        break;
                    case 10:
                        Functions.ResetSeason(season);
                        verifyInput = false;
                        break;
                    case 11:
                        Functions.inputPlayerToGetStats(input, season);
                        verifyInput = false;
                        break;
                    case 12:
                        Functions.viewAllPlayerStats(season);
                        verifyInput = false;
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Select a valid option!");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Select a valid option!");
                input.next();
            }
        } while (!verifyInput);

    }

    /**
     * Displays the credits menu for the PPFootballManager application.
     * <p>
     * This method shows information about the creators of the game and the context of the project.
     * The user is prompted to press 0 to exit the credits screen and return to the previous menu.
     * Input is validated to ensure only valid options are accepted.
     * </p>
     *
     * @param input the {@link Scanner} object used to read user input from the console
     */
    public static void creditsMenu(Scanner input) {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println("PPFootballManager v1.0 - Season 24/25");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##--------------Credits--------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  This game was create by:           |");
            System.out.println("|  Rúben Pereira nº8230162            |");
            System.out.println("|  Hugo Martins nº8230273             |");
            System.out.println("|                                     |");
            System.out.println("|  This project is an OOP work        |");
            System.out.println("|  carried out at IPP/ESTG in         |");
            System.out.println("|  the PP Class!                      |");
            System.out.println("|-------------------------------------|");
            System.out.println("                                       ");
            System.out.println("Press 0 to exit the credits!");
            try {
                option = input.nextInt();
                verifyInput = true;
                switch (option) {
                    case 0:
                        return;
                    default:
                        System.out.println("Select a valid option!");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Select a valid option!");
                input.next();
            }
        } while (!verifyInput);
    }

    /**
     * Displays the credits menu for the PPFootballManager application.
     * <p>
     * Shows information about the authors and the academic context of the project.
     * The user is prompted to press 0 to exit the credits screen. Handles invalid input
     * by prompting the user until a valid option is entered.
     * </p>
     *
     * @param input the {@link Scanner} object used to read user input from the console
     */
    public static void clubMenu() {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println("PPFootballManager v1.0 - Season 24/25");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##------------View Club-------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - View all Clubs          |");
            System.out.println("|  Option 2 - View a Club             |");
            System.out.println("|  option 0 - Back                    |");
            System.out.println("|-------------------------------------|");
            System.out.println("                                       ");
            Scanner input = new Scanner(System.in);
            try {
                option = input.nextInt();
                verifyInput = true;
                switch (option) {
                    case 1:
                        Functions.viewAllClubs();
                        verifyInput = false;
                        break;
                    case 2:
                        Functions.viewClub();
                        verifyInput = false;
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Select a valid option!");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Select a valid option!");
                input.next();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (!verifyInput);
    }

    /**
     * Displays the player menu and processes user input for viewing player information.
     * <p>
     * This menu allows the user to:
     * <ul>
     *   <li>View all players in the system</li>
     *   <li>View details of a specific player</li>
     *   <li>Return to the previous menu</li>
     * </ul>
     * The method handles invalid input by prompting the user until a valid option is entered.
     * </p>
     */
    public static void playerMenu() {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println("PPFootballManager v1.0 - Season 24/25");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##------------View Player------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - View all Players        |");
            System.out.println("|  Option 2 - View a Player           |");
            System.out.println("|  option 0 - Back                    |");
            System.out.println("|-------------------------------------|");
            System.out.println("                                       ");
            Scanner input = new Scanner(System.in);
            try {
                option = input.nextInt();
                verifyInput = true;
                switch (option) {
                    case 1:
                        Functions.viewAllPlayers();
                        verifyInput = false;
                        break;
                    case 2:
                        Functions.viewPlayer();
                        verifyInput = false;
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Select a valid option!");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Select a valid option!");
                input.next();
            }
        } while (!verifyInput);
    }
}