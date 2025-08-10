package net.tucky143.nerdy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class GradlePropertiesUpdater {

    public static void main(String args) {

        File projectRoot = new File(args);
        if (!projectRoot.exists() || !projectRoot.isDirectory()) {
            System.err.println("Invalid project root directory: " + args);
            System.exit(1);
        }

        File gradleProperties = new File(projectRoot, "gradle.properties");

        try {
            if (gradleProperties.exists()) {
                List<String> lines = Files.readAllLines(gradleProperties.toPath());
                AtomicBoolean found = new AtomicBoolean(false);

                List<String> newLines = lines.stream()
                        .map(line -> {
                            if (line.trim().startsWith("org.gradle.configuration-cache=")) {
                                found.set(true);
                                return "org.gradle.configuration-cache=false";
                            }
                            return line;
                        })
                        .collect(Collectors.toList());

                if (!found.get()) {
                    newLines.add("org.gradle.configuration-cache=false");
                }

                Files.write(gradleProperties.toPath(), newLines);
                System.out.println("Updated gradle.properties to disable configuration cache.");
            } else {
                Files.write(gradleProperties.toPath(), List.of("org.gradle.configuration-cache=false"));
                System.out.println("Created gradle.properties with configuration cache disabled.");
            }
        } catch (IOException e) {
            System.err.println("Error updating gradle.properties: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void SetTrue(String args) {

        File projectRoot = new File(args);
        if (!projectRoot.exists() || !projectRoot.isDirectory()) {
            System.err.println("Invalid project root directory: " + args);
            System.exit(1);
        }

        File gradleProperties = new File(projectRoot, "gradle.properties");

        try {
            if (gradleProperties.exists()) {
                List<String> lines = Files.readAllLines(gradleProperties.toPath());
                AtomicBoolean found = new AtomicBoolean(false);

                List<String> newLines = lines.stream()
                        .map(line -> {
                            if (line.trim().startsWith("org.gradle.configuration-cache=")) {
                                found.set(true);
                                return "org.gradle.configuration-cache=true";
                            }
                            return line;
                        })
                        .collect(Collectors.toList());

                if (!found.get()) {
                    newLines.add("org.gradle.configuration-cache=true");
                }

                Files.write(gradleProperties.toPath(), newLines);
                System.out.println("Updated gradle.properties to enable configuration cache.");
            } else {
                Files.write(gradleProperties.toPath(), List.of("org.gradle.configuration-cache=true"));
                System.out.println("Created gradle.properties with configuration cache enabled.");
            }
        } catch (IOException e) {
            System.err.println("Error updating gradle.properties: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
