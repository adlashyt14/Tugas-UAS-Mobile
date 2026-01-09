package com.example.uas;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    public static List<CartItem> cartList = new ArrayList<>();

    public static void addOrUpdate(CartItem newItem) {
        for (CartItem item : cartList) {
            // Logika baru: Cek Nama DAN Topping
            // Jika nama sama DAN toppingnya juga sama persis, baru update quantity
            if (item.getNama().equals(newItem.getNama()) &&
                    item.getTopping().equals(newItem.getTopping())) {

                item.setQuantity(item.getQuantity() + newItem.getQuantity());

                // Update harga total string jika perlu (opsional)
                // Karena harga kamu String, pastikan cara kalkulasinya konsisten
                return;
            }
        }
        // Jika topping berbeda, maka dianggap item baru dan masuk ke baris baru
        cartList.add(newItem);
    }
}
