package model;

import javax.naming.Name;

public class Dienst {
    private String name;
    private double preis;

    public Dienst (String name, double preis) {
        this.name = name;
        this.preis = preis;
    }

    public String getName (){
        return name;
    }

    public double getPreis(){
        return preis;
    }
}
