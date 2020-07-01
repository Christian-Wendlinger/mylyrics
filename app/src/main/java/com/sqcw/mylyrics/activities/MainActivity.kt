package com.sqcw.mylyrics.activities

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.initializeAppBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeAppBar(this, "Playlists", false)

        // add Playlist button functionality
        btnAddPlaylist.setOnClickListener {
            initializeCreatePlaylistDialog()
        }
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
    }

    // create the Code for the dialog to add a playlist
    private fun initializeChangePlaylistDialog() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.playlist_dialog_change_layout, null)

        dialog.setView(dialogView)
        dialog.setCancelable(false)

        //set buttons
        dialog.setPositiveButton("change") { dialogInterface: DialogInterface, i: Int -> }
        dialog.setNegativeButton("cancel") { dialogInterface: DialogInterface, i: Int -> }
        dialog.setNeutralButton("delete") { dialogInterface: DialogInterface, i: Int -> }
        val customDialog = dialog.create()

        customDialog.show()
    }
}