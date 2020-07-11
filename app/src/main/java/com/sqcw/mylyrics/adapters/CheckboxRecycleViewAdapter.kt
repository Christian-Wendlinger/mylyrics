package com.sqcw.mylyrics.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.addToPlaylists
import com.sqcw.mylyrics.models.PlaylistModel
import kotlinx.android.synthetic.main.add_to_item_layout.view.*

class CheckboxRecycleViewAdapter(private var playlist: MutableList<PlaylistModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.checkBox.setOnClickListener {
                // set ids of playlists to add
                if (!itemView.checkBox.isChecked) {
                    addToPlaylists.remove(playlist[adapterPosition].id)
                } else {
                    addToPlaylists.add(playlist[adapterPosition].id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_to_item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return playlist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.checkBox.text = playlist[position].name
    }
}