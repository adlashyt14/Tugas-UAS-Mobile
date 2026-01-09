package com.example.uas;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<CartItem> list;
    private OnCartChangeListener listener;

    // Interface untuk komunikasi ke Activity
    public interface OnCartChangeListener {
        void onQtyChanged();
    }

    public OrderAdapter(List<CartItem> list, OnCartChangeListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartItem item = list.get(position);

        holder.nama.setText(item.getNama());
        holder.harga.setText(item.getHarga()); // Harga per unit/total string
        holder.qty.setText(String.valueOf(item.getQuantity()));
        holder.topping.setText("Topping: " + item.getTopping());
        holder.gambar.setImageResource(item.getGambar());

        // Logika Tombol Plus (+)
        holder.btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(position); // Update tampilan baris ini
            if (listener != null) listener.onQtyChanged(); // Trigger hitung total di Activity
        });

        // Logika Tombol Minus (-)
        holder.btnMin.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(position);
                if (listener != null) listener.onQtyChanged();
            } else {
                // Jika qty 1 ditekan minus, hapus dari keranjang
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
                if (listener != null) listener.onQtyChanged();
            }
        });

        holder.btnEdit.setOnClickListener(v -> ((Activity) holder.itemView.getContext()).finish());
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, harga, qty, topping;
        ImageView gambar;
        Button btnEdit, btnPlus, btnMin;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvOrderNama);
            harga = itemView.findViewById(R.id.tvOrderHarga);
            qty = itemView.findViewById(R.id.tvOrderQty);
            topping = itemView.findViewById(R.id.tvOrderTopping);
            gambar = itemView.findViewById(R.id.ivOrderGambar);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnPlus = itemView.findViewById(R.id.btnPlus); // Pastikan ID ini sesuai di XML
            btnMin = itemView.findViewById(R.id.btnMin);   // Pastikan ID ini sesuai di XML
        }
    }
}
