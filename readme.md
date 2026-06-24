# REST-APIs mit Javalin 2

[API-Dokumentation](https://javalin.io/documentation)
[GitHub-Repository](https://github.com/EboUPrime/java_examples/tree/REST_APIs)

## Methoden der REST-API

### GET
- Zweck: Daten abrufen (z. B. Liste aller Benutzer oder Details eines einzelnen Benutzers).
- Beispiel: `GET /users` → Liste aller Benutzer; `GET /users/{id}` → Details eines Benutzers mit der angegebenen ID.
- Statuscodes:
  - 200 OK: Erfolgreiche Abfrage, Daten werden zurückgegeben.
  - 404 Not Found: Benutzer mit der angegebenen ID existiert nicht.

## Fazit

REST-APIs sind der Grundbaustein moderner Webanwendungen und Microservices. Durch klare Konventionen, HTTP-Standards und Zustandslosigkeit sind sie leicht verständlich, skalierbar und gut wartbar. In Kombination mit Frameworks wie **Javalin** lassen sich RESTful APIs in Java schnell und übersichtlich umsetzen.

Dieses Projekt demonstriert HTTP‑Statuscodes mit einer kleinen Javalin‑REST‑API und einer interaktiven HTML‑Seite.



## Versionen

### Java
- Java Version: 21 (LTS)
- Empfehlung: Verwende Java 21 oder höher, um die neuesten Sprachfeatures und Verbesserungen zu nutzen.

### Javalin 7.2.2
- Aktuelle Version: 7.2.2 (Stand: Juni 2024)
- Weitere Informationen und Updates findest Du auf der offiziellen Javalin-Website: [javalin](https://javalin.io)

### slf4j-simple
- Aktuelle Version: 2.0.17 (Stand: Juni 2024)
- Weitere Informationen und Updates findest Du auf der offiziellen SLF4J-Website: [slf4j](https://www.slf4j.org)

### Jackson Databind
- Aktuelle Version: 2.21.2 (Stand: Juni 2024)
- Weitere Informationen und Updates findest Du auf der offiziellen Jackson-Website: [Jackson Databind](https://www.tutorialspoint.com/jackson/jackson_data_binding.htm)



## Verwendete Abhängigkeiten
In diesem Projekt werden folgende Bibliotheken verwendet (siehe auch `pom.xml`). Hier ist eine kurze Erklärung, warum sie gebraucht werden und welche Version konkret eingesetzt ist.

- `io.javalin:javalin:7.2.2`
  - Zweck: Leichtgewichtiges Web-Framework zum schnellen Erstellen von HTTP-Endpunkten und REST-APIs. Javalin bietet eine einfache API für Routing, Middleware, Request/Response-Handling und Serverseitiges Rendering.
  - Warum diese Version: 7.2.2 ist die zum Projektzeitpunkt verwendete stabile Version. Javalin entwickelt sich aktiv weiter — bei Updates auf neue Major-Releases auf Breaking-Changes achten.
  - Hinweise: Javalin nutzt intern einen Servlet-Container (standardmäßig Jetty) und ist für Lern- und Demo-Projekte ideal.

```xml
<dependency>
    <groupId>io.javalin</groupId>
    <artifactId>javalin</artifactId>
    <version>7.2.2</version>
</dependency>
```

- `org.slf4j:slf4j-simple:2.0.17`
  - Zweck: SLF4J ist eine Logging-Abstraktion; `slf4j-simple` ist eine einfache, konsolenorientierte Implementierung von SLF4J. Sie schreibt Logs direkt auf stdout/stderr und ist einfach zu konfigurieren.
  - Warum diese Version: 2.0.17 passt zur SLF4J-API-Version und ist für Demo- und Entwicklungszwecke ausreichend. In Produktionsprojekten empfiehlt sich eine robustere Implementierung (z. B. Logback) und eine konfigurierte Logging-Policy.
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>2.0.17</version>
</dependency>
```
- `com.fasterxml.jackson.core:jackson-databind:2.21.2`
  - Zweck: JSON-Serialisierung und -Deserialisierung (Object ↔ JSON). Jackson wird verwendet, um Request-Bodies zu parsen und Java-Objekte als JSON zurückzugeben.
  - Warum diese Version: 2.21.2 ist die im Projekt verwendete Version. Jackson erhält regelmäßig Sicherheitsupdates — bei produktivem Einsatz sollte man Security-Bulletins prüfen und zeitnah aktualisieren.
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.21.2</version>
</dependency>
```

Kurz: Diese Abhängigkeiten sind absichtlich minimal gehalten — sie reichen für ein kleines Lernprojekt und ein interaktives Browser-Tutorial aus. Für grössere Projekte oder Produktionsempfehlungen siehe die jeweiligen Projektseiten und Sicherheits-Notes.

---

## Schnellstart
- Mit Deiner IDE: Main-Klasse (`com.ebouprime.Main`) ausführen.
- Mit Maven (falls `exec-maven-plugin` konfiguriert ist):

Wie das Tutorial funktioniert
1) Öffne `http://localhost:7070/example.html` im Browser.
2) Im Tutorial‑Abschnitt wählst Du:
    - Methode (GET/POST/PUT/PATCH/DELETE)
    - ID (oder die Option, eine existierende ID automatisch zu wählen)
    - Payload‑Szenario: `full` (vollständige Felder), `missing` (fehlende Felder), `none` (kein Body)
    - Content‑Type: `application/json` oder `application/x-www-form-urlencoded` (für POST/PUT/PATCH)
3) Klicke `Predict` — die Seite fragt `/users` und zeigt eine begründete Vorhersage (z. B. 201, 409, 404) sowie die Schritte, wie die Vorhersage entsteht.
4) Klicke `Run` — die Anfrage wird ausgeführt, und du erhältst den tatsächlichen HTTP‑Status und die Antwort (Actual). Vergleiche Predicted vs Actual.

Beispiel‑Szenarien
- POST mit bereits vorhandener ID → Predicted: 409 Conflict; Actual: 409 + Text "User with id X already exists".
- POST mit neuer ID + vollständigen Feldern → Predicted: 201 Created; Actual: 201 + JSON des neuen Users.
- PUT auf bestehende ID mit vollständigen Feldern → Predicted: 200 OK; Actual: 200 + JSON.
- DELETE auf bestehende ID → Predicted: 204 No Content; Actual: 204 (kein Body).


---

## Keywords
REST APIs Javalin Java Benutzerverwaltung HTTP GET Methoden Statuscodes API-Dokumentation Benutzerliste Benutzerdetails Fehlerbehandlung Datenabruf

## Meta‑Description
Ein einfaches REST-API-Projekt mit Javalin zur Demonstration von HTTP-Methoden und Statuscodes. Enthält Beispiele für Benutzerverwaltung und Fehlerbehandlung.
