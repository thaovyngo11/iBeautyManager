package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class DienstTest {

    @Test
    void getDienst() {
        Dienst d = new Dienst("Massage", "Thai Massage", 35.0);
        assertEquals("Massage", d.getDienst());
    }

    @Test
    void getAngebot() {
        Dienst d = new Dienst("Nägel", "Pediküre", 20.0);
        assertEquals("Pediküre", d.getAngebot());
    }

    @Test
    void getPreis() {
        Dienst d = new Dienst("Kosmetik", "Wimpernverlängerung", 80.0);
        assertEquals(80.0, d.getPreis());
    }

    @Test
    void getAlleAngebot() {
        List<Dienst> angebote = Dienst.getAlleAngebot();

        // Prüfe ob 12 Dienste enthalten sind
        assertEquals(12, angebote.size());

        // Prüfe ob ein konkretes Beispiel vorhanden ist
        boolean gefunden = false;
        for (Dienst d : angebote) {
            if (d.getDienst().equals("Massage") &&
                    d.getAngebot().equals("Thai Massage") &&
                    d.getPreis() == 35.0) {
                gefunden = true;
                break;
            }
        }

        assertTrue(gefunden);
    }
}