package com.sqcw.mylyrics.models

import java.io.Serializable

data class SongModel(var id: Int, var name: String, var data: SongInformationModel) : Serializable