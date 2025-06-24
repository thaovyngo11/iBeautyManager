package view;

import com.toedter.calendar.JDateChooser;
import controller.TerminVerwaltung;
import model.Dienst;
import model.Termin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;
import java.util.*;
import java.util.List;

public class iBeautyFenster extends JFrame {

    // ----------------------------- //
    // 1. ATTRIBUTE: GUI-KOMPONENTEN //
    // ----------------------------- //

    private JPanel myPanel;
    private JLabel lbl_slogan;
    private JLabel lbl_KundenName;
    private JLabel lbl_Telefonnummer;
    private JLabel lbl_Dienste;
    private JLabel lbl_Angeboten;

    private JTextField tf_Kundenname;
    private JTextField tf_Telefonnummer;
    private JTextField tf_Gesamtpreis;
    private JTextField tf_Filtern;

    private JCheckBox chk_Dienst1;
    private JCheckBox chk_Dienst2;
    private JCheckBox chk_Dienst3;

    private JComboBox cbx_Angebot1;
    private JComboBox cbx_Angebot2;
    private JComboBox cbx_Angebot3;

    private JLabel lbl_Datum;
    private JLabel lbl_Uhrzeit;
    private JPanel panelDatum;
    private JSpinner spn_Uhrzeit;

    private JButton btn_Berechnen;
    private JButton btn_Speichern_und_Anzeigen;
    private JButton btn_Filtern;

    private JScrollPane scp_Termin_Uebersicht;
    private JTable tb_Termin_Uebersicht;

    private JDateChooser dateChooser;                              // JDateChooser & TableModel (nicht über GUI-Designer erstellt)
    private DefaultTableModel tableModel;
    private TerminVerwaltung verwaltung = new TerminVerwaltung();  // Speichert und verwaltet alle Termine in der App
    private boolean initialisiert = false;                         // Kontrollvariable: true = Termine wurden schon geladen, false = noch nicht geladen

    // ------------------------------------- //
    // 2. KONSTRUKTOR: GUI INITIALISIEREN    //
    // ------------------------------------- //

    public iBeautyFenster() {

        setTitle("iBeauty Manager");                     // Den Fenstertitel setzen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Wenn auf das "X" geklickt wird, wird das Programm beendet
        setExtendedState(JFrame.MAXIMIZED_BOTH);         // Fenster wird im Vollbildmodus geöffnet
        setContentPane(myPanel);                         // Verwendet das im GUI Designer gestaltete myPanel als Hauptinhalt
        setVisible(true);                                // Fenster anzeigen

        setupDatum_Uhrzeit_Tabelle();    // Datum, Uhrzeit und Tabelle vorbereiten
        setupActionListener_Berechnen(); // ActionListener für "Berechnen"-Button
        setupActionListener_Speichern(); // ActionListener für "Speichern"-Button
        setupActionListener_Filtern();   // ActionListener für "Filtern"-Button
        ladeInitialTermine();            // Beispieltermine laden und anzeigen
    }

    /* Alternative Konstruktor mit Steuerung, ob Beispieldaten geladen werden sollen.
     * Wird beim Unit-Test verwendet, um das Fenster ohne initiale Daten zu öffnen.
     * true = Beispieldaten werden geladen, false = leeres Fenster */

    public iBeautyFenster(boolean ladeInitial) {
        setTitle("iBeauty Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setContentPane(myPanel);
        setVisible(true);

        setupDatum_Uhrzeit_Tabelle();
        setupActionListener_Berechnen();
        setupActionListener_Speichern();
        setupActionListener_Filtern();

        if (ladeInitial) {
            ladeInitialTermine();
        }
    }

    // ------------------------------------------ //
    // 3. KONSTRUKTOR: DATUM - UHRZEIT - TABELLE  //
    // ------------------------------------------ //

    private void setupDatum_Uhrzeit_Tabelle() {

        Locale.setDefault(Locale.GERMANY);                                  // Sprache und Format auf Deutsch festlegen
        dateChooser = new JDateChooser();                                   // JDateChooser-Objekt initialisieren (Kalenderfeld)
        dateChooser.setPreferredSize(new Dimension(150, 25));  // Größe des Datumswählers festlegen
        panelDatum.setLayout(new FlowLayout(FlowLayout.LEFT));              // Layout des Panels auf FlowLayout setzen
        panelDatum.add(dateChooser);

        spn_Uhrzeit.setModel(new SpinnerDateModel());                                           // SpinnerDateModel: Uhrzeit initialisieren
        spn_Uhrzeit.setEditor(new JSpinner.DateEditor(spn_Uhrzeit, "HH:mm"));   // setEditor(...): Anzeigeformat für Uhrzeit festlegen (z.B.: 10:30)

        tableModel = new DefaultTableModel(
                new String[]{"Kundenname", "Telefonnummer", "Dienst", "Preis", "Datum"}, 0) { // Erstellen eines Tabellenmodells mit Spaltenüberschriften und initial 0 Zeilen (leere Tabelle)
            @Override
            public boolean isCellEditable(int row, int column) { // Überschreibt die Methode isCellEditable, um die Bearbeitung der Zellen zu verhindern (immer false zurückgeben)

                return false;
            }
        };

        tb_Termin_Uebersicht.setModel(tableModel); // Weist das erstellte TableModel der Tabelle zu

        int[] widths = {90, 110, 200, 60, 270};    // Ein Array mit gewünschten Spaltenbreiten (in Pixeln)
        for (int i = 0; i < widths.length; i++) {  // Schleife, um jede Spalte der Tabelle anzupassen

            tb_Termin_Uebersicht.getColumnModel().getColumn(i).setPreferredWidth(widths[i]); // Breite setzen
            tb_Termin_Uebersicht.getColumnModel().getColumn(i).setMinWidth(widths[i]);       // Minimale Breite verhindern, dass Spalte kleiner wird
            tb_Termin_Uebersicht.getColumnModel().getColumn(i).setMaxWidth(widths[i]);       // Maximale Breite verhindern, dass Spalte breiter wird
            tb_Termin_Uebersicht.getColumnModel().getColumn(i).setResizable(false);          // Benutzer darf die Spaltenbreite nicht per Maus verändern
        }
        tb_Termin_Uebersicht.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);                               // Nur eine Zeile darf gleichzeitig ausgewählt werden
        tb_Termin_Uebersicht.getColumnModel().getColumn(2).setCellRenderer(new TableCellRenderer() {  // Weist der dritten Spalte "Dienst" einen benutzerdefinierten CellRenderer zu

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                JTextArea area = new JTextArea(value == null ? "" : value.toString()); // Erstellt eine JTextArea, um den Zelleninhalt anzuzeigen

                area.setLineWrap(true);         // Zeilenumbruch aktivieren, wenn der Text zu lang ist
                area.setWrapStyleWord(true);    // Zeilenumbruch erfolgt an Wortgrenzen (nicht mitten im Wort)
                area.setOpaque(true);           // Hintergrundfarbe der JTextArea sichtbar machen

                if (isSelected) {                                        // Wenn die Zelle aktuell ausgewählt ist:
                    area.setBackground(table.getSelectionBackground());  // Hintergrundfarbe auf Auswahlfarbe setzen (im Blau sein)
                    area.setForeground(table.getSelectionForeground());  // Textfarbe auf Auswahlfarbe setzen (im Blau sein)

                } else {                                                 // Wenn die Zelle nicht ausgewählt ist:
                    area.setBackground(table.getBackground());           // Standard-Hintergrundfarbe verwenden
                    area.setForeground(table.getForeground());           // Standard-Textfarbe verwenden
                }

                int preferredHeight = area.getPreferredSize().height;    // getPreferredSize().height: nötige Höhe berechnen, um den ganzen Text anzuzeigen

                if (table.getRowHeight(row) != preferredHeight) {        // Wenn die aktuelle Zeilenhöhe unpassend ist
                    table.setRowHeight(row, preferredHeight);            // Zeilenhöhe entsprechend anpassen
                }
                return area;
            }
        });
    }

    // ---------------------------- //
    // 4. ACTIONLISTENER: Berechnen //
    // ---------------------------- //

    // Hilfsmethode zur Preisberechnung
    // Prüft, welche Dienste ausgewählt wurden, und summiert deren Preise
    private void setupActionListener_Berechnen() {

        btn_Berechnen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double preis = berechneGesamtpreis();     // Gesamtpreis berechnen durch Aufruf einer Hilfsmethode
                tf_Gesamtpreis.setText(preis + " Euro");  // Ergebnis im Textfeld anzeigen (z.B. "80.0 Euro")
            }
        });
    }

    public double berechneGesamtpreis() { // Hilfsmethode zur Preisberechnung
        double preis = 0.0;

        if (chk_Dienst1.isSelected()) {                                   // Wenn Dienst 1 (Massage) ausgewählt ist
            String angebot = cbx_Angebot1.getSelectedItem().toString();   // Ausgewähltes Angebot aus der ComboBox holen (z.B. "Thai Massage")
            preis += getPreis("Massage", angebot);               // Preis automatisch aus der Dienst-Liste holen. getPreis(Kategorie, Angebot) durchsucht alle gespeicherten Dienste
        }
        if (chk_Dienst2.isSelected()) {
            String angebot = cbx_Angebot2.getSelectedItem().toString();
            preis += getPreis("Nägel", angebot);
        }
        if (chk_Dienst3.isSelected()) {
            String angebot = cbx_Angebot3.getSelectedItem().toString();
            preis += getPreis("Kosmetik", angebot);
        }
        return preis; // Rückgabe des Gesamtpreises
    }

    // ---------------------------------------- //
    // 5. ACTIONLISTENER: Speichern und Anzeigen //
    // ---------------------------------------- //

    // Erstellt einen neuen Termin aus Nutzereingaben und fügt ihn zur Tabelle hinzu
    private void setupActionListener_Speichern() {

        btn_Speichern_und_Anzeigen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Termin termin = erzeugeTerminAusEingaben();           // Neuen Termin basierend auf Benutzereingaben erzeugen
                    for (int i = 0; i < tableModel.getRowCount(); i++) {  // Prüfen, ob ein anderer Termin am selben Tag zu nah dran liegt (< 45 Minuten)

                        String existingDatumStr = tableModel.getValueAt(i, 4).toString();
                        LocalDateTime existingDatum = LocalDateTime.parse(existingDatumStr);
                        long minutes = Math.abs(Duration.between(existingDatum, termin.getDatum()).toMinutes());

                        if (existingDatum.toLocalDate().equals(termin.getDatum().toLocalDate()) && minutes < 45) {
                            throw new IllegalArgumentException("Termin ist zu nah an einem bestehenden Termin (weniger als 45 Minuten Abstand).");
                        }
                    }

                    verwaltung.addTermin(termin); // Termin wird gespeichert

                    // Dienstnamen (als Text mit Zeilenumbrüchen) vorbereiten
                    String dienstNamen = "";
                    for (Dienst d : termin.getDienstList()) {
                        if (!dienstNamen.isEmpty()) dienstNamen += "\n";
                        dienstNamen += d.getDienst() + ": " + d.getAngebot();
                    }

                    // Neue Zeile zur Tabelle hinzufügen
                    tableModel.addRow(new Object[]{
                            termin.getKundenname(),
                            termin.getTelefonnummer(),
                            dienstNamen,
                            termin.getGesamtpreis(),
                            termin.getDatum().toString()
                    });

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE); // Falls Eingabefehler, zeigt Fehlermeldung an
                }
            }
        });
    }

    // Diese Methode liest die Eingaben des Nutzers aus,
    // prüft sie auf Fehler, und erstellt ein gültiges Termin-Objekt
    public Termin erzeugeTerminAusEingaben() {

        String kundenname = tf_Kundenname.getText().trim();
        if (kundenname.isEmpty() || !kundenname.matches("[a-zA-ZäöüÄÖÜß ]+"))                         //Wenn tf_Kundenname leer ist und der Name Zahlen oder Sonderzeichen enthält, Fehlermeldung auslösen
            throw new IllegalArgumentException("Bitte geben Sie einen gültigen Namen ein!");

        String telefonnummer = tf_Telefonnummer.getText().trim();
        if (telefonnummer.isEmpty() || !telefonnummer.matches("\\d+"))                               // Wenn tf_Telefonnummer leer ist und keine Ziffern hat, Fehler auslösen
            throw new IllegalArgumentException("Bitte geben Sie eine gültige Telefonnummer ein!");

        List<Dienst> dienstList = new ArrayList<>();                                                       // Neue Liste für ausgewählte Dienstleistungen erstellen
        if (chk_Dienst1.isSelected()) {                                                                    // Wenn Dienst 1 (Massage) ausgewählt ist
            String angebot = cbx_Angebot1.getSelectedItem().toString();                                    // Das ausgewählte Unterangebot (z.B. "Thai Massage") aus der ComboBox holen
            dienstList.add(new Dienst("Massage", angebot, getPreis("Massage", angebot)));  // Neues Dienst-Objekt erstellen und zur Liste hinzufügen
        }
        if (chk_Dienst2.isSelected()) {
            String angebot = cbx_Angebot2.getSelectedItem().toString();
            dienstList.add(new Dienst("Nägel", angebot, getPreis("Nägel", angebot)));
        }
        if (chk_Dienst3.isSelected()) {
            String angebot = cbx_Angebot3.getSelectedItem().toString();
            dienstList.add(new Dienst("Kosmetik", angebot, getPreis("Kosmetik", angebot)));
        }

        if (dienstList.isEmpty())                                                                        // Wenn keine Dienstleistung ausgewählt wurde, Fehler auslösen
            throw new IllegalArgumentException("Bitte wählen Sie mindestens einen Dienst aus.");

        String preisText = tf_Gesamtpreis.getText().trim().replaceAll("[^\\d.]", "");  // Gesamtpreis-Text holen und Leerzeichen entfernen, nur Zahlen und Dezimalpunkt behalten (z.B. " 45.0 Euro " = "45.0")
        if (preisText.isEmpty())                                                                         // Wenn kein Preis berechnet wurde, Fehler auslösen
            throw new IllegalArgumentException("Bitte berechnen Sie zuerst den Gesamtpreis.");
        double preis = Double.parseDouble(preisText);                                                    // Preis-Text in eine Zahl (double) umwandeln (z.B. "45.0" = 45.0)

        Date datum = dateChooser.getDate();                                         // Ausgewähltes Datum von "JDateChooser" holen
        if (datum == null)                                                          // Wenn kein Datum gewählt wurde, Fehler auslösen
            throw new IllegalArgumentException("Bitte wählen Sie ein Datum aus.");

        LocalDate date = datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); // Umwandlung von Date zu LocalDate (Zum Beispiel: 2025-06-20)
        Date zeit = (Date) spn_Uhrzeit.getValue();
        Calendar cal = Calendar.getInstance();
        cal.setTime(zeit);
        LocalTime time = LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));  // Umwandlung von Date zu LocalTime (Zum Beispiel: 10:30)
        LocalDateTime terminZeit = LocalDateTime.of(date, time);                         // Datum und Uhrzeit zu einem LocalDateTime-Objekt kombinieren (z.B. 2025-06-20T10:30)
        LocalDateTime jetzt = LocalDateTime.now().withSecond(0).withNano(0);

        if (terminZeit.isBefore(jetzt))
            throw new IllegalArgumentException("Termin darf nicht in der Vergangenheit liegen."); // Keine Termine in der Vergangenheit erlaubt

        return new Termin(kundenname, telefonnummer, dienstList, terminZeit); // Termin-Objekt mit allen Daten zurückgeben
    }

    // -------------------------- //
    // 6. ACTIONLISTENER: Filtern //
    // -------------------------- //

    private void setupActionListener_Filtern() {
        btn_Filtern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filterText = tf_Filtern.getText().trim().toLowerCase(); // Text aus dem Filter-Textfeld holen, Leerzeichen entfernen und in Kleinbuchstaben umwandeln
                filterTabelle(filterText);                                     // Tabelle anhand des Filtertexts neu laden
            }
        });
    }

    // Tabelle filtern und neu aufbauen
    public void filterTabelle(String filterText) {

        tableModel.setRowCount(0);                             // Alle bisherigen Zeilen aus der Tabelle löschen
        for (Termin t : verwaltung.getAlleTermine()) {         // Alle gespeicherten Termine durchgehen
            if (filterText.isEmpty()
                    || t.getKundenname().toLowerCase().contains(filterText)
                    || t.getTelefonnummer().toLowerCase().contains(filterText)) {

                /* Termin wird angezeigt, wenn mindestens eine der folgenden Bedingungen erfüllt ist:
                     - Das Filterfeld ist leer
                     - Der Kundenname enthält den Filtertext
                     - Die Telefonnummer enthält den Filtertext */

                String dienstNamen = "";
                for (Dienst d : t.getDienstList()) {
                    if (!dienstNamen.isEmpty()) dienstNamen += "\n";
                    dienstNamen += d.getDienst() + ": " + d.getAngebot();
                }

                tableModel.addRow(new Object[]{
                        t.getKundenname(),
                        t.getTelefonnummer(),
                        dienstNamen,
                        t.getGesamtpreis(),
                        t.getDatum().toString()
                });
            }
        }
    }

    // ---------------------------------------------------//
    // 7. HILFSMETHODEN: Daten laden und Preis ermitteln  //
    // ---------------------------------------------------//

    // Methode zum Laden der gespeicherten Termine und Einfügen in die Tabelle
    private void ladeInitialTermine() {

        if (!initialisiert) {            // Wenn die Termine noch nicht geladen wurden
            verwaltung.initObjekte();    // Beispieltermine in die Verwaltung laden
            initialisiert = true;        // Nur einmal laden
        }

        for (Termin t : verwaltung.getAlleTermine()) {
            String dienstNamen = "";
            for (Dienst d : t.getDienstList()) {
                if (!dienstNamen.isEmpty()) dienstNamen += "\n";
                dienstNamen += d.getDienst() + ": " + d.getAngebot();
            }

            tableModel.addRow(new Object[]{
                    t.getKundenname(),
                    t.getTelefonnummer(),
                    dienstNamen,
                    t.getGesamtpreis(),
                    t.getDatum().toString()
            });
        }
    }

    // Methode zum Ermitteln des Preises anhand Kategorie + Angebot
    public double getPreis(String kategorie, String angebot) {
        for (Dienst d : Dienst.getAlleAngebot()) {
            if (d.getDienst().equals(kategorie) && d.getAngebot().equals(angebot)) {
                return d.getPreis();
            }
        }
        return 0.0;
    }

    // --------------------------------------------------------------------//
    // 8. HILFSMETHODEN: Getter-Methoden zur Unterstützung von Unit-Tests  //
    // --------------------------------------------------------------------//

    // Getter für Textfelder
    public JTextField getTf_Kundenname() {
        return tf_Kundenname;
    }

    public JTextField getTf_Telefonnummer() {
        return tf_Telefonnummer;
    }

    public JTextField getTf_Gesamtpreis() {
        return tf_Gesamtpreis;
    }

    public JTextField getTf_Filtern() {
        return tf_Filtern;
    }

    // Getter für Checkboxen
    public JCheckBox getChk_Dienst1() {
        return chk_Dienst1;
    }

    public JCheckBox getChk_Dienst2() {
        return chk_Dienst2;
    }

    public JCheckBox getChk_Dienst3() {
        return chk_Dienst3;
    }

    // Getter für ComboBoxen
    public JComboBox getCbx_Angebot1() {
        return cbx_Angebot1;
    }

    public JComboBox getCbx_Angebot2() {
        return cbx_Angebot2;
    }

    public JComboBox getCbx_Angebot3() {
        return cbx_Angebot3;
    }

    // Getter für Kalender und Uhrzeit
    public JDateChooser getDateChooser() {
        return dateChooser;
    }

    public JSpinner getSpn_Uhrzeit() {
        return spn_Uhrzeit;
    }

    // Getter für Tabelle und Verwaltung
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public TerminVerwaltung getVerwaltung() {
        return verwaltung;
    }

    public JTable getTb_Termin_Uebersicht() {
        return tb_Termin_Uebersicht;
    }
}
