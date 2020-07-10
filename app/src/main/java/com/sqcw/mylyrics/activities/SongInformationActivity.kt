package com.sqcw.mylyrics.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.initializeAppBar
import com.sqcw.mylyrics.models.SongModel
import kotlinx.android.synthetic.main.activity_song_information.*

class SongInformationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_information)

        initializeAppBar(this, intent.extras!!["song_name"] as String, true)

        val data = SongModel(1, "", "", "", "")
        // change text
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (tab == tab.parent.getTabAt(0)) {
                    lyricsText.text = data.lyrics
                } else {
                    lyricsText.text = data.artist
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    // back button functionality
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}