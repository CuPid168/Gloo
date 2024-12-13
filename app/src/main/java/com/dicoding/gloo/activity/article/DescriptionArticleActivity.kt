package com.dicoding.gloo.activity.article

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.gloo.R

class DescriptionArticleActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ARTICLE = "EXTRA_ARTICLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_article)

        val backButton = findViewById<ImageView>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        // Setup action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Article Description"

        // Retrieve the article object passed via intent
        val article = intent.getParcelableExtra<Article>(EXTRA_ARTICLE)
        if (article != null) {
            setupArticleDescription(article)
        }
    }

    private fun setupArticleDescription(article: Article) {
        // Bind article data to views
        val tvTitle: TextView = findViewById(R.id.tvArticleTitle)
        val ivImage: ImageView = findViewById(R.id.tvArticlePhotoDescription)
        val tvSource: TextView = findViewById(R.id.tvArticleSource)
        val tvDate: TextView = findViewById(R.id.tvArticleDate)
        val tvDescription: TextView = findViewById(R.id.tvArticleDescription)

        // Set article details
        tvTitle.text = article.title
        tvSource.text = article.source
        tvDate.text = article.date
        tvDescription.text = article.description

        // Load image using Glide
        val imageResource = if (article.photoDescription.startsWith("http")) {
            article.photoDescription // URL
        } else {
            // Handle drawable resource name
            resources.getIdentifier(
                article.photoDescription.replace("@drawable/", ""),
                "drawable",
                packageName
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
