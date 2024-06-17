package com.example.demo2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlantUMLParser {

    // Regex zum Finden von Klassen, Paketen und Beziehungen
    private static final String CLASS_REGEX = "class\\s+([\\w.]+)";
    private static final String PACKAGE_REGEX = "package\\s+([\\w.]+)\\s*\\{";
    private static final String RELATION_REGEX = "([\\w.]+)\\s*--\\|?>\\s*([\\w.]+)";

    public ParsedData parse(String filePath) throws Exception {
        // Liest Dateiinhalt in Zeilen
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        // Verbinde Zeilen zu einem einzigen String
        String content = String.join("\n", lines);

        // Extrahiere Klassen und deren Pakete aus dem Inhalt
        Map<String, String> classToPackage = extractClassToPackageMap(content);
        
        // Hole alle Klassen aus der Map
        Set<String> classes = classToPackage.keySet();
        
        // Extrahiere alle Pakete aus dem Inhalt
        Set<String> packages = extractMatches(content, PACKAGE_REGEX);
        
        // Extrahiere alle Beziehungen zwischen Klassen
        List<String[]> relations = extractRelations(content, classToPackage);

        return new ParsedData(classes, packages, relations);
    }

    private Map<String, String> extractClassToPackageMap(String content) {
        Pattern packagePattern = Pattern.compile(PACKAGE_REGEX);
        Matcher packageMatcher = packagePattern.matcher(content);

        Map<String, String> classToPackage = new HashMap<>();
        String currentPackage = "";
        int packageStart = 0;

        while (packageMatcher.find()) {
            // Paketname gefunden
            currentPackage = packageMatcher.group(1);
            packageStart = packageMatcher.end();

            // Bestimme das Ende des Pakets
            int packageEnd = content.indexOf("}", packageStart);
            String packageContent = content.substring(packageStart, packageEnd);
            
            // Finde Klassen innerhalb des Pakets
            Set<String> classesInPackage = extractMatches(packageContent, CLASS_REGEX);

            for (String className : classesInPackage) {
                classToPackage.put(currentPackage + "." + className, currentPackage);
            }
        }

        return classToPackage;
    }

    private Set<String> extractMatches(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        Set<String> matches = new HashSet<>();
        
        // Finde alle Übereinstimmungen
        while (matcher.find()) {
            matches.add(matcher.group(1));
        }
        
        return matches;
    }

    private List<String[]> extractRelations(String content, Map<String, String> classToPackage) {
        Pattern pattern = Pattern.compile(RELATION_REGEX);
        Matcher matcher = pattern.matcher(content);
        List<String[]> relations = new ArrayList<>();
        
        // Finde alle Beziehungen
        while (matcher.find()) {
            String fromClass = matcher.group(1);
            String toClass = matcher.group(2);

            // Stelle sicher, dass Namen vollständig sind
            if (classToPackage.containsKey(fromClass)) {
                fromClass = classToPackage.get(fromClass) + "." + fromClass;
            }
            if (classToPackage.containsKey(toClass)) {
                toClass = classToPackage.get(toClass) + "." + toClass;
            }

            relations.add(new String[]{fromClass, toClass});
        }
        
        return relations;
    }

    public static class ParsedData {
        private final Set<String> classes;
        private final Set<String> packages;
        private final List<String[]> relations;

        // Konstruktor mit Klassen, Paketen und Beziehungen
        public ParsedData(Set<String> classes, Set<String> packages, List<String[]> relations) {
            this.classes = classes;
            this.packages = packages;
            this.relations = relations;
        }

        // Getter für Klassen
        public Set<String> getClasses() {
            return classes;
        }

        // Getter für Pakete
        public Set<String> getPackages() {
            return packages;
        }

        // Getter für Beziehungen
        public List<String[]> getRelations() {
            return relations;
        }
    }
}