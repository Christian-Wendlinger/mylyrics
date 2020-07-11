package com.sqcw.mylyrics.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqcw.mylyrics.DatabaseHelper
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.adapters.SongsInPlaylistRecycleViewAdapter
import com.sqcw.mylyrics.currentSongs
import com.sqcw.mylyrics.initializeAppBar
import kotlinx.android.synthetic.main.activity_main.*

class SongsInPlaylistActivity : AppCompatActivity() {
    private val db = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_in_playlist)

        val playlistId = intent.extras!!["playlist_name"] as String
        initializeAppBar(this, playlistId, true)

        btnAddSong.setOnClickListener {
            val intent = Intent(this, AddSongActivity::class.java)
            intent.putExtra("playlist_id", playlistId)
            startActivity(intent)
        }

        // read song data for playlists
        rvSongsInPlaylist.layoutManager = LinearLayoutManager(this)
        currentSongs = db.getSongsOfPlaylist(intent.extras!!["playlist_id"].toString())
        rvSongsInPlaylist.adapter =
            SongsInPlaylistRecycleViewAdapter(currentSongs)
    }

    override fun onResume() {
        super.onResume()
        currentSongs = db.getSongsOfPlaylist(intent.extras!!["playlist_id"].toString())
        rvSongsInPlaylist.adapter =
            SongsInPlaylistRecycleViewAdapter(currentSongs)
    }

    // back button functionality
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}