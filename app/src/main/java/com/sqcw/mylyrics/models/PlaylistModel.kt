package com.sqcw.mylyrics.models

data class PlaylistModel(var id: Int, var name: String, var songs: MutableList<SongModel>)