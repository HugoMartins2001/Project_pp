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

import java.util.Scanner;

public class main {
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