package com.example.firebasedevgf

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.firebasedevgf.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataBase = Firebase.database.reference

        val listener = object : ValueEventListener{
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(String::class.java)
                binding.nombre.text = "Firebase Modo remoto: $data"
            }

            override fun onCancelled(error: DatabaseError) {}

        }

        val dataRef = dataBase.child("saludo_firebase").child("data")

        dataRef.addValueEventListener(listener)

        binding.btnEnviar.setOnClickListener {
            dataRef.setValue(binding.tvEnviarAlgo.editText!!.text.toString())
        }

        binding.btnEnviar.setOnLongClickListener {
            dataRef.removeValue()
            true
        }

    }
}