package com.sqcw.mylyrics.activities

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.adapters.PlaylistRecycleViewAdapter
import com.sqcw.mylyrics.initializeAppBar
import com.sqcw.mylyrics.models.PlaylistModel
import com.sqcw.mylyrics.models.SongInformationModel
import com.sqcw.mylyrics.models.SongModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.playlist_dialog_change_layout.*

class MainActivity : AppCompatActivity() {
    private var names = mutableListOf(
        PlaylistModel(
            1,
            "Hip Hop",
            mutableListOf(
                SongModel(1, "Song 1", SongInformationModel("David Guetta", "Yeah yeah")),
                SongModel(1, "Song 2", SongInformationModel("David Bretter", "Yeah yeah 2"))
            )
        ),
        PlaylistModel(
            2,
            "Rock",
            mutableListOf(SongModel(1, "Song 1", SongInformationModel("David Guetta", "Yeah yeah")))
        ),
        PlaylistModel(3, "Pop", mutableListOf())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeAppBar(this, "Playlists", false)

        // add Playlist button functionality
        btnAddSong.setOnClickListener {
            initializeCreatePlaylistDialog()
        }

        rvSongsInPlaylist.layoutManager = LinearLayoutManager(this)
        rvSongsInPlaylist.adapter = PlaylistRecycleViewAdapter(names)
        rvSongsInPlaylist.adapter!!.notifyDataSetChanged()
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
            rvSongsInPlaylist.adapter!!.notifyDataSetChanged()
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