package view;

import javax.swing.*;

public class iBeautyFenster extends JFrame{
    private JTextField tf_Name;
    private JTextField tf_Telefon;
    private JCheckBox chk_Dienst1;
    private JCheckBox chk_Dienst2;
    private JCheckBox chk_Dienst3;
    private JComboBox cbx_Angebot1;
    private JComboBox cbx_Angebot2;
    private JComboBox cbx_Angebot3;
    private JTextField tf_Filtern;
    private JPanel myPanel;
    private JLabel lbl_Slogan;
    private JLabel lbl_Name;
    private JLabel lbl_Termin;
    private JLabel lbl_Telefon;
    private JLabel lbl_Dienste;
    private JLabel lbl_Angeboten;
    private JButton btn_Filtern;
    private JScrollPane scp_Termin_Uebersicht;
    private JTable tb_Termin_Uebersicht;
    private JButton btn_Speichern_und_Anzeigen;
    private JLabel lbl_Gesamtpreis;
    private JTextField textField1;

    public iBeautyFenster() {
        setTitle("iBeauty Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setContentPane(myPanel);
        setVisible(true);
    }
}
