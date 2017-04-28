package com.newardassociates.demo;

import java.util.logging.Logger;

class LogicError {
    static Logger LOGGER = Logger.getLogger(LogicError.class.getCanonicalName());

    static void go(String... args) {
        LOGGER.entering(LogicError.class.getSimpleName(), "go", args);

        

        LOGGER.exiting(LogicError.class.getSimpleName(), "go");
    }

}
