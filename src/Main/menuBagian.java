/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Main;

import Kelas.Bagian;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rizan
 */
public class menuBagian extends javax.swing.JPanel {

    /**
     * Creates new form menuDashboard
     */
    public menuBagian() {
        initComponents();
        loadTabel();
    }

    public void loadTabel() {
        // Model tabel dengan sel yang tidak bisa diedit
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua sel tidak dapat diedit
            }
        };
        model.addColumn("Kode Bagian Surat");
        model.addColumn("Nama Bagian Surat");
        model.addColumn("User Login");

        try {
            Bagian k = new Bagian();
            ResultSet data = k.KodeTampilTabel();

            while (data.next()) {
                model.addRow(new Object[]{
                    data.getString("kode_bagian"),
                    data.getString("nama_bagian"),
                    data.getString("user_login"),});
            }

            data.close();
        } catch (SQLException sQLException) {
        }

        tb_Bagian.setModel(model);
        tb_Bagian.getTableHeader().setReorderingAllowed(false); // Tidak bisa geser header
        tb_Bagian.getTableHeader().setResizingAllowed(false);   // Tidak bisa ubah ukuran kolom
    }

    void reset() {
        tf_Kode.setText(null);
        tf_Nama.setText(null);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tf_Kode = new javax.swing.JTextField();
        tf_Nama = new javax.swing.JTextField();
        bt_Tambah = new javax.swing.JButton();
        bt_Ubah = new javax.swing.JButton();
        bt_Hapus = new javax.swing.JButton();
        bt_Reset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_Bagian = new javax.swing.JTable();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel1.setText("Menu Bagian");

        jLabel2.setText("Kode Bagian");

        jLabel3.setText("Nama Bagian");

        bt_Tambah.setText("Tambah");
        bt_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_TambahActionPerformed(evt);
            }
        });

        bt_Ubah.setText("Ubah");
        bt_Ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_UbahActionPerformed(evt);
            }
        });

        bt_Hapus.setText("Hapus");
        bt_Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_HapusActionPerformed(evt);
            }
        });

        bt_Reset.setText("Reset");
        bt_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ResetActionPerformed(evt);
            }
        });

        tb_Bagian.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_Bagian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_BagianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_Bagian);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1042, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(bt_Tambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bt_Ubah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bt_Hapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bt_Reset))
                            .addComponent(tf_Nama)
                            .addComponent(tf_Kode))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tf_Kode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tf_Nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_Tambah)
                    .addComponent(bt_Ubah)
                    .addComponent(bt_Hapus)
                    .addComponent(bt_Reset))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void bt_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_TambahActionPerformed
        try {
            if (tf_Kode.getText().isEmpty() || tf_Nama.getText().isEmpty()) {
                TimeJOption.AutoCloseJOptionPane.showMessageDialog("Kode dan Nama bagian tidak boleh kosong!", null, JOptionPane.ERROR_MESSAGE, 3000);
                return;
            }

            Bagian kodeTambah = new Kelas.Bagian();
            kodeTambah.setKode_bagian(tf_Kode.getText());
            kodeTambah.setNama_bagian(tf_Nama.getText());
            kodeTambah.setUser_login(MenuUtama.lb_Username.getText());

            kodeTambah.KodeTambah();
            reset();
            loadTabel();

        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + sQLException.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_TambahActionPerformed

    private void bt_UbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_UbahActionPerformed
        try {
            if (MenuUtama.lb_Username.getText().isEmpty() || tf_Nama.getText().isEmpty()) {
                TimeJOption.AutoCloseJOptionPane.showMessageDialog("Kode dan Nama bagian tidak boleh kosong!", null, JOptionPane.ERROR_MESSAGE, 3000);
                return;
            }

            Bagian kodeUbah = new Bagian();
            kodeUbah.setUser_login(MenuUtama.lb_Username.getText());
            kodeUbah.setNama_bagian(tf_Nama.getText());
            kodeUbah.setKode_bagian(tf_Kode.getText());
            kodeUbah.KodeUbah();

            reset();
            loadTabel();
            tf_Kode.setEditable(false);
        } catch (SQLException ex) {
            Logger.getLogger(menuBagian.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_UbahActionPerformed

    private void bt_HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_HapusActionPerformed
        try {

            if (tf_Kode.getText().isEmpty()) {
                TimeJOption.AutoCloseJOptionPane.showMessageDialog("Pilih data yang ingin dihapus!", null, JOptionPane.WARNING_MESSAGE, 1000);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Yakin ingin menghapus data ini? \n Ini akan memindahkan data ke dalam sampah",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {

                Bagian kodeHapus = new Bagian();
                kodeHapus.setUser_login(MenuUtama.lb_Username.getText());
                kodeHapus.setKode_bagian(tf_Kode.getText());
                kodeHapus.KodeHapus();
                reset();
                loadTabel();
                tf_Kode.setEditable(false);
            }
        } catch (SQLException sQLException) {
        }
    }//GEN-LAST:event_bt_HapusActionPerformed

    private void bt_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ResetActionPerformed
        reset();
        loadTabel();
    }//GEN-LAST:event_bt_ResetActionPerformed

    private void tb_BagianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_BagianMouseClicked
        int baris = tb_Bagian.rowAtPoint(evt.getPoint());
        String kode = tb_Bagian.getValueAt(baris, 0).toString();
        String nama = tb_Bagian.getValueAt(baris, 1).toString();
        tf_Kode.setText(kode);
        tf_Nama.setText(nama);
        tf_Kode.setEditable(false);
    }//GEN-LAST:event_tb_BagianMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Hapus;
    private javax.swing.JButton bt_Reset;
    private javax.swing.JButton bt_Tambah;
    private javax.swing.JButton bt_Ubah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_Bagian;
    private javax.swing.JTextField tf_Kode;
    private javax.swing.JTextField tf_Nama;
    // End of variables declaration//GEN-END:variables
}
