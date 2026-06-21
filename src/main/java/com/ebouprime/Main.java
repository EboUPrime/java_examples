package com.ebouprime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class Main {
    // Einfache, demonstrative Logger-Instanz für die ganze Klasse
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Demo: setze slf4j-simple Default-Log-Level auf TRACE (vor Logger-Erzeugung einstellen)
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");

        LOG.trace("Trace-Message: Start der Anwendung");
        LOG.debug("Debug-Message: Debug-Informationen verfügbar");
        LOG.info("Info-Message: Anwendung initialisiert");

        String appVersion = "1.0.0";
        // Parameterisiertes Logging (performant)
        LOG.info("Starte Beispiel-Anwendung, Version={}", appVersion);

        // MDC (kontextuelle Informationen) -- praktisches Beispiel
        MDC.put("requestId", "req-12345");
        try {
            createUser("max");
        } finally {
            MDC.remove("requestId");
        }

        // Beispiel: Exception-Logging
        try {
            createUserThatFails("will-fail");
        } catch (Exception e) {
            LOG.error("Erwarteter Fehler im Hauptprogramm: {}", e.getMessage(), e);
        }

        LOG.warn("Warnung: Demo beendet - keine Persistenz vorhanden");
        LOG.info("Beende Anwendung");
    }

    // Statische Hilfsfunktion: legt (simuliert) einen Benutzer an
    static void createUser(String username) {
        MDC.put("user", username);
        LOG.info("Erzeuge Benutzer: {}", username);

        if (username == null || username.isBlank()) {
            LOG.warn("Ungültiger Benutzername: '{}'", username);
            MDC.remove("user");
            return;
        }

        LOG.debug("Validierung abgeschlossen für Benutzer: {}", username);
        LOG.info("Benutzer '{}' erfolgreich angelegt (simuliert)", username);
        MDC.remove("user");
    }

    // Statische Hilfsfunktion: simuliert einen Fehler und wirft eine RuntimeException
    static void createUserThatFails(String username) {
        MDC.put("user", username);
        LOG.info("Erzeuge Benutzer (mit Fehler): {}", username);
        try {
            throw new IllegalStateException("Datenbank nicht erreichbar");
        } catch (RuntimeException re) {
            // Exception-Logging inkl. Stacktrace
            LOG.error("Fehler beim Anlegen von Benutzer {}: {}", username, re.getMessage(), re);
            throw re;
        } finally {
            MDC.remove("user");
        }
    }
}