package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TerminTest {

    @Test
    void getKundenname() {
        Termin t = new Termin("Büsra", "015203793261",
                List.of(new Dienst("Massage", "Thai Massage", 35.0)),
                LocalDateTime.of(2025, 5, 17, 10, 0));
        assertEquals("Büsra", t.getKundenname());
    }

    @Test
    void getTelefonnummer() {
        Termin t = new Termin("Thao Vy", "015209922581",
                List.of(
                        new Dienst("Nägel", "Pediküre", 20.0),
                        new Dienst("Nägel", "Nagel Design", 30.0)
                ),
                LocalDateTime.of(2025, 5, 31, 9, 30));
        assertEquals("015209922581", t.getTelefonnummer());
    }

    @Test
    void getDienstList() {
        List<Dienst> dienste = new ArrayList<>();
        dienste.add(new Dienst("Kosmetik", "Wimpernverlängerung", 80.0));
        dienste.add(new Dienst("Nägel", "Nägel auffüllen", 28.0));

        Termin t = new Termin("Lara", "015202618469", dienste,
                LocalDateTime.of(2025, 6, 2, 15, 20));

        assertEquals(2, t.getDienstList().size());
        assertEquals("Wimpernverlängerung", t.getDienstList().get(0).getAngebot());
        assertEquals("Nägel auffüllen", t.getDienstList().get(1).getAngebot());
    }

    @Test
    void getDatum() {
        LocalDateTime datum = LocalDateTime.of(2025, 6, 2, 15, 20);
        Termin t = new Termin("Lara", "015202618469",
                List.of(
                        new Dienst("Kosmetik", "Wimpernverlängerung", 80.0),
                        new Dienst("Nägel", "Nägel auffüllen", 28.0)
                ),
                datum);
        assertEquals(datum, t.getDatum());
    }

    @Test
    void getGesamtpreis() {
        List<Dienst> dienste = new ArrayList<>();
        dienste.add(new Dienst("Nägel", "Pediküre", 20.0));
        dienste.add(new Dienst("Nägel", "Nagel Design", 30.0));

        Termin t = new Termin("Thao Vy", "015209922581", dienste,
                LocalDateTime.of(2025, 5, 31, 9, 30));

        assertEquals(50.0, t.getGesamtpreis());
    }
}