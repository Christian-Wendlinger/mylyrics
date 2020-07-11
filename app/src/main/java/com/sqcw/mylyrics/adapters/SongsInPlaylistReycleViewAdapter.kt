package com.sqcw.mylyrics.adapters

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.sqcw.mylyrics.DatabaseHelper
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.activities.SongInformationActivity
import com.sqcw.mylyrics.currentPlaylistId
import com.sqcw.mylyrics.currentSongs
import com.sqcw.mylyrics.models.SongModel
import kotlinx.android.synthetic.main.playlist_layout.view.songName
import kotlinx.android.synthetic.main.song_layout.view.*

class SongsInPlaylistRecycleViewAdapter(private var songs: MutableList<SongModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SongInformationActivity::class.java)
                // put necessary values
                intent.putExtra("song_name", songs[adapterPosition].name)

                //navigate
                itemView.context.startActivity(intent)
            }

            itemView.delete.setOnClickListener {
                initializeDeleteSongDialog(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.song_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.songName.text = songs[position].name
    }

    // create delete song dialog
    private fun initializeDeleteSongDialog(itemView: ViewHolder) {
        val dialog = AlertDialog.Builder(itemView.itemView.context)
        val dialogView = LayoutInflater.from(itemView.itemView.context)
            .inflate(R.layout.delete_song_dialog_layout, null)

        dialog.setView(dialogView)
        dialog.setCancelable(false)

        //set buttons
        dialog.setPositiveButton("delete") { dialogInterface: DialogInterface, i: Int -> }
        dialog.setNegativeButton("cancel") { dialogInterface: DialogInterface, i: Int -> }
        val customDialog = dialog.create()

        customDialog.show()

        // cancel listener
        customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
            customDialog.dismiss()
        }

        // delete button
        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            // update data
            val db = DatabaseHelper(itemView.itemView.context)
            db.deleteSongFromPlaylist(
                currentPlaylistId.toString(),
                songs[itemView.adapterPosition].id.toString()
            )
            currentSongs = db.getSongsOfPlaylist(currentPlaylistId.toString())
            songs = currentSongs

            //show updates
            notifyDataSetChanged()
            customDialog.dismiss()

            // notify user
            Toast.makeText(
                itemView.itemView.context,
                "Deleted " + itemView.itemView.songName.text,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}