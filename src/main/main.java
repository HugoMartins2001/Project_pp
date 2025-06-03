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

import data.Exporter;
import data.Importer;

import java.util.Scanner;

/**
 * Entry point for the Football Manager application.
 *
 * This class initializes data by importing all leagues and then displays a menu
 * interface for the user to interact with various features such as managing
 * leagues, clubs, and players.
 *
 */
public class main {

    /**
     * Main method that starts the Football Manager application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Importer importer = new Importer();
        importer.importAllLeagues(); // Load all existing league data
        int Options;

        do {
            Options = Menus.mainMenu(input);
            switch (Options) {
                case 1:
                    Menus.startLeagueMenu(input); // Option to start a league
                    break;
                case 2:
                    Menus.clubMenu(); // Option to manage clubs
                    break;
                case 3:
                    Menus.playerMenu(); // Option to manage players
                    break;
                case 4:
                    Menus.creditsMenu(input); // Show credits
                    break;
                case 0:
                    Exporter exporter = new Exporter();
                    exporter.exportToJson(); // Export data to JSON
                    exporter.exportHtmlReports(); // Export data to HTML
                    break;
                default:
                    System.out.println("Select a valid option!");
            }
        } while (Options != 0);
    }
}
