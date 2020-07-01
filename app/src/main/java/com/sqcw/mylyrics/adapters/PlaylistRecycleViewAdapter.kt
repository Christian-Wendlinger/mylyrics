package com.sqcw.mylyrics.adapters

import android.app.Dialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.sqcw.mylyrics.PlaylistModel
import com.sqcw.mylyrics.R
import kotlinx.android.synthetic.main.playlist_dialog_change_layout.*
import kotlinx.android.synthetic.main.playlist_layout.view.*
import kotlinx.android.synthetic.main.song_layout.view.playlistName

class PlaylistRecycleViewAdapter(private var playlists: MutableList<PlaylistModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
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
        return playlists.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.playlistName.text = playlists[position].name
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
        customDialog.playlistNameTextField!!.setText(playlists[itemView.adapterPosition].name)
        // set char counter
        customDialog.counter!!.text =
            playlists[itemView.adapterPosition].name.length.toString() + "/25"

        // save to database

        // listeners
        customDialog.getButton(Dialog.BUTTON_NEUTRAL).setOnClickListener {
            playlists.removeAt(itemView.adapterPosition)
            notifyDataSetChanged()
            customDialog.dismiss()

            // notify user
            Toast.makeText(
                itemView.itemView.context,
                "Deleted Playlist " + itemView.itemView.playlistName.text,
                Toast.LENGTH_SHORT
            ).show()
        }

        // cancel dialog
        customDialog.getButton(Dialog.BUTTON_NEGATIVE).setOnClickListener {
            notifyDataSetChanged()
            customDialog.dismiss()
        }

        // change playlist name
        customDialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener {
            // change values
            playlists[itemView.adapterPosition].name =
                customDialog.playlistNameTextField!!.text.toString()
            itemView.itemView.playlistName.text = playlists[itemView.adapterPosition].name
            notifyDataSetChanged()
            customDialog.dismiss()

            //save to database

            // notify user
            Toast.makeText(
                itemView.itemView.context,
                "Changed Playlist to " + itemView.itemView.playlistName.text,
                Toast.LENGTH_SHORT
            ).show()
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