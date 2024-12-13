package com.dicoding.gloo.activity.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.gloo.R

class MyCommunityFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyCommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_community, container, false)

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewMyCommunity)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MyCommunityAdapter(getListMyCommunity())
        recyclerView.adapter = adapter

        return view
    }

    private fun getListMyCommunity(): List<MyCommunity> {
        val times = resources.getStringArray(R.array.my_community_time)
        val posts = resources.getStringArray(R.array.my_community_post)
        val likes = resources.getStringArray(R.array.my_community_likeCount)
        val comments = resources.getStringArray(R.array.my_community_commentCount)

        val maxSize = minOf(times.size, posts.size, likes.size, comments.size)

        val communityList = mutableListOf<MyCommunity>()
        for (i in 0 until maxSize) {
            communityList.add(
                MyCommunity(
                    myTime = times[i],
                    myPost = posts[i],
                    myLike = likes[i],
                    myComment = comments[i]
                )
            )
        }
        return communityList
    }

}
