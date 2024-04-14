package com.example.gymguru

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class Cardio : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cardio)
        if(intent.hasExtra("Username")){
            username = intent.getStringExtra("Username").toString()
        }

        try {
            database = FirebaseDatabase.getInstance().reference

            val recyclerView: RecyclerView = findViewById(R.id.cardioView)
            val cardioExerciseList: MutableList<ExcerciseAdd> = mutableListOf()
            val exerciseRef = database.child("excercise")
            val backButton = findViewById<Button>(R.id.backButton)
            backButton.setOnClickListener{
                val intent = Intent(this, WyswietlanieCwiczen::class.java)
                intent.putExtra("Username", username)
                startActivity(intent)
                finish()
            }
            exerciseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    cardioExerciseList.clear()
                    for (childSnapshot in snapshot.children) {
                        val exercise = childSnapshot.getValue(ExcerciseAdd::class.java)
                        if (exercise?.muscleGroup == "Cardio") {
                            exercise?.let {
                                cardioExerciseList.add(it)
                            }
                        }
                    }

                    val adapter = ExerciseAdapter(cardioExerciseList)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@Cardio)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ExerciseList", "Cancelled: ${error.message}")
                }
            })

        } catch (e: Exception) {
            // Obsługa błędów
            e.printStackTrace()
        }
    }

}

