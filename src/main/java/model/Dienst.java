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


    // Getter-Methode für das Angebot
    public String getAngebot (){
        return angebot;
    }


    // Getter-Methode für den Preis
    public double getPreis(){
        return preis;
    }


    public static List<Dienst> getAlleDienstleistungen() {
        List<Dienst> liste = new ArrayList<>();

                //Massage
                liste.add(new Dienst("Massage", "Thai Massage", 35.0));
                liste.add(new Dienst("Massage","Fuß Massage", 40.0));
                liste.add(new Dienst("Massage", "Aromaöl Massage", 25.0));

                //Nägel
                liste.add(new Dienst("Nägel", "Pediküre", 20.0));
                liste.add(new Dienst("Nägel","Maniküre", 10.0));
                liste.add(new Dienst("Nägel", "Nagel Design", 30.0));
                liste.add(new Dienst("Nägel","Nägel auffüllen", 28.0));
                liste.add(new Dienst("Nägel","Nagelmodellage", 30.4));

                //Kosmetik Angebote
                liste.add(new Dienst("Kosmetik","Wimpernverlängerung", 80.0));
                liste.add(new Dienst("Kosmetik", "Augebrauen und Wimpern färben", 15.0));
                liste.add(new Dienst("Kosmetik","Wimpernwelle", 69.0));
                liste.add(new Dienst("Kosmetik", "Augenbrauen zupfen", 15.0));

                return liste;
        }
    }
}
