package com.e2g16.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.e2g16.quizapp.databinding.FragmentWithdrawalBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Withdrawal : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentWithdrawalBinding
    var currentCoin = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.database.reference.child("PlayerCoin")
            .child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.value
                    if (value != null) {
                        binding.coin.text = value.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO()
                }

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWithdrawalBinding.inflate(inflater, container, false)
        Firebase.database.reference.child("PlayerCoin")
            .child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        currentCoin = snapshot.value as Long
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
            )
        binding.transfer.setOnClickListener {
            if (binding.amount.text.toString().isEmpty() && binding.bankAcc.text.toString().isEmpty()) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else if (binding.amount.text.toString().isEmpty()) {
                Toast.makeText(context, "Please fill the amount", Toast.LENGTH_SHORT).show()
            } else if (binding.bankAcc.text.toString().isEmpty()) {
                Toast.makeText(context, "Please fill the bank account", Toast.LENGTH_SHORT).show()
            } else if (binding.amount.text.toString().toLong() > currentCoin) {
                Toast.makeText(context, "Insufficient Balance", Toast.LENGTH_SHORT).show()
            } else {
                val amount = binding.amount.text.toString().toLong()
                val bankAcc = binding.bankAcc.text.toString()
                val ref = Firebase.database.reference.child("Withdrawal")
                val key = ref.push().key
                val map = HashMap<String, Any>()
                map["amount"] = amount
                map["bankAcc"] = bankAcc
                map["status"] = "Pending"
                map["key"] = key!!
                ref.child(key).setValue(map)
                Firebase.database.reference.child("PlayerCoin")
                    .child(Firebase.auth.currentUser!!.uid)
                    .setValue(currentCoin - amount)
                Toast.makeText(context, "Request Sent", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        return binding.root
    }

    companion object {

    }
}