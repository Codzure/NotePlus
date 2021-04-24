package com.leonard.noteplus.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast

fun Activity.CustomSnackBar(ctx: Context, textMsg: String){
    val layout: View = layoutInflater.inflate(
        com.leonard.noteplus.R.layout.custom_snack_bar, null)
    val text = layout.findViewById<TextView>(com.leonard.noteplus.R.id.text)
    text.text = textMsg
    val toast = Toast(ctx)
    toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 250)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = layout
    toast.show()
}