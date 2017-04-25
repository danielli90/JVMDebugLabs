package com.newardassociates.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Just a big ol' object consuming a chunk o' RAM
 */
class Blob {
    private byte[] buffer;

    Blob() {
        RunawayAllocation.LOGGER.entering("Blob", ".ctor");
        int size = new java.util.Random().nextInt(8000);
        RunawayAllocation.LOGGER.info("Allocating " + size);
        buffer = new byte[size];
        RunawayAllocation.LOGGER.exiting("Blob", ".ctor");
    }
}

class RunawayAllocation {

    static Logger LOGGER = Logger.getLogger(RunawayAllocation.class.getCanonicalName());
    private static List<Blob> runaway = new ArrayList<>();

    static void go(String... args) {
        LOGGER.info("Entering RunawayAllocation...");

        System.out.println("Beginning allocation; press Ctrl-C to abort");
        try {
            while (true) {
                runaway.add(new Blob());
            }
        } catch (OutOfMemoryError oom) {
            System.out.println("POP goes the VM");
            oom.printStackTrace();
        }

        LOGGER.info("Exiting RunawayAllocation...");
    }
}
