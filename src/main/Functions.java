package main;

import league.League;
import league.Season;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Functions {

    public static Season CreateSeason(Scanner input) {
        String name = null;
        int year = 0;
        int maxTeams = 0;
        boolean verifyInput = false;

        do {
            System.out.println("Enter the name of the League:");
            try {
                name = input.nextLine();
                verifyInput = true;
            } catch (InputMismatchException exception) {
                System.out.println("Select a valid option!");
                input.next();
            }
            System.out.println("Enter the year of the League:");
            try{
                year = input.nextInt();
                verifyInput = true;
            } catch (InputMismatchException exception) {
                System.out.println("Select a valid option!");
                input.next();
            }
            System.out.println("Enter the maximum number of teams:");
            try {
                maxTeams = input.nextInt();
                verifyInput = true;
            } catch (InputMismatchException exception) {
                System.out.println("Select a valid option!");
                input.next();
            }
        } while (!verifyInput);
        return new Season(name, year, maxTeams);
    }
}
