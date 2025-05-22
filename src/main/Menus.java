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

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import data.Importer;
import team.Club;
import player.Player;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menus {

    public static int showMenu(Scanner input) {
        int option = 0;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println("PPFootballManager v1.0 - Season 24/25");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##-------------Main Menu-------------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - Start a new game        |");
            System.out.println("|  Option 2 - Load a game             |");
            System.out.println("|  Option 3 - Club                    |");
            System.out.println("|  Option 4 - Player                  |");
            System.out.println("|  Option 5 - Credits                 |");
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

    //public static int startGame(Scanner input) {}

    //public static int loadGame(Scanner input) {}

    //public static int createTeamAndPlayers(Scanner input) {}

    public static void credits(Scanner input) {
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

    public static void Club() {
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
                        viewAllClubs();
                        verifyInput = false;
                        break;
                    case 2:
                        viewClub();
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

    public static void viewAllClubs() {
        System.out.println("=======================================");
        System.out.println("PPFootballManager v1.0 - Season 24/25");
        System.out.println("=======================================");

        try {
            Importer importer = new Importer();
            Club[] clubs = importer.importClubs("files/clubs.json");

            System.out.println("\nView Available Clubs:");
            System.out.println("---------------------------------------");

            for (Club club : clubs) {
                if (club != null) {
                    System.out.printf("Name: %-20s | Country: %-15s | Stadium: %s%n",
                            club.getName(),
                            club.getCountry(),
                            club.getStadiumName());
                }
            }

            System.out.println("---------------------------------------");
        } catch (IOException error) {
            System.out.println("Error while loading the clubs: " + error.getMessage());
        }
    }

    public static void viewClub() {
        int option;
        Scanner input = new Scanner(System.in);

        do {
            System.out.println("=======================================");
            System.out.println("PPFootballManager v1.0 - Season 24/25");
            System.out.println("=======================================");
            System.out.println("Enter the name of the club you want to view:");
            String clubCode = input.nextLine();

            try {
                Importer importer = new Importer();
                Club[] clubs = importer.importClubs("files/clubs.json");

                boolean found = false;
                for (Club club : clubs) {
                    if (club.getCode().equalsIgnoreCase(clubCode)) {
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
            } catch (IOException error) {
                System.out.println("Error while loading the clubs: " + error.getMessage());
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

            if (option != 1 && option != 0) {
                System.out.println("Select a valid option!");
            }
        } while (option == 1);
    }

    public static void Player() {
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
                        viewAllPlayers();
                        verifyInput = false;
                        break;
                    case 2:
                        viewPlayer();
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

            if (option != 1 && option != 0) {
                System.out.println("Invalid Option!");
            }
        } while (option == 1);
    }

}