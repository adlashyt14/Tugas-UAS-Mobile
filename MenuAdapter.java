package com.example.uas;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList; // Tambahkan ini
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<Menu> listMenu;
    private List<Menu> listMenuFull; // List cadangan untuk menyimpan data asli

    public MenuAdapter(List<Menu> listMenu) {
        this.listMenu = listMenu;
        // Salin listMenu ke listMenuFull agar data asli tidak hilang saat di-filter
        this.listMenuFull = new ArrayList<>(listMenu);
    }

    // LOGIKA SEARCH/FILTER
    public void filter(String text) {
        listMenu.clear();
        if (text.isEmpty()) {
            listMenu.addAll(listMenuFull); // Jika input kosong, tampilkan semua data
        } else {
            text = text.toLowerCase().trim();
            for (Menu item : listMenuFull) {
                // Cek apakah nama menu mengandung kata kunci yang dicari
                if (item.getNama().toLowerCase().contains(text)) {
                    listMenu.add(item);
                }
            }
        }
        notifyDataSetChanged(); // Memberitahu RecyclerView bahwa data berubah
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        // Ambil data berdasarkan posisi yang sedang di-filter
        Menu item = listMenu.get(position);

        holder.txtNama.setText(item.getNama());
        holder.txtHarga.setText(item.getHarga());
        holder.img.setImageResource(item.getGambar());

        // Listener untuk Tombol Order
        holder.btnOrder.setOnClickListener(view -> {
            moveDetail(view, item);
        });

        // Listener untuk Klik Area Item (Seluruh kotak)
        holder.itemView.setOnClickListener(v -> {
            moveDetail(v, item);
        });
    }

    // Fungsi pembantu agar kode perpindahan halaman tidak duplikat
    private void moveDetail(View view, Menu item) {
        Intent intent = new Intent(view.getContext(), DetailMenuActivity.class);
        intent.putExtra("NAMA_MENU", item.getNama());
        intent.putExtra("HARGA_MENU", item.getHarga());
        intent.putExtra("GAMBAR_MENU", item.getGambar());
        intent.putExtra("DESKRIPSI_MENU", item.getDeskripsi());
        view.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtNama, txtHarga;
        Button btnOrder;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgMenu);
            txtNama = itemView.findViewById(R.id.txtNama);
            txtHarga = itemView.findViewById(R.id.txtHarga);
            btnOrder = itemView.findViewById(R.id.btnOrder);
        }
    }
}
