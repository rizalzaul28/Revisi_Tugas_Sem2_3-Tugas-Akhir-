/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kelas;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author rizan
 */
public class Surat_Keluar {

    int id_suratkeluar, bulan, tahun, jumlah = 0;
    String bagian, kategori, no_surat, perihal, tujuan, nama_file,
            filterKategori, filterBagian, user_login, status, kata_kunci;
    java.sql.Date tgl_dibuat, tanggal, tgl_awal, tgl_akhir;
    FileInputStream file;

    private Connection conn;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;

    private List<PerubahanData> listeners = new ArrayList<>();

    public interface PerubahanData {

        void AktifPerubahanData();
    }

    public void TambahPerubahanData(PerubahanData listener) {
        listeners.add(listener);
    }

    public void HapusPerubahanData(PerubahanData listener) {
        listeners.remove(listener);
    }

    public void NotifPerubahanData() {
        for (PerubahanData listener : listeners) {
            listener.AktifPerubahanData();
        }
    }

    public Surat_Keluar() throws SQLException {
        Koneksi koneksi = new Koneksi();
        conn = koneksi.koneksiDB();
    }

    public int getId_suratkeluar() {
        return id_suratkeluar;
    }

    public void setId_suratkeluar(int id_suratkeluar) {
        this.id_suratkeluar = id_suratkeluar;
    }

    public int getBulan() {
        return bulan;
    }

    public void setBulan(int bulan) {
        this.bulan = bulan;
    }

    public String getBagian() {
        return bagian;
    }

    public void setBagian(String bagian) {
        this.bagian = bagian;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNo_surat() {
        return no_surat;
    }

    public void setNo_surat(String no_surat) {
        this.no_surat = no_surat;
    }

    public String getPerihal() {
        return perihal;
    }

    public void setPerihal(String perihal) {
        this.perihal = perihal;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getNama_file() {
        return nama_file;
    }

    public void setNama_file(String nama_file) {
        this.nama_file = nama_file;
    }

    public String getFilterKategori() {
        return filterKategori;
    }

    public void setFilterKategori(String filterKategori) {
        this.filterKategori = filterKategori;
    }

    public String getFilterBagian() {
        return filterBagian;
    }

    public void setFilterBagian(String filterBagian) {
        this.filterBagian = filterBagian;
    }

    public Date getTgl_dibuat() {
        return tgl_dibuat;
    }

    public void setTgl_dibuat(Date tgl_dibuat) {
        this.tgl_dibuat = tgl_dibuat;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public java.sql.Date getTgl_awal() {
        return tgl_awal;
    }

    public void setTanggalAwal(java.util.Date tanggalAwal) {
        if (tanggalAwal != null) {
            this.tgl_awal = new java.sql.Date(tanggalAwal.getTime());
        }
    }

    public java.sql.Date getTgl_akhir() {
        return tgl_akhir;
    }

    public void setTanggalAkhir(java.util.Date tanggalAkhir) {
        if (tanggalAkhir != null) {
            this.tgl_akhir = new java.sql.Date(tanggalAkhir.getTime());
        }
    }

    public FileInputStream getFile() {
        return file;
    }

    public void setFile(FileInputStream file) {
        this.file = file;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public String getKata_kunci() {
        return kata_kunci;
    }

    public void setKata_kunci(String kata_kunci) {
        this.kata_kunci = kata_kunci;
    }

    // Method untuk membuat nomor id otomatis (autoId)
    public int autoId() {
        int newID = 1;

        try {
            query = "SELECT MAX(id_suratkeluar) AS max_id FROM surat_keluar";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.next()) {
                String lastNoUrut = rs.getString("max_id");

                if (lastNoUrut != null && !lastNoUrut.isEmpty()) {
                    String numericPart = lastNoUrut.split("\\.")[0];
                    newID = Integer.parseInt(numericPart) + 1;
                }
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghasilkan nomor urut baru!");
            e.printStackTrace();
        }

        return newID;
    }

    // Method untuk menampilkan jumlah Surat Keluar
    public int TampilJumlahSuratKeluar() {
        query = "SELECT COUNT(*) AS jumlah FROM surat_keluar";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);

            if (rs.next()) {
                jumlah = rs.getInt("jumlah");
            }

            rs.close();
            st.close();
        } catch (SQLException sQLException) {
            JOptionPane.showMessageDialog(null, "Data gagal ditampilkan: " + sQLException.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return jumlah;
    }

    // Method untuk melihat nomor surat
    public String GetNoSurat() {
        String noSurat = "001";
        try {
            String query = "SELECT no_surat FROM surat_keluar WHERE bagian = ? AND kategori = ? AND YEAR(tanggal_dibuat) = ? ORDER BY id_suratkeluar DESC LIMIT 1";
            ps = conn.prepareStatement(query);
            ps.setString(1, bagian);
            ps.setString(2, kategori);
            ps.setInt(3, tahun);
            rs = ps.executeQuery();

            if (rs.next()) {
                String lastNoSurat = rs.getString("no_surat");
                String[] parts = lastNoSurat.split("/");
                String nomorUrut = parts[0].split("\\.")[1];
                int lastNo = Integer.parseInt(nomorUrut);
                noSurat = String.format("%03d", lastNo + 1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noSurat;
    }

    // Method untuk menampilkan data di tabel MenuSuratKeluar (KodeTampilTabel)
    public ResultSet KodeTampilTabel() {;
        query = "SELECT * FROM surat_keluar WHERE status = '1'";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Data gagal ditampilkan", null, JOptionPane.ERROR_MESSAGE, 3000);
        }

        return rs;
    }

    // Method untuk menampilkan data di tabel MenuLogSuratKeluar (KodeTampilTabel)
    public ResultSet KodeTampilTabelLog() {;
        query = "SELECT * FROM log_suratkeluar";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Data gagal ditampilkan", null, JOptionPane.ERROR_MESSAGE, 3000);
        }

        return rs;
    }

    // Method untuk menampilkan data di tabel MenuSampahSuratKeluar (KodeTampilTabel)
    public ResultSet KodeTampilTabelSampah() {;
        query = "SELECT * FROM surat_keluar WHERE status = '0'";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Data gagal ditampilkan", null, JOptionPane.ERROR_MESSAGE, 3000);
        }

        return rs;
    }

    // Method untuk menggabungkan filter
    public ResultSet KodeTampilByFilters() throws SQLException {
        String query = "SELECT * FROM surat_keluar WHERE 1=1";

        if (bagian != null && !bagian.isEmpty()) {
            query += " AND bagian = ?";
        }
        if (kategori != null && !kategori.isEmpty()) {
            query += " AND kategori = ?";
        }
        if (tgl_awal != null && tgl_akhir != null) {
            query += " AND tanggal_dibuat BETWEEN ? AND ?";
        }

        PreparedStatement ps = conn.prepareStatement(query);

        int paramIndex = 1;
        if (bagian != null && !bagian.isEmpty()) {
            ps.setString(paramIndex++, bagian);
        }
        if (kategori != null && !kategori.isEmpty()) {
            ps.setString(paramIndex++, kategori);
        }
        if (tgl_awal != null && tgl_akhir != null) {
            ps.setDate(paramIndex++, tgl_awal);
            ps.setDate(paramIndex++, tgl_akhir);
        }

        return ps.executeQuery();
    }

    // Untuk mengkonversi ke angka romawi
    public String KonversiRomawi() {
        String[] bulanRomawi = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII"};
        return bulanRomawi[bulan - 1];
    }

    // Method untuk menambah data (KodeTambah)
    public void KodeTambah() {
        query = "INSERT INTO surat_keluar (id_suratkeluar, kategori, bagian, no_surat, tanggal_dibuat, perihal, tujuan, nama_file, file, user_login, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '1')";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id_suratkeluar);
            ps.setString(2, kategori);
            ps.setString(3, bagian);
            ps.setString(4, no_surat);
            ps.setDate(5, tgl_dibuat);
            ps.setString(6, perihal);
            ps.setString(7, tujuan);
            ps.setString(8, nama_file);
            ps.setBlob(9, file);
            ps.setString(10, user_login);

            ps.executeUpdate();
            ps.close();

            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Surat berhasil ditambahkan!", null, JOptionPane.INFORMATION_MESSAGE, 1000);

        } catch (SQLException e) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Gagal menambahkan surat!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Untuk mengambil ekstensi file
    public String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }

    // Method untuk mengubah data (KodeUbah)
    public void KodeUbah() {
        query = "UPDATE surat_keluar SET bagian = ?, kategori = ?, no_surat = ?, tanggal_dibuat = ?, perihal = ?, tujuan = ?, nama_file = ?, file = ?, user_login = ?, status = '1' WHERE id_suratkeluar = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, bagian);
            ps.setString(2, kategori);
            ps.setString(3, no_surat);
            ps.setDate(4, tgl_dibuat);
            ps.setString(5, perihal);
            ps.setString(6, tujuan);
            ps.setString(7, nama_file);
            ps.setBlob(8, file);
            ps.setString(9, user_login);
            ps.setInt(10, id_suratkeluar);

            ps.executeUpdate();
            ps.close();

            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Surat berhasil diubah!", null, JOptionPane.INFORMATION_MESSAGE, 1000);

        } catch (SQLException e) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Gagal mengubah surat!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk menghapus data ke sampah (KodeHapus)
    public void KodeHapus() {
        query = "UPDATE surat_keluar SET user_login = ?, status = '0' WHERE id_suratkeluar = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user_login);
            ps.setInt(2, id_suratkeluar);

            ps.executeUpdate();
            ps.close();

            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Surat berhasil dihapus!", null, JOptionPane.INFORMATION_MESSAGE, 1000);

        } catch (SQLException e) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Gagal menghapus surat!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 30000);
        }
    }

    // Method untuk menghapus data permanen (KodeHapusPermanen)
    public void KodeHapusPermanen() {
        query = "DELETE FROM surat_keluar WHERE id_suratkeluar = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id_suratkeluar);
            ps.executeUpdate();
            ps.close();
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Kategori Surat berhasil dihapus permanen!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (Exception e) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Kategori Surat gagal dihapus permanen!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk merestore data dari sampah (KodeRestore)
    public void KodeRestore() {
        query = "UPDATE surat_keluar SET user_login = ?, status = '1' WHERE id_suratkeluar = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user_login);
            ps.setInt(2, id_suratkeluar);

            ps.executeUpdate();
            ps.close();

            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Surat berhasil diRestore!", null, JOptionPane.INFORMATION_MESSAGE, 1000);

        } catch (SQLException e) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Gagal merestore surat!: " + e.getMessage(), null, JOptionPane.ERROR_MESSAGE, 30000);
        }
    }

    // Method untuk membuka File
    public byte[] BukaFile() throws SQLException {
        byte[] IsiFile = null;
        query = "SELECT file FROM surat_keluar WHERE nama_file = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, nama_file);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            IsiFile = rs.getBytes("file");
        }

        rs.close();
        ps.close();

        return IsiFile;

    }

    // Method untuk mencari berdasarkan perihal atau tujuan (KodeCari)
    // Method untuk mencari berdasarkan perihal atau tujuan (KodeCari)
    public ResultSet KodeCari() {
        ResultSet rs = null;
        String query = "SELECT * FROM surat_keluar WHERE (perihal LIKE ? OR tujuan LIKE ?) AND status = '1'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            String keywordPattern = "%" + this.kata_kunci + "%"; // Format pencarian dengan LIKE
            ps.setString(1, keywordPattern); // Perihal
            ps.setString(2, keywordPattern); // Tujuan
            rs = ps.executeQuery();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal ditampilkan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return rs;
    }

}
