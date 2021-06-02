# JBang basierte Client API Tests

## Preconditions

- Installation siehe https://github.com/jbangdev/jbang#installation. Für
  Window gibt es die Installationsvarianten mit
  [Chocolatey](https://chocolatey.org) oder [Scoop](https://scoop.sh),
  beides Opensource Package Manager für Windows ala RPM oder APT für
  Linux. Ich verwende / kenne [Chocolatey](https://chocolatey.org). Sehr
  praktisch mit einer sehr grossen Bibliothek
- Apg konformes Maven settings.xml in ~/.m2/


## Ausführung

1. Ohne Rest Server, dh. direkt auf Service Implementation

`./run.s direct`

2. Restful Server

Im Root directory des Projektes:

`./gradlew bootRun --info`

Und in neuer Shell in diesem Directory:

`./run.s rest`

## Implementation

Es werden Files mit Java Code geschrieben. Package-, File- namen etc
sind nicht relevant. Das Beispiel Script ist hier
simple-test-scenario.java](scripts/simple-test-scenario.java)

Das File hat einen JBang spezifischen Vorspann:
```
///usr/bin/env jbang
"$0" "$@" ; exit $? //REPOS
central=https://artifactory4t4apgsga.jfrog.io/artifactory/repo/ //DEPS
com.apgsga.testing.sample:api-testing-sample-client:0.5.1-SNAPSHOT
//DEPS org.junit.jupiter:junit-jupiter-api:5.5.2 //FILES logback.xml
./logback.xml 
```




## Offener Punkt

- [ ] Das "packaging" der JBang Scripts. Als Git Repository,
      höchstwahrscheinlich.
