package controller;


    import model.Dienst;
    import model.Termin;
    import java.util.ArrayList;
    import java.util.List;
    import java.time.LocalDateTime;


public class TerminVerwaltung {

        private List<Termin> termine;

        // Konstruktor
        public TerminVerwaltung() {
            termine = new ArrayList<>();
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

        // Beispielobjekte erstellen
        public static List<Dienst> getAlleAngebot() {
            List<model.Dienst> angebot = new ArrayList<>();

            // Dienstobjekte
            Dienst massage = new Dienst("Massage", "Thai-Massage", 35.0);
            Dienst naegel = new Dienst("Nägel", "Mani- und Pediküre", 30.0);
            Dienst kosmetik = new Dienst("Kosmetik", "Wimpern- & Augenbrauen färben",15.0);

            // Terminobjekte mit beispielhaften Daten
            Termin emma = new Termin("Emma Schmidt", "0123456789", "Thai-Massage",);
            Termin jule = new Termin("Jule Müller", "0187654321", "Mani- und Pediküre",);
            Termin lena = new Termin("Lena Baumann", "0152223332","Wimpern- und Augenbrauenfärben",);


            // Termine zur Liste hinzufügen
            terminHinzufuegen(emma);
            terminHinzufuegen(jule);
            terminHinzufuegen(lena);
        }
    }



}