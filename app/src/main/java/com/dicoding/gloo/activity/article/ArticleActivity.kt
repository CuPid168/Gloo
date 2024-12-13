package com.dicoding.gloo.activity.article

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.gloo.R

class ArticleActivity : AppCompatActivity() {
    private lateinit var rvArticle: RecyclerView
    private val list = ArrayList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val backButton = findViewById<ImageView>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        rvArticle = findViewById(R.id.recyclerView)
        rvArticle.setHasFixedSize(true)

        list.addAll(getListArticles())
        showRecyclerList()
    }

    private fun getListArticles(): ArrayList<Article> {
        // Load data from resources or predefined values
        val dataImage = resources.getStringArray(R.array.Thumbnail_Photo)
        val dataTitle = resources.getStringArray(R.array.Title_Article)
        val dataSource = resources.getStringArray(R.array.Source_Article)
        val dataDate = resources.getStringArray(R.array.Date_Article)
        val dataDescription = resources.getStringArray(R.array.Description_Article)
        val photoDescription = resources.getStringArray(R.array.Photo_Description_Article)

        val listArticles = ArrayList<Article>()
        for (i in dataTitle.indices) {
            val article = Article(
                dataImage[i],
                dataTitle[i],
                dataSource[i],
                dataDate[i],
                dataDescription[i],
                photoDescription[i]
            )
            listArticles.add(article)
        }
        return listArticles
    }

    private fun showRecyclerList() {
        rvArticle.layoutManager = LinearLayoutManager(this)
        rvArticle.adapter = ArticleAdapter(list)
    }
}
