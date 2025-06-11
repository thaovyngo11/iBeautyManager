package view;

import javax.swing.*;

public class iBeautyFenster extends JFrame{
    private JTextField tf_Name;
    private JTextField tf_Telefon;
    private JCheckBox chk_Dienst1;
    private JCheckBox chk_Dienst2;
    private JCheckBox chk_Dienst3;
    private JComboBox cbx_Dienstleistung1;
    private JComboBox cbx_Dienstleistung2;
    private JComboBox cbx_Dienstleistung3;
    private JTextField tf_Filtern;
    private JRadioButton rdb_Filtern;
    private JPanel myPanel;
    private JLabel lbl_slogan;
    private JLabel lbl_Name;
    private JLabel lbl_Termin;
    private JLabel lbl_Uhrzeit;
    private JLabel lbl_Telefon;
    private JLabel lbl_Dienst;
    private JLabel lbl_Dienstleistung;
    private JScrollPane scp_Termin_Uebersicht;
    private JTable tb_Termin_Uebersicht;

    public iBeautyFenster() {
        setTitle("iBeauty Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setContentPane(myPanel);
        setVisible(true);
    }
}
