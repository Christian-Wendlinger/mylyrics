package com.sqcw.mylyrics.activities

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqcw.mylyrics.*
import com.sqcw.mylyrics.adapters.SongsInAddSongRecycleViewAdapter
import kotlinx.android.synthetic.main.activity_add_song_layout.*
import kotlinx.android.synthetic.main.filter_dialog_layout.*

class AddSongActivity : AppCompatActivity() {

    private val db = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_song_layout)

        initializeAppBar(this, "Add Song", true)

        // search on text change
        searchText.doOnTextChanged { text, start, count, after ->
            query = text.toString()
            queriedSongs = db.fetchSongs()
            rvSongs.adapter = SongsInAddSongRecycleViewAdapter(queriedSongs)
        }

        // initialize the recyclerview
        rvSongs.layoutManager = LinearLayoutManager(this)
        queriedSongs = db.fetchSongs()
        rvSongs.adapter = SongsInAddSongRecycleViewAdapter(queriedSongs)

        //open filter
        filterIcon.setOnClickListener {
            initializeFilterDialog()
        }
    }

    // back button functionality
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initializeFilterDialog() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.filter_dialog_layout, null)

        dialog.setView(dialogView)
        dialog.setCancelable(false)

        //set buttons
        dialog.setPositiveButton("apply") { dialogInterface: DialogInterface, i: Int -> }
        dialog.setNegativeButton("cancel") { dialogInterface: DialogInterface, i: Int -> }
        dialog.setNeutralButton("reset") { dialogInterface: DialogInterface, i: Int -> }
        val customDialog = dialog.create()

        customDialog.show()

        // set current values
        customDialog.artistInput.setText(artist)
        customDialog.albumInput.setText(album)


        // apply
        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            //set new values
            artist = customDialog.artistInput.text.toString()
            album = customDialog.albumInput.text.toString()

            //query songs
            queriedSongs = db.fetchSongs()
            rvSongs.adapter = SongsInAddSongRecycleViewAdapter(queriedSongs)

            customDialog.dismiss()
        }

        // cancel
        customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
            customDialog.dismiss()
        }

        // reset
        customDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {
            // reset values
            artist = ""
            album = ""

            // query songs
            queriedSongs = db.fetchSongs()
            rvSongs.adapter = SongsInAddSongRecycleViewAdapter(queriedSongs)

            customDialog.dismiss()
        }
    }
}