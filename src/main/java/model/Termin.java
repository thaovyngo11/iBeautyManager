package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Termin {
    private String kundenname;
    private String telefonnummer;
    private LocalDate datum;
    private LocalTime uhrzeit;

    public Termin(String kundenname, String telefonnummer, LocalDate datum, LocalTime uhrzeit) {
        this.kundenname = kundenname;
        this.telefonnummer = telefonnummer;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
    }

    public String getKundenname() {
        return kundenname;
    }

    public String getTelefonummer() {
        return telefonnummer;
    }

    public LocalDate getdatum() {
        return datum;
    }

    public LocalTime getUhrzeit(){
        return uhrzeit;

    }




}
