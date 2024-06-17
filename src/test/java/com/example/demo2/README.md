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

![UML-Diagramm der Projektstruktur](architecture.png)