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
            Options = Menus.showMenu(input);
            switch (Options) {
                case 1:
                    Menus.startGame();
                    break;
                case 2:
                    Menus.loadGame();
                    break;
                case 3:
                    Menus.createTeamAndPlayers();
                    break;
                case 4:
                    Menus.credits(input);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Select a valid option!");
            }
        } while (Options != 0);
    }
}