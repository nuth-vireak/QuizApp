package com.e2g16.quizapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.e2g16.quizapp.Withdrawal
import com.e2g16.quizapp.databinding.FragmentSpinBinding
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
import java.util.Random

class SpinFragment : Fragment() {

    private lateinit var binding: FragmentSpinBinding
    private lateinit var timer: CountDownTimer
    private val itemTiles = arrayOf("100", "Try Again", "500", "Try again", "200", "Try Again")
    private var currentChance = 0L
    private var currentCoin = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSpinBinding.inflate(inflater, container, false)

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

        Firebase.database.reference.child("PlayChance")
            .child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.value
                    if (value != null) {
                        currentChance = snapshot.value as Long
                        binding.spinChance.text = snapshot.value.toString()
                    } else {
                        binding.spinChance.text = "0"
                        binding.spin.isEnabled = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled: ${error.message}")
                }

            })

        Firebase.database.reference.child("PlayerCoin")
            .child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.value
                    if (value != null) {
                        currentCoin = snapshot.value as Long
                        binding.coinWithdrawal.text = currentCoin.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled: ${error.message}")
                }

            })

        return binding.root
    }

    private fun showResult(itemTitle: String, spin: Int){

        if (spin % 2 == 0) {
            var winCoin = itemTitle.toInt()
            Firebase.database.reference.child("PlayerCoin")
                .child(Firebase.auth.currentUser!!.uid)
                .setValue(winCoin + currentCoin)

            var historyModelClass = HistoryModelClass(System.currentTimeMillis().toString(), winCoin.toString(), false)
            Firebase.database.reference.child("PlayerCoinHistory")
                .push()
                .child(Firebase.auth.currentUser!!.uid)
                .setValue(historyModelClass)

            binding.coinWithdrawal.text = (winCoin + currentCoin).toString()
        }

        Toast.makeText(requireContext(), itemTitle, Toast.LENGTH_SHORT).show()

        currentChance -= 1

        Firebase.database.reference.child("PlayChance")
            .child(Firebase.auth.currentUser!!.uid)
            .setValue(currentChance)

        binding.spin.isEnabled = true
    }
 
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.coinWithdrawal.setOnClickListener {
            var bottomSheetDialog: BottomSheetDialogFragment = Withdrawal()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        binding.CoinWithdrawal1.setOnClickListener {
            var bottomSheetDialog: BottomSheetDialogFragment = Withdrawal()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }

        binding.spin.setOnClickListener{

            binding.spin.isEnabled = false

            if (currentChance > 0) {
                var spin = Random().nextInt(6)
                var degrees = 60f * spin

                timer = object : CountDownTimer(5000, 50){
                    var rotation = 0f

                    override fun onTick(millisUntilFinished: Long) {
                        rotation += 5f
                        if (rotation >= degrees) {
                            rotation = degrees
                            timer.cancel()
                            showResult(itemTiles[spin], spin)
                        }
                        binding.wheel.rotation = rotation
                    }

                    override fun onFinish() {
                        TODO("Not yet implemented")
                    }

                }.start()
            } else {
                Toast.makeText(activity, "No chance left", Toast.LENGTH_SHORT).show()
                binding.spin.isEnabled = true
            }

        }
    }

}