package com.e2g16.quizapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.e2g16.quizapp.Withdrawal
import com.e2g16.quizapp.adaptor.HistoryAdaptor
import com.e2g16.quizapp.databinding.FragmentHistoryBinding
import com.e2g16.quizapp.model.HistoryModelClass
import com.e2g16.quizapp.model.User
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class HistoryFragment : Fragment() {
    val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }
    private var ListHistory = ArrayList<HistoryModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ListHistory.add(HistoryModelClass("12:03", "200"))
        ListHistory.add(HistoryModelClass("05:46", "200"))
        ListHistory.add(HistoryModelClass("11:50", "500"))
        ListHistory.add(HistoryModelClass("09:03", "100"))

        Firebase.database.reference.child("Users")
            .child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user = snapshot.getValue<User>()
                        binding.name.text = user?.name
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                }
            )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        binding.HistoryRecycleView.layoutManager = LinearLayoutManager(requireContext())
        var adapter = HistoryAdaptor(ListHistory)
        binding.HistoryRecycleView.adapter = adapter
        binding.HistoryRecycleView.setHasFixedSize(true)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {

    }
}