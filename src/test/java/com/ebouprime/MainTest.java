package com.ebouprime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @AfterEach
    void cleanup() {
        // Sicherstellen, dass MDC nach jedem Test leer ist
        MDC.clear();
    }

    @Test
    void testCreateUser_success_cleansMdc() {
        // Aufruf darf keine Exception werfen
        Main.createUser("max");
        // MDC sollte nach dem Aufruf entfernt sein
        assertNull(MDC.get("user"), "MDC 'user' sollte nach createUser entfernt sein");
    }

    @Test
    void testCreateUser_emptyName_warnsAndCleansMdc() {
        Main.createUser("");
        assertNull(MDC.get("user"), "MDC 'user' sollte nach createUser mit leerem Namen entfernt sein");
    }

    @Test
    void testCreateUserThatFails_throws_and_cleansMdc() {
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> Main.createUserThatFails("will-fail"));
        assertEquals("Datenbank nicht erreichbar", ex.getMessage());
        assertNull(MDC.get("user"), "MDC 'user' sollte nach fehlgeschlagenem createUser entfernt sein");
    }

    @Test
    void testMain_executes_and_cleansMdc() {
        // main sollte ohne Exception enden
        Main.main(new String[0]);
        assertNull(MDC.get("requestId"), "MDC 'requestId' sollte nach main entfernt sein");
        assertNull(MDC.get("user"), "MDC 'user' sollte nach main entfernt sein");
    }
}

