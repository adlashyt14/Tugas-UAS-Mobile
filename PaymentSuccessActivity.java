package com.example.uas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentSuccessActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable redirectRunnable;
    private int countdown = 5; // Waktu hitung mundur
    private TextView tvRedirectInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        Button btnBackHome = findViewById(R.id.btnBackHome);
        tvRedirectInfo = findViewById(R.id.tvRedirectInfo);

        // Setup aksi tombol
        btnBackHome.setOnClickListener(v -> navigateToHome());

        // Setup Redirect Otomatis
        startAutoRedirect();
    }

    private void startAutoRedirect() {
        handler = new Handler(Looper.getMainLooper());

        // Update teks hitung mundur setiap detik (Opsional, untuk UX lebih baik)
        redirectRunnable = new Runnable() {
            @Override
            public void run() {
                if (countdown > 0) {
                    tvRedirectInfo.setText("Otomatis kembali dalam " + countdown + " detik...");
                    countdown--;
                    handler.postDelayed(this, 1000); // Jalankan lagi setelah 1 detik
                } else {
                    navigateToHome(); // Waktu habis, pindah
                }
            }
        };

        // Mulai runnable
        handler.post(redirectRunnable);
    }

    private void navigateToHome() {
        // Penting: Hapus callback agar tidak terpanggil dua kali (misal user klik tombol di detik ke-4)
        if (handler != null && redirectRunnable != null) {
            handler.removeCallbacks(redirectRunnable);
        }

        Intent intent = new Intent(PaymentSuccessActivity.this, HomeActivity.class);
        // Hapus semua activity sebelumnya agar user tidak bisa back ke halaman pembayaran
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Bersihkan handler saat activity dihancurkan untuk mencegah memory leak
        if (handler != null && redirectRunnable != null) {
            handler.removeCallbacks(redirectRunnable);
        }
    }
}
