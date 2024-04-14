package com.example.gymguru

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class AccountInfo: ComponentActivity() {

    private lateinit var EmailTextView: TextView
    private lateinit var passwordTextview: TextView
    private lateinit var database: DatabaseReference
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_info)
        database = FirebaseDatabase.getInstance().reference
        EmailTextView = findViewById(R.id.emailTextview)
        passwordTextview = findViewById(R.id.passwordTextview)

        val addButton = findViewById<Button>(R.id.addExcerciseButton)
        val excerciseButton = findViewById<Button>(R.id.excercisesButton)
        val accinfoButton = findViewById<Button>(R.id.accInfoButton)

        val pokazEmail = findViewById<Button>(R.id.showEmailButton)
        val pokazHaslo = findViewById<Button>(R.id.showPasswordButton)
        val logOut = findViewById<Button>(R.id.logoutButton)
        val usernameText = findViewById<TextView>(R.id.textViewUsername)

        if(intent.hasExtra("Username")){
            username = intent.getStringExtra("Username").toString()
        }

        usernameText.text = "Witaj $username"

        logOut.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            intent.removeExtra("Username")
            startActivity(intent)
            finish()
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

        pokazEmail.setOnClickListener {
            pokazEmailzBazy()
        }

        pokazHaslo.setOnClickListener{
            pokazHaslozBazy()
        }
    }

    private fun pokazEmailzBazy() {
        val accRef = database.child("account")

        accRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val user = childSnapshot.getValue(Uzytkownik::class.java)
                    if (user != null && (user.login.equals(username) || user.email.equals(username))) {
                        val emailFromDatabase = user.email
                        EmailTextView.text = "Email: $emailFromDatabase"
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun pokazHaslozBazy() {
        val accRef = database.child("account")

        accRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    val user = childSnapshot.getValue(Uzytkownik::class.java)
                    if (user != null && (user.login.equals(username) || user.password.equals(username))) {
                        val hasloFromDatabase = user.password
                        passwordTextview.text = "Hasło: $hasloFromDatabase"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Obsługa błędów odczytu z bazy danych
            }
        })
    }

}
