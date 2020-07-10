package com.sqcw.mylyrics.activities

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqcw.mylyrics.DatabaseHelper
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.adapters.PlaylistRecycleViewAdapter
import com.sqcw.mylyrics.initializeAppBar
import com.sqcw.mylyrics.playlists
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.playlist_dialog_change_layout.*

class MainActivity : AppCompatActivity() {
    private val db = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeAppBar(this, "Playlists", false)

        // add Playlist button functionality
        btnAddSong.setOnClickListener {
            initializeCreatePlaylistDialog()
        }

        // setup playlists
        rvSongsInPlaylist.layoutManager = LinearLayoutManager(this)
        playlists = db.readPlaylists()
        rvSongsInPlaylist.adapter = PlaylistRecycleViewAdapter(playlists)
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

            // write new Playlist to database and read new playlists
            db.addPlaylist(customDialog.playlistNameTextField.text.toString())
            playlists = db.readPlaylists()

            customDialog.dismiss()
            Toast.makeText(
                this,
                "Created Playlist " + playlists[playlists.size - 1].name,
                Toast.LENGTH_SHORT
            )
                .show()

            rvSongsInPlaylist.adapter = PlaylistRecycleViewAdapter(playlists)
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