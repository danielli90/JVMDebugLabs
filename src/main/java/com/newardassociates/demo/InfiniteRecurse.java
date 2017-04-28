package com.newardassociates.demo;

import java.util.logging.Logger;

class InfiniteRecurse {
    static Logger LOGGER = Logger.getLogger(InfiniteRecurse.class.getCanonicalName());

    static void go(String... args) {
        LOGGER.entering(InfiniteRecurse.class.getSimpleName(), "go", args);

        System.out.println("Fibonacci(12) = " + fibonacci(12));

        LOGGER.exiting(InfiniteRecurse.class.getSimpleName(), "go");
    }

    static int fibonacci(int f) {
        if (f == 0)
            return 1;
        else
            return fibonacci(f); // <--- yup, that's a bug!
    }
}
