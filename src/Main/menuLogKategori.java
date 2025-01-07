/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Main;

import Kelas.Kategori;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rizan
 */
public class menuLogKategori extends javax.swing.JPanel {

    /**
     * Creates new form menuLogBagian
     */
    public menuLogKategori() throws ParseException {
        initComponents();
        loadTabel();
    }
    
    public void loadTabel() throws ParseException {

        // Model tabel dengan sel yang tidak bisa diedit
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua sel tidak dapat diedit
            }
        };

        model.addColumn("User Login");
        model.addColumn("Kolom yang berubah");
        model.addColumn("Nilai lama");
        model.addColumn("Nilai baru");
        model.addColumn("Waktu");
        model.addColumn("Keterangan");

        try {
            Kategori k = new Kategori();
            ResultSet data = k.KodeTampilTabelLog();

            // Format waktu: 21:11:16 (newline) 25 Desember 2024
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));

            while (data.next()) {
                String waktu = data.getString("waktu"); // Ambil waktu dari database
                String waktuFormatted = "";
                if (waktu != null) {
                    waktuFormatted = timeFormat.format(originalFormat.parse(waktu)) + "\n"
                            + dateFormat.format(originalFormat.parse(waktu));
                }

                model.addRow(new Object[]{
                    data.getString("user_login"),
                    data.getString("kolom_yang_berubah"),
                    data.getString("nilai_lama"),
                    data.getString("nilai_baru"),
                    waktuFormatted, // Format waktu yang sudah diubah
                    data.getString("keterangan"),});
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace(); // Tambahkan log untuk debugging
        }

        tb_LogKategori.setModel(model);

        // Mengatur word wrap di setiap kolom tabel
        tb_LogKategori.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JTextArea textArea = new JTextArea(value != null ? value.toString() : "");
                textArea.setLineWrap(true); // Aktifkan pembungkusan teks
                textArea.setWrapStyleWord(true); // Bungkus berdasarkan kata
                textArea.setOpaque(true); // Pastikan background sel sesuai
                if (isSelected) {
                    textArea.setBackground(table.getSelectionBackground());
                    textArea.setForeground(table.getSelectionForeground());
                } else {
                    textArea.setBackground(table.getBackground());
                    textArea.setForeground(table.getForeground());
                }
                return textArea;
            }
        });

        // Mengatur tinggi baris agar sesuai dengan konten
        tb_LogKategori.setRowHeight(40);

        // Mengatur ukuran kolom
        tb_LogKategori.getColumnModel().getColumn(0).setPreferredWidth(90); // Kolom "User Login"
        tb_LogKategori.getColumnModel().getColumn(1).setPreferredWidth(80); // Kolom "Kolom yang berubah"
        tb_LogKategori.getColumnModel().getColumn(2).setPreferredWidth(130); // Kolom "Nilai Lama"
        tb_LogKategori.getColumnModel().getColumn(3).setPreferredWidth(130); // Kolom "Nilai Baru"
        tb_LogKategori.getColumnModel().getColumn(4).setPreferredWidth(110); // Kolom "Waktu"
        tb_LogKategori.getColumnModel().getColumn(5).setPreferredWidth(230); // Kolom "Keterangan"

        // Mengatur agar tabel tidak bisa diubah ukuran kolomnya atau di-geser
        tb_LogKategori.getTableHeader().setReorderingAllowed(false); // Tidak bisa geser header
        tb_LogKategori.getTableHeader().setResizingAllowed(false);   // Tidak bisa ubah ukuran kolom
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_LogKategori = new javax.swing.JTable();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Menu Log Kategori");

        tb_LogKategori.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tb_LogKategori);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1042, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_LogKategori;
    // End of variables declaration//GEN-END:variables
}
