package com.example.sayit

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterDialog(val elemente : ArrayList<DataAntwort>) : RecyclerView.Adapter<HolderDialog>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDialog {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.themen_layout, parent, false)

        return HolderDialog(mView)
    }

    override fun getItemCount(): Int {
        return elemente.size
    }

    override fun onBindViewHolder(holder: HolderDialog, position: Int) {
        holder.verbinden(elemente.get(position))
    }
}

class HolderDialog(itemView : View) : RecyclerView.ViewHolder(itemView){

    fun verbinden(element : DataAntwort){
        val content : TextView = itemView.findViewById(R.id.textViewContent)
        content.text = element.content
        content.setTextColor(Color.rgb(0, 0, 0))
        content.setBackgroundColor(Color.rgb(getRandom(), getRandom(), getRandom()))
        itemView.setOnClickListener {
            Log.i("Test", "Geklickt wurde ${element.content}")
        }
    }

    fun getRandom (): Int {
        val random = Math.random() * 255
        val randomInt = random.toInt()
        return randomInt
    }

}