package com.sqcw.mylyrics.activities

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqcw.mylyrics.PlaylistModel
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.adapters.PlaylistRecycleViewAdapter
import com.sqcw.mylyrics.initializeAppBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.playlist_dialog_change_layout.*

class MainActivity : AppCompatActivity() {
    private var names = mutableListOf(
        PlaylistModel(1, "Nummer 1", mutableListOf("")),
        PlaylistModel(2, "Nummer 2", mutableListOf("")),
        PlaylistModel(3, "Nummer 3", mutableListOf(""))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeAppBar(this, "Playlists", false)

        // add Playlist button functionality
        btnAddSong.setOnClickListener {
            initializeCreatePlaylistDialog()
        }

        rvPlaylists.layoutManager = LinearLayoutManager(this)
        rvPlaylists.adapter = PlaylistRecycleViewAdapter(names)
        rvPlaylists.adapter!!.notifyDataSetChanged()
    }

    // create the Code for the dialog to add a playlist
    private fun initializeCreatePlaylistDialog() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.playlist_dialog_layout, null)

        dialog.setView(dialogView)
        dialog.setCancelable(false)

        //set buttons
        dialog.setPositiveButton("create") { dialogInterface: DialogInterface, i: Int -> }
        dialog.setNegativeButton("cancel") { dialogInterface: DialogInterface, i: Int -> }
        val customDialog = dialog.create()

        customDialog.show()

        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            names.add(
                PlaylistModel(
                    1,
                    customDialog.playlistNameTextField.text.toString(),
                    mutableListOf()
                )
            )
            customDialog.dismiss()
            Toast.makeText(
                this,
                "Created Playlist " + names[names.size - 1].name,
                Toast.LENGTH_SHORT
            )
                .show()
            rvPlaylists.adapter!!.notifyDataSetChanged()
        }
    }
}