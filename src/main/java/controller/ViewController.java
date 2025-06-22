package controller;

import view.iBeautyFenster;

public class ViewController {
    public iBeautyFenster iBeautyFenster;

    public ViewController(iBeautyFenster view) {
        this.iBeautyFenster = view;

    }

    public void iBeautyFenster_anzeigen() {

        iBeautyFenster.setVisible(true);
    }
}