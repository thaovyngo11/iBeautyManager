package model;


import java.util.ArrayList;
import java.util.List;

public class Dienst {

    /* Diese Klasse beschreibt eine Dienstleistung
       Zum Beispiel: Kategorie = "Massage", Angebot = "Thai Massage", Preis = 35.0 */

    // ATTRIBUTE
    private String dienst;
    private String angebot;
    private double preis;

    /* KONSTRUKTUR:

       Wenn wir ein neues Dienst-Objekt erstellen wollen,
       brauchen wir diese 3 Infos: Kategorie, Angebot, Preis. */

    public Dienst (String dienst, String angebot, double preis) {
        this.dienst = dienst;
        this.angebot = angebot;
        this.preis = preis;
    }

    /* GETTER- UND SETTER METHODE:

       Diese Methoden helfen uns, auf die privaten Daten (Dienst, Angebot, preis) zugreifen oder sie zu ändern.

       * Getter = Was steht drin?
       * Setter = Setze einen neuen Wert */

    public String getDienst (){
        return dienst;
    }

    public String getAngebot (){
        return angebot;
    }

    public double getPreis(){
        return preis;
    }

    /* METHODE:

       *Diese Methode ist "static",
        weil wir sie direkt über die Klasse aufrufen wollen,
        ohne zuerst ein Objekt von 'Dienst' zu erstellen.

       *Diese Methode gibt eine Liste mit Beispiel-Angeboten zurück (ArrayList)

       *Sie kann z.B. für Tabellen (JTable) genutzt werden */

    public static List<Dienst> getAlleAngebot() {
        List<Dienst> angebot = new ArrayList<>(); // Neue Liste erstellen

        //Massage-Angebote hinzufügt
        angebot.add(new Dienst("Massage", "Thai Massage", 35.0));
        angebot.add(new Dienst("Massage","Fuß Massage", 40.0));
        angebot.add(new Dienst("Massage", "Aromaöl Massage", 25.0));

        //Nägel-Angebote hinzufügt
        angebot.add(new Dienst("Nägel", "Pediküre", 20.0));
        angebot.add(new Dienst("Nägel","Maniküre", 10.0));
        angebot.add(new Dienst("Nägel", "Nagel Design", 30.0));
        angebot.add(new Dienst("Nägel","Nägel auffüllen", 28.0));
        angebot.add(new Dienst("Nägel","Nagelmodellage", 30.4));

        //Kosmetik-Angebote hinzufügt
        angebot.add(new Dienst("Kosmetik","Wimpernverlängerung", 80.0));
        angebot.add(new Dienst("Kosmetik", "Augebrauen und Wimpern färben", 15.0));
        angebot.add(new Dienst("Kosmetik","Wimpernwelle", 69.0));
        angebot.add(new Dienst("Kosmetik", "Augenbrauen zupfen", 15.0));

        // Rückgabe der vollständigen Liste aller Angebote
        return angebot;
    }
}
