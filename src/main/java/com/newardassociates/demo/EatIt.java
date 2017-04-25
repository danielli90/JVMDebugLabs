package com.newardassociates.demo;

import java.util.logging.Logger;

class EatIt {

    private static Logger LOGGER = Logger.getLogger(EatIt.class.getCanonicalName());

    static void go(String... args) {
        LOGGER.entering(EatIt.class.getSimpleName(), "go", args);

        try {
            System.out.println("The Count says, Let us count from 0 to 8!");
            String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
            for (int i = 5; i < numbers.length; i++) {
                System.out.println(numbers[i] + "!");
            }
        } catch (Exception ex) {
            // TODO: Should probably... nah, this'll never happen, right?
        }
        System.out.println("AH AH AH AH!!!!");

        LOGGER.exiting(EatIt.class.getSimpleName(), "go");
    }
}
