package com.dicoding.gloo.activity.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.gloo.R

class AllCommunityFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AllCommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_all_community, container, false)

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewAllCommunity)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AllCommunityAdapter(getListAllCommunity())
        recyclerView.adapter = adapter

        return view
    }

    // Function to fetch list of All Community from strings.xml
    private fun getListAllCommunity(): List<AllCommunity> {
        val photos = resources.getStringArray(R.array.all_community_photo)
        val names = resources.getStringArray(R.array.all_community_name)
        val times = resources.getStringArray(R.array.all_community_time)
        val posts = resources.getStringArray(R.array.all_community_post)
        val likes = resources.getStringArray(R.array.all_community_likeCount)
        val comments = resources.getStringArray(R.array.all_community_commentCount)

        val communityList = mutableListOf<AllCommunity>()
        for (i in photos.indices) {
            communityList.add(
                AllCommunity(
                    allPhoto = photos[i],
                    allName = names[i],
                    allTime = times[i],
                    allPost = posts[i],
                    allLike = likes[i],
                    allComment = comments[i]
                )
            )
        }
        return communityList
    }
}
