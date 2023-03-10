package com.example.notes.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Models.Note
import com.example.notes.R
import kotlin.random.Random

class NotesAdapter(private val context : Context, val listener: NoteClickListener): RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val NotesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }

    fun updateList(newList: List<Note>){
        fullList.clear()
        fullList.addAll(newList)

        NotesList.clear()
        NotesList.addAll(fullList)
        notifyDataSetChanged()

    }

    fun filterList(search: String){
        NotesList.clear()

            for(item in fullList){
                if(item.title?.lowercase()?.contains(search.lowercase()) == true||
                        item.note?.lowercase()?.contains(search.lowercase()) == true){
                    NotesList.add(item)
                }
            }
        notifyDataSetChanged()
    }

    fun randomColor(): Int{
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NotesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true
        holder.Note_tv.text =currentNote.note
        holder.data.text = currentNote.date
        holder.data.isSelected = true
        holder.notes_layot.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(), null))

        holder.notes_layot.setOnClickListener{
            listener.onItemClicked(NotesList[holder.adapterPosition])
        }
        holder.notes_layot.setOnClickListener {
            listener.onLongItemClicked(NotesList[holder.adapterPosition], holder.notes_layot)
            true
        }
    }


    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val notes_layot = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val Note_tv = itemView.findViewById<TextView>(R.id.tv_note)
        val data = itemView.findViewById<TextView>(R.id.tv_data)
    }
    interface NoteClickListener{
        fun onItemClicked(note:Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}