package com.example.gymguru

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(private val exerciseList: List<ExcerciseAdd>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val muscleGroupTextView: TextView = itemView.findViewById(R.id.muscleGroupTextView)
        val linkTextView: TextView = itemView.findViewById(R.id.linkTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentExercise = exerciseList[position]

        holder.nameTextView.text = "${currentExercise.name}"
        holder.descriptionTextView.text = "Opis: ${currentExercise.description}"
        holder.muscleGroupTextView.text = "Zaangażowana partia mięsniowa: ${currentExercise.muscleGroup}"
        holder.linkTextView.text = "Link do ćwiczenia: ${currentExercise.link}"
        Log.d("TAG", "Przypisano dane do widoku")
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }
}
