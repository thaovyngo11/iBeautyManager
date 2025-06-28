
package model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/* Die Klasse TerminVerwaltung dient zur zentralen Verwaltung von Terminen.
   Sie speichert alle Termine, bietet Zugriff auf die Terminliste
   und ermöglicht das Filtern nach Telefonnummern.*/

    public class TerminVerwaltung {

    /* Die Klasse funktioniert wie eine Verwaltungszentrale für alle gebuchten Termine:
       Sie ist für das Speichern, Abrufen und Filtern von Terminen zuständig. */

        // ATTRIBUTE: Statische Liste zur Speicherung aller Termine
        private static List<Termin> termine = new ArrayList<>();

        public TerminVerwaltung() {

        }
        /*Fügt einen neuen Termin zur Terminliste hinzu.*/
        public static void addTermin(Termin termin) {
            termine.add(termin);
        }

        public static List<Termin> getAlleTermine() {
            return new ArrayList<>(termine);
        }

        public static List<Termin> filterNachTelefon(String telefonnummer) {
            List<Termin> gefiltert = new ArrayList<>();
            for (Termin t : termine) {
                if (t.getTelefonnummer().equalsIgnoreCase(telefonnummer)) {
                    gefiltert.add(t);
                }
            }
            return gefiltert;
        }

        public static void initObjekte() {
            termine.clear();
            List<Dienst> angebot1 = new ArrayList<>();

            angebot1.add(new Dienst("Massage", "Thai Massage", 35.0));

            Termin t1 = new Termin("Büsra", "015203793261", angebot1,
                    LocalDateTime.of(2025, 5, 17, 10, 0)
            );
            termine.add(t1);

            List<Dienst> angebot2 = new ArrayList<>();

            angebot2.add(new Dienst("Nägel", "Pediküre", 20.0));
            angebot2.add(new Dienst("Nägel", "Nagel Design", 30.0));

            Termin t2 = new Termin("Thao Vy", "015209922581", angebot2,
                    LocalDateTime.of(2025, 5, 31, 9, 30)
            );
            termine.add(t2);

            List<Dienst> angebot3 = new ArrayList<>();

            angebot3.add(new Dienst("Kosmetik", "Wimpernverlängerung", 80.0));
            angebot3.add(new Dienst("Nägel", "Nägel auffüllen", 28.0));

            Termin t3 = new Termin("Lara", "015202618469", angebot3,
                    LocalDateTime.of(2025, 6, 2, 15, 20)
            );
            termine.add(t3);
        }

    }
