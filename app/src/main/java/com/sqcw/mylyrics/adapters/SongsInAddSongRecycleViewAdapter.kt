package com.sqcw.mylyrics.adapters

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sqcw.mylyrics.*
import com.sqcw.mylyrics.activities.SongInformationActivity
import com.sqcw.mylyrics.models.SongModel
import kotlinx.android.synthetic.main.add_to_dialog_layout.view.*
import kotlinx.android.synthetic.main.song_toadd_layout.view.*

class SongsInAddSongRecycleViewAdapter(private var songs: MutableList<SongModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SongInformationActivity::class.java)
                // put necessary values
                intent.putExtra("song_name", songs[adapterPosition].name)
                intent.putExtra("artist", songs[adapterPosition].artist)
                intent.putExtra("album", songs[adapterPosition].album)
                intent.putExtra("lyrics", songs[adapterPosition].lyrics)

                //navigate
                itemView.context.startActivity(intent)
            }

            itemView.add.setOnClickListener {
                currentSongId = songs[adapterPosition].id
                initializeAddToDialog(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.song_toadd_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.songName.text = songs[position].name
    }

    private fun initializeAddToDialog(itemView: ViewHolder) {
        val builder = AlertDialog.Builder(itemView.itemView.context)
        val dialogView = LayoutInflater.from(itemView.itemView.context)
            .inflate(R.layout.add_to_dialog_layout, null)

        builder.setView(dialogView)
        builder.setCancelable(false)

        dialogView.recyclerView.layoutManager = LinearLayoutManager(itemView.itemView.context)
        dialogView.recyclerView.adapter = CheckboxRecycleViewAdapter(playlists)
        dialogView.recyclerView.adapter!!.notifyDataSetChanged()

        //set buttons
        builder.setPositiveButton("apply") { dialogInterface: DialogInterface, i: Int -> }
        builder.setNegativeButton("cancel") { dialogInterface: DialogInterface, i: Int -> }


        val customDialog = builder.create()
        customDialog.show()

        // cancel dialog
        customDialog.getButton(Dialog.BUTTON_NEGATIVE).setOnClickListener {
            addToPlaylists.clear()
            customDialog.dismiss()
        }

        // add to selected playlists
        customDialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
            val db = DatabaseHelper(itemView.itemView.context)

            // change values
            for (id in addToPlaylists) {
                db.addSongToPlaylist(id, currentSongId)
            }
            addToPlaylists.clear()

            customDialog.dismiss()
        }
    }
}