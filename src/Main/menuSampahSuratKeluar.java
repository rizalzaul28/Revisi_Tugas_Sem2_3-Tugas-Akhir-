/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Main;

import Kelas.Bagian;
import Kelas.Kategori;
import Kelas.Surat_Keluar;
import PopUp.PopUpSuratKeluar;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rizan
 */
public class menuSampahSuratKeluar extends javax.swing.JPanel implements Surat_Keluar.PerubahanData {

    private Surat_Keluar sk;

    /**
     * Creates new form menuSuratKeluar
     */
    public menuSampahSuratKeluar() throws SQLException, ParseException {
        initComponents();
        sk = new Surat_Keluar();
        sk.TambahPerubahanData(this);

        BlokJDate();

        // Mengatur Locale ke bahasa Indonesia
        dc_tglAwalSampah.setLocale(new Locale("id"));
        dc_tglAkhirSampah.setLocale(new Locale("id"));

        // Mengatur format tanggal ke "dd MMMM yyyy"
        dc_tglAwalSampah.setDateFormatString("dd MMMM yyyy");
        dc_tglAkhirSampah.setDateFormatString("dd MMMM yyyy");

        loadTabel();
        cbBagianSurat();
        cbKategoriSurat();

//        cb_KategoriMenu.addActionListener(evt -> applyFilters());
//        cb_BagianMenu.addActionListener(evt -> applyFilters());
//
//        dc_tglAwal.addPropertyChangeListener(evt -> {
//            if ("date".equals(evt.getPropertyName())) {
//                applyFilters();
//            }
//        });
//
//        dc_tglAkhir.addPropertyChangeListener(evt -> {
//            if ("date".equals(evt.getPropertyName())) {
//                applyFilters();
//            }
//        });
//
    }

    void cbKategoriSurat() {
        try {
            cb_KategoriMenuSampah.addItem("--Pilih Kategori Surat--");

            Kategori ks = new Kategori();
            ResultSet data = ks.Tampil_CbKategoriSurat();

            while (data.next()) {
                cb_KategoriMenuSampah.addItem(data.getString("kode_kategori") + " - " + data.getString("nama_kategori"));
            }

            cb_KategoriMenuSampah.setSelectedItem("--Pilih Kategori Surat--");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cbBagianSurat() {
        try {
            cb_BagianMenuSampah.addItem("--Pilih Bagian Surat--");

            Bagian bg = new Bagian();
            ResultSet data = bg.Tampil_CbBagianSurat();

            while (data.next()) {
                cb_BagianMenuSampah.addItem(data.getString("kode_bagian") + " - " + data.getString("nama_bagian"));
            }

            cb_BagianMenuSampah.setSelectedItem("--Pilih Bagian Surat--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTabel() throws ParseException {
        // Model tabel dengan sel yang tidak bisa diedit
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua sel tidak dapat diedit
            }
        };

        model.addColumn(null); // Kolom tersembunyi untuk ID
        model.addColumn("Bagian");
        model.addColumn("Kategori");
        model.addColumn("No Surat");
        model.addColumn("Tanggal Dibuat");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");
        model.addColumn("User Login");

        try {
            Surat_Keluar k = new Surat_Keluar();
            ResultSet data = k.KodeTampilTabelSampah();

            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));

            while (data.next()) {

                String formattedDate = "";
                if (data.getString("tanggal_dibuat") != null) {
                    java.util.Date date = java.sql.Date.valueOf(data.getString("tanggal_dibuat"));
                    formattedDate = dateFormat.format(date);
                }

                model.addRow(new Object[]{
                    data.getString("id_suratkeluar"), // ID
                    data.getString("bagian"),
                    data.getString("kategori"),
                    data.getString("no_surat"),
                    formattedDate,
                    data.getString("perihal"),
                    data.getString("tujuan"),
                    data.getString("nama_file"),
                    data.getString("user_login"),});
            }

            data.close();
        } catch (SQLException sQLException) {
            sQLException.printStackTrace(); // Tambahkan log untuk debugging
        }

        tb_SampahSuratKeluar.setModel(model);
        // Sembunyikan kolom ID
        tb_SampahSuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SampahSuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SampahSuratKeluar.getColumnModel().getColumn(0).setWidth(0);

        // Mengatur word wrap di setiap kolom tabel
        tb_SampahSuratKeluar.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        tb_SampahSuratKeluar.setRowHeight(40);

        // Mengatur ukuran kolom
        tb_SampahSuratKeluar.getColumnModel().getColumn(0).setPreferredWidth(100);  // Kolom "Bagian"
        tb_SampahSuratKeluar.getColumnModel().getColumn(1).setPreferredWidth(100);  // Kolom "Kategori"
        tb_SampahSuratKeluar.getColumnModel().getColumn(2).setPreferredWidth(125); // Kolom "Nomor Surat"
        tb_SampahSuratKeluar.getColumnModel().getColumn(3).setPreferredWidth(125); // Kolom "Tanggal dibuat"
        tb_SampahSuratKeluar.getColumnModel().getColumn(4).setPreferredWidth(130); // Kolom "Perihal"
        tb_SampahSuratKeluar.getColumnModel().getColumn(5).setPreferredWidth(130); // Kolom "Tujuan"
        tb_SampahSuratKeluar.getColumnModel().getColumn(6).setPreferredWidth(200); // Kolom "Nama File"
        tb_SampahSuratKeluar.getColumnModel().getColumn(7).setPreferredWidth(90); // Kolom "User Login"

        // Mengatur agar tabel tidak bisa diubah ukuran kolomnya atau di-geser
        tb_SampahSuratKeluar.getTableHeader().setReorderingAllowed(false); // Tidak bisa geser header
        tb_SampahSuratKeluar.getTableHeader().setResizingAllowed(false);   // Tidak bisa ubah ukuran kolom
    }
    //
    //    private void applyFilters() {
    //        String selectedKategori = cb_KategoriMenu.getSelectedItem().toString();
    //        String selectedBagian = cb_BagianMenu.getSelectedItem().toString();
    //        java.util.Date tanggalAwal = dc_tglAwal.getDate(); // Tanggal awal
    //        java.util.Date tanggalAkhir = dc_tglAkhir.getDate(); // Tanggal akhir
    //
    //        String filterKategori = null;
    //        String filterBagian = null;
    //        java.sql.Date sqlTanggalAwal = null;
    //        java.sql.Date sqlTanggalAkhir = null;
    //
    //        if (!selectedKategori.equals("--Pilih Kategori Surat--")) {
    //            filterKategori = selectedKategori.split(" - ")[0];
    //        }
    //
    //        if (!selectedBagian.equals("--Pilih Bagian Surat--")) {
    //            filterBagian = selectedBagian.split(" - ")[0];
    //        }
    //
    //        if (tanggalAwal != null) {
    //            sqlTanggalAwal = new java.sql.Date(tanggalAwal.getTime());
    //        }
    //
    //        if (tanggalAkhir != null) {
    //            sqlTanggalAkhir = new java.sql.Date(tanggalAkhir.getTime());
    //        }
    //
    //        DefaultTableModel model = new DefaultTableModel();
    //        model.addColumn("Bagian");
    //        model.addColumn("Kategori");
    //        model.addColumn("No Surat");
    //        model.addColumn("Tanggal Dibuat");
    //        model.addColumn("Perihal");
    //        model.addColumn("Tujuan");
    //        model.addColumn("Nama File");
    //        model.addColumn("User Login");
    //
    //        try {
    //            Surat_Keluar bg = new Surat_Keluar();
    //            ResultSet data = bg.KodeTampilByFilters();
    //
    //            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));
    //
    //            while (data.next()) {
    //                String formattedDate = "";
    //                if (data.getString("tgl_dibuat") != null) {
    //                    java.util.Date date = java.sql.Date.valueOf(data.getString("tgl_dibuat"));
    //                    formattedDate = dateFormat.format(date);
    //                }
    //
    //                model.addRow(new Object[]{
    //                    data.getString("bagian"),
    //                    data.getString("kategori"),
    //                    data.getString("no_surat"),
    //                    formattedDate,
    //                    data.getString("perihal"),
    //                    data.getString("tujuan"),
    //                    data.getString("nama_file"),
    //                    data.getString("user_login"),});
    //            }
    //
    //            data.close();
    //        } catch (SQLException sQLException) {
    //            sQLException.printStackTrace();
    //        }
    //
    //        tb_SuratKeluar.setModel(model);
    //
    //        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
    //        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
    //        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    //    }
    //
    //    private void filterTabelByKategori() throws ParseException {
    //        String selectedKategori = cb_KategoriMenu.getSelectedItem().toString();
    //        if (selectedKategori.equals("--Pilih Kategori Surat--")) {
    //            loadTabel();
    //            return;
    //        }
    //
    //        String kodeKategori = selectedKategori.split(" - ")[0]; // Ambil kode kategori
    //        DefaultTableModel model = new DefaultTableModel();
    //        model.addColumn("Bagian");
    //        model.addColumn("Kategori");
    //        model.addColumn("No Surat");
    //        model.addColumn("Tanggal Dibuat");
    //        model.addColumn("Perihal");
    //        model.addColumn("Tujuan");
    //        model.addColumn("Nama File");
    //        model.addColumn("User Login");
    //
    //        try {
    //            Surat_Keluar bg = new Surat_Keluar();
    //            ResultSet data = bg.KodeTampilByKategori();
    //
    //            while (data.next()) {
    //                if (data.getString("kategori").equals(kodeKategori)) {
    //                    model.addRow(new Object[]{
    //                        data.getString("bagian"),
    //                        data.getString("kategori"),
    //                        data.getString("no_surat"),
    //                        data.getString("tanggal_dibuat"),
    //                        data.getString("perihal"),
    //                        data.getString("tujuan"),
    //                        data.getString("nama_file"),
    //                        data.getString("user_login"),});
    //                }
    //            }
    //
    //            data.close();
    //        } catch (SQLException sQLException) {
    //            sQLException.printStackTrace();
    //        }
    //
    //        tb_SuratKeluar.setModel(model);
    //
    //        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
    //        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
    //        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    //    }
    //
    //    private void filterTabelByBagian() throws ParseException {
    //        String selectedBagian = cb_BagianMenu.getSelectedItem().toString();
    //
    //        if (selectedBagian.equals("--Pilih Bagian Surat--")) {
    //            loadTabel();
    //        } else {
    //            String filterBagian = selectedBagian.split(" - ")[0];
    //            DefaultTableModel model = new DefaultTableModel();
    //            model.addColumn("Bagian");
    //            model.addColumn("Kategori");
    //            model.addColumn("No Surat");
    //            model.addColumn("Tanggal Dibuat");
    //            model.addColumn("Perihal");
    //            model.addColumn("Tujuan");
    //            model.addColumn("Nama File");
    //            model.addColumn("User Login");
    //
    //            try {
    //                Surat_Keluar bg = new Surat_Keluar();
    //                ResultSet data = bg.KodeTampilByBagian();
    //
    //                while (data.next()) {
    //                    model.addRow(new Object[]{
    //                        data.getString("bagian"),
    //                        data.getString("kategori"),
    //                        data.getString("no_surat"),
    //                        data.getString("tanggal_dibuat"),
    //                        data.getString("perihal"),
    //                        data.getString("tujuan"),
    //                        data.getString("nama_file"),
    //                        data.getString("user_login"),});
    //                }
    //
    //                data.close();
    //            } catch (SQLException sQLException) {
    //                sQLException.printStackTrace();
    //            }
    //
    //            tb_SuratKeluar.setModel(model);
    //
    //            tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
    //            tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
    //            tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    //        }
    //    }
    //
    //    private void tf_TglPropertyChange(java.beans.PropertyChangeEvent evt) throws ParseException {
    //        if ("date".equals(evt.getPropertyName())) {
    //            java.util.Date selectedDate = dc_tglAkhir.getDate();
    //            if (selectedDate != null) {
    //                filterTabelByTanggal(selectedDate);
    //            } else {
    //                filterTabelByKategori();
    //                filterTabelByBagian();
    //                loadTabel();
    //            }
    //        }
    //    }
    //
    //    private void filterTabelByTanggal(java.util.Date tanggal) {
    //        DefaultTableModel model = new DefaultTableModel();
    //        model.addColumn("Bagian");
    //        model.addColumn("Kategori");
    //        model.addColumn("No Surat");
    //        model.addColumn("Tanggal Dibuat");
    //        model.addColumn("Perihal");
    //        model.addColumn("Tujuan");
    //        model.addColumn("Nama File");
    //        model.addColumn("User Login");
    //
    //        try {
    //            java.sql.Date sqlDate = new java.sql.Date(tanggal.getTime());
    //            Surat_Keluar bg = new Surat_Keluar();
    //            ResultSet data = bg.KodeTampilByTanggal();
    //
    //            while (data.next()) {
    //                model.addRow(new Object[]{
    //                    data.getString("bagian"),
    //                    data.getString("kategori"),
    //                    data.getString("no_surat"),
    //                    data.getString("tanggal_dibuat"),
    //                    data.getString("perihal"),
    //                    data.getString("tujuan"),
    //                    data.getString("nama_file"),
    //                    data.getString("user_login"),});
    //            }
    //
    //            data.close();
    //        } catch (SQLException sQLException) {
    //            sQLException.printStackTrace();
    //        }
    //
    //        tb_SuratKeluar.setModel(model);
    //
    //        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
    //        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
    //        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);
    //    }

    public void reset() throws ParseException {
        cb_KategoriMenuSampah.setSelectedIndex(0);
        cb_BagianMenuSampah.setSelectedIndex(0);
        dc_tglAwalSampah.setCalendar(null);
        dc_tglAkhirSampah.setCalendar(null);
        tf_CariSampah.setText(null);
        loadTabel();
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        bt_Reset = new javax.swing.JButton();
        cb_BagianMenuSampah = new javax.swing.JComboBox<>();
        cb_KategoriMenuSampah = new javax.swing.JComboBox<>();
        dc_tglAwalSampah = new com.toedter.calendar.JDateChooser();
        dc_tglAkhirSampah = new com.toedter.calendar.JDateChooser();
        tf_CariSampah = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_SampahSuratKeluar = new javax.swing.JTable();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Menu Sampah Surat Keluar");

        jLabel2.setText("Filter");

        jLabel3.setText("Bagian");

        jLabel4.setText("Kategori");

        jLabel5.setText("Tanggal Awal");

        jLabel6.setText("Tanggal Akhir");

        jLabel7.setText("Cari berdasarkan perihal dan tujuan");

        bt_Reset.setText("Reset");
        bt_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ResetActionPerformed(evt);
            }
        });

        tb_SampahSuratKeluar.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_SampahSuratKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_SampahSuratKeluarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_SampahSuratKeluar);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb_BagianMenuSampah, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cb_KategoriMenuSampah, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dc_tglAkhirSampah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dc_tglAwalSampah, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(tf_CariSampah)))
                        .addGap(18, 18, 18)
                        .addComponent(bt_Reset)
                        .addGap(0, 352, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(53, 53, 53)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dc_tglAwalSampah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dc_tglAkhirSampah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(cb_BagianMenuSampah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(cb_KategoriMenuSampah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tf_CariSampah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_Reset))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void bt_ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ResetActionPerformed
        try {
            reset();
        } catch (ParseException ex) {
            Logger.getLogger(menuSampahSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_ResetActionPerformed

    private void tb_SampahSuratKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_SampahSuratKeluarMouseClicked
        try {
            int baris = tb_SampahSuratKeluar.getSelectedRow();
            if (baris != -1 && tb_SampahSuratKeluar.getValueAt(baris, 0) != null) {

                String Id = tb_SampahSuratKeluar.getValueAt(baris, 0).toString();
                String Bagian = tb_SampahSuratKeluar.getValueAt(baris, 1).toString();
                String Kategori = tb_SampahSuratKeluar.getValueAt(baris, 2).toString();
                String Nomor = tb_SampahSuratKeluar.getValueAt(baris, 3).toString();
                String Tanggal = tb_SampahSuratKeluar.getValueAt(baris, 4).toString();
                String Perihal = tb_SampahSuratKeluar.getValueAt(baris, 5).toString();
                String Tujuan = tb_SampahSuratKeluar.getValueAt(baris, 6).toString();
                String File = tb_SampahSuratKeluar.getValueAt(baris, 7).toString();

                PopUpSuratKeluar pusk = new PopUpSuratKeluar(null, true, sk, true);

                pusk.bt_Tambah.setVisible(false);
                pusk.bt_Ubah.setVisible(false);
                pusk.bt_Hapus.setVisible(false);
                pusk.bt_Lihat.setVisible(false);
                pusk.bt_Reset.setVisible(false);
                pusk.bt_Salin.setVisible(false);

                pusk.isInitializing = true; // Set flag sebelum pengisian data
                pusk.lb_Id.setText(Id);

                for (int i = 0; i < pusk.cb_Bagian.getItemCount(); i++) {
                    String item = pusk.cb_Bagian.getItemAt(i);
                    if (item.startsWith(Bagian + " -")) {
                        pusk.cb_Bagian.setSelectedIndex(i);
                        break;
                    }
                }

                for (int i = 0; i < pusk.cb_Kategori.getItemCount(); i++) {
                    String item = pusk.cb_Kategori.getItemAt(i);
                    if (item.startsWith(Kategori + " -")) {
                        pusk.cb_Kategori.setSelectedIndex(i);
                        break;
                    }
                }

                pusk.tf_NoSurat.setText(Nomor);

                if (Tanggal != null && !Tanggal.isEmpty()) {
                    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));
                    java.util.Date date = formatter.parse(Tanggal);
                    pusk.dc_Tanggal.setDate(date);
                }

                pusk.tf_Perihal.setText(Perihal);
                pusk.tf_Tujuan.setText(Tujuan);
                pusk.ta_File.setText(File);

                pusk.isInitializing = false; // Selesai inisialisasi
                pusk.setVisible(true);
            }
        } catch (SQLException | java.text.ParseException ex) {
            Logger.getLogger(menuLogSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tb_SampahSuratKeluarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Reset;
    private javax.swing.JComboBox<String> cb_BagianMenuSampah;
    private javax.swing.JComboBox<String> cb_KategoriMenuSampah;
    private com.toedter.calendar.JDateChooser dc_tglAkhirSampah;
    private com.toedter.calendar.JDateChooser dc_tglAwalSampah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_SampahSuratKeluar;
    private javax.swing.JTextField tf_CariSampah;
    // End of variables declaration//GEN-END:variables

    public void BlokJDate() {
        blokirEditor(dc_tglAwalSampah);
        blokirEditor(dc_tglAkhirSampah);
    }

    private void blokirEditor(JDateChooser dateChooser) {
        if (dateChooser.getDateEditor() instanceof JTextFieldDateEditor) {
            JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
            editor.setEditable(false);
        }
    }

    @Override
    public void AktifPerubahanData() {
        try {
            loadTabel();
        } catch (ParseException ex) {
            Logger.getLogger(menuSampahSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
