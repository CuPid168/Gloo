package com.dicoding.gloo.activity.consultation

import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.gloo.R

class ConsultationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultation)

        val backButton = findViewById<ImageView>(R.id.backButton)
        val allConsultationsTab = findViewById<TextView>(R.id.allConsultations)
        val myConsultationsTab = findViewById<TextView>(R.id.myConsultations)

        // Back Button functionality
        backButton.setOnClickListener {
            finish() // Kembali ke aktivitas sebelumnya
        }

        // Default fragment (All Consultations)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, AllConsultationsFragment())
            .commit()

        // Set the initial state of the tabs (All Consultations selected by default)
        allConsultationsTab.setTextColor(ContextCompat.getColor(this, R.color.blue_500))
        allConsultationsTab.setTypeface(null, Typeface.BOLD)

        // Change fragment when tabs are clicked
        allConsultationsTab.setOnClickListener {
            // Update UI for selected tab
            allConsultationsTab.setTextColor(ContextCompat.getColor(this, R.color.blue_500))
            allConsultationsTab.setTypeface(null, Typeface.BOLD)
            myConsultationsTab.setTextColor(ContextCompat.getColor(this, R.color.gray_500))
            myConsultationsTab.setTypeface(null, Typeface.NORMAL)

            // Replace fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, AllConsultationsFragment())
                .commit()
        }

        myConsultationsTab.setOnClickListener {
            // Update UI for selected tab
            allConsultationsTab.setTextColor(ContextCompat.getColor(this, R.color.gray_500))
            allConsultationsTab.setTypeface(null, Typeface.NORMAL)
            myConsultationsTab.setTextColor(ContextCompat.getColor(this, R.color.blue_500))
            myConsultationsTab.setTypeface(null, Typeface.BOLD)

            // Replace fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, MyConsultationsFragment())
                .commit()
        }
    }
}
