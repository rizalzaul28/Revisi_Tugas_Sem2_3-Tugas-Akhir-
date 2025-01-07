/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kelas;

import java.sql.Connection;
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
public class Bagian {

    String kode_bagian, nama_bagian, user_login, status;

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

    public Bagian() throws SQLException {
        Koneksi koneksi = new Koneksi();
        conn = koneksi.koneksiDB();
    }

    public String getKode_bagian() {
        return kode_bagian;
    }

    public void setKode_bagian(String kode_bagian) {
        this.kode_bagian = kode_bagian;
    }

    public String getNama_bagian() {
        return nama_bagian;
    }

    public void setNama_bagian(String nama_bagian) {
        this.nama_bagian = nama_bagian;
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

    // Method untuk menambah data (KodeTambah)
    public void KodeTambah() {
        query = "INSERT INTO bagian_surat (kode_bagian, nama_bagian, user_login, status) VALUES (?,?,?,'1')";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, kode_bagian);
            ps.setString(2, nama_bagian);
            ps.setString(3, user_login);
            ps.executeUpdate();
            ps.close();
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Bagian Surat berhasil ditambahkan!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (SQLException sQLException) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Bagian Surat gagal ditambahkan!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk mengubah data (KodeUbah)
    public void KodeUbah() {
        query = "UPDATE bagian_surat SET nama_bagian = ?, user_login = ?, status = '1' WHERE kode_bagian = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, nama_bagian);
            ps.setString(2, user_login);
            ps.setString(3, kode_bagian);

            int rowsAffected = ps.executeUpdate();
            ps.close();

            if (rowsAffected > 0) {
                TimeJOption.AutoCloseJOptionPane.showMessageDialog("Bagian Surat berhasil diubah!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
            } else {
                TimeJOption.AutoCloseJOptionPane.showMessageDialog("Bagian Surat tidak ditemukan atau tidak ada perubahan!", null, JOptionPane.WARNING_MESSAGE, 3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Bagian Surat gagal diubah!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk menghapus data ke sampah (KodeHapus)
    public void KodeHapus() {
        query = "UPDATE bagian_surat SET status = '0', user_login = ?"
                + "WHERE kode_bagian = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user_login);
            ps.setString(2, kode_bagian);
            ps.executeUpdate();
            ps.close();
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Bagian Surat berhasil dihapus!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (Exception e) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Bagian Surat gagal dihapus!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk merestore data (KodeRestore)
    public void KodeRestore() {
        query = "UPDATE bagian_surat SET status = '1', user_login = ?"
                + "WHERE kode_bagian = ?";

        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user_login);
            ps.setString(2, kode_bagian);
            ps.executeUpdate();
            ps.close();
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Bagian Surat berhasil diRestore!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (Exception e) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Bagian Surat gagal diRestore!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk menghapus data permanen (KodeHapusPermanen)
    public void KodeHapusPermanen() {
        try {
            // STEP 1: Set variabel global @current_user_login
            query = "SET @user_login = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, user_login);
            ps.executeUpdate();
            ps.close();

            // STEP 2: Eksekusi query DELETE
            query = "DELETE FROM bagian_surat WHERE kode_bagian = ?;";
            ps = conn.prepareStatement(query);
            ps.setString(1, kode_bagian);
            ps.executeUpdate();
            ps.close();
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Bagian Surat berhasil dihapus permanen!", null, JOptionPane.INFORMATION_MESSAGE, 1000);
        } catch (Exception e) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Bagian Surat gagal dihapus permanen!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }
    }

    // Method untuk menampilkan data di tabel MenuBagian (KodeTampilTabel)
    public ResultSet KodeTampilTabel() {;
        query = "SELECT * FROM bagian_surat WHERE status = '1'";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Data gagal ditampilkan", null, JOptionPane.ERROR_MESSAGE, 3000);
        }

        return rs;
    }

    // Method untuk menampilkan data di tabel log(KodeTampilTabel)
    public ResultSet KodeTampilTabelLog() {
        query = "SELECT * FROM log_bagiankategori_surat WHERE tabel_terkait = 'bagian_surat'";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Data gagal ditampilkan", null, JOptionPane.ERROR_MESSAGE, 3000);
        }

        return rs;
    }

    // Method untuk menampilkan data di tabel sampah(KodeTampilTabel)
    public ResultSet KodeTampilTabelSampah() {
        query = "SELECT * FROM bagian_surat WHERE status = '0'";

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Data gagal ditampilkan", null, JOptionPane.ERROR_MESSAGE, 3000);
        }

        return rs;
    }

    // Method untuk menampilkan data (cb_Bagian)
    public ResultSet Tampil_CbBagianSurat() {

        try {
            query = "SELECT kode_bagian, nama_bagian FROM bagian_surat WHERE status = '1'";
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException sQLException) {
            TimeJOption.AutoCloseJOptionPane.showMessageDialog("Data Gagal Ditampilkan!", null, JOptionPane.ERROR_MESSAGE, 3000);
        }

        return rs;

    }

}
