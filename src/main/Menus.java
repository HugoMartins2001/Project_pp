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
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menus {

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

    public static void startGameMenu(Scanner input) {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println(" PPFootballManager v1.0 - Season 24/25 ");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##------------Start Game-------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - New Game                |");
            System.out.println("|  Option 2 - Load Game               |");
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
                        leagueMenu(input , newLeague);
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
                        verifyInput = false;
                        break;
                    case 2:
                        ISeason loadedSeason = Functions.loadSeason(input, league);
                        if(loadedSeason == null) {
                            break;
                        }
                        seasonMenu(input, (Season)loadedSeason);
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

    //public static int loadGame(Scanner input) {}

    //public static int createTeamAndPlayers(Scanner input) {}

    public static void seasonMenu(Scanner input, Season season) {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println(" PPFootballManager v1.0 - Season 24/25 ");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##------------Start Game-------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - Friendly Match          |");
            System.out.println("|  Option 2 - Start Season            |");
            System.out.println("|  Option 3 - Add Clubs (Manually)    |");
            System.out.println("|  Option 4 - Add Clubs (Automatic)   |");
            System.out.println("|  Option 5 - Remove Clubs (Manually) |");
            System.out.println("|  Option 6 - Remove Clubs (Automatic)|");
            System.out.println("|  Option 7 - List Season Clubs       |");
            System.out.println("|  Option 8 - Reset Season            |");
            System.out.println("|  Option 0 - Exit                    |");
            System.out.println("|-------------------------------------|");
            System.out.println("                                       ");
            try {
                option = input.nextInt();
                verifyInput = true;
                switch (option) {
                    case 1:
                        Functions.startFriendlyMatch(input, season);
                        verifyInput = false;
                        break;
                    case 2:
                        startSeasonMenu(input);
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

    public static void startSeasonMenu(Scanner input) {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println("PPFootballManager v1.0 - Season 24/25");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##------------Start Season-----------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - Friendly Match          |");
            System.out.println("|  Option 2 - Start Season            |");
            System.out.println("|  Option 3 - Add Clubs (Manually)    |");
            System.out.println("|  Option 4 - Add Clubs (Automatic)   |");
            System.out.println("|  Option 5 - Remove Clubs (Manually) |");
            System.out.println("|  Option 6 - Remove Clubs (Automatic)|");
            System.out.println("|  Option 7 - List Season Clubs       |");
            System.out.println("|  Option 8 - Reset Season            |");
            System.out.println("|  Option 0 - Exit                    |");
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
            }
        } while (!verifyInput);
    }

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