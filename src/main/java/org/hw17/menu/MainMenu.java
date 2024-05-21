package org.hw17.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {
    private static final Logger logger = LoggerFactory.getLogger(MainMenu.class);

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean loop = true;

        while (loop) {
            try {
                System.out.println("========================================= ");
                System.out.println("* Welcome to the university loan system * ");
                System.out.println("========================================= ");
                System.out.println("1) Register ");
                System.out.println("2) Login ");
                System.out.println("3) Exit ");
                System.out.println("-------------------------------------------");

                String input = scanner.nextLine();
                switch (input) {

                    case "1":
                        logger.info("User selected Register");
                        LoanRegister.registryMenu();
                    case "2":
                        logger.info("User selected Login");
                        LoanRegister.signIn();
                    case "3": {
                        logger.info("User selected Exit");
                        return;
                    }
                    default:
                        logger.warn("Invalid input: " + input);
                        System.out.println(">>>> Wrong entry! <<<<");

                }
            } catch (Exception e) {
                logger.error("An error occurred: " + e.getMessage(), e);
                if (e instanceof InputMismatchException)
                    System.out.println("Wrong entry!");
                else
                    System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
    }
}
