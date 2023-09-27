package com.e2g16.quizapp.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e2g16.quizapp.databinding.CategoryitemBinding
import com.e2g16.quizapp.modle.categoryModelClass

class categoryadaptor(var categoryList: ArrayList<categoryModelClass>) : RecyclerView.Adapter<categoryadaptor.MycategoryViewHolder>() {
    class MycategoryViewHolder(var binding: CategoryitemBinding) :RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MycategoryViewHolder {
        return MycategoryViewHolder(CategoryitemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: MycategoryViewHolder, position: Int) {
        var datelist = categoryList[position]
        holder.binding.categoryImage.setImageResource(datelist.catImage)
        holder.binding.category.text = datelist.catText
    }

}