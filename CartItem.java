package com.example.uas;

public class CartItem {
    private String nama;
    private String harga; // Contoh: "Rp. 45.000"
    private int gambar;
    private int quantity;
    private String topping; // Tambahan untuk menyimpan info keju & daging

    public CartItem(String nama, String harga, int gambar, int quantity, String topping) {
        this.nama = nama;
        this.harga = harga;
        this.gambar = gambar;
        this.quantity = quantity;
        this.topping = topping;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }
}
