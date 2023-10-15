package com.e2g16.quizapp.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.e2g16.quizapp.Withdrawal
import com.e2g16.quizapp.databinding.FragmentSpinBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Random

class SpinFragment : Fragment() {

    private lateinit var binding: FragmentSpinBinding
    private lateinit var timer: CountDownTimer
    private val itemTiles = arrayOf("100", "Try Again", "500", "Try again", "200", "Try Again")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSpinBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showResult(itemTitle: String){
        Toast.makeText(requireContext(), itemTitle, Toast.LENGTH_SHORT).show()
        binding.Spin.isEnabled = true
    }
 
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        binding.Spin.setOnClickListener{
            binding.Spin.isEnabled = false

            var spin = Random().nextInt(6)
            var degrees = 60f * spin

            timer = object : CountDownTimer(5000, 50){
                var rotation = 0f

                override fun onTick(millisUntilFinished: Long) {
                    rotation += 5f
                    if (rotation >= degrees) {
                        rotation = degrees
                        timer.cancel()
                        showResult(itemTiles[spin])
                    }
                    binding.wheel.rotation = rotation
                }

                override fun onFinish() {
                    TODO("Not yet implemented")
                }

            }.start()

        }
    }

}