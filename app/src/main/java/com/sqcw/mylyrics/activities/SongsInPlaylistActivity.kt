package com.sqcw.mylyrics.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.adapters.SongsInPlaylistRecycleViewAdapter
import com.sqcw.mylyrics.initializeAppBar
import com.sqcw.mylyrics.models.SongModel
import kotlinx.android.synthetic.main.activity_main.*

class SongsInPlaylistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_in_playlist)

        initializeAppBar(this, intent.extras!!["playlist_name"] as String, true)

        btnAddSong.setOnClickListener {
            val intent = Intent(this, AddSongActivity::class.java)
            startActivity(intent)
        }

        rvSongsInPlaylist.layoutManager = LinearLayoutManager(this)
        rvSongsInPlaylist.adapter =
            SongsInPlaylistRecycleViewAdapter(intent.getSerializableExtra("songs") as MutableList<SongModel>)
        rvSongsInPlaylist.adapter!!.notifyDataSetChanged()
    }

    // back button functionality
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}