# JBang basierte Client API Tests

## Preconditions

- Installation siehe die
  [JBang installations Anleitung](https://github.com/jbangdev/jbang#installation).
  Für Windows gibt es die Installationsvarianten mit
  [Chocolatey](https://chocolatey.org) oder [Scoop](https://scoop.sh),
  beides sind Opensource Package Manager für Windows ala RPM oder APT
  für Linux. Ich verwende / kenne [Chocolatey](https://chocolatey.org).
  Sehr praktisch mit einer sehr grossen Bibliothek
- Apg konformes Maven settings.xml in ~/.m2/


## Implementation

Es werden Files mit Java Code geschrieben. Die Package- respective File-
namen sind allerdings nicht relevant. Das Beispiel Script ist hier
[simple-test-scenario.java](scripts/simple-test-scenario.java)

### JBang

Das File hat aber einen JBang spezifischen Vorspann:

![jbang_vor.png](screenshots/jbang_vor.png)

wobei

- (a) Maven Repository von welchem, die Dependencies geholt werden: Apg
  standard read-only Maven Repository. Setzt das settings.xml voraus,
  von wo der User und Passwort für das "central"  Repository definiert
  sind
- (b) Dependencies fur das script als normale Maven Koordinaten. Der
  Client hier ist als fat/uber JAR publiziert, siehe auch
  [build.gradle](../../client/build.gradle), dh es beinhaltet alle
  Dependencies, welches der Client braucht.
- (c) Es können zusätzliche Files für den Klassenpfad definiert werden
- (d) shebang, damit das script auch direkt ausführt werden kann.
  Bemerkung: Funktioniert bei mir nicht

Für weitere Dokumentation zu JBang, siehe den
[Github Site](https://github.com/jbangdev/jbang)

Bemerkung: JBang ist relative neu : V 0.71.1. Ist noch in Entwicklung

### Java Code

Die effektive Implementation, siehe [simple-test-scenario.java: Lines
22-58](scripts/simple-test-scenario.java#L22-L58) respective Logik, wird
in reinem Java geschrieben.


### Ausführung

Generell wird das File mit JBang ausgeführt, siehe das Script
[run.sh](run.sh)

![run.png](screenshots/run.png)

- (A) : The JBang Aufruf des Scripts mit zwei applikatorischen
  Parametern
- (B) : ["Workaround"](https://github.com/jbangdev/jbang/issues/902) für
  nicht standard Locations des lokalen Maven Repositories. Ist vor allem
  für die Entwicklung mit SNAPSHOT Dependencies wichtig, wenn nicht
  remote publiziert wird. Bei Bedarf allenfalls ent – kommentieren und
  updaten

Dh. Es muss nicht mit javac gebaut werden und als Jar publiziert und
deployt werden. Das Bauen und Packaging von Java Programmen ist mit
JBang nicht mehr notwendig.

Das konkrete script hat zwei Parameter:

- $1 : Modus: direct / rest
- $2 : baseUrl , zb http://localhost:8080

d.h zwei Ausführungsvarianten

1. Ohne Rest Server, dh. direkt auf der Service Implementation.

`./run.sh direct`

1. Gegen den Rest Server

Im Root directory des Projektes:

`./gradlew bootRun --info`

Und in einer neuer Shell in diesem Directory:

`./run.sh rest`

oder z. B.

`./run.sh rest http://localhost:8080`


Auf Windows analog mit Powershell oder cmd.exe


## Offener Punkt

- [ ] Das packaging der JBang Test Scripts. Als Git Repository,
      höchstwahrscheinlich.
