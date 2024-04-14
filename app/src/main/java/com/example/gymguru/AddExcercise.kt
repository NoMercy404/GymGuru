package com.example.gymguru

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import android.content.Context
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Toast

data class ExcerciseAdd(
    val id: String = "",
    val name: String = "",
    val muscleGroup: String = "",
    val description: String = "",
    val link: String = ""
)

class AddExcercise : ComponentActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var muscleGroupSpinner: Spinner
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_excercise)
        database = Firebase.database.reference

        if(intent.hasExtra("Username")){
            username = intent.getStringExtra("Username").toString()
        }

        val addButton = findViewById<Button>(R.id.addExcerciseButton1)
        val excerciseButton = findViewById<Button>(R.id.excercisesButton1)
        val accinfoButton = findViewById<Button>(R.id.accInfoButton1)

        val dodajCwiczenie = findViewById<Button>(R.id.AddExerciseButton)
        val context: Context = this
        dodajCwiczenie.setOnClickListener {
            val name = findViewById<EditText>(R.id.Name).text.toString()
            val muscleGroup = findViewById<Spinner>(R.id.spinnerMuscleGroup).selectedItem.toString()
            val description = findViewById<EditText>(R.id.excerciseDescription).text.toString()
            val link = "https://www.youtube.com/watch?v=2zsXkiMxiT4"



            if(name.isNotBlank() && description.isNotBlank() && link.isNotBlank()){
                database.child("counterExcercise").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (isYouTubeLink(link)) {
                            val ExcerciseCounter = dataSnapshot.getValue(Long::class.java) ?: 0
                            val newExcerciseId = (ExcerciseCounter + 1).toString()
                            val newExcercise = ExcerciseAdd(newExcerciseId, name, muscleGroup, description, link)

                            database.child("counterExcercise").setValue(ExcerciseCounter + 1)
                            database.child("excercise").child(newExcerciseId).setValue(newExcercise)

                            showToast(context,"Pomyślnie dodano Ćwiczenie do bazy danych")
                        } else {
                            showToast(context, "Niepoprawny Link do YouTube")
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }else{
                showToast(this,"Proszę usupełnić wszystkie pola")
            }
            val nameEditText = findViewById<EditText>(R.id.Name)
            val descriptionEditText = findViewById<EditText>(R.id.excerciseDescription)
            val linkEditText = findViewById<EditText>(R.id.excerciseLink)
            nameEditText.text.clear()
            descriptionEditText.text.clear()
            linkEditText.text.clear()

    }
        addButton.setOnClickListener {
            val intent = Intent(this, AddExcercise::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        excerciseButton.setOnClickListener {
            val intent = Intent(this, WyswietlanieCwiczen::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        accinfoButton.setOnClickListener {
            val intent = Intent(this, AccountInfo::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
}
    fun isYouTubeLink(text: String): Boolean {
        val youtubeRegex = ("(?:https?:\\/\\/)?(?:www\\.)?"
                + "(?:youtube\\.com\\/\\S*\\/|youtube\\.com\\/\\S*\\S|youtu\\.be\\/|"
                + "y2u\\.be\\/|youtube\\.com\\?\\S*\\S*v=|youtube\\.com\\/embed\\/|youtube\\.com\\/v\\/|"
                + "youtube\\.com\\/e\\/|youtube\\.com\\/\\S*\\?\\S*v=|youtube\\.com\\/"
                + "redirect\\?\\S*v=|youtube\\.com\\/\\S*\\?feature=player_embedded&v=|"
                + "youtube\\.com\\/\\S*\\?v=)([a-zA-Z0-9_-]{11})")

        val pattern = Regex(youtubeRegex)
        return pattern.matches(text)
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }
}

