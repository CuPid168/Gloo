package com.dicoding.gloo.activity.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.gloo.R

class MyCommunityAdapter(private val communityList: List<MyCommunity>) :
        RecyclerView.Adapter<MyCommunityAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val myPostTime: TextView = view.findViewById(R.id.myPostTime)
        val myPostContent: TextView = view.findViewById(R.id.myPostContent)
        val myLikeCount: TextView = view.findViewById(R.id.myLikeCount)
        val myCommentCount: TextView = view.findViewById(R.id.myCommentCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_community, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val community = communityList[position]

        // Bind text data
        holder.myPostTime.text = community.myTime
        holder.myPostContent.text = community.myPost
        holder.myLikeCount.text = community.myLike
        holder.myCommentCount.text = community.myComment
    }

    override fun getItemCount(): Int {
        return communityList.size
    }
}