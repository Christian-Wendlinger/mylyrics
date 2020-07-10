package com.sqcw.mylyrics.adapters

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.sqcw.mylyrics.R
import com.sqcw.mylyrics.activities.SongInformationActivity
import com.sqcw.mylyrics.models.SongModel
import kotlinx.android.synthetic.main.song_toadd_layout.view.*

class SongsInAddSongRecyvleViewAdapter(private var songs: MutableList<SongModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SongInformationActivity::class.java)

                intent.putExtra("song_name", songs[adapterPosition].name)

                itemView.context.startActivity(intent)
            }

            itemView.add.setOnClickListener {
                initializeAddToDialog((ViewHolder(itemView)))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.song_toadd_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.songName.text = songs[position].name
    }

    private fun initializeAddToDialog(itemView: ViewHolder) {
        val builder = AlertDialog.Builder(itemView.itemView.context)
        val dialogView = LayoutInflater.from(itemView.itemView.context)
            .inflate(R.layout.add_to_dialog_layout, null)

        builder.setView(dialogView)
        builder.setCancelable(false)

        //set buttons
        builder.setPositiveButton("apply") { dialogInterface: DialogInterface, i: Int -> }
        builder.setNegativeButton("cancel") { dialogInterface: DialogInterface, i: Int -> }

        /*val playlistlist = arrayListOf<PlaylistModel>(
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

        val selectedItems = ArrayList<Int>()

        val playlistArray = arrayOfNulls<String>(playlistlist.size)

        val checkedPlaylistArray = booleanArrayOf()

        var i = 0
        val iterator = playlistlist.listIterator()
        for (item in iterator) {
            checkedPlaylistArray[i] = false
            playlistArray[i] = item.name
            i += 1
        }


        builder.setMultiChoiceItems(playlistArray, checkedPlaylistArray) { dialog, which, isChecked->
            // Update the clicked item checked status
            checkedPlaylistArray[which] = isChecked
        }*/

        val customDialog = builder.create()
        customDialog.show()

        // TODO add to every Playlist
    }
}