package controller;


import model.Dienst;
import model.Termin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



public class TerminVerwaltung {

        private static List<Termin> termine;

        // Konstruktor
        public TerminVerwaltung() {
            termine = new ArrayList<>();
            initObjekte();
        }

        // Termin hinzufügen
        public boolean terminHinzufuegen(Termin termin) {
            return termine.add(termin);
        }

        // Termin löschen
        public boolean terminLoeschen(Termin termin) {
            return termine.remove(termin);
        }

        // Alle Termine abrufen
        public List<Termin> getAlleTermine() {
            return new ArrayList<>(termine);
        }

        // Nach Telefonnummer filtern
        public List<Termin> filterNachTelefon(String telefonnummer) {
            List<Termin> gefiltert = new ArrayList<>();
            for (Termin t : termine) {
                if (t.getTelefonnummer().equals(telefonnummer)) {
                    gefiltert.add(t);
                }
            }
            return gefiltert;
        }


        public void initObjekte() {
        List<Dienst> angebot1 = new ArrayList<>();

        angebot1.add(new Dienst("Massage", "Thai Massage", 35.0));

        Termin t1 = new Termin("Büsra", "015203793261", angebot1,
                LocalDateTime.of(2025, 5, 17, 10, 0 )
        );
        termine.add(t1);

        List<Dienst> angebot2 = new ArrayList<>();

        angebot2.add(new Dienst("Nägel", "Pediküre", 20.0));
        angebot2.add(new Dienst("Nägel", "Nagel Design", 30.0));

        Termin t2 = new Termin("Thao Vy", "015209922581", angebot2,
                LocalDateTime.of(2025, 5, 31, 9, 30 )
        );
        termine.add(t2);

        List<Dienst> angebot3 = new ArrayList<>();

        angebot3.add(new Dienst("Kosmetik", "Wimpernverlängerung", 80.0));
        angebot3.add(new Dienst("Nägel", "Nägel auffüllen", 28.0));

        Termin t3 = new Termin("Lara", "015202618469", angebot3,
                LocalDateTime.of(2025, 6, 2, 15, 20 )
        );
        termine.add(t3);
    }
}
