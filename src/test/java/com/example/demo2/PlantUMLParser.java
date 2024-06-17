package com.example.demo2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlantUMLParser {

    private static final String CLASS_REGEX = "class\\s+([\\w.]+)";
    private static final String PACKAGE_REGEX = "package\\s+([\\w.]+)\\s*\\{";
    private static final String RELATION_REGEX = "([\\w.]+)\\s*--\\|?>\\s*([\\w.]+)";

    public ParsedData parse(String filePath) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        String content = String.join("\n", lines);

        Map<String, String> classToPackage = extractClassToPackageMap(content);
        Set<String> classes = classToPackage.keySet();
        Set<String> packages = extractMatches(content, PACKAGE_REGEX);
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
            currentPackage = packageMatcher.group(1);
            packageStart = packageMatcher.end();

            int packageEnd = content.indexOf("}", packageStart);
            String packageContent = content.substring(packageStart, packageEnd);
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
        while (matcher.find()) {
            matches.add(matcher.group(1));
        }
        return matches;
    }

    private List<String[]> extractRelations(String content, Map<String, String> classToPackage) {
        Pattern pattern = Pattern.compile(RELATION_REGEX);
        Matcher matcher = pattern.matcher(content);
        List<String[]> relations = new ArrayList<>();
        while (matcher.find()) {
            String fromClass = matcher.group(1);
            String toClass = matcher.group(2);

            // Ensure fully qualified names
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

        public ParsedData(Set<String> classes, Set<String> packages, List<String[]> relations) {
            this.classes = classes;
            this.packages = packages;
            this.relations = relations;
        }

        public Set<String> getClasses() {
            return classes;
        }

        public Set<String> getPackages() {
            return packages;
        }

        public List<String[]> getRelations() {
            return relations;
        }
    }
}
