package com.e2g16.quizapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.e2g16.quizapp.R
import com.e2g16.quizapp.adaptor.categoryadaptor
import com.e2g16.quizapp.databinding.FragmentHomeBinding
import com.e2g16.quizapp.modle.categoryModelClass


class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private var categoryList = ArrayList<categoryModelClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryList.add(categoryModelClass(R.drawable.scince, "Science"))
        categoryList.add(categoryModelClass(R.drawable.englishs, "English"))
        categoryList.add(categoryModelClass(R.drawable.englishs, "history"))
        categoryList.add(categoryModelClass(R.drawable.mathmetic, "mathematics"))
        binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        var adapter = categoryadaptor(categoryList)
        binding.categoryRecyclerView.adapter = adapter
        binding.categoryRecyclerView.setHasFixedSize(true)
    }

    companion object {

    }
}