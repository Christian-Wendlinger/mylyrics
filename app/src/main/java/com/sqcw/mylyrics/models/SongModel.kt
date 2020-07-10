package com.sqcw.mylyrics.models

data class SongModel(
    var id: Int,
    var name: String,
    var artist: String,
    var album: String,
    var lyrics: String
)