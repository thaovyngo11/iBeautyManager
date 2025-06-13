package model;


import java.time.LocalDateTime;
import java.util.List;


// Die Klasse Termin repräsentiert einen Termin mit Kundendaten, Angeboten und einem Zeitpunkt
public class Termin {
    // Attribute zur Speicherung von Kundendaten, Angebot und Datum/Zeit
    private String kundenname;
    private String telefonnummer;
    private List<Dienst> angebot;
    private LocalDateTime datumuhrzeit;


    // Attribute zur Speicherung von Kundendaten, Angebot und Datum/Zeit
    public Termin(String kundenname, String telefonnummer,List<Dienst> angebot, LocalDateTime datumuhrzeit) {
        this.kundenname = kundenname;
        this.telefonnummer = telefonnummer;
        this.angebot = angebot;
        this.datumuhrzeit = datumuhrzeit;
    }

    // Getter-Methode für den Kundennamen
    public String getKundenname() {
        return kundenname;
    }

    // Getter-Methode für die Telefonnummer
    public String getTelefonummer() {
        return telefonnummer;
    }

    // Getter-Methode für das Angebot (Liste von Diensten)
    public List<Dienst> getAngebot() {
        return angebot;
    }

    // Getter-Methode für Datum und Uhrzeit des Termins
    public LocalDateTime getdatumuhrzeit() {
        return datumuhrzeit;
    }

    // Methode zur Berechnung des Gesamtpreises aller Dienste im Angebot
    public double berechnenGesamtpreis(){
        double summe = 0;
        for (Dienst d : angebot) {
            summe += d.getPreis(); // Preis jedes Dienstes wird zur Gesamtsumme addiert
        }
        return summe;
    }




}
