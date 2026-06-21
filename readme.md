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


## Versionen

### Java
- Java Version: 21 (LTS)
- Empfehlung: Verwende Java 21 oder höher, um die neuesten Sprachfeatures und Verbesserungen zu nutzen.

### Javalin 7.2.2
- Aktuelle Version: 7.2.2 (Stand: Juni 2024)
- Weitere Informationen und Updates findest Du auf der offiziellen Javalin-Website: https://javalin.io


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



## Schnellstart
- Mit Deiner IDE: Main-Klasse (`com.ebouprime.Main`) ausführen.
- Mit Maven (falls `exec-maven-plugin` konfiguriert ist):

```bash
mvn compile exec:java -Dexec.mainClass="com.ebouprime.Main"
```
