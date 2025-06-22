package controller;


import model.Dienst;
import model.Termin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TerminVerwaltungTest {

    @BeforeEach
    void setup() {
        TerminVerwaltung.initObjekte();
    }
    @Test
    void addTermin() {
        int vorher = TerminVerwaltung.getAlleTermine().size();

        Termin neuerTermin = new Termin(
                "Testkunde",
                "017612345678",
                List.of(), // leere Dienstliste
                java.time.LocalDateTime.of(2025, 6, 30, 12, 0)
        );

        TerminVerwaltung.addTermin(neuerTermin);

        List<Termin> termine = TerminVerwaltung.getAlleTermine();
        assertEquals(vorher + 1, termine.size());
        assertEquals("Testkunde", termine.get(termine.size() - 1).getKundenname());
    }

    @Test
    void getAlleTermine() {
        List<Termin> termine = TerminVerwaltung.getAlleTermine();
        assertEquals(3, termine.size()); // initObjekte() erstellt 3 Einträge
        assertEquals("Büsra", termine.get(0).getKundenname());
        assertEquals("Thao Vy", termine.get(1).getKundenname());
        assertEquals("Lara", termine.get(2).getKundenname());
    }

    @Test
    void filterNachTelefon() {
        List<Termin> gefiltert = TerminVerwaltung.filterNachTelefon("015209922581");
        assertEquals(1, gefiltert.size());
        assertEquals("Thao Vy", gefiltert.get(0).getKundenname());
    }

    @Test
    void initObjekte() {
        List<Termin> termine = TerminVerwaltung.getAlleTermine();
        assertEquals(3, termine.size());

        assertTrue(termine.stream().anyMatch(t -> t.getKundenname().equals("Büsra")));
        assertTrue(termine.stream().anyMatch(t -> t.getKundenname().equals("Thao Vy")));
        assertTrue(termine.stream().anyMatch(t -> t.getKundenname().equals("Lara")));
    }
}