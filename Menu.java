package com.example.uas;

public class Menu {
    private String nama;
    private String harga;
    private int gambar;
    private String deskripsi;

    public Menu(String nama, String harga, int gambar, String deskripsi) {
        this.nama = nama;
        this.harga = harga;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
    }

    public String getNama() { return nama; }
    public String getHarga() { return harga; }
    public int getGambar() { return gambar; }

    public String getDeskripsi() {
        return deskripsi;
    }
}
