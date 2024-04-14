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
import com.google.firebase.database.database
import android.content.Context
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Toast

data class Uzytkownik(
    val id: String = "",
    val email: String = "",
    val login: String = "",
    val password: String = ""
)

class Login : ComponentActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        database = Firebase.database.reference

        val rejestracja = findViewById<Button>(R.id.Zarejestruj)
        val zaloguj = findViewById<Button>(R.id.Zaloguj)
        val context: Context = this

        rejestracja.setOnClickListener {
            val intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
        }

        zaloguj.setOnClickListener {
            logguj()
        }


    }

    private fun logguj() {
        val username = findViewById<EditText>(R.id.Login).text.toString()
        val password = findViewById<EditText>(R.id.Haslo).text.toString()

        val accRef = database.child("account")

        var findAccount = false
        var blankData = false

        if (username.isBlank() || password.isBlank()) {
            blankData = true
        }

        accRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (childSnapshot in snapshot.children) {
                    val user = childSnapshot.getValue(Uzytkownik::class.java)

                    if (user != null && user.password.equals(password) && (user.login.equals(
                            username
                        ) || user.email.equals(username))
                    ) {
                        findAccount = true
                        glowny(user.login)
                    }
                }

                if (blankData) {
                    showToast(this@Login, "Proszę o wprowadzanie danych")
                } else if (!findAccount && !blankData) {
                    showToast(this@Login, "Takie konto nie istnieje lub wprowadzono błedne dane")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


            fun showToast(context: Context, message: String) {
                Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
            }
            private fun glowny(username: String) {
                if (!isFinishing) {
                    val intent = Intent(this@Login, AccountInfo::class.java)
                    intent.putExtra("Username", username)
                    startActivity(intent)
                    finish()
                }
            }


        })
    }
}