package com.newardassociates.demo;

import java.util.logging.Logger;

/**
 * Generate a NullPointerException on demand
 */
class NullInvocation {

    private static Logger LOGGER = Logger.getLogger(NullInvocation.class.getCanonicalName());

    static void go(String... args) {
        LOGGER.entering(NullInvocation.class.getSimpleName(), "go");

        Object notReallyAnObject = null;
        String nraoString = notReallyAnObject.toString();

        LOGGER.exiting(NullInvocation.class.getSimpleName(), "go");
    }
}
