package com.sqcw.mylyrics.activities

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.adapters.SongsInAddSongRecyvleViewAdapter
import com.sqcw.mylyrics.initializeAppBar
import com.sqcw.mylyrics.models.SongModel
import kotlinx.android.synthetic.main.activity_add_song_layout.*


class AddSongActivity : AppCompatActivity() {
    private var names = mutableListOf(
        SongModel(1, "Song 1", "", "", ""),
        SongModel(2, "Song 2", "", "", "")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_song_layout)

        initializeAppBar(this, "Add Song", true)

        // click search
        searchIcon.setOnClickListener {

            var foundnames = mutableListOf<SongModel>()

            val iterator = names.listIterator()
            for (item in iterator) {
                // TODO vergleich mit Datenbank
                if (item.name == searchText.text.toString()) {
                    foundnames.add(item)
                }
            }
            rvSongs.layoutManager = LinearLayoutManager(this)
            rvSongs.adapter = SongsInAddSongRecyvleViewAdapter(foundnames)
            rvSongs.adapter!!.notifyDataSetChanged()
        }

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

        // apply
        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            customDialog.dismiss()
        }

        // cancel
        customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
            customDialog.dismiss()
        }

        // reset
        customDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {
            customDialog.dismiss()
        }
    }

    private fun initializeAddToDialog() {
        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.add_to_dialog_layout, null)

        dialog.setView(dialogView)
        dialog.setCancelable(false)

        //set buttons
        dialog.setPositiveButton("apply") { dialogInterface: DialogInterface, i: Int -> }
        dialog.setNegativeButton("cancel") { dialogInterface: DialogInterface, i: Int -> }
        val customDialog = dialog.create()

        customDialog.show()
    }
}