package com.newardassociates.demo;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class DoNothing extends Object {
    @Override
    public String toString() {
        return "DoNothing";
    }
}

class ClassRunaway {
    static Logger LOGGER = Logger.getLogger(ClassRunaway.class.getCanonicalName());

    static List<ClassLoader> loaders = new ArrayList<ClassLoader>();

    static void go(String... args) {
        LOGGER.entering(ClassRunaway.class.getSimpleName(), "go", args);

        try {
            // Create a local directory, destroying it if it already exists
            File localClassesDir = new File("./classes");
            if (localClassesDir.exists()) {
                Files.delete(localClassesDir.toPath().resolve("./DoNothing.class"));

                Files.delete(localClassesDir.toPath());
            }
            Path dir = Files.createDirectory(localClassesDir.toPath());

            // Find the DoNothing.class code, and copy it to that directory
            InputStream stream = ClassRunaway.class.getClassLoader().getResourceAsStream("com/newardassociates/demo/DoNothing.class");
            long copy = Files.copy(stream, dir.resolve("./DoNothing.class"));
        } catch (java.io.IOException ioEx) {
            LOGGER.log(Level.WARNING, "Something went wrong w/local classes directory; bailing!", ioEx);
        }


        boolean neverChanges = true;
        while (neverChanges) {
            // Start creating URLClassLoaders that will load DoNothing, and keep each one
            // in the loaders list
            try {
                LOGGER.fine("Preparing to load DoNothing");
                ClassLoader loader = new URLClassLoader(new URL[]{new File("./classes").toURL()});

                loader.loadClass("com.newardassociates.demo.DoNothing");

                LOGGER.fine("Loaded!");
                loaders.add(loader);
            } catch (MalformedURLException malURLEx) {
                LOGGER.log(Level.WARNING, "ClassLoader could not be created", malURLEx);
                return; // If we can't create the ClassLoader, then the bug isn't ever going to happen
            } catch (ClassNotFoundException cnfEx) {
                LOGGER.log(Level.WARNING, "Could not load DoNothing", cnfEx);
                return; // Same story; can't load the class => can't get the PermGen to empty out
            }
        }

        LOGGER.exiting(ClassRunaway.class.getSimpleName(), "go");
    }
}
