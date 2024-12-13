package com.dicoding.gloo.activity.consultation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.dicoding.gloo.R

class MyConsultationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_consultations, container, false)

        // Menggunakan LinearLayout untuk chatConsultationBtn
        val chatConsultationBtn = view.findViewById<LinearLayout>(R.id.chatConsultationBtn)

        // Set listener untuk berpindah ke halaman chat
        chatConsultationBtn.setOnClickListener {
            val intent = Intent(requireContext(), ChatConsultationActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
