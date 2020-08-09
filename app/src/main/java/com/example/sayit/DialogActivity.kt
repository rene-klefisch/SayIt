package com.example.sayit

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_dialog.*

class DialogActivity : AppCompatActivity() {
    val liste = ArrayList<DataAntwort>()
    var recyclerView : RecyclerView? = null
    var id : String? = null

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)

        val intent = intent

        id = intent.getStringExtra("id")

        Log.i("Test", id)

        /*liste.add(DataAntwort("Text", "asdf"))
        liste.add(DataAntwort("Text", "asdf"))
        liste.add(DataAntwort("Text", "asdf"))
        liste.add(DataAntwort("Text", "asdf"))
        liste.add(DataAntwort("Text", "asdf"))
        liste.add(DataAntwort("Text", "asdf"))
         */
        recyclerView = findViewById(R.id.dialogRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        holeListe()
        imageButtonAntworten.setOnClickListener {
            val antwort = DataAntwort(id!!, editTextAntworten.text.toString())
            schreibeInDb(antwort)
        }

    }

    fun holeListe(){
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("antworten").orderByChild("themenId").equalTo(id)
        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    liste.clear()
                    for(i in snapshot.children){
                        val postEintrag = i.getValue(DataAntwort::class.java)
                        Log.i("Test", "Inhalt von post: ${i.key}")
                        //postEintrag!!.id = i.key!!
                        liste.add(postEintrag!!)
                    }
                    val adapter = AdapterDialog(liste)
                    recyclerView?.adapter = adapter
                }
            }

        }
        ref.addValueEventListener(listener)
    }

    fun schreibeInDb(msg: DataAntwort){
        val dataBase = FirebaseDatabase.getInstance()
        val ref = dataBase.getReference("/")
        val key = ref.child("antworten").push().key
        if (key != null) {
            ref.child("antworten").child(key).setValue(msg)
        }
    }
}