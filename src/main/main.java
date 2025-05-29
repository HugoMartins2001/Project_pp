package main;

import java.util.Scanner;

/**
 * Entry point for the Football Manager application.
 * <p>
 * This class provides the main menu loop, allowing users to navigate through
 * the application's features such as starting a league, managing clubs, managing players,
 * and viewing credits. User input is handled via the console.
 * </p>
 * <p>
 * Authors:
 * <ul>
 *   <li>Rúben Tiago Martins Pereira (8230162) - LsircT2</li>
 *   <li>Hugo Leite Martins (8230273) - LsircT2</li>
 * </ul>
 * </p>
 */
public class main {

    /**
     * The main method that starts the Football Manager application.
     * <p>
     * Displays the main menu and processes user input in a loop until the user chooses to exit.
     * </p>
     *
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int Options;

        do {
            Options = Menus.mainMenu(input);
            switch (Options) {
                case 1:
                    Menus.startLeagueMenu(input);
                    break;
                case 2:
                    Menus.clubMenu();
                    break;
                case 3:
                    Menus.playerMenu();
                    break;
                case 4:
                    Menus.creditsMenu(input);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Select a valid option!");
            }
        } while (Options != 0);
    }
}