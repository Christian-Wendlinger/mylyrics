package com.sqcw.mylyrics.activities

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.initializeAppBar

class AddSongActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_song_layout)

        initializeAppBar(this, "Add Song", true)
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