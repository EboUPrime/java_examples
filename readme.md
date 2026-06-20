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
        Javalin app = Javalin.create().start(7070);
        app.get("/", ctx -> ctx.result("Hello World"));
    }
}
```

Dieses Beispiel startet einen Server auf Port 7070 und beantwortet GET-Anfragen an `/` mit dem Text "Hello World".

## Maven-Dependency (Beispiel)
Füge die Javalin-Abhängigkeit in Deine `pom.xml` ein. Prüfe die aktuelle Version auf https://javalin.io/ oder in Maven Central.

```xml
<dependency>
  <groupId>io.javalin</groupId>
  <artifactId>javalin</artifactId>
  <version>5.x.x</version> <!-- aktuelle Version prüfen -->
</dependency>
```

## Schnellstart
- Mit Deiner IDE: Main-Klasse (`com.ebouprime.Main`) ausführen.
- Mit Maven (falls `exec-maven-plugin` konfiguriert ist):

```bash
mvn compile exec:java -Dexec.mainClass="com.ebouprime.Main"
```

Viel Erfolg beim Entwickeln mit Javalin! Bei Bedarf kann ich die Readme noch um Hinweise zu Middleware, Routing-Buildern oder OpenAPI/Swagger UI erweitern.
