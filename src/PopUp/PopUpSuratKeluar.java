/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package PopUp;

import Kelas.Bagian;
import Kelas.Kategori;
import Kelas.Surat_Keluar;
import Main.MenuUtama;
import Main.menuSuratKeluar;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author rizan
 */
public class PopUpSuratKeluar extends javax.swing.JDialog {

    /**
     * Creates new form PopUpSuratKeluar
     */
    private Surat_Keluar sk;
    private boolean isEditMode = false; // Tambahkan flag untuk melacak mode
    public boolean isInitializing = true; // Flag untuk mencegah pemanggilan updateNoSurat saat inisialisasi data

    public PopUpSuratKeluar(java.awt.Frame parent, boolean b, Surat_Keluar srtkel, boolean editMode) throws SQLException {
        super(parent, b);
        initComponents();
        this.sk = srtkel;

        cbBagianSurat();
        cbKategoriSurat();

        dc_Tanggal.setDate(new Date());
        dc_Tanggal.setLocale(new Locale("id"));
        dc_Tanggal.setDateFormatString("dd MMMM yyyy");

        autoId();
        BlokJDate();

        // Tambahkan listener untuk combo box dan date chooser
        cb_Kategori.addItemListener(e -> {
            if (!isInitializing) {
                try {
                    updateNoSurat();
                } catch (SQLException ex) {
                    Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        cb_Bagian.addItemListener(e -> {
            if (!isInitializing) {
                try {
                    updateNoSurat();
                } catch (SQLException ex) {
                    Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        dc_Tanggal.addPropertyChangeListener("date", evt -> {
            if (!isInitializing) {
                try {
                    updateNoSurat();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        isInitializing = false; // Inisialisasi selesai
    }

    void cbKategoriSurat() {
        try {
            cb_Kategori.addItem("--Pilih Kategori Surat--");

            Kategori ks = new Kategori();
            ResultSet data = ks.Tampil_CbKategoriSurat();

            while (data.next()) {
                cb_Kategori.addItem(data.getString("kode_kategori") + " - " + data.getString("nama_kategori"));
            }

            cb_Kategori.setSelectedItem("--Pilih Kategori Surat--");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void cbBagianSurat() {
        try {
            cb_Bagian.addItem("--Pilih Bagian Surat--");

            Bagian bg = new Bagian();
            ResultSet data = bg.Tampil_CbBagianSurat();

            while (data.next()) {
                cb_Bagian.addItem(data.getString("kode_bagian") + " - " + data.getString("nama_bagian"));
            }

            cb_Bagian.setSelectedItem("--Pilih Bagian Surat--"); // Pilih default option
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateNoSurat() throws SQLException {
        // Validasi input
        if (cb_Kategori.getSelectedItem() == null || cb_Bagian.getSelectedItem() == null) {
            tf_NoSurat.setText("");
            return;
        }

        String kategori = cb_Kategori.getSelectedItem().toString();
        String bagian = cb_Bagian.getSelectedItem().toString();

        if (kategori.equals("--Pilih Kategori Surat--") || bagian.equals("--Pilih Bagian Surat--")) {
            tf_NoSurat.setText("");
            return;
        }

        // Ekstrak kategori dan bagian
        String selectedKategori = kategori.split(" - ")[0];
        String selectedBagian = bagian.split(" - ")[0];

        Date tanggal = dc_Tanggal.getDate();
        if (tanggal == null) {
            tf_NoSurat.setText("");
            return;
        }

        // Ambil bulan dan tahun dari tanggal
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tanggal);
        int bulan = calendar.get(Calendar.MONTH) + 1; // Nilai bulan dari 1-12
        int tahun = calendar.get(Calendar.YEAR);

        // Jangan ubah nomor surat jika dalam mode edit
        if (isEditMode) {
            return;
        }

        // Gunakan kelas SuratKeluar untuk memproses nomor surat
        Surat_Keluar surat = new Surat_Keluar();
        surat.setKategori(selectedKategori); // Set kategori
        surat.setBagian(selectedBagian);     // Set bagian
        surat.setTahun(tahun);               // Set tahun

        String noSurat = surat.GetNoSurat(); // Ambil nomor surat
        surat.setBulan(bulan);               // Set bulan untuk konversi ke Romawi
        String bulanRomawi = surat.KonversiRomawi(); // Konversi bulan ke angka Romawi

        // Format nomor surat
        String formattedNoSurat = String.format("%s.%s/%s/%s/%d",
                selectedKategori,
                noSurat,
                selectedBagian,
                bulanRomawi,
                tahun
        );

        // Tampilkan nomor surat
        tf_NoSurat.setText(formattedNoSurat);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lb_Id = new javax.swing.JLabel();
        cb_Bagian = new javax.swing.JComboBox<>();
        cb_Kategori = new javax.swing.JComboBox<>();
        tf_NoSurat = new javax.swing.JTextField();
        dc_Tanggal = new com.toedter.calendar.JDateChooser();
        tf_Perihal = new javax.swing.JTextField();
        tf_Tujuan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_File = new javax.swing.JTextArea();
        bt_Close = new javax.swing.JButton();
        bt_Upload = new javax.swing.JButton();
        bt_Salin = new javax.swing.JButton();
        bt_Tambah = new javax.swing.JButton();
        bt_Reset = new javax.swing.JButton();
        bt_Lihat = new javax.swing.JButton();
        bt_Hapus = new javax.swing.JButton();
        bt_Ubah = new javax.swing.JButton();
        bt_Restore = new javax.swing.JButton();
        bt_HapusPermanen = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));

        jLabel2.setText("Form Tambah Surat Keluar");

        jLabel3.setText("Bagian");

        jLabel4.setText("Kategori");

        jLabel5.setText("No Surat");

        jLabel6.setText("Tangal dibuat");

        jLabel7.setText("Perihal");

        jLabel8.setText("Tujuan");

        lb_Id.setText("9999");

        ta_File.setColumns(20);
        ta_File.setRows(5);
        jScrollPane1.setViewportView(ta_File);

        bt_Close.setText("Close");
        bt_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_CloseActionPerformed(evt);
            }
        });

        bt_Upload.setText("Upload Surat");
        bt_Upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_UploadActionPerformed(evt);
            }
        });

        bt_Salin.setText("Salin Nomor");
        bt_Salin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SalinActionPerformed(evt);
            }
        });

        bt_Tambah.setText("Tambah");
        bt_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_TambahActionPerformed(evt);
            }
        });

        bt_Reset.setText("Reset");

        bt_Lihat.setText("Lihat Surat");
        bt_Lihat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_LihatActionPerformed(evt);
            }
        });

        bt_Hapus.setText("Hapus Surat");
        bt_Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_HapusActionPerformed(evt);
            }
        });

        bt_Ubah.setText("Ubah");
        bt_Ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_UbahActionPerformed(evt);
            }
        });

        bt_Restore.setText("Restore");
        bt_Restore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_RestoreActionPerformed(evt);
            }
        });

        bt_HapusPermanen.setText("Hapus Permanen");
        bt_HapusPermanen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_HapusPermanenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(bt_Upload))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dc_Tanggal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tf_Perihal, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tf_Tujuan, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cb_Kategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tf_NoSurat)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(bt_Tambah, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bt_Reset, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(bt_Ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(bt_Lihat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(bt_Hapus))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(bt_Restore, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(bt_HapusPermanen, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(cb_Bagian, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(bt_Close)
                    .addComponent(jLabel2))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bt_Salin, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_Id, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lb_Id))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_Bagian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_Kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tf_NoSurat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(bt_Salin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dc_Tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tf_Perihal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tf_Tujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bt_Upload)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(bt_Tambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bt_Reset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_Lihat)
                    .addComponent(bt_Hapus)
                    .addComponent(bt_Ubah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_Restore)
                    .addComponent(bt_HapusPermanen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(bt_Close)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bt_CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_CloseActionPerformed
        dispose();
    }//GEN-LAST:event_bt_CloseActionPerformed
    private int mouseX, mouseY;
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - mouseX, y - mouseY);
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void bt_SalinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SalinActionPerformed
        try {
            String textToCopy = tf_NoSurat.getText();

            if (textToCopy != null && !textToCopy.isEmpty()) {
                // Menyalin teks ke clipboard
                java.awt.datatransfer.StringSelection stringSelection = new java.awt.datatransfer.StringSelection(textToCopy);
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

                // Menampilkan pesan dengan teks yang disalin dalam bold
                String message = "<html>Nomor surat <b>\"" + textToCopy + "\"</b> telah disalin ke Clipboard.</html>";
                TimeJOption.AutoCloseJOptionPane.showMessageDialog(message, "Berhasil", JOptionPane.INFORMATION_MESSAGE, 2000);
            } else {
                // Menampilkan pesan jika teks kosong
                TimeJOption.AutoCloseJOptionPane.showMessageDialog("Nomor urut kosong, tidak ada yang disalin.", "Kesalahan", JOptionPane.ERROR_MESSAGE, 2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyalin teks.", "Kesalahan", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_SalinActionPerformed

    private void bt_UploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_UploadActionPerformed
        // Validasi apakah `tf_NoSurat` kosong
        if (tf_NoSurat.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nomor Surat tidak boleh kosong. Harap isi Nomor Surat terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return; // Hentikan proses jika kosong
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih File PDF");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Pastikan file yang dipilih adalah PDF
            if (selectedFile.getName().toLowerCase().endsWith(".pdf")) {
                // Tampilkan path file ke dalam `ta_File`
                ta_File.setText(selectedFile.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(this, "Silakan pilih file PDF!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_bt_UploadActionPerformed

    private void bt_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_TambahActionPerformed
        String filePath = ta_File.getText();
        if (filePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap unggah file terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validasi apakah `tf_NoSurat` kosong
        if (tf_NoSurat.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nomor Surat tidak boleh kosong. Harap isi Nomor Surat terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Ambil nama file berdasarkan `tf_NoSurat` dan ganti karakter `/` atau `.` dengan `_`
            String sanitizedNoSurat = tf_NoSurat.getText().replace("/", "_").replace(".", "_");
            File selectedFile = new File(filePath);
            String newFileName = sanitizedNoSurat + "_" + selectedFile.getName();

            File targetFolder = new File("Upload/SuratKeluar"); // Ganti dengan lokasi folder Anda
            if (!targetFolder.exists()) {
                targetFolder.mkdirs(); // Buat folder jika belum ada
            }
            File newFile = new File(targetFolder, newFileName);

            // Salin file ke folder baru dengan nama baru
            java.nio.file.Files.copy(selectedFile.toPath(), newFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // Simpan data file ke database atau lakukan tindakan lain sesuai kebutuhan
            JOptionPane.showMessageDialog(this, "Data surat keluar berhasil disimpan!\nFile disimpan di: " + newFile.getAbsolutePath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        try {
            Surat_Keluar kodetambah = new Surat_Keluar();

            kodetambah.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));

            String kategori = cb_Kategori.getSelectedItem().toString();
            if (!kategori.equals("--Pilih Kategori Surat--")) {
                kodetambah.setKategori(kategori.split(" - ")[0]);
            } else {

                JOptionPane.showMessageDialog(this, "Pilih kategori surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String bagian = cb_Bagian.getSelectedItem().toString();
            if (!bagian.equals("--Pilih Bagian Surat--")) {
                kodetambah.setBagian(bagian.split(" - ")[0]);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih bagian surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String nomorSurat = tf_NoSurat.getText();
            if (nomorSurat != null && !nomorSurat.isEmpty()) {
                kodetambah.setNo_surat(nomorSurat);
            } else {
                JOptionPane.showMessageDialog(this, "Nomor surat tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            java.util.Date tanggalDibuatUtil = dc_Tanggal.getDate();
            if (tanggalDibuatUtil != null) {
                java.sql.Date tanggalDibuatSQL = new java.sql.Date(tanggalDibuatUtil.getTime());
                kodetambah.setTgl_dibuat(tanggalDibuatSQL);
            } else {
                JOptionPane.showMessageDialog(this, "Tanggal dibuat tidak valid!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String perihal = tf_Perihal.getText();
            if (perihal != null && !perihal.isEmpty()) {
                kodetambah.setPerihal(perihal);
            } else {
                JOptionPane.showMessageDialog(this, "Perihal tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tujuan = tf_Tujuan.getText();
            if (tujuan != null && !tujuan.isEmpty()) {
                kodetambah.setTujuan(tujuan);
            } else {
                JOptionPane.showMessageDialog(this, "Tujuan tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            File file = new File(ta_File.getText());
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                String fileExtension = kodetambah.getFileExtension(file.getName());
                String formattedNamaFile = nomorSurat.replace(".", "_").replace("/", "_") + fileExtension;
                kodetambah.setFile(fis);
                kodetambah.setNama_file(formattedNamaFile);
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            kodetambah.setUser_login(MenuUtama.lb_Username.getText());

            kodetambah.KodeTambah();
            autoId();

            menuSuratKeluar kt = new menuSuratKeluar();
            kt.loadTabel();
            sk.NotifPerubahanData();

            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Gagal menyimpan surat keluar: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException | ParseException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_bt_TambahActionPerformed

    private void bt_UbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_UbahActionPerformed
        try {
            Surat_Keluar kodeUbah = new Surat_Keluar();

            // Set ID
            kodeUbah.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));

            // Validasi kategori
            String kategori = cb_Kategori.getSelectedItem().toString();
            if (!kategori.equals("--Pilih Kategori Surat--")) {
                kodeUbah.setKategori(kategori.split(" - ")[0]);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih kategori surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validasi bagian
            String bagian = cb_Bagian.getSelectedItem().toString();
            if (!bagian.equals("--Pilih Bagian Surat--")) {
                kodeUbah.setBagian(bagian.split(" - ")[0]);
            } else {
                JOptionPane.showMessageDialog(this, "Pilih bagian surat terlebih dahulu!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validasi nomor surat
            String nomorSurat = tf_NoSurat.getText().replace("/", "_").replace(".", "_");
            if (nomorSurat != null && !nomorSurat.isEmpty()) {
                kodeUbah.setNo_surat(nomorSurat);
            } else {
                JOptionPane.showMessageDialog(this, "Nomor surat tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validasi tanggal
            java.util.Date tanggalDibuatUtil = dc_Tanggal.getDate();
            if (tanggalDibuatUtil != null) {
                java.sql.Date tanggalDibuatSQL = new java.sql.Date(tanggalDibuatUtil.getTime());
                kodeUbah.setTgl_dibuat(tanggalDibuatSQL);
            } else {
                JOptionPane.showMessageDialog(this, "Tanggal dibuat tidak valid!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validasi perihal
            String perihal = tf_Perihal.getText();
            if (perihal != null && !perihal.isEmpty()) {
                kodeUbah.setPerihal(perihal);
            } else {
                JOptionPane.showMessageDialog(this, "Perihal tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validasi tujuan
            String tujuan = tf_Tujuan.getText();
            if (tujuan != null && !tujuan.isEmpty()) {
                kodeUbah.setTujuan(tujuan);
            } else {
                JOptionPane.showMessageDialog(this, "Tujuan tidak boleh kosong!", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Periksa apakah file diubah
            File selectedFile = new File(ta_File.getText());
            if (selectedFile.exists() && selectedFile.isFile()) {
                // Jika file baru diunggah, hapus file lama dan simpan file baru
                File oldFile = new File("Upload/SuratKeluar/" + kodeUbah.getNama_file());
                if (oldFile.exists() && oldFile.isFile()) {
                    oldFile.delete();
                }

                // Salin file baru ke folder
                String newFileName = nomorSurat + "_" + selectedFile.getName();
                File targetFolder = new File("Upload/SuratKeluar");
                if (!targetFolder.exists()) {
                    targetFolder.mkdirs();
                }
                File newFile = new File(targetFolder, newFileName);
                java.nio.file.Files.copy(selectedFile.toPath(), newFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                // Set nama file dan path file baru
                kodeUbah.setNama_file(newFileName);
                kodeUbah.setFile(new FileInputStream(newFile));
            } else {
                // Jika file tidak berubah, gunakan nama file lama
                kodeUbah.setNama_file(kodeUbah.getNama_file()); // Tetap gunakan file lama
            }

            // Set user login
            kodeUbah.setUser_login(MenuUtama.lb_Username.getText());

            // Simpan perubahan ke database
            kodeUbah.KodeUbah();

            // Reload data
            menuSuratKeluar kt = new menuSuratKeluar();
            kt.loadTabel();
            sk.NotifPerubahanData();

            // Tutup form
            dispose();

        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah surat keluar: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_UbahActionPerformed

    private void bt_HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_HapusActionPerformed
        try {

            Surat_Keluar kodeHapus = new Surat_Keluar();
            kodeHapus.setUser_login(MenuUtama.lb_Username.getText());
            kodeHapus.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));
            // Simpan perubahan ke database
            kodeHapus.KodeHapus();

            // Reload data
            menuSuratKeluar kt = new menuSuratKeluar();
            kt.loadTabel();
            sk.NotifPerubahanData();

            // Tutup form
            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_bt_HapusActionPerformed

    private void bt_LihatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_LihatActionPerformed
        try {
            String namaFile = ta_File.getText();
            if (namaFile == null || namaFile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama file tidak tersedia.", "Kesalahan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Surat_Keluar suratKeluar = new Surat_Keluar();
            suratKeluar.setNama_file(namaFile);
            byte[] fileData = suratKeluar.BukaFile();

            if (fileData != null) {
                File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + namaFile);
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(fileData);
                fos.close();

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(tempFile);
                } else {
                    JOptionPane.showMessageDialog(this, "Desktop tidak didukung pada sistem ini.", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan untuk surat ini.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membuka file: " + e.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bt_LihatActionPerformed

    private void bt_RestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_RestoreActionPerformed
        try {

            Surat_Keluar kodeRestore = new Surat_Keluar();
            kodeRestore.setUser_login(MenuUtama.lb_Username.getText());
            kodeRestore.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));
            // Simpan perubahan ke database
            kodeRestore.KodeRestore();

            // Reload data
            menuSuratKeluar kt = new menuSuratKeluar();
            kt.loadTabel();
            sk.NotifPerubahanData();

            // Tutup form
            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_RestoreActionPerformed

    private void bt_HapusPermanenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_HapusPermanenActionPerformed
        try {
            Surat_Keluar kodeHapus = new Surat_Keluar();
            kodeHapus.setId_suratkeluar(Integer.parseInt(lb_Id.getText()));
            kodeHapus.KodeHapusPermanen();

            // Reload data
            menuSuratKeluar kt = new menuSuratKeluar();
            kt.loadTabel();
            sk.NotifPerubahanData();

            // Tutup form
            dispose();

        } catch (SQLException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bt_HapusPermanenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopUpSuratKeluar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Surat_Keluar srtkel = new Surat_Keluar();
                    new PopUpSuratKeluar(null, true, srtkel, true).setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(PopUpSuratKeluar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Close;
    public javax.swing.JButton bt_Hapus;
    public javax.swing.JButton bt_HapusPermanen;
    public javax.swing.JButton bt_Lihat;
    public javax.swing.JButton bt_Reset;
    public javax.swing.JButton bt_Restore;
    public javax.swing.JButton bt_Salin;
    public javax.swing.JButton bt_Tambah;
    public javax.swing.JButton bt_Ubah;
    private javax.swing.JButton bt_Upload;
    public static javax.swing.JComboBox<String> cb_Bagian;
    public static javax.swing.JComboBox<String> cb_Kategori;
    public static com.toedter.calendar.JDateChooser dc_Tanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lb_Id;
    public static javax.swing.JTextArea ta_File;
    public static javax.swing.JTextField tf_NoSurat;
    public static javax.swing.JTextField tf_Perihal;
    public static javax.swing.JTextField tf_Tujuan;
    // End of variables declaration//GEN-END:variables

    public void BlokJDate() {
        if (dc_Tanggal.getDateEditor() instanceof JTextFieldDateEditor) {
            JTextFieldDateEditor editorAwal = (JTextFieldDateEditor) dc_Tanggal.getDateEditor();
            editorAwal.setEditable(false);
        }
    }

    private void autoId() throws SQLException {
        Surat_Keluar auto = new Surat_Keluar();
        int newID = auto.autoId();
        lb_Id.setText(String.valueOf(newID));
    }

}
