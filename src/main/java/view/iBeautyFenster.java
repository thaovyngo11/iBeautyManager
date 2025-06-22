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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class iBeautyFenster extends JFrame{

    // -------------//
    // 1. ATTRIBUTE //
    // -------------//

    // GUI-Komponenten
    private JPanel myPanel;
    private JLabel lbl_slogan;
    private JLabel lbl_KundenName;
    private JLabel lbl_Telefonnummer;
    private JTextField tf_Kundenname;
    private JTextField tf_Telefonnummer;

    private JLabel lbl_Dienste;
    private JLabel lbl_Angeboten;
    private JCheckBox chk_Dienst1;
    private JCheckBox chk_Dienst2;
    private JCheckBox chk_Dienst3;
    private JComboBox cbx_Angebot1;
    private JComboBox cbx_Angebot2;
    private JComboBox cbx_Angebot3;

    private JTextField tf_Gesamtpreis;
    private JButton btn_Berechnen;

    private JLabel lbl_Datum;
    private JPanel panelDatum;
    private JLabel lbl_Uhrzeit;
    private JSpinner spn_Uhrzeit;

    private JButton btn_Speichern_und_Anzeigen;

    private JTextField tf_Filtern;
    private JButton btn_Filtern;

    private JScrollPane scp_Termin_Uebersicht;
    private JTable tb_Termin_Uebersicht;

    // Spezial-Komponenten: JDateChooser & TableModel (nicht über GUI-Designer erstellt)
    private JDateChooser dateChooser;
    private DefaultTableModel tableModel;

    // Ein Objekt zur Verwaltung und Speicherung aller Termine
    private TerminVerwaltung verwaltung = new TerminVerwaltung();

    /* Eine Kontrollvariable zu anzeigen, ob Daten von einem Termin schon einmal geladen wurden
     - "false" - Daten werden geladen.
     - "true" - Daten sind bereits vorhanden, also wird nichts mehr gemacht. */
    private boolean initialisiert = false;

    // -----------------------------------------//
    // 2. KONSTRUKTOR – INITIALISIERUNG DER GUI //
    // -----------------------------------------//

    public iBeautyFenster() {
        setTitle("iBeauty Manager");                     // Setzt den Titel des Fensters
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Bestimmt das Verhalten beim Schließen des Fensters
        setExtendedState(JFrame.MAXIMIZED_BOTH);         // Startet die Anwendung im Vollbildmodus
        setContentPane(myPanel);                         // Setzt das Haupt-Panel als Inhalt des Fensters
        setVisible(true);                                // Zeigt das Fenster an

        // -----------------------------------------//
        // 3. DATUM, UHRZEIT UND TABELLE EINRICHTEN //
        // -----------------------------------------//

        // DATUM //

        Locale.setDefault(Locale.GERMANY);                                   // Sprache und Format auf Deutsch festlegen
        dateChooser = new JDateChooser();                                    // JDateChooser-Objekt initialisieren (Kalenderfeld)
        dateChooser.setPreferredSize(new Dimension(150, 25));   // Größe des Datumswählers festlegen
        panelDatum.setLayout(new FlowLayout(FlowLayout.LEFT));               // Layout des Panels auf FlowLayout setzen
        panelDatum.add(dateChooser);                                         // Datumswähler zum Panel hinzufügen

        // UHRZEIT //

        spn_Uhrzeit.setModel(new SpinnerDateModel());                                          // SpinnerDateModel: Uhrzeit initialisieren
        spn_Uhrzeit.setEditor(new JSpinner.DateEditor(spn_Uhrzeit, "HH:mm"));  // setEditor(...): Anzeigeformat für Uhrzeit festlegen (z.B.: 10:30)

        // TABELLE //

        tableModel = new DefaultTableModel(new String[]{"Kundenname", "Telefonnummer", "Dienst", "Preis", "Datum"}, 0) {   // Erstellen eines Tabellenmodells mit Spaltenüberschriften und initial 0 Zeilen (leere Tabelle)
            @Override
            public boolean isCellEditable(int row, int column) {  // Überschreibt die Methode isCellEditable, um die Bearbeitung der Zellen zu verhindern (immer false zurückgeben)

                return false;
            }
        };

        tb_Termin_Uebersicht.setModel(tableModel);    // Weist das erstellte TableModel der Tabelle zu

        int[] widths = {90, 110, 200, 60, 270};       // Ein Array mit gewünschten Spaltenbreiten (in Pixeln)
        for (int i = 0; i < widths.length; i++) {     // Schleife, um jede Spalte der Tabelle anzupassen

            tb_Termin_Uebersicht.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);  // Breite setzen
            tb_Termin_Uebersicht.getColumnModel().getColumn(i).setMinWidth(widths[i]);        // Minimale Breite verhindern, dass Spalte kleiner wird
            tb_Termin_Uebersicht.getColumnModel().getColumn(i).setMaxWidth(widths[i]);        // Maximale Breite verhindern, dass Spalte breiter wird
            tb_Termin_Uebersicht.getColumnModel().getColumn(i).setResizable(false);           // Benutzer darf die Spaltenbreite nicht per Maus verändern

        }

        tb_Termin_Uebersicht.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);           // Nur eine Zeile darf gleichzeitig ausgewählt werden

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

                int preferredHeight = area.getPreferredSize().height; // getPreferredSize().height: nötige Höhe berechnen, um den ganzen Text anzuzeigen

                if (table.getRowHeight(row) != preferredHeight) {     // Wenn die aktuelle Zeilenhöhe unpassend ist
                    table.setRowHeight(row, preferredHeight);         // Zeilenhöhe entsprechend anpassen
                }
                return area;
            }
        });

        // ---------------------------------------------------//
        // 4. ACTIONLISTENER:  BERECHNEN, SPEICHERN & FILTERN //
        // ---------------------------------------------------//

        // BERECHNEN //

        btn_Berechnen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double preis = 0.0;                                              // Gesamtpreis zurücksetzen

                // Dienst 1 (Massage) //
                if (chk_Dienst1.isSelected()) {                                  // Wenn der Benutzer den Dienst "Massage" ausgewählt hat
                    String angebot = cbx_Angebot1.getSelectedItem().toString();  // Das ausgewählte Unterangebot (z.B.: "Thai Massage") aus der ComboBox auslesen

                    if (angebot.equals("Thai Massage")) {
                        preis += 35.0;                                           // Wenn "Thai Massage" gewählt wurde, 35 Euro zum Gesamtpreis hinzufügen
                    } else if (angebot.equals("Fuß Massage")) {
                        preis += 40.0;
                    } else if (angebot.equals("Aromaöl Massage")) {
                        preis += 25.0;
                    }
                }

                // Dienst 2 (Nägel) //
                if (chk_Dienst2.isSelected()) {
                    String angebot = cbx_Angebot2.getSelectedItem().toString();
                    if (angebot.equals("Pediküre")) {
                        preis += 20.0;
                    } else if (angebot.equals("Maniküre")) {
                        preis += 10.0;
                    } else if (angebot.equals("Nagel Design")) {
                        preis += 30.0;
                    } else if (angebot.equals("Nägel auffüllen")) {
                        preis += 28.0;
                    } else if (angebot.equals("Nagelmodellage")) {
                        preis += 30.4;
                    }
                }

                // Dienst 3 (Kosmetik) //
                if (chk_Dienst3.isSelected()) {
                    String angebot = cbx_Angebot3.getSelectedItem().toString();
                    if (angebot.equals("Wimpernverlängerung")) {
                        preis += 80.0;
                    } else if (angebot.equals("Augenbrauen und Wimpern färben")) {
                        preis += 15.0;
                    } else if (angebot.equals("Wimpernwelle")) {
                        preis += 69.0;
                    } else if (angebot.equals("Augenbrauen zupfen")) {
                        preis += 15.0;
                    }
                }

                tf_Gesamtpreis.setText(preis + " Euro"); // Gesamtpreis wird in der tf_Gesamtpreis (TextField) angezeigt
            }
        });

        // SPEICHERN UND ANZEIGEN //

        btn_Speichern_und_Anzeigen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // EXCEPTION HANDLUNG: Fehlerbehandlung mit try-catch

                try {

                    // Kundennamme //

                    String kundenname = tf_Kundenname.getText().trim();  // Den eingegebenen Namen aus dem Textfeld holen

                    if (kundenname.isEmpty()) {  // Wenn tf_Kundenname leer ist, eine Fehlermeldung auslösen
                        throw new IllegalArgumentException("Bitte geben Sie einen gültigen Namen ein!");
                    }

                    if (!kundenname.matches("[a-zA-ZäöüÄÖÜß ]+")) {  // Wenn der Name Zahlen oder Sonderzeichen enthält, Fehler auslösen
                        throw new IllegalArgumentException("Der Name darf keine Zahlen oder Sonderzeichen erhalten!");
                    }

                    // Telefonnummer //

                    String telefonnummer = tf_Telefonnummer.getText().trim();  // Telefonnummer aus dem Textfeld holen

                    if (telefonnummer.isEmpty()) { // Wenn tf_Telefonnummer leer ist, ebenfalls Fehler auslösen
                        throw new IllegalArgumentException("Bitte geben Sie eine gültige Telefonnummer ein!");
                    }

                    if (!telefonnummer.matches("\\d+")) { // Wenn tf_Telefonnummer hat keine Ziffern, Fehler auslösen
                        throw new IllegalArgumentException("Die Telefonnumer darf keine Buchstaben oder Sonderzeichen erhalten!");
                    }

                    List<Dienst> dienstList = new ArrayList<>();                       // Neue Liste für ausgewählte Dienstleistungen erstellen

                    if (chk_Dienst1.isSelected()) {                                    // Wenn Dienst 1 (Massage) ausgewählt ist
                        String angebot = cbx_Angebot1.getSelectedItem().toString();    // Das ausgewählte Unterangebot (z.B. "Thai Massage") aus der ComboBox holen
                        double preis = getPreis("Massage", angebot);          // Den Preis für das gewählte Angebot ermitteln
                        dienstList.add(new Dienst("Massage", angebot, preis));  // Neues Dienst-Objekt erstellen und zur Liste hinzufügen
                    }
                    if (chk_Dienst2.isSelected()) {
                        String angebot = cbx_Angebot2.getSelectedItem().toString();
                        double preis = getPreis("Nägel", angebot);
                        dienstList.add(new Dienst("Nägel", angebot, preis));
                    }
                    if (chk_Dienst3.isSelected()) {
                        String angebot = cbx_Angebot3.getSelectedItem().toString();
                        double preis = getPreis("Kosmetik", angebot);
                        dienstList.add(new Dienst("Kosmetik", angebot, preis));
                    }

                    // Dienst-List //

                    if (dienstList.isEmpty()) {    // Wenn keine Dienstleistung ausgewählt wurde, Fehler auslösen
                        throw new IllegalArgumentException("Bitte wählen Sie mindestens einen Dienst aus.");
                    }

                    String preisText = tf_Gesamtpreis.getText().trim(); // Gesamtpreis-Text holen und Leerzeichen entfernen (z.B: " 45.0 Euro " = "45.0 Euro")

                    if (preisText.isEmpty()) {    // Wenn kein Preis berechnet wurde, Fehler auslösen
                        throw new IllegalArgumentException("Bitte berechnen Sie zuerst den Gesamtpreis.");

                    } else {
                        preisText = preisText.replaceAll("[^\\d]", ""); // Alle nicht-numerischen Zeichen entfernen (z.B. "45.0 Euro" = "45.0")
                        double preis = Double.parseDouble(preisText);                    // Den Text in eine Zahl vom Typ double umwandeln
                    }

                    // Datum //

                    Date datum = dateChooser.getDate(); //Ausgewähltes Datum von "JDateChooser" holen

                    if (datum == null) { // Wenn kein Datum gewählt wurde, Fehler auslösen
                        throw new IllegalArgumentException("Bitte wählen Sie ein Datum aus.");
                    }

                    LocalDate date = datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();  // Umwandlung von Date zu LocalDate (Zum Beispiel: 2025-06-20)

                    Date zeit = (Date) spn_Uhrzeit.getValue();                                        // Uhrzeit aus dem Spinner holen und umwandeln
                    LocalTime time = zeit.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();   // Umwandlung von Date zu LocalTime (Zum Beispiel: 10:30)

                    LocalDateTime terminZeit = LocalDateTime.of(date, time);                          // Datum und Uhrzeit zu einem LocalDateTime-Objekt kombinieren (Zum Beispiel: 2025-06-20T10:30)

                    // Terminzeit //

                    if (terminZeit.isBefore(LocalDateTime.now())) {
                        throw new IllegalArgumentException("Termin darf nicht in der Vergangenheit liegen.");   // Keine Termine in der Vergangenheit erlaubt
                    }

                    for (int i = 0; i < tableModel.getRowCount(); i++) {

                        String existingDatumStr = tableModel.getValueAt(i, 4).toString();
                        LocalDateTime existingDatum = LocalDateTime.parse(existingDatumStr);

                        if (existingDatum.toLocalDate().equals(terminZeit.toLocalDate())) {  // Prüfen, ob Termin mit ähnlicher Zeit schon existiert

                            long minutes = Math.abs(java.time.Duration.between(existingDatum, terminZeit).toMinutes());

                            if (minutes < 45) { // Wenn zwei Termine weniger als 45 Minuten Abstand haben, ist das nicht erlaubt
                                throw new IllegalArgumentException("Termin ist zu nah an einem bestehenden Termin (weniger als 45 Minuten Abstand).");
                            }
                        }
                    }

                    Termin termin = new Termin(kundenname, telefonnummer, dienstList, terminZeit);
                    verwaltung.addTermin(termin);    // Termin wird gespeichert

                    String dienstNamen = "";         // String zur Anzeige der Dienstleistungen vorbereiten

                    for (Dienst d : dienstList) {    // Durch alle gewählten Dienstleistungen des Termins gehen
                        if (!dienstNamen.isEmpty())
                            dienstNamen += "\n";     // Zeilenumbruch hinzufügen

                        dienstNamen += d.getDienst() + ": " + d.getAngebot(); // Dienst und Angebot zur Übersicht hinzufügen. Format: (Dienst: Angebot)
                    }

                    tableModel.addRow(new Object[]{      // Neue Zeile in die Tabelle einfügen (Zeile zeigt alle Informationen zum Termin an)

                            termin.getKundenname(),      // Name des Kunden aus dem Terminobjekt                    (Thao Vy)
                            termin.getTelefonnummer(),   // Telefonnummer aus dem Terminobjekt                      (015209922581)
                            dienstNamen,                 // Übersicht über gewählte Dienstleistungen zurückgeben    (Massage: Thai Massage)
                            termin.getGesamtpreis(),     // Gesamtpreis aller gewählten Dienstleistungen            (35.0)
                            termin.getDatum().toString() // Terminzeitpunkt als String                              ("2025-05-31T09:30")
                    });

                } catch(IllegalArgumentException ex) {   // Fehler vom Typ "IllegalArgumentException" abfangen
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE); // Fehlermeldung in einem Dialogfenster anzeigen
                }
            }
        });

        // FILTERN //

        btn_Filtern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String filterText = tf_Filtern.getText().trim().toLowerCase(); // Daten aus dem Eingabefeld (tf_Filtern) holen (Leerzeichen entfernen, in Kleinbuchstaben umwandeln)
                tableModel.setRowCount(0); // Alle aktuellen Zeilen aus der Tabelle löschen

                List<Termin> alleTermine = verwaltung.getAlleTermine(); // Gesamte Terminliste von der Verwaltung abrufen

                for (Termin t : alleTermine) {  // Durch alle Termine iterieren

                    if (filterText.isEmpty()
                            || t.getKundenname().toLowerCase().contains(filterText)
                            || t.getTelefonnummer().toLowerCase().contains(filterText))

                    /* Termin wird angezeigt, wenn mindestens eine der folgenden Bedingungen erfüllt ist:
                     - Das Filterfeld ist leer
                     - Der Kundenname enthält den Filtertext
                     - Die Telefonnummer enthält den Filtertext */

                    {

                        String dienstNamen = "";

                        for (Dienst d : t.getDienstList()) {

                            if (!dienstNamen.isEmpty())
                                dienstNamen += "\n";

                                dienstNamen += d.getDienst() + ": " + d.getAngebot();
                        }

                        tableModel.addRow(new Object[] {
                                t.getKundenname(),
                                t.getTelefonnummer(),
                                dienstNamen.toString(),
                                t.getGesamtpreis(),
                                t.getDatum().toString()
                        });
                    }
                }
            }
        });

        ladeInitialTermine(); // Lädt alle gespeicherten Termine aus der TerminVerwaltung und zeigt sie in der Tabelle an
    }

    // ---------------------------------------------------//
    // 5. HILFSMETHODEN: Daten laden und Preis ermitteln  //
    // ---------------------------------------------------//

    private void ladeInitialTermine() {  // Methode zum Laden der gespeicherten Termine und Einfügen in die Tabelle

        if (!initialisiert) {            // Wenn die Termine noch nicht geladen wurden
            verwaltung.initObjekte();    // Beispieltermine in die Verwaltung laden
            initialisiert = true;        // Nur einmal laden
        }

        for (Termin t : verwaltung.getAlleTermine()) {   // Alle Termine durchlaufen und in die Tabelle einfügen

            String dienstNamen = "";

            for (Dienst d : t.getDienstList()) {
                if (!dienstNamen.isEmpty()) {
                    dienstNamen += "\n";
                }
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

    public double getPreis(String kategorie, String angebot) { // Methode zum Ermitteln des Preises anhand Kategorie + Angebot

        for (Dienst d : Dienst.getAlleAngebot()) { // Alle verfügbaren Angebote durchsuchen

            if (d.getDienst().equals(kategorie) && d.getAngebot().equals(angebot)) { // Wenn Kategorie und Angebot übereinstimmen, Preis zurückgeben
                return d.getPreis();
            }
        }
        return 0.0;
    }
}
