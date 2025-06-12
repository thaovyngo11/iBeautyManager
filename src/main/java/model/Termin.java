package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Termin {
    private String kundenname;
    private String telefonnummer;
    private Dienst[] dienste;
    private LocalDate datum;
    private LocalTime uhrzeit;

    public Termin(String kundenname, String telefonnummer,Dienst[] dienste, LocalDate datum, LocalTime uhrzeit) {
        this.kundenname = kundenname;
        this.telefonnummer = telefonnummer;
        this.dienste = dienste;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
    }

    public String getKundenname() {
        return kundenname;
    }

    public String getTelefonummer() {
        return telefonnummer;
    }

    public Dienst[] getDienste() {
        return dienste;
    }

    public LocalDate getdatum() {
        return datum;
    }

    public LocalTime getUhrzeit(){
        return uhrzeit;
    }

    public double berechnenGesamtpreis(){
        double summe = 0;
        for (Dienst d : dienste) {
            summe += d.getPreis();
        }
        return summe;
    }




}
