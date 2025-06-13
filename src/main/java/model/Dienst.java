package model;


import java.util.ArrayList;
import java.util.List;

// Die Klasse Dienst repräsentiert eine Dienstleistung mit Namen, Kategorie (Angebot) und Preis
public class Dienst {
    // Attribute zur Speicherung von Namen des Dienstes, Angebot und Preis
    private String dienst;
    private String angebot;
    private double preis;

    // Konstruktor, um ein Termin-Objekt mit Werten zu erstellen
    public Dienst (String dienst, String angebot, double preis) {
        this.dienst = dienst;
        this.angebot = angebot;
        this.preis = preis;
    }

    // Getter-Methode für den Dienstnamen
    public String getDienst (){
        return dienst;
    }

    // Setter-Methode für den Dienstnamen
    public void setDienst (String dienst) {
        this.dienst = dienst;
    }

    // Getter-Methode für das Angebot
    public String getAngebot (){
        return angebot;
    }

    // Setter-Methode für das Angebot (Kategorie)
    public void setAngebot (String Angebot) {
        this.angebot = angebot;
    }

    // Getter-Methode für den Preis
    public double getPreis(){
        return preis;
    }

    // Setter-Methode für den Preis
    public void setPreis (double preis) {
        this.preis = preis;
    }

    public static String[] getAlleAngebote(){
        return new String[] {

                //Massage Angebote
                "Tai Massage",
                "Fuß Massage",
                "Aromaöl Massage",

                //Nägel Angebote
                "Pediküre",
                "Maniküre",
                "Nagel Design",
                "Nägel auffüllen",
                "Nagelmodellage",

                //Kosmetik Angebote
                "Wimpernverlängerung",
                "Augebrauen und Wimpern färben",
                "Wimpernwelle",
                "Augenbrauen zupfen"
        };
    }
}
