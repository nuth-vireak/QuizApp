package com.e2g16.quizapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.e2g16.quizapp.R
import com.e2g16.quizapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    var isExpand = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.imageButton.setOnClickListener {
            if (isExpand) {
                binding.expadableconstraintlayout.visibility = View.VISIBLE
                binding.imageButton.setImageResource(R.drawable.arrowup)
            } else {
                binding.expadableconstraintlayout.visibility = View.GONE
                binding.imageButton.setImageResource(R.drawable.downarrow)
            }
            isExpand = !isExpand
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {

    }
}