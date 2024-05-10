package org.hw17.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean loop = true;

        while (loop) {
            try {
                System.out.println( "========================================= ");
                System.out.println( "* Welcome to the university loan system * ");
                System.out.println( "========================================= ");
                System.out.println("1) Register ");
                System.out.println("2) Login ");
                System.out.println("3) Exit ");
                System.out.println("-------------------------------------------");

                String input = scanner.nextLine();
                switch (input) {

                    case "1" -> LoanRegister.registryMenu();
                    case "2" -> LoanRegister.signIn();
                    case "3" -> {
                        return;
                    }
                    default -> System.out.println(">>>> Wrong entry! <<<<");

                }
            } catch (Exception e) {
                if (e instanceof InputMismatchException)
                    System.out.println("Wrong entry!");
                else
                    System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }
}
