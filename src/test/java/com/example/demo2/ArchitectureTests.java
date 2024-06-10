package com.example.demo2;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Controller;

import jakarta.persistence.Entity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class ArchitectureTests {

    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("com.example.demo2");

    // Prüft, ob Controller im Controller-Paket liegen
    @Test
    public void controllersShouldResideInControllerPackage() {
        classes()
            .that().areAnnotatedWith(Controller.class)
            .should().resideInAnyPackage("com.example.demo2..controller..")
            .check(importedClasses);
    }

    // Prüft, ob Repositories im Repository-Paket liegen
    @Test
    public void repositoriesShouldResideInRepositoryPackage() {
        classes()
            .that().areAnnotatedWith(Repository.class)
            .should().resideInAPackage("com.example.demo2..repository..")
            .check(importedClasses);
    }

    // Prüft, ob Entitäten im Domain-Paket liegen
    @Test
    public void entitiesShouldResideInDomainPackage() {
        classes()
            .that().areAnnotatedWith(Entity.class)
            .should().resideInAnyPackage("com.example.demo2..domain..")
            .check(importedClasses);
    }

    // Prüft, ob zyklische Abhängigkeiten zwischen Paketen vorkommen
    @Test
    public void noCyclicDependenciesBetweenPackages() {
        slices().matching("com.example.demo2.(*)..")
            .should().beFreeOfCycles()
            .check(importedClasses);
    }

}
