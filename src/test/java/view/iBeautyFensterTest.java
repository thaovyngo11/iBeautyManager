package view;

import model.Dienst;
import model.Termin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class iBeautyFensterTest {

    iBeautyFenster fenster;
    @BeforeEach
    void cleanUp() {
        Dienst.getAlleAngebot().clear();
        fenster = new iBeautyFenster(false);
        fenster.getVerwaltung().getAlleTermine().clear();
        ((DefaultTableModel) fenster.getTb_Termin_Uebersicht().getModel()).setRowCount(0);
    }
    @Test
    void getPreisMassage_Test() {
        //iBeautyFenster fenster = new iBeautyFenster();
        Dienst.getAlleAngebot().clear();
        Dienst.getAlleAngebot().add(new Dienst("Massage", "Thai Massage", 35.0));

        double result = fenster.getPreis("Massage", "Thai Massage");
        assertEquals(35.0, result);
    }

    @Test
    void getPreisNaegel_Test() {
        //iBeautyFenster fenster = new iBeautyFenster();
        Dienst.getAlleAngebot().clear();
        Dienst.getAlleAngebot().add(new Dienst("Nägel", "Maniküre", 10.0));

        double result = fenster.getPreis("Nägel", "Maniküre");
        assertEquals(10.0, result);
    }

    @Test
    void getPreisKosmetik_Test() {
        //iBeautyFenster fenster = new iBeautyFenster();
        Dienst.getAlleAngebot().clear();
        Dienst.getAlleAngebot().add(new Dienst("Kosmetik", "Wimpernverlängerung", 80.0));

        double result = fenster.getPreis("Kosmetik", "Wimpernverlängerung");
        assertEquals(80.0, result);
    }

    @Test
    void getPreisUnbekannt_Test() {
        //iBeautyFenster fenster = new iBeautyFenster();
        Dienst.getAlleAngebot().clear();

        double result = fenster.getPreis("Friseur", "Haarschnitt");
        assertEquals(0.0, result);
    }

    @Test
    void berechneGesamtpreis() {
        //iBeautyFenster fenster = new iBeautyFenster();

        Dienst.getAlleAngebot().clear();
        Dienst.getAlleAngebot().add(new Dienst("Massage", "Thai Massage", 35.0));
        Dienst.getAlleAngebot().add(new Dienst("Nägel", "Maniküre", 10.0));
        Dienst.getAlleAngebot().add(new Dienst("Kosmetik", "Augenbrauen zupfen", 15.0));

        fenster.getChk_Dienst1().setSelected(true);
        fenster.getCbx_Angebot1().setSelectedItem("Thai Massage");

        fenster.getChk_Dienst2().setSelected(true);
        fenster.getCbx_Angebot2().setSelectedItem("Maniküre");

        fenster.getChk_Dienst3().setSelected(true);
        fenster.getCbx_Angebot3().setSelectedItem("Augenbrauen zupfen");

        double preis = fenster.berechneGesamtpreis();

        assertEquals(60.0, preis);
    }

    @Test
    void erzeugeTerminAusEingaben() {
        //iBeautyFenster fenster = new iBeautyFenster();

        Dienst.getAlleAngebot().clear();
        Dienst.getAlleAngebot().add(new Dienst("Massage", "Fuß Massage ", 40.0));
        Dienst.getAlleAngebot().add(new Dienst("Nägel", "Nagel Design", 30.0));

        fenster.getTf_Kundenname().setText("Thao Vy");
        fenster.getTf_Telefonnummer().setText("015209922581");

        fenster.getChk_Dienst1().setSelected(true);
        fenster.getCbx_Angebot1().setSelectedItem("Fuß Massage");

        fenster.getChk_Dienst2().setSelected(true);
        fenster.getCbx_Angebot2().setSelectedItem("Nagel Design");

        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 25, 11, 00);
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        fenster.getDateChooser().setDate(date);
        fenster.getSpn_Uhrzeit().setValue(date);

        double preis = fenster.berechneGesamtpreis();
        fenster.getTf_Gesamtpreis().setText(preis + " Euro");

        Termin termin = fenster.erzeugeTerminAusEingaben();

        assertEquals("Thao Vy", termin.getKundenname());
        assertEquals("015209922581", termin.getTelefonnummer());
        assertEquals(2, termin.getDienstList().size());
        assertEquals(70.0, termin.getGesamtpreis());
    }

    @Test
    void filterTabelle() {
        //iBeautyFenster fenster = new iBeautyFenster(false);

        Dienst dienst1 = new Dienst("Kosmetik", "Wimpernverlängerung", 80.0);
        Dienst dienst2 = new Dienst("Nägel", "Nägel auffüllen", 28.0);
        Dienst.getAlleAngebot().clear();
        Dienst.getAlleAngebot().add(dienst1);
        Dienst.getAlleAngebot().add(dienst2);

        List<Dienst> dienstList = new ArrayList<>();
        dienstList.add(dienst1);
        dienstList.add(dienst2);

        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 2, 15, 20);
        Termin termin = new Termin("lara", "015202618469", dienstList, dateTime);
        fenster.getVerwaltung().addTermin(termin);

        fenster.filterTabelle("lara");

        JTable table = fenster.getTb_Termin_Uebersicht();
        assertEquals(1, table.getRowCount());
        String name = table.getValueAt(0, 0).toString();
        assertEquals("lara", name);
    }
}