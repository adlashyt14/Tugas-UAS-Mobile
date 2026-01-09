package com.example.uas; // Sesuaikan dengan package Anda

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailMenuActivity extends AppCompatActivity {

    private RadioGroup rgKeju, rgDaging;
    private TextView tvQty, tvHarga, tvNama, tvDeskripsi;
    private ImageView ivGambar;
    private int quantity = 1;
    private int basePriceInt = 0; // Untuk perhitungan matematika
    private int imageRes;
    private boolean isMinuman = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        // 1. Inisialisasi View
        ivBackInit();

        TextView tvLabelToppingHeader = findViewById(R.id.tvLabelToppingHeader);
        View layoutLabelKeju = findViewById(R.id.layoutLabelKeju);   // Gunakan View atau LinearLayout
        View layoutLabelDaging = findViewById(R.id.layoutLabelDaging); // Gunakan View atau LinearLayout

        rgKeju = findViewById(R.id.rgKeju);
        rgDaging = findViewById(R.id.rgDaging);
        tvQty = findViewById(R.id.tvQty);
        tvHarga = findViewById(R.id.tvDetailHarga);
        tvNama = findViewById(R.id.tvDetailNama);
        tvDeskripsi = findViewById(R.id.tvDetailDeskripsi);
        ivGambar = findViewById(R.id.ivDetailGambar);
        Button btnPlus = findViewById(R.id.btnPlus);
        Button btnMinus = findViewById(R.id.btnMinus);
        Button btnOrder = findViewById(R.id.btnOrder);

        // 2. Menangkap Data dari Intent (Gunakan KEY yang sesuai dengan MenuAdapter Anda)
        String namaMenu = getIntent().getStringExtra("NAMA_MENU");
        String hargaMenu = getIntent().getStringExtra("HARGA_MENU"); // Misal: "Rp 45.000"
        String deskripsiMenu = getIntent().getStringExtra("DESKRIPSI_MENU");
        imageRes = getIntent().getIntExtra("GAMBAR_MENU", 0);

        // --- LOGIKA CEK MINUMAN ---
        if ((namaMenu != null && namaMenu.toLowerCase().contains("minuman")) || (deskripsiMenu != null && deskripsiMenu.toLowerCase().contains("minuman"))) {
            isMinuman = true;
        }


        if (isMinuman) {
            // Hide Radio Group
            rgKeju.setVisibility(View.GONE);
            rgDaging.setVisibility(View.GONE);

            // Hide Tulisan Labelnya juga
            tvLabelToppingHeader.setVisibility(View.GONE);
            layoutLabelKeju.setVisibility(View.GONE);
            layoutLabelDaging.setVisibility(View.GONE);

        } else {
            // Show semua jika bukan minuman
            rgKeju.setVisibility(View.VISIBLE);
            rgDaging.setVisibility(View.VISIBLE);

            tvLabelToppingHeader.setVisibility(View.VISIBLE);
            layoutLabelKeju.setVisibility(View.VISIBLE);
            layoutLabelDaging.setVisibility(View.VISIBLE);
        }

        // 3. Menampilkan Data ke UI
        tvNama.setText(namaMenu);
        tvDeskripsi.setText(deskripsiMenu);
        ivGambar.setImageResource(imageRes);
        tvHarga.setText(hargaMenu);

        // 4. Konversi harga String ke Integer untuk kalkulasi
        if (hargaMenu != null) {
            String cleanPrice = hargaMenu.replaceAll("[^0-9]", "");
            basePriceInt = Integer.parseInt(cleanPrice);
        }

        // 5. Logic Tombol Quantity
        btnPlus.setOnClickListener(v -> {
            quantity++;
            updateUI();
        });

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateUI();
            }
        });

        // 6. Action Tombol Order
        btnOrder.setOnClickListener(v -> addToCart());

        // Default Pilihan (Agar tidak null)
        rgKeju.check(R.id.rbMozzarella);
        rgDaging.check(R.id.rbExtraBeef);
    }

    private void ivBackInit() {
        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> onBackPressed());
    }

    private void updateUI() {
        tvQty.setText(String.valueOf(quantity));
        int totalHarga = basePriceInt * quantity;
        // Format kembali ke Rupiah untuk tampilan
        tvHarga.setText("Rp " + String.format("%,d", totalHarga).replace(',', '.'));
    }

    private void addToCart() {
        // Ambil topping yang dipilih
        int idKeju = rgKeju.getCheckedRadioButtonId();
        int idDaging = rgDaging.getCheckedRadioButtonId();

        RadioButton rbKeju = findViewById(idKeju);
        RadioButton rbDaging = findViewById(idDaging);

        // Validasi jika belum pilih (meskipun sudah di-default)
        if (rbKeju == null || rbDaging == null) {
            Toast.makeText(this, "Silahkan pilih topping!", Toast.LENGTH_SHORT).show();
            return;
        }

        String toppingFinal = rbKeju.getText().toString() + ", " + rbDaging.getText().toString();

        if (isMinuman == true){
            toppingFinal = "-";
        }
        // Buat objek CartItem baru
        // Konstruktor: nama, harga (string), gambar, qty, topping
        CartItem newItem = new CartItem(
                tvNama.getText().toString(),
                tvHarga.getText().toString(),
                imageRes,
                quantity,
                toppingFinal
        );

        // Kirim ke CartManager (Logika addOrUpdate yang sudah kita revisi)
        CartManager.addOrUpdate(newItem);

        Toast.makeText(this, "Berhasil masuk keranjang!", Toast.LENGTH_SHORT).show();

        // Pindah ke MyOrderActivity
        Intent intent = new Intent(this, MyOrderActivity.class);
        startActivity(intent);
    }
}
