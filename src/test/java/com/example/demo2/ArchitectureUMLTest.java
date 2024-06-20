package com.example.demo2;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArchitectureUMLTest {

    private static final String PUML_FILE_PATH = "architecture.puml";
    private static JavaClasses importedClasses;
    private static List<String> controllers = new ArrayList<>();
    private static List<String> repositories = new ArrayList<>();
    private static List<String> entities = new ArrayList<>();

    @BeforeAll
    public static void setup() throws IOException {
        importedClasses = new ClassFileImporter().importPackages("com.example.demo2");

        parsePumlFile();
    }

    @Test
    public void controllersShouldResideInControllerPackage() {
        controllers.forEach(controller -> 
            ArchRuleDefinition.classes()
                .that().haveFullyQualifiedName(controller)
                .should().resideInAnyPackage("com.example.demo2.controller..")
                .check(importedClasses)
        );
    }

    @Test
    public void repositoriesShouldResideInRepositoryPackage() {
        repositories.forEach(repository -> 
            ArchRuleDefinition.classes()
                .that().haveFullyQualifiedName(repository)
                .should().resideInAnyPackage("com.example.demo2.repository..")
                .check(importedClasses)
        );
    }

    @Test
    public void entitiesShouldResideInDomainPackage() {
        entities.forEach(entity -> 
            ArchRuleDefinition.classes()
                .that().haveFullyQualifiedName(entity)
                .should().resideInAnyPackage("com.example.demo2.domain..")
                .check(importedClasses)
        );
    }

    private static void parsePumlFile() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(PUML_FILE_PATH));
        
        Pattern classPattern = Pattern.compile("class\\s+(\\S+)");
        Pattern interfacePattern = Pattern.compile("interface\\s+(\\S+)");
        Pattern stereotypePattern = Pattern.compile("<<\\s*(\\S+)\\s*>>");

        String currentClass = null;
        for (String line : lines) {
            Matcher classMatcher = classPattern.matcher(line);
            Matcher interfaceMatcher = interfacePattern.matcher(line);

            if (classMatcher.find()) {
                currentClass = classMatcher.group(1);
            } else if (interfaceMatcher.find()) {
                currentClass = interfaceMatcher.group(1);
            } else if (currentClass != null) {
                Matcher stereotypeMatcher = stereotypePattern.matcher(line);
                if (stereotypeMatcher.find()) {
                    String stereotype = stereotypeMatcher.group(1).toLowerCase();
                    switch (stereotype) {
                        case "(c,":
                        case "controller":
                            controllers.add(currentClass);
                            break;
                        case "(r,":
                        case "repository":
                            repositories.add(currentClass);
                            break;
                    }
                    currentClass = null;
                }
            }
        }
    }
}
