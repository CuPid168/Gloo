package com.dicoding.gloo.activity.consultation

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.gloo.R

class ChatConsultationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_consultation)

        val backButton = findViewById<ImageView>(R.id.backButton)

        backButton.setOnClickListener {
            finish() // Kembali ke aktivitas sebelumnya
        }
    }
}
