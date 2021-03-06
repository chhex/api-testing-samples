# Api Testing in Apg

## Agenda

- [ ] Integrations Testing in Apg
- [ ] Scope des Projektes
- [ ] Motivation Test Project
- [ ] Aktueller Stand Tool Kandidaten
- [ ] Weiteres Vorgehen / Nächste Schritte

## Integrations Testing Apg

Nicht abschließend / subjektiv

### Ansätze Testdaten Bereitstellung
- (a) : außerhalb der Tests gemanagte und vordefinierte Testdaten für
  einen applications Kontext (Light)
- (b) (automatisierte) Suche nach geeigneten Testdaten (Clones, Light)
- (c) (automatisiertes) Kreieren von Testdaten (Clones,
  Light)
- (d) Kombination von a) , b), c)

### Tests "End Points" Integrations Tests

- Db: F- und V- Schicht (View und Stored Procedures)
- Spring Service Komponenten: @Service - , @Component- Annotationen
- Spring Rest Controller: Tests gegen laufenden Container
- Spring Mock Rest Controller: Test gegen gemockte Container Environment
- Test Projekte

### Tooling

- Manuell via IT21 UI und PlSql Developer
- Manuell via PlSql Developer
- Windows Bat Scripts (sqlPlus)
- Diverses Java Tooling , siehe unten
- Jenkins Builds mit Java Tests
- Jenkins Pipeline mit Java Tests

#### Java Tooling

- [JUnit <= 4.x](https://junit.org/junit4/) : Java "Standard" mit
  Limitations
- [Junit >= 5 (Jupiter)](https://junit.org/junit5/) : Adressiert Junit 4
  an below Limitations
- [DbUnit](http://dbunit.sourceforge.net) : Vor allem fuer Datenbank
  zentrisches Testen
- [TestNg](https://testng.org/doc/) : Adressiert Junit 4 an below
  Limitation, Test Gruppe , Ablaeufe etc
- [Spring Boot Test](https://spring.io/guides/gs/testing-web/) : Spring Boot instrumentieren fuer JUnit Tests
- [Spock](https://spockframework.org) : Groovy basiertes Testframework,
  intuitiv , addressiert Adressiert Junit 4  an below Limitations

### Automatisiertes Testen von IT21 Funktionalitaet

TODO Ansätze / Ist Beschreiben / Analysieren Pro / Cons

PD2, Digiflex? , Faktura, weitere

als Input zur Tool Evalution und des weiteres Vorgehens

## Scope des Projektes

Wahl einer Toolchain, welches automatisierte Integrations Tests von IT21
Funktionalität, die auf dem [Rest Api](https://restfulapi.net) eines
Anwendungsgebietes aufsetzen.

Proof of Concept des Ansatzes anhand eines ausgewählten
Anwendungsgebietes.

Grundsätzlich baut das Projekt auf den Test Erfahrungen in der Apg auf.
Es kann mit den erarbeiteten Findings allenfalls Input zur Verbesserung
eines oder mehrerer der in der Apg etablierten Test Ansätze liefern. Es
wird aber nicht grundsätzlich an den immanenten Restriktionen 
ändern können.

Das Projekt soll jedoch die Erwartungen, respektive die Voraussetzungen
aus Sicht des Testens an die Restschnittstellen der Java Service
Projekte definieren.

## [Motivation dieses Test/Sample Projektes](https://github.com/chhex/api-testing-samples.git)

- Konzentration / Reduktion / Vereinfachung auf die das Wesentliche
- Was ist ein Rest API?
- Erste Tool Evaluation / Eindrücke
- Das Test Projekt ist einfach erweiterbar - um gegebenen Falls neue
  Anforderungen zu adressieren z. B. It21 Db statt H2

Das Projekt zeigt die verschiedenen Schichten in einem File:
[SimpleApplication.kt](./src/main/kotlin/com/apgsga/apitestingsample/SimpleApplication.kt)

- [Spring Boot Application](./src/main/kotlin/com/apgsga/apitestingsample/SimpleApplication.kt#L16-L19)
- [Rest Controller](./src/main/kotlin/com/apgsga/apitestingsample/SimpleApplication.kt#L25-L69)
- [Service Schicht](./src/main/kotlin/com/apgsga/apitestingsample/SimpleApplication.kt#L69-L102)
- [Datenbank Zugriffs Schicht](./src/main/kotlin/com/apgsga/apitestingsample/SimpleApplication.kt#L102-L108)
- [Datenbank Objekt](./src/main/kotlin/com/apgsga/apitestingsample/SimpleApplication.kt#L109)
- [Service Komponente](./src/main/kotlin/com/apgsga/apitestingsample/Calculator.kt#L8)

Aktuell wird die in Memory Db H2 verwendet. Es kann allenfalls die IT21
Db, das Schema Testutils verwendet werden.

Das Projekt zeigt unterschiedliche Testansätze respektive Methoden
Rest APIäs aufzurufen:

- (a)
  [Automatisierte Junit 5 / Spring Boot Tests](src/test/kotlin/com/apgsga/apitestingsample/IntegrationTests.kt#L18)
- (b)
  [Curl Scripts für alle Rest Api's der Applikation](./src/test/scripts)
- (c)
  [Gescriptetes spezifisches Testszenario](./src/test/ruby/testscenario.rb)

Persönliche Wertung
- (a) : Relativ viel Code für das Bootsstrappen und Erzeugen der
  Testdaten. Typesafeness ist in diesem Kontext fast ein obstacle.
- (c) : Da fehlt die „Logik". Mit Bash wird das schnell etwas mühsam.
- (d) : Nice, aber setzt Ruby Kenntnisse voraus, oder im mindesten ein
  polyglotter Entwickler

Aber man sieht, dass die grundsätzliche Automation von Rest Api's Aufrufen nicht zu
schwierig sein darf / ist: 

1. Pre-Conditions fuer den Test 
2. Konstruktion und Parametrisierung der Json Objekte, allenfalls auch via
Rest API Factory Methoden.
3. Aufruf des zu testenden Rest API url mit dem Json Objekt und allenfalls Path Variablen 
4. Post-Conditions Testen
5. Aufräumen 

(1) und allenfalls (5) ist wohl die grösste Herausforderung, aber ausserhalb des engeren Scope dieses Projektes 

## Tool Evaluation

###  Aktuelle Kandidatenliste

Quellen:
https://www.softwaretestinghelp.com/api-testing-tools/#1_ReadyAPI
https://medium.com/@alicealdaine/top-10-api-testing-tools-rest-soap-services-5395cb03cfa9

1. [Katalon Studio](https://www.katalon.com) : Kostenpflichtig
2. [SoupUI](https://www.soapui.org) : Kostenpflichtig
3. [Postman](https://www.postman.com) : Gratis, kostenpflichtige Version
4. [Tricentis Tosca](https://www.tricentis.com/products/) : Contract
   Sales
5. [Apigee](https://cloud.google.com/apigee/) : Kostenpflichtig
6. [JMeter](https://jmeter.apache.org) : Open Source
7. [Rest-Assured](https://rest-assured.io) : Open Source
8. [Assertible](https://assertible.com) : Gratis , kostenpflichtige Version
9. [Karate DSL](https://github.com/intuit/karate) : Open Source
10. [Swagger](https://swagger.io/) : Open Source
11. [Rest Console](https://github.com/ahmadnassri/restconsole)
12. [API Fortress](https://apifortress.com) : Contract Sales
13. [Pyresttest](https://github.com/svanoort/pyresttest) : Open Source
14. [Fiddler](https://www.telerik.com/fiddler) : Contract Sales
15. [Airborne](https://github.com/brooklynDev/airborne) : Open Source
16. [Accelq](https://www.accelq.com) : Contact Sales
17. [Helium](https://github.com/stanfy/helium) : Open Source
18. [rest-client](https://github.com/rest-client/rest-client) : Open
    Source

Was beim ersten Durchblick auffällt: Es gibt Tools, wie zb Swagger,
welches den ganzen Life-cycle abdecken , dh auch die Spezifikation und
das Entwickeln von Rest API's.

Im Weiteren gibt es solche, welche ein script bares Rest Client DSL
anbieten. Andere welche ein UI basiertes interaktives Zusammenstellen
von Rest Aufrufen erlauben.

Und natürlich Kombinationen

TODO Analyse Market some more

## Anforderungen

TODO - To be discussed
- Scope : stimmt dieser? 
- Zielpublikum (Entwickler vs Tester)
- Usage Scenarios
- Erwartungen
- Platform Prerequisites (Windows,WSL, etc)
- Kosten
- Licence
- Security
- Rest API va Db Schnittstellen - alles via Rest API?
- UI vs Scripting
- DSL allenfalls 
- Automation (Jenkins)
- Life - cycle , welches das Tool abdecken soll, nur Testen oder auch
  das Entwicklen
- Innerhalb / Ausserhalb des Entwickler IDE
- Eigenes UI


## Weiteres Vorgehen

DRAFT , TODO to be discussed , was , wer

1. Schrittweise Verfeinerung der Anforderungsspezifikation
2. Beschreiben Ist Stand von automatisierten funktionalen
   Integrationstests in der Apg
3. Reduktion der Kandidaten Liste, (Papier) (alle)
4. Verifikation Vollständigkeit (Papier)
5. Kurz-Evaluation, Demo und Reduktion auf 2 - 3 Kandidaten anhand
   Sample Projekt
6. Auswahl Testgegenstand / Anwendungsgebiet s 6.1 Analyse
   Integrationstests Ist vs Soll  
   6.2 Analyse Service API  
   6.3 Entwicklung Rest Controller
7. Tests Anwendungsgebiet mit Tool / Proof of Concept

## Offene Punkte

- [ ] Security resp Autorisierung von Rest API's, wie?: allenfalls in
      Sample Projekt einbauen
- [ ] Ist der Scope zu eng? Einige dieser Tools kommen von Performance- / Loadtesting 
- [ ] Entwicklung eigenes Test DSL - adaptiert an Apg Anforderungen :
      Option?
- [ ] Test Setup via Rest API oder direkt via JDBC
- [ ] Verbesserung / Vereinheitlichung Java basierter Tests
- [ ] Testen via Rest API vs direktes Testen auf der Datenbank
      Schnittstelle, allenfalls mit some Java/ Script glue darum herum
