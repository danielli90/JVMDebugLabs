package com.newardassociates.demo;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
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
        RunawayAllocation.LOGGER.fine("Allocating " + size);
        buffer = new byte[size];
        RunawayAllocation.LOGGER.exiting("Blob", ".ctor");
    }
}

class RunawayAllocation {

    static Logger LOGGER = Logger.getLogger(RunawayAllocation.class.getCanonicalName());
    private static List<Blob> runaway = new ArrayList<>();

    static void go(String... args) {
        LOGGER.entering(RunawayAllocation.class.getSimpleName(), "go", args);

        traceMemorySizes();

        try {
            while (true) {
                runaway.add(new Blob());
            }
        } catch (OutOfMemoryError oom) {
            System.out.println("POP goes the VM");
            oom.printStackTrace();
        }

        LOGGER.exiting(RunawayAllocation.class.getSimpleName(), "go");
    }

    static void traceMemorySizes() {
        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage mu = memBean.getHeapMemoryUsage();
        LOGGER.fine("Max: " + mu.getMax());
    }
}
