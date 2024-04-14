package com.example.gymguru

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.cardview.widget.CardView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class WyswietlanieCwiczen : ComponentActivity() {

    private lateinit var database: DatabaseReference
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.wyswietlaniecwiczen)
        database = FirebaseDatabase.getInstance().reference
        
        if(intent.hasExtra("Username")){
            username = intent.getStringExtra("Username").toString()
        }

        val klatkaLayout = findViewById<CardView>(R.id.klatka)
        val plecyLayout = findViewById<CardView>(R.id.plecy)
        val barkiLayout = findViewById<CardView>(R.id.barki)
        val tricepsLayout = findViewById<CardView>(R.id.triceps)
        val bicepsLayout = findViewById<CardView>(R.id.biceps)
        val przedramieLayout = findViewById<CardView>(R.id.przedramie)
        val posladkiLayout = findViewById<CardView>(R.id.posladki)
        val brzuchLayout = findViewById<CardView>(R.id.brzuch)
        val lydkiLayout = findViewById<CardView>(R.id.lydki)
        val fbwLayout = findViewById<CardView>(R.id.fbw)
        val cardioLayout = findViewById<CardView>(R.id.cardio)

        val Back = findViewById<LinearLayout>(R.id.back)

        klatkaLayout.setOnClickListener {
            val intent = Intent(this, KlatkaP::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        plecyLayout.setOnClickListener {
            val intent = Intent(this, Plecy::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        barkiLayout.setOnClickListener {
            val intent = Intent(this, Barki::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        tricepsLayout.setOnClickListener {
            val intent = Intent(this, Triceps::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        bicepsLayout.setOnClickListener {
            val intent = Intent(this, Biceps::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        przedramieLayout.setOnClickListener {
            val intent = Intent(this, Przedramie::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        posladkiLayout.setOnClickListener {
            val intent = Intent(this, Posladki::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        brzuchLayout.setOnClickListener {
            val intent = Intent(this, Brzuch::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        lydkiLayout.setOnClickListener {
            val intent = Intent(this, Lydki::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        fbwLayout.setOnClickListener {
            val intent = Intent(this, Fbw::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }
        cardioLayout.setOnClickListener {
            val intent = Intent(this, Cardio::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }

        Back.setOnClickListener {
            val intent = Intent(this, AccountInfo::class.java)
            intent.putExtra("Username", username)
            startActivity(intent)
            finish()
        }

    }
}

