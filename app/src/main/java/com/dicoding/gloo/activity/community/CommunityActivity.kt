package com.dicoding.gloo.activity.community

import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.gloo.R

class CommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        val backButton = findViewById<ImageView>(R.id.backButton)
        val allCommunityTab = findViewById<TextView>(R.id.allCommunity)
        val myCommunityTab = findViewById<TextView>(R.id.myCommunity)

        // Back Button functionality
        backButton.setOnClickListener {
            finish() // Kembali ke aktivitas sebelumnya
        }

        // Default fragment for All Community
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewCommunity, AllCommunityFragment())
            .commit()

        allCommunityTab.setTextColor(ContextCompat.getColor(this, R.color.blue_500))
        allCommunityTab.setTypeface(null, Typeface.BOLD)

        allCommunityTab.setOnClickListener {
            // Update UI for selected tab
            allCommunityTab.setTextColor(ContextCompat.getColor(this, R.color.blue_500))
            allCommunityTab.setTypeface(null, Typeface.BOLD)
            myCommunityTab.setTextColor(ContextCompat.getColor(this, R.color.gray_500))
            myCommunityTab.setTypeface(null, Typeface.NORMAL)

            // Replace fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerViewCommunity, AllCommunityFragment())
                .commit()
        }

        myCommunityTab.setOnClickListener {
            // Update UI for selected tab
            allCommunityTab.setTextColor(ContextCompat.getColor(this, R.color.gray_500))
            allCommunityTab.setTypeface(null, Typeface.NORMAL)
            myCommunityTab.setTextColor(ContextCompat.getColor(this, R.color.blue_500))
            myCommunityTab.setTypeface(null, Typeface.BOLD)

            // Replace fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerViewCommunity, MyCommunityFragment())
                .commit()
        }
    }
}
