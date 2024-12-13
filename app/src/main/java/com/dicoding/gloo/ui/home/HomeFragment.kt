//package com.dicoding.gloo.ui.home
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import com.dicoding.gloo.databinding.FragmentHomeBinding
//
//class HomeFragment : Fragment() {
//
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!
//    private val homeViewModel: HomeViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        // Observe LiveData and update UI
//        homeViewModel.text.observe(viewLifecycleOwner) { text ->
//            binding.textHome.text = text
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}


package com.dicoding.gloo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.gloo.activity.article.ArticleActivity
import com.dicoding.gloo.activity.community.CommunityActivity
import com.dicoding.gloo.activity.consultation.ConsultationActivity
import com.dicoding.gloo.activity.medicine.MedicineActivity
import com.dicoding.gloo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Menghubungkan fragment dengan fragment_home.xml
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Event Listener untuk tombol konsultasi
        binding.consulationButton.setOnClickListener {
            val intent = Intent(requireContext(), ConsultationActivity::class.java)
            startActivity(intent)
        }

        binding.articleButton.setOnClickListener {
            val intent = Intent(requireContext(), ArticleActivity::class.java)
            startActivity(intent)
        }

        binding.communityButton.setOnClickListener {
            val intent = Intent(requireContext(), CommunityActivity::class.java)
            startActivity(intent)
        }

        binding.medicineButton.setOnClickListener {
            val intent = Intent(requireContext(), MedicineActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
