package com.sqcw.mylyrics.activities

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.initializeAppBar

class SongsInPlaylistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_in_playlist)

        initializeAppBar(this, "Playlist name", true)
    }

    private fun initializeDeleteSongDialog() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.delete_song_dialog_layout, null)

        dialog.setView(dialogView)
        dialog.setCancelable(false)

        //set buttons
        dialog.setPositiveButton("delete") { dialogInterface: DialogInterface, i: Int -> }
        dialog.setNegativeButton("cancel") { dialogInterface: DialogInterface, i: Int -> }
        val customDialog = dialog.create()

        customDialog.show()
    }
}