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

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menus {

    public static int showMenu(Scanner input) {
        int option = 0;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println("PPFootballManager v1.0 -Temporada 24/25");
            System.out.println("=======================================");
            System.out.println("                                       ");
            System.out.println("##-----------Menu Principal----------##");
            System.out.println("|-------------------------------------|");
            System.out.println("|  Option 1 - Start a new game        |");
            System.out.println("|  Option 2 - Load a game             |");
            System.out.println("|  Option 3 - Create teams/players    |");
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

    //public static int startGame(Scanner input) {}

    //public static int loadGame(Scanner input) {}

    //public static int createTeamAndPlayers(Scanner input) {}

    public static void credits(Scanner input) {
        int option;
        boolean verifyInput = false;

        do {
            System.out.println("=======================================");
            System.out.println("PPFootballManager v1.0 -Temporada 24/25");
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
                if(option == 0){
                    verifyInput = true;
                }else{
                    System.out.println("Select a valid option!");
                }
            } catch (InputMismatchException exception) {
                System.out.println("Select a valid option!");
                input.next();
            }
        } while (!verifyInput);
        Menus.showMenu(input);
    }
}