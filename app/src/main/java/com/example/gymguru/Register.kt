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

data class AccountAdd(
    val id: String = "",
    val Login: String = "",
    val Password: String = "",
    val Email: String = "",
)

class Register : ComponentActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        database = Firebase.database.reference


        val zarejestruj = findViewById<Button>(R.id.Register)
        val powrot = findViewById<Button>(R.id.Powrot)
        val context: Context = this
        powrot.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        zarejestruj.setOnClickListener {
            val email = findViewById<EditText>(R.id.Email).text.toString()
            val login = findViewById<EditText>(R.id.Login).text.toString()
            val password = findViewById<EditText>(R.id.Haslo).text.toString()


            if(email.isNotBlank() && login.isNotBlank() && password.isNotBlank()){
                database.child("AccountCounter").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (isEmailAddress(email)) {
                            val AccountCounter = dataSnapshot.getValue(Long::class.java) ?: 0
                            val newAccountId = (AccountCounter + 1).toString()
                            val newAccount = AccountAdd(newAccountId, login, password,email)

                            database.child("AccountCounter").setValue(AccountCounter + 1)
                            database.child("account").child(newAccountId).setValue(newAccount)

                            showToast(context,"Pomyślnie zarejestrowano")
                        } else {
                            showToast(context, "Źle wpisano e-mail")
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }else{
                showToast(this,"Proszę usupełnić wszystkie pola")
            }
            val emailEditText = findViewById<EditText>(R.id.Email)
            val loginEditText = findViewById<EditText>(R.id.Login)
            val hasloEditText = findViewById<EditText>(R.id.Haslo)
            emailEditText.text.clear()
            loginEditText.text.clear()
            hasloEditText.text.clear()
        }
    }
    fun isEmailAddress(email: String): Boolean {
        val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
        return emailRegex.matches(email)
    }


    fun showToast(context: Context, message: String) {
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }
}

