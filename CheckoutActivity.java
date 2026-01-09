package com.example.uas;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uas.CartItem;
import com.example.uas.CartManager;
import com.example.uas.CheckoutAdapter;
import com.example.uas.R;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView rvCheckoutItems;
    private TextView tvTotalItemsHeader, tvTotalBayar;
    private Button btnBayar;
    private ImageView btnBack;

    private LinearLayout btnMethodCash, btnMethodQris;
    private ImageView ivIconCash, ivIconQris;

    private String selectedMethod = "CASH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // 1. Inisialisasi
        rvCheckoutItems = findViewById(R.id.rvCheckoutItems);
        tvTotalItemsHeader = findViewById(R.id.tvTotalItemsHeader);
        btnBayar = findViewById(R.id.btnBayar);
        btnBack = findViewById(R.id.btnBack);

        btnMethodCash = findViewById(R.id.btnMethodCash);
        btnMethodQris = findViewById(R.id.btnMethodQris);
        ivIconCash = findViewById(R.id.ivIconCash);
        ivIconQris = findViewById(R.id.ivIconQris);

        // 2. Setup RecyclerView
        rvCheckoutItems.setLayoutManager(new LinearLayoutManager(this));
        CheckoutAdapter adapter = new CheckoutAdapter(CartManager.cartList);
        rvCheckoutItems.setAdapter(adapter);

        // 3. Update Header Total Items
        int totalQty = 0;
        int totalPrice = 0;

        for (CartItem item : CartManager.cartList) {
            totalQty += item.getQuantity();

            // Hitung total harga (Logic bersih-bersih string)
            String cleanPrice = item.getHarga().replaceAll("[^0-9]", "");
            // Asumsi: item.getHarga() adalah total per baris. Jika satuan, kalikan qty.
            totalPrice += Integer.parseInt(cleanPrice);
        }

        tvTotalItemsHeader.setText("Total " + totalQty + " Items");

        // 4. Update Footer Total
        String formattedPrice = "Rp " + String.format("%,d", totalPrice).replace(',', '.');
        // Cari TextView total di footer (sesuaikan ID dengan layout footer Anda)
        TextView tvFooterTotal = findViewById(R.id.tvTotalBayar); // Misal ID di footer
        if(tvFooterTotal != null) tvFooterTotal.setText(formattedPrice);

        // Back Button Logic
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        btnMethodCash.setOnClickListener(v -> updatePaymentMethod("CASH"));
        btnMethodQris.setOnClickListener(v -> updatePaymentMethod("QRIS"));

        // Bayar Button Logic
        btnBayar.setOnClickListener(v -> {
            processPayment();
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void updatePaymentMethod(String method) {
        selectedMethod = method;

        if (method.equals("CASH")) {
            // Set Cash jadi AKTIF
            btnMethodCash.setBackgroundResource(R.drawable.bg_selection_active);
            //ImageViewCompat.setImageTintList(ivIconCash, ColorStateList.valueOf(Color.parseColor("#4CAF50"))); // Hijau

            // Set QRIS jadi TIDAK AKTIF
            btnMethodQris.setBackgroundResource(R.drawable.bg_selection_inactive);
            //ImageViewCompat.setImageTintList(ivIconQris, ColorStateList.valueOf(Color.BLACK)); // Hitam

        } else if (method.equals("QRIS")) {
            // Set Cash jadi TIDAK AKTIF
            btnMethodCash.setBackgroundResource(R.drawable.bg_selection_inactive);
            //ImageViewCompat.setImageTintList(ivIconCash, ColorStateList.valueOf(Color.BLACK)); // Hitam

            // Set QRIS jadi AKTIF
            btnMethodQris.setBackgroundResource(R.drawable.bg_selection_active);
            //ImageViewCompat.setImageTintList(ivIconQris, ColorStateList.valueOf(Color.parseColor("#4CAF50"))); // Hijau
        }
    }

    private void processPayment() {
        // Cek metode apa yang dipilih
        if (selectedMethod.equals("QRIS")) {
            Toast.makeText(this, "Munculkan QR Code...", Toast.LENGTH_SHORT).show();
            // Nanti bisa diarahkan ke Activity Scan QR jika ada
        } else {
            Toast.makeText(this, "Silahkan bayar di kasir!", Toast.LENGTH_SHORT).show();
        }

        // Kosongkan keranjang dan kembali ke Home
        CartManager.cartList.clear();
        Intent intent = new Intent(CheckoutActivity.this, PaymentSuccessActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
