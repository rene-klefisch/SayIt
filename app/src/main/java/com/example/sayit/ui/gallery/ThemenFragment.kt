package com.example.sayit.ui.gallery

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sayit.Adapter
import com.example.sayit.DataThema
import com.example.sayit.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ThemenFragment : Fragment() {

    private lateinit var themenViewModel: ThemenViewModel
    val liste = ArrayList<DataThema>()
    var recyclerView : RecyclerView? = null

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        themenViewModel =
            ViewModelProviders.of(this).get(ThemenViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_themen, container, false)
        /*liste.add(DataThema("Text", "asdf"))
        liste.add(DataThema("Text", "asdf"))
        liste.add(DataThema("Text", "asdf"))
        liste.add(DataThema("Text", "asdf"))
        liste.add(DataThema("Text", "asdf"))
        liste.add(DataThema("Text", "asdf"))
         */
        val sendButton = root.findViewById<ImageButton>(R.id.imageButtonSendThema)
        val text = root.findViewById<EditText>(R.id.editTextThema)
        sendButton.setOnClickListener{
            val nachricht = DataThema(text.text.toString(), "")
            schreibeInDb(nachricht)
        }
        recyclerView = root!!.findViewById(R.id.recyclerViewThemen)
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        holeListe()
        //schreibeInDb()

        return root
    }

    fun schreibeInDb(msg: DataThema){
        val dataBase = FirebaseDatabase.getInstance()
        val ref = dataBase.getReference("/")
        val key = ref.child("post").push().key
        if (key != null) {
            ref.child("post").child(key).setValue(msg)
        }
        /*ref.setValue(liste.get(3))
        liste.forEach{
            val key = ref.child("post").push().key
            ref.child("post").child(key!!).setValue(it)
        }
         */
    }

    fun holeListe(){
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("post")
        val listener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    liste.clear()
                    for(i in snapshot.children){
                        val postEintrag = i.getValue(DataThema::class.java)
                        Log.i("Test", "Inhalt von post: ${i.key}")
                        postEintrag!!.id = i.key!!
                        liste.add(postEintrag!!)
                    }
                    val adapter = Adapter(liste, context!!)
                    recyclerView?.adapter = adapter
                }
            }

        }
        ref.addValueEventListener(listener)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}