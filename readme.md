# Javalin

[Javalin](https://javalin.io/) — Ein leichtgewichtiges Web-Framework für Java

## Ein kurzer Überblick
Javalin ist ein minimalistisches Java-Framework für Webanwendungen und REST-APIs. Es setzt auf Einfachheit, gute Performance und eine kleine, leicht verständliche API, die sich besonders für Microservices, Prototypen und kleine bis mittelgroße Webanwendungen eignet.

## Was ist Javalin?
Javalin basiert auf dem Jetty-Webserver und versucht, Entwickler durch eine flache API-Oberfläche schnell produktiv zu machen. Statt komplexer Vererbungsstrukturen oder vieler Konfigurationen bietet Javalin eine direkte, pragmatische Herangehensweise an Routing, Middleware und API-Dokumentation.

## Vorteile von Javalin

- Einfachheit: Sehr geringer Einstieg: keine abstrakten Basisklassen oder umfangreiche Konfigurationen nötig.
- Leichtgewichtig & performant: Kleine Codebasis, direkte Nutzung von Jetty für gute Laufzeit-Eigenschaften.
- Flexibilität: Synchroner Standardbetrieb mit einfacher Möglichkeit zur asynchronen Verarbeitung.
- OpenAPI-Integration: Unterstützung für OpenAPI/Swagger zur einfachen API-Dokumentation.

## Beispiel: Einfacher Java‑Webserver
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

## Beispiel: REST-API in Java mit Javalin

```java
import io.javalin.Javalin;

public class UserApi {
    static void main(String[] args) {
        Javalin app = Javalin.create(
            config -> {
                config.routes.get("/users", ctx -> ctx.json(UserService.getAll()));
                config.routes.get("/users/{id}", ctx -> {
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    ctx.json(UserService.getById(id));
                });
                config.routes.post("/users", ctx -> {
                    User user = ctx.bodyAsClass(User.class);
                    UserService.create(user);
                    ctx.status(201).json(user);
                });
                config.delete("/users/{id}", ctx -> {
                    int id = Integer.parseInt(ctx.pathParam("id"));
                    UserService.delete(id);
                    ctx.status(204);
                });
            }
        ).start(7070);
    }
}

```

```xml
<dependency>
    <groupId>io.javalin</groupId>
    <artifactId>javalin</artifactId>
    <version>7.2.2</version>
</dependency>
```

## Best Practices

- **Ressourcennamen im Plural** verwenden: `/users` statt `/user`
- **Versionierung** der API: `/api/v1/users`
- **JSON** als Standardformat für Anfrage- und Antwort-Body
- **Authentifizierung** via JWT oder OAuth 2.0
- **API-Dokumentation** mit OpenAPI/Swagger

## Fazit

REST-APIs sind der Grundbaustein moderner Webanwendungen und Microservices. Durch klare Konventionen, HTTP-Standards und Zustandslosigkeit sind sie leicht verständlich, skalierbar und gut wartbar. In Kombination mit Frameworks wie **Javalin** lassen sich RESTful APIs in Java schnell und übersichtlich umsetzen.

---

## Schnellstart
- Mit Deiner IDE: Main-Klasse (`com.ebouprime.Main`) ausführen.
- Mit Maven (falls `exec-maven-plugin` konfiguriert ist):

```bash
mvn compile exec:java -Dexec.mainClass="com.ebouprime.Main"
```

## Versionen
### Javalin 7.2.2
- Aktuelle Version: 7.2.2 (Stand: Juni 2024)
- Weitere Informationen und Updates findest Du auf der offiziellen Javalin-Website: https://javalin.io/

### Java
- Java Version: 21 (LTS) 
- Empfehlung: Verwende Java 21 oder höher, um die neuesten Sprachfeatures und Verbesserungen zu nutzen.