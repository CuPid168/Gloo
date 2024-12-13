package com.dicoding.gloo.activity.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.gloo.R
import android.util.Log

class AllCommunityAdapter(private val communityList: List<AllCommunity>) :
    RecyclerView.Adapter<AllCommunityAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val profileImage: ImageView = view.findViewById(R.id.allComProfileImage)
        val userName: TextView = view.findViewById(R.id.allComUserName)
        val postTime: TextView = view.findViewById(R.id.postTime)
        val postContent: TextView = view.findViewById(R.id.postContent)
        val likeCount: TextView = view.findViewById(R.id.likeCount)
        val commentCount: TextView = view.findViewById(R.id.commentCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_all_community, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val community = communityList[position]

        // Log the value of allPhoto to debug
        Log.d("AllCommunityAdapter", "Photo for ${community.allName}: ${community.allPhoto}")

        // Use a when block to resolve image resource directly
        val imageResource = when (community.allPhoto) {
            "photo_community_1" -> R.drawable.photo_community_1
            "photo_community_2" -> R.drawable.photo_community_2
            "photo_community_3" -> R.drawable.photo_community_3
            else -> {
                Log.e("AllCommunityAdapter", "Unknown photo: ${community.allPhoto}")
                R.drawable.error_image // Default fallback
            }
        }

        // Use Glide to load image
        Glide.with(holder.itemView.context)
            .load(imageResource)
            .circleCrop()
            .placeholder(R.drawable.placeholder_image) // Placeholder while loading
            .error(R.drawable.error_image)             // Fallback image for errors
            .into(holder.profileImage)

        // Bind text data
        holder.userName.text = community.allName
        holder.postTime.text = community.allTime
        holder.postContent.text = community.allPost
        holder.likeCount.text = community.allLike
        holder.commentCount.text = community.allComment
    }

    override fun getItemCount(): Int {
        return communityList.size
    }
}

