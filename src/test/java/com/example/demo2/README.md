# Evaluation der Integration von ArchUnit in unser Großprojekt

## Einleitung
ArchUnit ist eine Java-Bibliothek, die statische Analyse ermöglicht. Wir können damit Architekturregeln definieren und durchsetzen. Ziel dieser Evaluation ist es, die Vor- und Nachteile der Integration von ArchUnit in unser Projekt abzuwägen, um eine fundierte Entscheidung zu treffen.

## Vorteile und Nachteile

| **Vorteile**                                      | **Nachteile**                                       |
|---------------------------------------------------|-----------------------------------------------------|
| **Automatisierte Architektursicherung**           | **Zusätzlicher Initialaufwand**                     |
| Konsistenz und Wartbarkeit der Software durch klare Regeln. | Zeit und Aufwand für Einrichtung, besonders in großen Projekten. |
| **Früherkennung von Architekturverletzungen**     | **Lernkurve**                                       |
| Fehler früh im Entwicklungsprozess erkennen und beheben. | Entwickler müssen sich einarbeiten. |
| **Transparente Dokumentation**                    | **Performance-Einbußen möglich**                    |
| Regeln als lebendige Dokumentation erhöhen Transparenz. | In großen Projekten längere Build-Zeiten. |
| **Förderung von Best Practices**                  | **Komplexe Regelverwaltung**                        |
| Standards und bewährte Methoden im Team fördern. | Viele Regeln erhöhen Verwaltungsaufwand. |

## Einsatzmöglichkeiten
1. **Architekturkonformität sicherstellen**: Regeln definieren, um Abhängigkeitsstrukturen korrekt zu halten.
2. **Vermeidung von zyklischen Abhängigkeiten**: Zyklische Abhängigkeiten zwischen Paketen oder Klassen vermeiden.
3. **Einhaltung von Namenskonventionen**: Sicherstellen, dass Namenskonventionen eingehalten werden.
4. **Modularitätsprüfung**: Überprüfen, dass nur definierte Abhängigkeiten zwischen Modulen bestehen.

## Durchgeführte Tests

1. **Controller im Controller-Paket:**
   - **Test:** `controllersShouldResideInControllerPackage`
   - **Beschreibung:** Prüft, ob `@Controller`-Klassen im Paket `com.example.demo2..controller..` liegen.

2. **Repositories im Repository-Paket:**
   - **Test:** `repositoriesShouldResideInRepositoryPackage`
   - **Beschreibung:** Prüft, ob `@Repository`-Klassen im Paket `com.example.demo2..repository..` liegen.

3. **Entitäten im Domain-Paket:**
   - **Test:** `entitiesShouldResideInDomainPackage`
   - **Beschreibung:** Prüft, ob `@Entity`-Klassen im Paket `com.example.demo2..domain..` liegen.

4. **Zyklische Abhängigkeiten zwischen Paketen:**
   - **Test:** `noCyclicDependenciesBetweenPackages`
   - **Beschreibung:** Prüft, ob keine zyklischen Abhängigkeiten in `com.example.demo2` bestehen.

5. **Controller sollen nur andere Schichten verwenden:**
   - **Test:** `controllersShouldOnlyUseOtherLayers`
   - **Beschreibung:** Prüft, ob `@Controller`-Klassen nur von bestimmten Paketen abhängen (`java..`, `org.springframework..`, `com.example.demo2.domain..`, `com.example.demo2.repository..`).

6. **Repositories sollen nur andere Schichten verwenden:**
   - **Test:** `repositoriesShouldOnlyUseOtherLayers`
   - **Beschreibung:** Prüft, ob `@Repository`-Klassen nur von bestimmten Paketen abhängen (`java..`, `org.springframework..`, `com.example.demo2.domain..`).

7. **Domain-Klassen sollen nur andere Schichten verwenden:**
   - **Test:** `domainClassesShouldOnlyUseOtherLayers`
   - **Beschreibung:** Prüft, ob `@Entity`-Klassen nur von bestimmten Paketen abhängen (`java..`, `jakarta..`, `com.example.demo2.domain..`).

## Fazit
ArchUnit unterstützt unser Team durch:
- Automatisierte Architektursicherung und Transparenz
- Frühe Fehlererkennung
- Förderung von Best Practices

Die Herausforderungen umfassen:
- Initialaufwand und Lernkurve
- Mögliche Performance-Einbußen und komplexe Regelverwaltung

Insgesamt bietet ArchUnit viele Vorteile für unser Projekt, die den anfänglichen Aufwand rechtfertigen können. Es gibt eigentlich keine wirklichen Nachteile, welche begründen könnten, warum wir ArchUnit nicht in unserem Projekt integrieren sollten. Alleine der Punkt, dass Best Practices quasi automatisch gesichert werden, ist genug Vorteil. Individuelle Testerstellung ist auch möglich (hier nicht angewandt), sollte bei größeren Testfällen dann auch angewandt werden.

## Einsatz von PlantUML

Projektstruktur visualisiert in einem UML-Diagramm:
![UML-Diagramm der Projektstruktur](https://github.com/EricDirla/demo2/blob/master/architecture.png)
Generiert durch [PlantUML-Generator Maven-Plugin](https://github.com/devlauer/plantuml-generator)

PlantUML kann genutzt werden, um die Projektstruktur in UML-Diagramme zu visualisieren. Hierbei werden die Klassen, Repositories und Controllers in verschiedenen Paketen dargestellt. Die Architektur kann auch in einer einzelnen Datei gespeichert werden, um sie später in einem Projekt zu verwenden (architecture.puml).

Zum Testen der Architektur würde ich davon absehen, da PlantUML-Dateien keine semantischen Informationen wie z.B. Spring Boot Annotationen enthält. Mit regulären ArchUnit-Tests kann die Architektur in hinsicht auf die Funktionsweise der Komponenten in der Codebase geprüft werden, mit PlantUML können bloß die Namen der Klassen und deren Pakete geprüft werden (was dem Sinn von Architektursicherung widerspricht).
Mithilfe eines Postprocessors könnte die Architektur mit den Annotationen erweitert werden, ist aber unnütze Arbeit, da ArchUnit eh schon genutzt wird.

Beispiel für PlantUML Entität mit Spring Boot Annotationen:
```
@startuml

class com.example.demo2.domain.Users {
    {field} +password : String
    {field} -user_id : Integer
    {field} -username : String
    {method} +getId () : Integer
    {method} +getName () : String
    {method} +setId ( paramInteger1 : Integer ) : void
    {method} +setName ( paramString1 : String ) : void
}

class com.example.demo2.repository.UserRepository {
    {method}  {abstract} +save ( enitity : Users ) : void
    {method}  {abstract} +findByUsername ( paramString1 : String ) : java.util.Optional
}

Users : << (E, #FF7700) Entity >>
UserRepository : << (R, #00FF00) Repository >>

UserRepository --> Users : manages
@enduml
```

#### Aktuell:

1. Klassennamen werden aus PlantUML-Datei extrahiert
   - Methode ```parsePumlFile()``` sucht nach Klassendeklarationen

2.
   - Klassen werden anhand von Stereotypen kategorisiert
   - ```controller```, ```repositories``` oder ```entities```

3. Prüfung der Klassenpositionen
   - ```controllersShouldResideInControllerPackage```
   - ```repositoriesShouldResideInRepositoryPackage```
   - ```entitiesShouldResideInDomainPackage```

ArchUnit prüft nur, ob die Klassen, so wie sie in der ```architecture.puml``` dargestellt sind, in den richtigen Paketen liegen. Es wird nicht geprüft, ob die Klassen tatsächlich die entsprechenden Spring-Annotationen haben, also ob sie tatsächlich Controller, Repositories oder Entitäten sind.
