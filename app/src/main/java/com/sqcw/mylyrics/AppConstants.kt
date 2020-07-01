package com.sqcw.mylyrics

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.appbar_layout.*

fun initializeAppBar(appCompatActivity: AppCompatActivity, title: String, back: Boolean) {
    //set up the action bar
    appCompatActivity.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
    appCompatActivity.supportActionBar!!.setCustomView(R.layout.appbar_layout)

    // set title
    appCompatActivity.tvTitle!!.text = title

    // enable back Button
    if (back) appCompatActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

}