package model;


import java.time.LocalDateTime;
import java.util.List;


public class Termin {

    /* Die Klasse "Termin" beschreibt einen Termin im System.
       Ein Termin besteht aus: Kundenname, Telefonnummer, Liste von Diensten, Datum und Uhrzeit. */

    // ATTRIBUTE

    public String kundenname;
    private String telefonnummer;
    private List<Dienst> dienstList;
    private LocalDateTime datum;

    // KONSTRUKTOR

    public Termin(String kundenname, String telefonnummer, List<Dienst> angebot, LocalDateTime datum) {
        this.kundenname = kundenname;
        this.telefonnummer = telefonnummer;
        this.dienstList = angebot;
        this.datum = datum;

    }

    // GETTER-METHODEN

    public String getKundenname() {

        return kundenname;
    }

    public String getTelefonnummer() {

        return telefonnummer;
    }

    public List<Dienst> getDienstList() {

        return dienstList;
    }

    public LocalDateTime getDatum() {

        return datum;
    }

    // METHODEN ZUR VERARBEITUNG

    /* Berechnet den Gesamtpreis des Termins (die Summe aller gewählten Dienstleistungen)

       Wir gehen die Liste mit einer for-each-Schleife durch und zählen die Preise zusammen.

       Zum Beispiel:
       Wenn 3 Dienste gebucht wurden mit Preisen 25.0, 15.0, 40.0 → ergibt sich Gesamtpreis = 80.0 */

    public double getGesamtpreis() {
        double summe = 0.0;
        for (Dienst d : dienstList) {
            summe = summe + d.getPreis();
        }
        return summe;
    }

}
