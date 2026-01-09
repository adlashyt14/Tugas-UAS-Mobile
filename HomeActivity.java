package com.example.uas;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvMenu;
    private EditText etSearch;
    private Button btnCheckOut;
    private MenuAdapter adapter;
    private List<Menu> dataMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvMenu = findViewById(R.id.rvMenu);
        etSearch = findViewById(R.id.etSearch);
        btnCheckOut = findViewById(R.id.btnCheckOut);

        // 1. Data dimasukkan ke dalam List
        dataMenu = new ArrayList<>();
        // Contoh pengisian data di HomeActivity
        dataMenu.add(new Menu("Burger Deluxe", "Rp 25.000", R.drawable.bergerpesan,
                "Burger premium dengan patty daging sapi 100% Australian beef, roti brioche lembut, keju leleh, sayuran segar, dan saus signature yang kaya rasa. Dibuat khusus untuk pengalaman makan yang lebih mewah dan juicy."));
        dataMenu.add(new Menu("Double Beef Burger", "Rp 25.000", R.drawable.piza,
                "Burger premium dengan double patty daging sapi pilihan, keju leleh, dan saus spesial."));
        dataMenu.add(new Menu("Pizza Pepperoni", "Rp 30.000", R.drawable.piza,
                "Pizza Italia autentik dengan topping pepperoni sapi melimpah dan mozarella premium."));
        dataMenu.add(new Menu("Chicken Popcorn", "Rp 20.000", R.drawable.chikenember,
                "Ayam goreng potongan kecil yang renyah di luar dan juicy di dalam dengan bumbu rempah."));
        dataMenu.add(new Menu("Coca Cola", "Rp 10.000", R.drawable.soda,
                "Minuman berkarbonasi dingin yang menyegarkan, cocok menemani hidangan utama."));
        // Anda bisa menambah ribuan menu di sini tanpa mengubah XML lagi

        // 2. Setting Grid 2 Kolom (Sama seperti GridLayout asli)
        rvMenu.setLayoutManager(new GridLayoutManager(this, 2));

        // 3. Pasang Adapter
        adapter = new MenuAdapter(dataMenu);
        rvMenu.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Panggil fungsi filter setiap kali user mengetik
                if (adapter != null) {
                    adapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnCheckOut.setOnClickListener(v -> {
            // Cek apakah keranjang kosong (Opsional)
            if (CartManager.cartList.isEmpty()) {
                Toast.makeText(HomeActivity.this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(HomeActivity.this, MyOrderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update teks tombol dengan jumlah item saat ini di CartManager
        if (btnCheckOut != null) {
            int jumlahItem = CartManager.cartList.size();
            if (jumlahItem > 0) {
                btnCheckOut.setText("LIHAT KERANJANG (" + jumlahItem + " ITEM)");
                btnCheckOut.setVisibility(View.VISIBLE);
            } else {
                btnCheckOut.setText("LIHAT KERANJANG");
                // btnCheckout.setVisibility(View.GONE); // Bisa disembunyikan jika kosong
            }
        }
    }
}
