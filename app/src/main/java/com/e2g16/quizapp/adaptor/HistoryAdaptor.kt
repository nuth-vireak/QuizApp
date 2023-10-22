package com.e2g16.quizapp.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e2g16.quizapp.databinding.HistoryitemBinding
import com.e2g16.quizapp.model.HistoryModelClass
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HistoryAdaptor(var ListHistory: ArrayList<HistoryModelClass>) :
    RecyclerView.Adapter<HistoryAdaptor.HistoryCoinViewHolder>() {
    class HistoryCoinViewHolder(var binding: HistoryitemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryCoinViewHolder {
        return HistoryCoinViewHolder(
            HistoryitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = ListHistory.size

    override fun onBindViewHolder(holder: HistoryCoinViewHolder, position: Int) {

        holder.binding.Time.text = ListHistory[position].timeAndDate
        holder.binding.Coin.text = ListHistory[position].coin
    }
}