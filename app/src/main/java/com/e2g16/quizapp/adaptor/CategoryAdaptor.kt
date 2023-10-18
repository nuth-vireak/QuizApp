package com.e2g16.quizapp.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e2g16.quizapp.databinding.CategoryitemBinding
import com.e2g16.quizapp.model.CategoryModelClass
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.e2g16.quizapp.QuizActivity
import com.e2g16.quizapp.model.Question
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryAdaptor(
    var categoryList: ArrayList<CategoryModelClass>,
    var requireActivity: FragmentActivity
) : RecyclerView.Adapter<CategoryAdaptor.MyCategoryViewHolder>() {
    class MyCategoryViewHolder(var binding: CategoryitemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private lateinit var questionList: ArrayList<Question>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCategoryViewHolder {
        return MyCategoryViewHolder(
            CategoryitemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: MyCategoryViewHolder, position: Int) {

        var datelist = categoryList[position]

        questionList = ArrayList()

        Firebase.firestore.collection("Questions")
            .document(datelist.catText.toString())
            .collection("question1")
            .get()
            .addOnSuccessListener { questionData ->
                questionList.clear()
                for (data in questionData.documents) {
                    var question: Question? = data.toObject(Question::class.java)
                    questionList.add(question!!)
                }
                holder.binding.totalQuestions.text = questionList.size.toString()
            }

        holder.binding.categoryImage.setImageResource(datelist.catImage)
        holder.binding.category.text = datelist.catText
        holder.binding.categorybtn.setOnClickListener {

            var intent = Intent(requireActivity, QuizActivity::class.java)
            intent.putExtra("categoryimg", datelist.catImage)
            intent.putExtra("questionType", datelist.catText)
            requireActivity.startActivity(intent)
        }
    }
}