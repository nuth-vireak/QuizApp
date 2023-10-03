package com.e2g16.quizapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.e2g16.quizapp.R
import com.e2g16.quizapp.Withdrawal
import com.e2g16.quizapp.adaptor.CategoryAdaptor
import com.e2g16.quizapp.databinding.FragmentHomeBinding
import com.e2g16.quizapp.model.CategoryModelClass
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private var categoryList = ArrayList<CategoryModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryList.add(CategoryModelClass(R.drawable.scince, "Science"))
        categoryList.add(CategoryModelClass(R.drawable.englishs, "English"))
        categoryList.add(CategoryModelClass(R.drawable.englishs, "history"))
        categoryList.add(CategoryModelClass(R.drawable.mathmetic, "mathematics"))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.CoinWithdrawal.setOnClickListener {
            var bottomSheetDialog: BottomSheetDialogFragment = Withdrawal()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        binding.CoinWithdrawal1.setOnClickListener {
            var bottomSheetDialog: BottomSheetDialogFragment = Withdrawal()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        var adapter = CategoryAdaptor(categoryList, requireActivity())
        binding.categoryRecyclerView.adapter = adapter
        binding.categoryRecyclerView.setHasFixedSize(true)
    }
    companion object {

    }
}
