package com.sqcw.mylyrics.adapters

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.sqcw.mylyrics.DatabaseHelper
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.activities.SongsInPlaylistActivity
import com.sqcw.mylyrics.models.PlaylistModel
import com.sqcw.mylyrics.playlists
import kotlinx.android.synthetic.main.playlist_dialog_change_layout.*
import kotlinx.android.synthetic.main.playlist_layout.view.*
import kotlinx.android.synthetic.main.song_layout.view.songName

class PlaylistRecycleViewAdapter(private var playlistsInternal: MutableList<PlaylistModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SongsInPlaylistActivity::class.java)
                // put necessary values
                intent.putExtra("playlist_id", playlistsInternal[adapterPosition].id)
                intent.putExtra("playlist_name", playlistsInternal[adapterPosition].name)

                //navigate
                itemView.context.startActivity(intent)
            }

            itemView.options.setOnClickListener {
                initializeChangePlaylistDialog(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.playlist_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return playlistsInternal.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.songName.text = playlistsInternal[position].name
    }


    // create the Code for the dialog to add a playlist
    private fun initializeChangePlaylistDialog(itemView: ViewHolder) {
        val dialog = AlertDialog.Builder(itemView.itemView.context)
        val dialogView = LayoutInflater.from(itemView.itemView.context)
            .inflate(R.layout.playlist_dialog_change_layout, null)

        dialog.setView(dialogView)
        dialog.setCancelable(false)

        //set buttons
        dialog.setPositiveButton("change") { dialogInterface: DialogInterface, i: Int -> }
        dialog.setNegativeButton("cancel") { dialogInterface: DialogInterface, i: Int -> }
        dialog.setNeutralButton("delete") { dialogInterface: DialogInterface, i: Int -> }
        val customDialog = dialog.create()

        customDialog.show()

        // set Playlist name in Textfield
        customDialog.playlistNameTextField!!.setText(playlistsInternal[itemView.adapterPosition].name)
        // set char counter
        customDialog.counter!!.text =
            playlistsInternal[itemView.adapterPosition].name.length.toString() + "/25"

        // listeners
        //delete playlist
        customDialog.getButton(Dialog.BUTTON_NEUTRAL).setOnClickListener {
            // delete in database
            val db = DatabaseHelper(itemView.itemView.context)
            db.deletePlaylist(playlistsInternal[itemView.adapterPosition].id.toString())

            // notify user
            Toast.makeText(
                itemView.itemView.context,
                "Deleted Playlist " + itemView.itemView.songName.text,
                Toast.LENGTH_SHORT
            ).show()

            playlists = db.readPlaylists()
            playlistsInternal = playlists
            notifyDataSetChanged()
            customDialog.dismiss()
        }

        // cancel dialog
        customDialog.getButton(Dialog.BUTTON_NEGATIVE).setOnClickListener {
            customDialog.dismiss()
        }

        // change playlist name
        customDialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
            // save to database
            val db = DatabaseHelper(itemView.itemView.context)
            db.updatePlaylistName(
                playlistsInternal[itemView.adapterPosition].id.toString(),
                customDialog.playlistNameTextField!!.text.toString()
            )

            // notify user
            Toast.makeText(
                itemView.itemView.context,
                "Changed Playlist to " + customDialog.playlistNameTextField!!.text.toString(),
                Toast.LENGTH_SHORT
            ).show()

            customDialog.dismiss()

            // read new data
            playlists = db.readPlaylists()
            playlistsInternal = playlists
            notifyDataSetChanged()
        }

        // parse Input length
        customDialog.playlistNameTextField.doOnTextChanged { text, start, count, after ->
            if (text!!.length <= 25) customDialog.counter.text = text.length.toString() + "/25"
            else {
                // cut Text
                customDialog.playlistNameTextField.setText(
                    text.toString().subSequence(0, 25).toString()
                )
                //position cursor
                customDialog.playlistNameTextField.setSelection(25)
            }
        }
    }
}