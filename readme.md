# Javalin^[[link](https://javalin.io/)]
## Ein Web-Framework für Java
Javalin ist ein minimalistisches Web-Framework speziell für die Entwicklung von Webanwendungen und REST-APIs in Java. Es legt den Fokus auf Einfachheit, hohe Performance und eine klare, leicht verständliche Struktur.

## Was ist Javalin?
Javalin ist ein leichtgewichtiges Java-Framework, das auf dem Jetty-Webserver basiert. Es wurde entwickelt, um moderne Webentwicklung ohne unnötige Komplexität zu ermöglichen. Entwickler benötigen nur wenige Konzepte, um produktiv zu werden, da auf komplizierte Vererbungsstrukturen und umfangreiche Konfigurationen verzichtet wird.

Durch diese reduzierte Architektur eignet sich Javalin besonders gut für schnelle Projekte, Microservices und APIs.

## Vorteile von Javalin für Java

### Einfachheit
Javalin setzt auf eine klare und minimalistische API. Es ist nicht notwendig, Klassen zu erweitern oder komplexe Interfaces zu implementieren. Dadurch lässt sich der Einstieg deutlich beschleunigen.

### Leichtgewichtig und performant
Das Framework besteht aus wenigen tausend Codezeilen und basiert direkt auf Jetty. Dadurch ist die Performance vergleichbar mit nativen Serverlösungen.

### Flexibilität
Standardmäßig arbeitet Javalin synchron, kann jedoch bei Bedarf auch asynchron genutzt werden. Dadurch lässt sich das Framework flexibel an unterschiedliche Anforderungen anpassen.

### OpenAPI-Unterstützung
Javalin bietet integrierte Unterstützung für OpenAPI inklusive Tools wie Swagger UI. Damit können REST-APIs einfach dokumentiert und getestet werden.
## Beispiel: Einfacher Java-Webserver

Ein einfacher Webserver lässt sich mit wenigen Zeilen Code umsetzen:

```java

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.routes.get("/", ctx -> ctx.result("Hello World"));
        }).start(7070);
    }
}

```
