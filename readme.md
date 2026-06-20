# REST-APIs mit Javalin

[API-Dokumentation](https://javalin.io/documentation)
[GitHub-Repository](https://github.com/EboUPrime/java_examples/tree/REST_APIs__HTTP-Statuscodes)



## Einfacher Java‑Webserver
Das folgende Minimalbeispiel zeigt, wie man mit wenigen Zeilen einen HTTP-Endpunkt bereitstellt:

```java
import io.javalin.Javalin;

public class Main {
    static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.routes.get("/", ctx -> ctx.result("Hello World"));
        }).start(7070);
        System.out.println("Server started on port 7070 ...");
        System.out.println("Press Ctrl+C to stop the server.");
        System.out.println("Visit http://localhost:7070 to see the result.");
    }
}
```

Dieses Beispiel startet einen Server auf Port 7070 und beantwortet GET-Anfragen an `/` mit dem Text "Hello World".

---

# REST-APIs

## REST — Eine Einführung für Entwickler

REST (Representational State Transfer) ist ein Architekturstil für die Kommunikation zwischen verteilten Systemen, insbesondere im Web. REST-APIs sind heute der de-facto-Standard für die Kommunikation zwischen Client und Server.

## Was ist REST?

REST wurde von Roy Fielding im Jahr 2000 in seiner Dissertation definiert. Es handelt sich nicht um ein Protokoll, sondern um einen **Architekturstil**, der auf dem HTTP-Protokoll aufbaut. Eine API, die den REST-Prinzipien folgt, nennt man **RESTful API**.

- [Roy Fielding](https://en.wikipedia.org/wiki/Roy_Fielding)

## Die 6 REST-Prinzipien

1. **Client-Server-Trennung** – Client und Server sind unabhängig voneinander. Der Client kümmert sich um die UI, der Server um die Datenhaltung.
2. **Zustandslosigkeit (Stateless)** – Jede Anfrage enthält alle nötigen Informationen. Der Server speichert keinen Sitzungszustand.
3. **Cachebarkeit** – Antworten können vom Client oder Zwischensystemen gecacht werden, sofern der Server dies erlaubt.
4. **Einheitliche Schnittstelle** – Ressourcen werden über einheitliche URIs und Methoden angesprochen.
5. **Schichtenarchitektur** – Zwischen Client und Server können Zwischenschichten (z. B. Load Balancer, Caches) existieren.
6. **Code on Demand (optional)** – Der Server kann ausführbaren Code (z. B. JavaScript) an den Client senden.

## HTTP-Methoden

REST nutzt die Standard-HTTP-Methoden für CRUD-Operationen:

| Methode  | Bedeutung           | Beispiel                    |
|----------|---------------------|-----------------------------|
| `GET`    | Ressource lesen     | `GET /users/1`              |
| `POST`   | Ressource erstellen | `POST /users`               |
| `PUT`    | Ressource ersetzen  | `PUT /users/1`              |
| `PATCH`  | Ressource anpassen  | `PATCH /users/1`            |
| `DELETE` | Ressource löschen   | `DELETE /users/1`           |

## Beispiel: REST-Endpunkte für eine Benutzerverwaltung

```
GET    /users          → Alle Benutzer abrufen
GET    /users/{id}     → Einzelnen Benutzer abrufen
POST   /users          → Neuen Benutzer erstellen
PUT    /users/{id}     → Benutzer vollständig ersetzen
PATCH  /users/{id}     → Benutzer teilweise aktualisieren
DELETE /users/{id}     → Benutzer löschen
```

## HTTP-Statuscodes

REST-APIs kommunizieren Ergebnisse über standardisierte HTTP-Statuscodes:

| Code | Bedeutung                    |
|------|------------------------------|
| 200  | OK – Anfrage erfolgreich     |
| 201  | Created – Ressource erstellt |
| 204  | No Content – Erfolg, kein Inhalt |
| 400  | Bad Request – Ungültige Anfrage |
| 401  | Unauthorized – Nicht authentifiziert |
| 403  | Forbidden – Kein Zugriff     |
| 404  | Not Found – Ressource nicht gefunden |
| 500  | Internal Server Error        |
| 503  | Service Unavailable          |

## Statuscodes Responsefälle
- **200 OK**: Die Anfrage war erfolgreich und die Antwort enthält die angeforderten Daten.
    - GET /users/1 → 200 OK + Benutzerinformationen
    - POST /user → 200 OK + Erstellte Benutzerinformationen
    - PUT /users/1 → 200 OK + Aktualisierte Benutzerinformationen
    - PATCH /users/1 → 200 OK + Aktualisierte Benutzerinformationen
    - DELETE /users/1 → 200 OK + Bestätigung der Löschung
- **201 Created**: Die Anfrage war erfolgreich und eine neue Ressource wurde erstellt.
    - POST /user → 201 Created + URI der neuen Ressource
    - PUT /users/1 → 201 Created + URI der neuen Ressource (wenn die Ressource vorher nicht existierte)
- **204 No Content**: Die Anfrage war erfolgreich, aber es gibt keine Daten zurückzugeben.
    - DELETE /users/1 → 204 No Content (wenn die Ressource erfolgreich gelöscht wurde)
    - PUT /users/1 → 204 No Content (wenn die Ressource erfolgreich aktualisiert wurde und keine Daten zurückgegeben werden)
    - PATCH /users/1 → 204 No Content (wenn die Ressource erfolgreich aktualisiert wurde und keine Daten zurückgegeben werden)
    - GET /users/1 → 204 No Content (wenn die Ressource existiert, aber keine Daten zurückgegeben werden sollen)
    - POST /user → 204 No Content (wenn die Ressource erfolgreich erstellt wurde und keine Daten zurückgegeben werden)
- **400 Bad Request**: Die Anfrage war ungültig oder konnte nicht verarbeitet werden.
    - POST /user → 400 Bad Request (wenn erforderliche Felder fehlen)
    - PUT /users/1 → 400 Bad Request (wenn die Anfrage ungültig ist)
    - PATCH /users/1 → 400 Bad Request (wenn die Anfrage ungültig ist)
- **401 Unauthorized**: Der Client ist nicht authentifiziert.
    - GET /users/1 → 401 Unauthorized (wenn Authentifizierung erforderlich ist)
    - POST /user → 401 Unauthorized (wenn Authentifizierung erforderlich ist)
    - PUT /users/1 → 401 Unauthorized (wenn Authentifizierung erforderlich ist)
    - PATCH /users/1 → 401 Unauthorized (wenn Authentifizierung erforderlich ist)
    - DELETE /users/1 → 401 Unauthorized (wenn Authentifizierung erforderlich ist)
- **403 Forbidden**: Der Client hat keine Berechtigung, auf die Ressource zuzugreifen.
    - GET /users/1 → 403 Forbidden (wenn der Client nicht die erforderlichen Berechtigungen hat)
    - POST /user → 403 Forbidden (wenn der Client nicht die erforderlichen Berechtigungen hat)
    - PUT /users/1 → 403 Forbidden (wenn der Client nicht die erforderlichen Berechtigungen hat)
    - PATCH /users/1 → 403 Forbidden (wenn der Client nicht die erforderlichen Berechtigungen hat)
    - DELETE /users/1 → 403 Forbidden (wenn der Client nicht die erforderlichen Berechtigungen hat)
- **404 Not Found**: Die angeforderte Ressource wurde nicht gefunden.
    - GET /users/999 → 404 Not Found (wenn der Benutzer mit ID 999 nicht existiert)
    - PUT /users/999 → 404 Not Found (wenn der Benutzer mit ID 999 nicht existiert)
    - PATCH /users/999 → 404 Not Found (wenn der Benutzer mit ID 999 nicht existiert)
    - DELETE /users/999 → 404 Not Found (wenn der Benutzer mit ID 999 nicht existiert)
- **500 Internal Server Error**: Ein unerwarteter Fehler ist auf dem Server aufgetreten.
    - GET /users/1 → 500 Internal Server Error (wenn ein Fehler bei der Verarbeitung der Anfrage auftritt)
    - POST /users → 500 Internal Server Error (wenn ein Fehler bei der Verarbeitung der Anfrage auftritt)
    - PUT /users/1 → 500 Internal Server Error (wenn ein Fehler bei der Verarbeitung der Anfrage auftritt)
    - PATCH /users/1 → 500 Internal Server Error (wenn ein Fehler bei der Verarbeitung der Anfrage auftritt)
- **503 Service Unavailable**: Der Server ist derzeit nicht verfügbar (z. B. wegen Wartungsarbeiten).
    - GET /users/1 → 503 Service Unavailable (wenn der Server vorübergehend nicht erreichbar ist)
    - POST /users → 503 Service Unavailable (wenn der Server vorübergehend nicht erreichbar ist)
    - PUT /users/1 → 503 Service Unavailable (wenn der Server vorübergehend nicht erreichbar ist)
    - PATCH /users/1 → 503 Service Unavailable (wenn der Server vorübergehend nicht erreichbar ist)

## Unterschiede
### zwischen 200 OK und 204 No Content
- **200 OK**: Die Anfrage war erfolgreich und die Antwort enthält die angeforderten Daten. Zum Beispiel, wenn ein Benutzer erfolgreich erstellt oder aktualisiert wurde, könnte die Antwort die Details des Benutzers enthalten.
- **204 No Content**: Die Anfrage war erfolgreich, aber es gibt keine Daten zurückzugeben. Dies wird oft verwendet, wenn eine Ressource erfolgreich gelöscht wurde oder wenn eine Aktualisierung erfolgreich war, aber keine weiteren Informationen zurückgegeben werden müssen.

### zwischen 201 Created und 204 No Content
- **201 Created**: Die Anfrage war erfolgreich und eine neue Ressource wurde erstellt. In diesem Fall sollte die Antwort die URI der neu erstellten Ressource enthalten, damit der Client sie direkt aufrufen kann.
- **204 No Content**: Die Anfrage war erfolgreich, aber es gibt keine Daten zurückzugeben. Dies wird oft verwendet, wenn eine Ressource erfolgreich gelöscht wurde oder wenn eine Aktualisierung erfolgreich war, aber keine weiteren Informationen zurückgegeben werden müssen.

### zwischen 400 Bad Request, 401 Unauthorized und 403 Forbidden
- **400 Bad Request**: Die Anfrage war ungültig oder konnte nicht verarbeitet werden. Dies könnte passieren, wenn erforderliche Felder fehlen oder die Daten ungültig sind.
- **401 Unauthorized**: Der Client ist nicht authentifiziert. Dies bedeutet, dass der Client keine gültigen Anmeldeinformationen bereitgestellt hat oder die Anmeldeinformationen ungültig sind.
- **403 Forbidden**: Der Client hat keine Berechtigung, auf die Ressource zuzugreifen. Dies bedeutet, dass der Client zwar authentifiziert ist, aber nicht die erforderlichen Berechtigungen hat, um auf die Ressource zuzugreifen.




## Fazit

REST-APIs sind der Grundbaustein moderner Webanwendungen und Microservices. Durch klare Konventionen, HTTP-Standards und Zustandslosigkeit sind sie leicht verständlich, skalierbar und gut wartbar. In Kombination mit Frameworks wie **Javalin** lassen sich RESTful APIs in Java schnell und übersichtlich umsetzen.

Dieses Projekt demonstriert HTTP‑Statuscodes mit einer kleinen Javalin‑REST‑API und einer interaktiven HTML‑Seite.



## Versionen

### Java
- Java Version: 21 (LTS)
- Empfehlung: Verwende Java 21 oder höher, um die neuesten Sprachfeatures und Verbesserungen zu nutzen.

### Javalin 7.2.2
- Aktuelle Version: 7.2.2 (Stand: Juni 2024)
- Weitere Informationen und Updates findest Du auf der offiziellen Javalin-Website: https://javalin.io
- 
### slf4j-simple
- Aktuelle Version: 2.0.17 (Stand: Juni 2024)
- Weitere Informationen und Updates findest Du auf der offiziellen SLF4J-Website: https://www.slf4j.org

### Jackson Databind
- Aktuelle Version: 2.21.2 (Stand: Juni 2024)
- Weitere Informationen und Updates findest Du auf der offiziellen Jackson-Website: https://www.tutorialspoint.com/jackson/jackson_data_binding.htm



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

Weitere Ressourcen
- Javalin-Dokumentation: https://javalin.io/documentation
- Jackson: https://github.com/FasterXML/jackson
- SLF4J: https://www.slf4j.org/

---

Keywords

REST, HTTP, HTTP-Statuscodes, Javalin, Java, CRUD, GET, POST, PUT, PATCH, DELETE, JSON, Jackson, SLF4J, Logging, Tutorial, API, Endpoint, Ressourcen, Zustandslosigkeit, Statuscode-Erklärung, 200 OK, 201 Created, 204 No Content, 400 Bad Request, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict, 500 Internal Server Error, 503 Service Unavailable, Content-Type, application/json, application/x-www-form-urlencoded, UTF-8, WordPress



---

## Schnellstart
- Mit Deiner IDE: Main-Klasse (`com.ebouprime.Main`) ausführen.
- Mit Maven (falls `exec-maven-plugin` konfiguriert ist):

Wie das Tutorial funktioniert
1) Öffne `/example.html` im Browser.
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

