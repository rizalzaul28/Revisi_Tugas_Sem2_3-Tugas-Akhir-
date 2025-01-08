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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rizan
 */
public class menuSuratKeluar extends javax.swing.JPanel implements Surat_Keluar.PerubahanData {

    private Surat_Keluar sk;

    /**
     * Creates new form menuSuratKeluar
     */
    public menuSuratKeluar() throws SQLException, ParseException {
        initComponents();
        addSearchListener();
        sk = new Surat_Keluar();
        sk.TambahPerubahanData(this);

        BlokJDate();

        // Mengatur Locale ke bahasa Indonesia
        dc_tglAwal.setLocale(new Locale("id"));
        dc_tglAkhir.setLocale(new Locale("id"));

        // Mengatur format tanggal ke "dd MMMM yyyy"
        dc_tglAwal.setDateFormatString("dd MMMM yyyy");
        dc_tglAkhir.setDateFormatString("dd MMMM yyyy");

        loadTabel();
        cbBagianSurat();
        cbKategoriSurat();

        cb_KategoriMenu.addActionListener(evt -> {
            try {
                applyFilters();
            } catch (SQLException ex) {
                Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        cb_BagianMenu.addActionListener(evt -> {
            try {
                applyFilters();
            } catch (SQLException ex) {
                Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        dc_tglAwal.addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                try {
                    applyFilters();
                } catch (SQLException ex) {
                    Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        dc_tglAkhir.addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                try {
                    applyFilters();
                } catch (SQLException ex) {
                    Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    void cbKategoriSurat() {
        try {
            cb_KategoriMenu.addItem("--Pilih Kategori Surat--");

            Kategori ks = new Kategori();
            ResultSet data = ks.Tampil_CbKategoriSurat();

            while (data.next()) {
                cb_KategoriMenu.addItem(data.getString("kode_kategori") + " - " + data.getString("nama_kategori"));
            }

            cb_KategoriMenu.setSelectedItem("--Pilih Kategori Surat--");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cbBagianSurat() {
        try {
            cb_BagianMenu.addItem("--Pilih Bagian Surat--");

            Bagian bg = new Bagian();
            ResultSet data = bg.Tampil_CbBagianSurat();

            while (data.next()) {
                cb_BagianMenu.addItem(data.getString("kode_bagian") + " - " + data.getString("nama_bagian"));
            }

            cb_BagianMenu.setSelectedItem("--Pilih Bagian Surat--");
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
            ResultSet data = k.KodeTampilTabel();

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

        tb_SuratKeluar.setModel(model);
        // Sembunyikan kolom ID
        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);

        // Mengatur word wrap di setiap kolom tabel
        tb_SuratKeluar.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        tb_SuratKeluar.setRowHeight(40);

        // Mengatur ukuran kolom
        tb_SuratKeluar.getColumnModel().getColumn(0).setPreferredWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(1).setPreferredWidth(100); // Kolom "Bagian"
        tb_SuratKeluar.getColumnModel().getColumn(2).setPreferredWidth(125); // Kolom "Kategori"
        tb_SuratKeluar.getColumnModel().getColumn(3).setPreferredWidth(125); // Kolom "Nomor Surat"
        tb_SuratKeluar.getColumnModel().getColumn(4).setPreferredWidth(120); // Kolom "Tanggal dibuat"
        tb_SuratKeluar.getColumnModel().getColumn(5).setPreferredWidth(150); // Kolom "Perihal"
        tb_SuratKeluar.getColumnModel().getColumn(6).setPreferredWidth(150); // Kolom "Tujuan"
        tb_SuratKeluar.getColumnModel().getColumn(7).setPreferredWidth(160); // Kolom "Nama File"
        tb_SuratKeluar.getColumnModel().getColumn(8).setPreferredWidth(90);  // Kolom "User Login"

        // Mengatur agar tabel tidak bisa diubah ukuran kolomnya atau di-geser
        tb_SuratKeluar.getTableHeader().setReorderingAllowed(false); // Tidak bisa geser header
        tb_SuratKeluar.getTableHeader().setResizingAllowed(false);   // Tidak bisa ubah ukuran kolom
    }

    private void applyFilters() throws SQLException {

        Surat_Keluar suratKeluar = new Surat_Keluar();

        // Set nilai filter dari komponen UI
        if (!cb_KategoriMenu.getSelectedItem().toString().equals("--Pilih Kategori Surat--")) {
            suratKeluar.setKategori(cb_KategoriMenu.getSelectedItem().toString().split(" - ")[0]);
        }

        if (!cb_BagianMenu.getSelectedItem().toString().equals("--Pilih Bagian Surat--")) {
            suratKeluar.setBagian(cb_BagianMenu.getSelectedItem().toString().split(" - ")[0]);
        }

        suratKeluar.setTanggalAwal(dc_tglAwal.getDate());
        suratKeluar.setTanggalAkhir(dc_tglAkhir.getDate());

        // Ambil data berdasarkan filter
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(null);
        model.addColumn("Bagian");
        model.addColumn("Kategori");
        model.addColumn("Nomor");
        model.addColumn("Tanggal");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");
        model.addColumn("User");

        try {
            ResultSet data = suratKeluar.KodeTampilByFilters();
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));

            while (data.next()) {
                String formattedDate = "";
                if (data.getString("tanggal_dibuat") != null) {
                    java.util.Date date = java.sql.Date.valueOf(data.getString("tanggal_dibuat"));
                    formattedDate = dateFormat.format(date);
                }

                model.addRow(new Object[]{
                    data.getString("id_suratkeluar"),
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tb_SuratKeluar.setModel(model);

        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);

        // Mengatur word wrap di setiap kolom tabel
        tb_SuratKeluar.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        tb_SuratKeluar.setRowHeight(40);

        // Mengatur ukuran kolom
        tb_SuratKeluar.getColumnModel().getColumn(0).setPreferredWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(1).setPreferredWidth(100); // Kolom "Bagian"
        tb_SuratKeluar.getColumnModel().getColumn(2).setPreferredWidth(125); // Kolom "Kategori"
        tb_SuratKeluar.getColumnModel().getColumn(3).setPreferredWidth(125); // Kolom "Nomor Surat"
        tb_SuratKeluar.getColumnModel().getColumn(4).setPreferredWidth(120); // Kolom "Tanggal dibuat"
        tb_SuratKeluar.getColumnModel().getColumn(5).setPreferredWidth(150); // Kolom "Perihal"
        tb_SuratKeluar.getColumnModel().getColumn(6).setPreferredWidth(150); // Kolom "Tujuan"
        tb_SuratKeluar.getColumnModel().getColumn(7).setPreferredWidth(160); // Kolom "Nama File"
        tb_SuratKeluar.getColumnModel().getColumn(8).setPreferredWidth(90);  // Kolom "User Login"

        // Mengatur agar tabel tidak bisa diubah ukuran kolomnya atau di-geser
        tb_SuratKeluar.getTableHeader().setReorderingAllowed(false); // Tidak bisa geser header
        tb_SuratKeluar.getTableHeader().setResizingAllowed(false);   // Tidak bisa ubah ukuran kolom

    }

    private void Cari() throws SQLException, ParseException {
        // Ambil teks dari text field
        String keyword = tf_Cari.getText().trim();
        if (keyword.isEmpty()) {
            loadTabel(); // Jika kosong, reset tabel
            return;
        }

        // Model tabel dengan sel yang tidak bisa diedit
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua sel tidak dapat diedit
            }
        };

        // Tambahkan kolom ke model
        model.addColumn(null); // Kolom untuk ID
        model.addColumn("Bagian");
        model.addColumn("Kategori");
        model.addColumn("No Surat");
        model.addColumn("Tanggal Dibuat");
        model.addColumn("Perihal");
        model.addColumn("Tujuan");
        model.addColumn("Nama File");
        model.addColumn("User Login");

        // Set kata kunci pencarian di objek Surat_Keluar
        Surat_Keluar suratKeluar = new Surat_Keluar();
        suratKeluar.setKata_kunci(keyword); // Set kata kunci menggunakan setter

        // Jalankan query dan ambil data
        try (ResultSet data = suratKeluar.KodeCari()) {
            // Format tanggal
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("id", "ID"));

            while (data.next()) {
                // Format tanggal
                String formattedDate = "";
                java.util.Date date = data.getDate("tanggal_dibuat");
                if (date != null) {
                    formattedDate = dateFormat.format(date);
                }

                // Tambahkan baris ke model
                model.addRow(new Object[]{
                    data.getString("id_suratkeluar"),
                    data.getString("bagian"),
                    data.getString("kategori"),
                    data.getString("no_surat"),
                    formattedDate,
                    data.getString("perihal"),
                    data.getString("tujuan"),
                    data.getString("nama_file"),
                    data.getString("user_login"),});
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log error untuk debugging
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Set model ke tabel
        tb_SuratKeluar.setModel(model);

        // Sembunyikan kolom ID
        tb_SuratKeluar.getColumnModel().getColumn(0).setMinWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setMaxWidth(0);
        tb_SuratKeluar.getColumnModel().getColumn(0).setWidth(0);

        // Custom renderer untuk word wrap
        tb_SuratKeluar.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JTextArea textArea = new JTextArea(value != null ? value.toString() : "");
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setOpaque(true);
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

        // Atur tinggi baris
        tb_SuratKeluar.setRowHeight(40);

        // Atur lebar kolom
        tb_SuratKeluar.getColumnModel().getColumn(1).setPreferredWidth(100); // Bagian
        tb_SuratKeluar.getColumnModel().getColumn(2).setPreferredWidth(125); // Kategori
        tb_SuratKeluar.getColumnModel().getColumn(3).setPreferredWidth(125); // No Surat
        tb_SuratKeluar.getColumnModel().getColumn(4).setPreferredWidth(120); // Tanggal Dibuat
        tb_SuratKeluar.getColumnModel().getColumn(5).setPreferredWidth(150); // Perihal
        tb_SuratKeluar.getColumnModel().getColumn(6).setPreferredWidth(150); // Tujuan
        tb_SuratKeluar.getColumnModel().getColumn(7).setPreferredWidth(160); // Nama File
        tb_SuratKeluar.getColumnModel().getColumn(8).setPreferredWidth(90);  // User Login

        // Atur header tabel
        tb_SuratKeluar.getTableHeader().setReorderingAllowed(false);
        tb_SuratKeluar.getTableHeader().setResizingAllowed(false);
    }

    private void addSearchListener() {
        // Timer dengan delay 300ms
        Timer timer = new Timer(500, e -> {
            String keyword = tf_Cari.getText().trim();
            if (keyword.isEmpty()) {
                try {
                    loadTabel(); // Jika text field kosong, reset tabel
                } catch (ParseException ex) {
                    Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    Cari(); // Jika tidak kosong, lakukan pencarian
                } catch (SQLException ex) {
                    Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        // Pastikan timer tidak otomatis dijalankan
        timer.setRepeats(false);

        // Tambahkan DocumentListener ke text field
        tf_Cari.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timer.restart(); // Restart timer setiap kali teks berubah
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                timer.restart(); // Restart timer setiap kali teks dihapus
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Tidak diperlukan untuk JTextField
            }
        });
    }

    public void reset() throws ParseException {
        cb_KategoriMenu.setSelectedIndex(0);
        cb_BagianMenu.setSelectedIndex(0);
        dc_tglAwal.setCalendar(null);
        dc_tglAkhir.setCalendar(null);
        tf_Cari.setText(null);
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
        bt_TambahSurat = new javax.swing.JButton();
        bt_Reset = new javax.swing.JButton();
        cb_BagianMenu = new javax.swing.JComboBox<>();
        cb_KategoriMenu = new javax.swing.JComboBox<>();
        dc_tglAwal = new com.toedter.calendar.JDateChooser();
        dc_tglAkhir = new com.toedter.calendar.JDateChooser();
        tf_Cari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_SuratKeluar = new javax.swing.JTable();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Menu Surat Keluar");

        jLabel2.setText("Filter");

        jLabel3.setText("Bagian");

        jLabel4.setText("Kategori");

        jLabel5.setText("Tanggal Awal");

        jLabel6.setText("Tanggal Akhir");

        jLabel7.setText("Cari berdasarkan perihal dan tujuan");

        bt_TambahSurat.setText("Tambah Surat Keluar");
        bt_TambahSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_TambahSuratActionPerformed(evt);
            }
        });

        bt_Reset.setText("Reset");
        bt_Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ResetActionPerformed(evt);
            }
        });

        tf_Cari.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tf_CariCaretUpdate(evt);
            }
        });
        tf_Cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_CariKeyReleased(evt);
            }
        });

        tb_SuratKeluar.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_SuratKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_SuratKeluarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_SuratKeluar);

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
                            .addComponent(bt_TambahSurat)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cb_BagianMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cb_KategoriMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dc_tglAkhir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dc_tglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(tf_Cari)))
                        .addGap(18, 18, 18)
                        .addComponent(bt_Reset)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(bt_TambahSurat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dc_tglAwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dc_tglAkhir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(cb_BagianMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(cb_KategoriMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tf_Cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_ResetActionPerformed

    private void bt_TambahSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_TambahSuratActionPerformed
        try {
            PopUpSuratKeluar ppsk = new PopUpSuratKeluar(null, true, sk, false);
            ppsk.bt_Ubah.setVisible(false);
            ppsk.bt_Hapus.setVisible(false);
            ppsk.bt_Lihat.setVisible(false);
            ppsk.bt_HapusPermanen.setVisible(false);
            ppsk.bt_Restore.setVisible(false);
            ppsk.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_TambahSuratActionPerformed

    private void tb_SuratKeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_SuratKeluarMouseClicked
        try {
            int baris = tb_SuratKeluar.getSelectedRow();
            if (baris != -1 && tb_SuratKeluar.getValueAt(baris, 0) != null) {

                String Id = tb_SuratKeluar.getValueAt(baris, 0).toString();
                String Bagian = tb_SuratKeluar.getValueAt(baris, 1).toString();
                String Kategori = tb_SuratKeluar.getValueAt(baris, 2).toString();
                String Nomor = tb_SuratKeluar.getValueAt(baris, 3).toString();
                String Tanggal = tb_SuratKeluar.getValueAt(baris, 4).toString();
                String Perihal = tb_SuratKeluar.getValueAt(baris, 5).toString();
                String Tujuan = tb_SuratKeluar.getValueAt(baris, 6).toString();
                String File = tb_SuratKeluar.getValueAt(baris, 7).toString();

                PopUpSuratKeluar pusk = new PopUpSuratKeluar(null, true, sk, true);

                pusk.bt_Tambah.setVisible(false);
                pusk.bt_HapusPermanen.setVisible(false);
                pusk.bt_Restore.setVisible(false);

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
    }//GEN-LAST:event_tb_SuratKeluarMouseClicked

    private void tf_CariCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tf_CariCaretUpdate
//        try {
//            Cari();
//        } catch (SQLException ex) {
//            Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_tf_CariCaretUpdate

    private void tf_CariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_CariKeyReleased
//        try {
//            Cari();
//        } catch (SQLException ex) {
//            Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }//GEN-LAST:event_tf_CariKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Reset;
    private javax.swing.JButton bt_TambahSurat;
    private javax.swing.JComboBox<String> cb_BagianMenu;
    private javax.swing.JComboBox<String> cb_KategoriMenu;
    private com.toedter.calendar.JDateChooser dc_tglAkhir;
    private com.toedter.calendar.JDateChooser dc_tglAwal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_SuratKeluar;
    private javax.swing.JTextField tf_Cari;
    // End of variables declaration//GEN-END:variables

    public void BlokJDate() {
        blokirEditor(dc_tglAwal);
        blokirEditor(dc_tglAkhir);
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
            Logger.getLogger(menuSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
