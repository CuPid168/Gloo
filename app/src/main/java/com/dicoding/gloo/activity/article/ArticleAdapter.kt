package com.dicoding.gloo.activity.article

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.gloo.R

class ArticleAdapter(
    private val articles: List<Article>
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivThumbnail: ImageView = itemView.findViewById(R.id.tv_Thumbnail_Photo)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_Title_Article)
        private val tvSource: TextView = itemView.findViewById(R.id.tv_Source_Article)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_Date_Article)

        fun bind(article: Article) {
            // Log untuk debugging URL/path gambar
            Log.d("ArticleAdapter", "Image URL/Path: ${article.imageUrl}")

            // Load gambar menggunakan Glide, periksa apakah URL atau path lokal
            val imageResource = if (article.imageUrl.startsWith("http")) {
                article.imageUrl // URL
            } else {
                // Jika path lokal, konversi menjadi resource ID
                itemView.context.resources.getIdentifier(
                    article.imageUrl.replace("@drawable/", ""), // Hilangkan "@drawable/"
                    "drawable",
                    itemView.context.packageName
                )
            }

            // Atur data teks
            tvTitle.text = article.title
            tvSource.text = article.source
            tvDate.text = article.date

            // Set listener untuk klik item
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DescriptionArticleActivity::class.java)
                intent.putExtra(DescriptionArticleActivity.EXTRA_ARTICLE, article)
                itemView.context.startActivity(intent)
            }
        }
    }
}
