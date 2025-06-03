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

import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import league.League;
import league.Season;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class containing all menu-related methods for user interaction. Handles
 * navigation and input for league, season, clubs, and players.
 */
public class Menus {

    /**
     * Displays the main menu and prompts the user to select an option.
     *
     * @param input Scanner object for reading user input.
     * @return Selected option as an integer.
     */
    public static int mainMenu(Scanner input) {
        int option = 0;
        boolean verifyInput = false;

        do {
            System.out.println("");
            System.out.println("=======================================");
            System.out.println(" PPFootballManager v1.0 - Season 24/25 ");
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
     * Displays the Start League Menu and processes user input to create or load
     * a league.
     *
     * @param input Scanner object for user input.
     */
    public static void startLeagueMenu(Scanner input) {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("");
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
                        System.out.println("");
                        System.out.print("Insert a League Name:  ");
                        String leagueName = input.next();
                        League newLeague = new League(leagueName);
                        Functions.addLeague(newLeague);
                        leagueMenu(input, newLeague);
                        verifyInput = false;
                        break;
                    case 2:
                        League loadedLeague = Functions.loadLeague(input);
                        if (loadedLeague == null) {
                            break;
                        }
                        leagueMenu(input, loadedLeague);
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
     * Displays the League Menu for a specific league. Allows creating or
     * loading seasons.
     *
     * @param input Scanner for input.
     * @param league League to be managed.
     */
    public static void leagueMenu(Scanner input, League league) {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("");
            System.out.println("=======================================");
            System.out.println(" PPFootballManager v1.0 - Season 24/25 ");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##------------Menu League------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - New Season              |");
            System.out.println("|  Option 2 - Load Season             |");
            System.out.println("|  Option 3 - Create season with existing clubs |");
            System.out.println("|  option 4 - Remove a team           |");
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
                    case 3:
                        Season newSeason1 = Functions.createSeasonFromExisting(input, league);
                        if (newSeason1 != null) {
                            try {
                                league.createSeason(newSeason1);
                                seasonMenu(input, newSeason1);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Erro ao criar temporada: " + e.getMessage());
                            }
                        }
                        verifyInput = false;
                        break;
                    case 4:
                        league.removeTeamFromSeason(year, teamName);
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
     * Displays the Season Menu for a specific season. Allows starting seasons,
     * viewing stats, etc.
     *
     * @param input Scanner for input.
     * @param season Season to be managed.
     */
    public static void seasonMenu(Scanner input, Season season) {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("");
            System.out.println("=========================================");
            System.out.println("  PPFootballManager v1.0 - Season 24/25  ");
            System.out.println("=========================================");
            System.out.println("                                         ");
            System.out.println("##--------------Start Game-------------##");
            System.out.println("|---------------------------------------|");
            System.out.println("|  Option 1 - Start Season              |");
            System.out.println("|  Option 2 - Friendly Match            |");
            System.out.println("|  Option 3 - View Season Standings     |");
            System.out.println("|  Option 4 - List a event form a match |");
            System.out.println("|  Option 5 - Get Player Stats          |");
            System.out.println("|  Option 6 - Add/Remove/List Clubs Menu|");
            System.out.println("|  Option 7 - Reset season              |");
            System.out.println("|  Option 0 - Exit                      |");
            System.out.println("|---------------------------------------|");
            System.out.println("                                         ");
            try {
                option = input.nextInt();
                verifyInput = true;
                switch (option) {
                    case 1:
                        if (season.isManager()) {
                            Functions.startSeasonManager(input, season);
                        }
                        Functions.startSeason(input, season);
                        verifyInput = false;
                        break;
                    case 2:
                        Functions.startFriendlyMatch(input, season);
                        verifyInput = false;
                        break;
                    case 3:
                        Functions.viewSeasonStandings(season);
                        verifyInput = false;
                        break;
                    case 4:
                        Functions.ListEventFromAMatch(input, season);
                        verifyInput = false;
                        break;
                    case 5:
                        getStatsMenu(input, season);
                        verifyInput = false;
                        break;
                    case 6:
                        addRemoveClubsMenu(input, season);
                        verifyInput = false;
                        break;
                    case 7:
                        Functions.ResetSeason(season);
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
            } catch (IllegalStateException error) {
                System.out.println(error.getMessage());
            }
        } while (!verifyInput);

    }

    /**
     * Displays the credits screen with project and authorship information.
     *
     * @param input Scanner for user input to return to the main menu.
     */
    public static void creditsMenu(Scanner input) {
        int option;
        boolean verifyInput = false;
        do {
            System.out.println("");
            System.out.println("=======================================");
            System.out.println(" PPFootballManager v1.0 - Season 24/25 ");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##--------------Credits--------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  This game was create by:           |");
            System.out.println("|  Ruben Pereira n 8230162            |");
            System.out.println("|  Hugo Martins n 8230273             |");
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
     * Displays the Club Menu to allow users to view all clubs or a specific
     * club.
     */
    public static void clubMenu() {
        int option;
        boolean verifyInput = false;
        do {
            System.out.println("");
            System.out.println("=======================================");
            System.out.println(" PPFootballManager v1.0 - Season 24/25 ");
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
     * Displays the Player Menu to allow users to view all players or a specific
     * player.
     */
    public static void playerMenu() {
        int option;
        boolean verifyInput = false;
        do {
            System.out.println("");
            System.out.println("=======================================");
            System.out.println(" PPFootballManager v1.0 - Season 24/25 ");
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

    /**
     * Displays the statistics menu and allows viewing individual or all
     * players' stats.
     *
     * @param input Scanner object for user input.
     * @param season The season from which to gather player statistics.
     */
    public static void getStatsMenu(Scanner input, Season season) {
        int option;
        boolean verifyInput = false;
        do {
            System.out.println("");
            System.out.println("=======================================");
            System.out.println(" PPFootballManager v1.0 - Season 24/25 ");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##------------Stats Menu-------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - Get stats by player     |");
            System.out.println("|  Option 2 - Get stats all players   |");
            System.out.println("|  option 0 - Back                    |");
            System.out.println("|-------------------------------------|");
            System.out.println("                                       ");
            try {
                option = input.nextInt();
                verifyInput = true;
                switch (option) {
                    case 1:
                        Functions.inputPlayerToGetStats(input, season);
                        verifyInput = false;
                        break;
                    case 2:
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
     * Displays the Add/Remove Clubs menu to manually or automatically modify
     * the clubs in a season.
     *
     * @param input Scanner for user input.
     * @param season Season to modify.
     */
    public static void addRemoveClubsMenu(Scanner input, Season season) {
        int option;
        boolean verifyInput = false;
        do {
            System.out.println("");
            System.out.println("=======================================");
            System.out.println(" PPFootballManager v1.0 - Season 24/25 ");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##------ Add/Remove Clubs Menu ------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - Add Clubs (Manually)    |");
            System.out.println("|  Option 2 - Add Clubs (Automatic)   |");
            System.out.println("|  Option 3 - Remove Clubs (Manually) |");
            System.out.println("|  Option 4 - Remove Clubs (Automatic)|");
            System.out.println("|  Option 5 - List All Season Clubs   |");
            System.out.println("|  option 0 - Back                    |");
            System.out.println("|-------------------------------------|");
            System.out.println("                                       ");
            try {
                option = input.nextInt();
                verifyInput = true;
                switch (option) {
                    case 1:
                        Functions.addClub(input, season);
                        verifyInput = false;
                        break;
                    case 2:
                        Functions.addAllClubsToSeason(season);
                        verifyInput = false;
                        break;
                    case 3:
                        Functions.removeClub(input, season);
                        verifyInput = false;
                        break;
                    case 4:
                        Functions.removeAllClubsToSeason(season);
                        verifyInput = false;
                        break;
                    case 5:
                        Functions.listSeasonClubs(season);
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
