package com.sqcw.mylyrics.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.initializeAppBar

class SongInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_information)

        initializeAppBar(this, "Song title", true)
    }
}