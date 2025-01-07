/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Kelas;

import java.sql.Date;

/**
 *
 * @author rizan
 */
public class NewClass {

    String filterKategori, filterBagian;
    java.sql.Date tanggalAwal, tanggalAkhir;

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

    public Date getTanggalAwal() {
        return tanggalAwal;
    }

    public void setTanggalAwal(Date tanggalAwal) {
        this.tanggalAwal = tanggalAwal;
    }

    public Date getTanggalAkhir() {
        return tanggalAkhir;
    }

    public void setTanggalAkhir(Date tanggalAkhir) {
        this.tanggalAkhir = tanggalAkhir;
    }
}
