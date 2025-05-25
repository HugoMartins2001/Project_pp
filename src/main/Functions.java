package main;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import data.Importer;
import event.GoalEvent;
import league.League;
import league.Season;
import match.Match;
import player.Player;
import simulation.MatchSimulatorStrat;
import team.Club;
import team.Formation;
import team.Team;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Functions {

    public static void clearConsole() {
            for (int i = 0; i < 50; ++i) System.out.println();
    }


    public static Season createSeason(Scanner input) {
        System.out.println("Enter the name of the season: ");
        String seasonName = input.next();

        int year = 0, maxTeams = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Enter the year of the season: ");
                year = input.nextInt();

                System.out.println("Enter the maximum number of teams for the season: ");
                maxTeams = input.nextInt();

                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numeric values.");
                input.next();
            }
        }
        return new Season(seasonName, year, maxTeams);
    }

    public static ISeason loadSeason(Scanner input, League league) {
        ISeason[] seasons = league.getSeasons();
        if (seasons.length == 0) {
            System.out.println("No seasons available in this league.");
            return null;
        }
        System.out.println("All seasons avaiable: ");
        for (ISeason season : seasons) {
            if (season == null) continue;
            System.out.println("Season: " + season.getName() + " | Year: " + season.getYear());
        }

        int year = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Enter the year of the season: ");
                year = input.nextInt();

                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numeric values.");
                input.next();
            }
        }
        return league.getSeason(year);
    }

    public static void addAllClubsToSeason(Season season) {
        Importer importer = new Importer();
        try {
            Club[] clubs = importer.importClubs("files/clubs.json");
            for (Club club : clubs) {
                if (club != null) {
                    season.addClub(club);
                }
            }
            System.out.println("All clubs have been added to the season: " + season.getName());
        } catch (Exception e) {
            System.out.println("Max Clubs reached.");
        }
    }

    public static void removeAllClubsToSeason(Season season) {
        IClub[] clubs = season.getCurrentClubs();
        if (clubs.length == 0) {
            System.out.println("No clubs available in this season.");
            return;
        }

        for (IClub club : clubs) {
            if (club != null) {
                season.removeClub(club);
            }
        }
        System.out.println("All clubs have been removed from the season: " + season.getName());
    }

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
        if(clubCounter < 2) {
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
        homeTeam.setAutomaticTeam(homeTeam.getPlayers(), (Formation) homeTeam.getFormation());

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
        awayTeam.setAutomaticTeam(awayTeam.getPlayers(), (Formation) awayTeam.getFormation());

        IMatch friendlyMatch = new Match(homeClub, awayClub, 0);
        friendlyMatch.setTeam(homeTeam);
        friendlyMatch.setTeam(awayTeam);
        MatchSimulatorStrat matchSimulator = new MatchSimulatorStrat();
        matchSimulator.simulate(friendlyMatch);

        System.out.println("Friendly match between " + homeClub.getName() + " and " + awayClub.getName() + " has been played.");
        if( friendlyMatch.getWinner() == null) {
            System.out.println("The match ended in a draw.");
        } else {
            System.out.println("The match winner is: " + friendlyMatch.getWinner().getClub().getName());
        }
        System.out.println("Final Score: " +
                friendlyMatch.getHomeClub().getName() + " " +
                friendlyMatch.getTotalByEvent(GoalEvent.class, homeClub) + " - " +
                friendlyMatch.getTotalByEvent(GoalEvent.class, awayClub) + " " +
                friendlyMatch.getAwayClub().getName());
        System.out.println("Match Events:");
        for (IEvent gameEvent : friendlyMatch.getEvents()) {
            if (gameEvent != null) {
                System.out.println(gameEvent);
            }
        }
    }

    private static Formation readFormation(Scanner input, String teamLabel) {
        String displayName;
        int defenders = -1, midfielders = -1, forwards = -1;
        final int TOTAL_OUTFIELD_PLAYERS = 10;

        System.out.println("Select the formation for the " + teamLabel + " team: ");
        System.out.print("Displayed name: ");
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

    public static void listSeasonClubs(Season season) {
        if(season == null) {
            throw new IllegalArgumentException("Season cannot be null.");
        }
        IClub[] clubs = season.getCurrentClubs();
        if (clubs.length == 0) {
            System.out.println("No clubs available in this season.");
            return;
        }

        System.out.println("Current Clubs in Season " + season.getName() + ":");
        System.out.println("---------------------------------------");
        for (IClub club : clubs) {
            if (club != null) {
                System.out.printf("Name: %-40s | Country: %-15s | Stadium: %s%n",
                        club.getName(),
                        club.getCountry(),
                        club.getStadiumName());
            }
        }
        System.out.println("---------------------------------------");
    }

    public static void viewAllClubs() {
        System.out.println("=======================================");
        System.out.println("PPFootballManager v1.0 - Season 24/25");
        System.out.println("=======================================");

        try {
            Importer importer = new Importer();
            Club[] clubs = importer.importClubs("files/clubs.json");

            System.out.println("\nView Available Clubs:");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

            for (Club club : clubs) {
                if (club != null) {
                    System.out.printf("Name: %-40s | Country: %-15s | Stadium: %s%n",
                            club.getName(),
                            club.getCountry(),
                            club.getStadiumName());
                }
            }

            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
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

            if (option != 1) {
                System.out.println("Select a valid option!");
            }
        } while (option == 1);
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

            if (option != 1) {
                System.out.println("Invalid Option!");
            }
        } while (option == 1);
    }
}
