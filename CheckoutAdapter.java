package com.example.uas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder> {

    private List<CartItem> list;

    public CheckoutAdapter(List<CartItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkout_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = list.get(position);

        holder.qty.setText(item.getQuantity() + "X");
        holder.nama.setText(item.getNama());
        holder.topping.setText(item.getTopping());

        // Asumsi menampilkan total harga per item (Harga x Qty)
        // Jika item.getHarga() masih string "Rp 45.000", kita tampilkan langsung
        holder.harga.setText(item.getHarga());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView qty, nama, topping, harga;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            qty = itemView.findViewById(R.id.tvItemQty);
            nama = itemView.findViewById(R.id.tvItemNama);
            topping = itemView.findViewById(R.id.tvItemTopping);
            harga = itemView.findViewById(R.id.tvItemHarga);
        }
    }
}
