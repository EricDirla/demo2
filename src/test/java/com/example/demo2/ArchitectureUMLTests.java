package com.example.demo2;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArchitectureUMLTests {

    private static PlantUMLParser.ParsedData parsedData;

    @BeforeAll
    static void init() throws Exception {
        PlantUMLParser parser = new PlantUMLParser();
        parsedData = parser.parse("C:\\Users\\Eric\\Desktop\\demo2\\architecture.puml");  // File path anpassen
    }

    @Test
    void checkDefinedClassesExist() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.example.demo2..");

        System.out.println("Classes from parsedData:");
        for (String className : parsedData.getClasses()) {
            System.out.println(className);
        }

        System.out.println("Imported classes:");
        importedClasses.forEach(javaClass -> System.out.println(javaClass.getName()));

        for (String fullyQualifiedClassName : parsedData.getClasses()) {
            boolean classExists = importedClasses.stream()
                .anyMatch(javaClass -> javaClass.getName().equals(fullyQualifiedClassName));
            assertThat(classExists)
                .as("Class " + fullyQualifiedClassName + " should exist in the codebase")
                .isTrue();
        }
    }

    @Test
    void checkRelationsAreRespected() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.example.demo2..");

        boolean anyClassesChecked = false;

        for (String[] relation : parsedData.getRelations()) {
            String fromClass = relation[0];
            String toClass = relation[1];

            try {
                ArchRuleDefinition.classes()
                    .that().haveNameMatching(fromClass)
                    .should().dependOnClassesThat().haveNameMatching(toClass)
                    .check(importedClasses);

                anyClassesChecked = true;
            } catch (AssertionError e) {
                System.out.println("Failed rule: " + fromClass + " -> " + toClass);
                System.out.println(e.getMessage());
                throw e;
            }
        }

        assertThat(anyClassesChecked).as("Mindestens ein Klassenprüfung sollte durchgeführt werden").isTrue();
    }
}
