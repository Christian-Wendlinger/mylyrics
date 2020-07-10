package com.sqcw.mylyrics

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sqcw.mylyrics.models.PlaylistModel
import com.sqcw.mylyrics.models.SongModel

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "MyLyrics.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            // table containing playlists (id, name)
            "CREATE TABLE playlists (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT)"
        )

        db?.execSQL(
            "CREATE TABLE matching (" +
                    "playlistId INTEGER, " +
                    "songId INTEGER, " +
                    "PRIMARY KEY (playlistId, songId))"
        )

        db?.execSQL(
            // table containing songs (id, name, artist, album, lyrics)
            "CREATE TABLE songs (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "artist TEXT," +
                    "album TEXT," +
                    "lyrics TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS playlists")
        db?.execSQL("DROP TABLE IF EXISTS matching")
        db?.execSQL("DROP TABLE IF EXISTS songs")
        onCreate(db)
    }

    fun addPlaylist(name: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        db.insert("playlists", null, contentValues)
    }

    fun deletePlaylist(id: String) {
        val db = this.writableDatabase
        db.delete("playlists", "id = ?", arrayOf(id))
    }

    fun updatePlaylistName(id: String, name: String) {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("id", id)
        contentValues.put("name", name)
        db.update("playlists", contentValues, "id = ?", arrayOf(id))
    }

    fun readPlaylists(): MutableList<PlaylistModel> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM playlists", null)

        val tmp = mutableListOf<PlaylistModel>()

        while (cursor.moveToNext()) {
            tmp.add(PlaylistModel(cursor.getInt(0), cursor.getString(1)))
        }
        cursor.close()

        return tmp
    }

    fun addSongToPlaylist(playlistId: String, songId: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("playlistId", playlistId)
        contentValues.put("songId", songId)
        db.insert("matching", null, contentValues)
    }

    fun deleteSongFromPlaylist(playlistId: String, songId: String) {
        val db = writableDatabase
        db.delete("matching", "playlistId = ? AND songId = ?", arrayOf(playlistId, songId))
    }

    fun newSong(name: String, artist: String, album: String, lyrics: String) {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("artist", artist)
        contentValues.put("album", album)
        contentValues.put("lyrics", lyrics)

        db.insert("songs", null, contentValues)
    }

    fun getSongsOfPlaylist(playlistId: String): MutableList<SongModel> {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT r.id, r.name, r.artist, r.album, r.lyrics FROM matching l INNER JOIN songs r ON l.songId = r.id WHERE r.id = ?",
            arrayOf(playlistId)
        )

        val tmp = mutableListOf<SongModel>()

        while (cursor.moveToNext()) {
            tmp.add(
                SongModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
                )
            )
        }
        cursor.close()

        return tmp
    }
}