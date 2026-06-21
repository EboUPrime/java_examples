
# Simple Logging Facade for Java (SLF4J)

[SLF4J](https://www.slf4j.org) | [GitHub Repository](https://github.com/EboUPrime/java_examples/tree/slf4j)

slf4j ist eine Schnittstelle für verschiedene Logging-Frameworks (z.B. Log4j, java.util.logging, Logback)
ermöglicht es, das Logging-Framework zur Laufzeit auszuwählen, ohne den Code zu ändern
bietet eine einheitliche API für das Logging, unabhängig vom verwendeten Framework
unterstützt verschiedene Log-Level (TRACE, DEBUG, INFO, WARN, ERROR)
ermöglicht die Verwendung von Platzhaltern in Log-Nachrichten (z.B. `logger.info("User {} logged in", username);`)
bietet Unterstützung für Mapped Diagnostic Context (MDC) und Nested Diagnostic Context (NDC) für die Kontextualisierung von Log-Nachrichten
ermöglicht die Integration mit verschiedenen Build-Tools (z.B. Maven, Gradle) und Logging-Frameworks (z.B. Logback, Log4j)
ist weit verbreitet und wird von vielen Java-Projekten verwendet, um eine flexible und einheitliche Logging-Lösung zu bieten.


## Glossar
- **Logging**: Das Aufzeichnen von Informationen über die Ausführung eines Programms, um Fehler zu diagnostizieren oder den Betrieb zu überwachen.
- **SLF4J**: Simple Logging Facade for Java, eine Schnittstelle für verschiedene Logging-Frameworks.
- **Log-Level**: Verschiedene Stufen der Log-Nachrichten (z.B. TRACE, DEBUG, INFO, WARN, ERROR), die die Wichtigkeit der Nachricht angeben.
- **Platzhalter**: Eine Möglichkeit, Log-Nachrichten mit variablen Daten zu formatieren, z.B. `logger.info("User {} logged in", username);`.
- **MDC (Mapped Diagnostic Context)**: Ein Mechanismus, um zusätzliche Informationen in Log-Nachrichten zu speichern, die für die Diagnose von Problemen nützlich sein können.
- **NDC (Nested Diagnostic Context)**: Ein Mechanismus, um verschachtelte Kontextinformationen in Log-Nachrichten zu speichern, z.B. bei der Verarbeitung von Anfragen in einem Webserver.

## Versionen

### Java
- Java Version: 21 (LTS)
- Empfehlung: Verwende Java 21 oder höher, um die neuesten Sprachfeatures und Verbesserungen zu nutzen.
- 
### slf4j-simple
- Aktuelle Version: 2.0.17 (Stand: Juni 2024)
- Weitere Informationen und Updates findest Du auf der offiziellen SLF4J-Website: https://www.slf4j.org

## Verwendete Abhängigkeiten
In diesem Projekt werden folgende Bibliotheken verwendet (siehe auch `pom.xml`). Hier ist eine kurze Erklärung, warum sie gebraucht werden und welche Version konkret eingesetzt ist.

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