package com.example.uas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyOrderActivity extends AppCompatActivity {

    private RecyclerView rvOrder;
    private TextView tvTotalHarga;
    private Button btnPilihPembayaran;
    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        rvOrder = findViewById(R.id.rvOrder);
        tvTotalHarga = findViewById(R.id.tvTotalHarga);
        btnPilihPembayaran = findViewById(R.id.btnPilihPembayaran);

        rvOrder.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi Adapter dengan Listener (Interface)
        adapter = new OrderAdapter(CartManager.cartList, new OrderAdapter.OnCartChangeListener() {
            @Override
            public void onQtyChanged() {
                // Setiap kali ada klik + atau -, fungsi ini akan dipanggil
                hitungTotal();
            }
        });

        rvOrder.setAdapter(adapter);

        hitungTotal();
        btnPilihPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyOrderActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void hitungTotal() {
        int total = 0;
        for (CartItem item : CartManager.cartList) {
            // Bersihkan string harga (Hapus Rp, titik, dan spasi)
            String cleanHarga = item.getHarga()
                    .replace("Rp", "")
                    .replace(".", "")
                    .replace(" ", "")
                    .trim();

            try {
                // Kita asumsikan item.getHarga() adalah HARGA SATUAN
                // Jika item.getHarga() dari Intent sudah harga total, jangan dikali item.getQuantity() lagi.
                // Namun standarnya: Harga Satuan * Quantity
                int hargaSatuan = Integer.parseInt(cleanHarga);
                total += (hargaSatuan * item.getQuantity());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Format kembali ke Rupiah dengan titik ribuan
        String formattedTotal = String.format("%,d", total).replace(',', '.');
        tvTotalHarga.setText("Total\nRp " + formattedTotal);
    }
}
