package com.leonard.noteplus.scanner

import android.view.View

object ViewExtension {
    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide(){
        this.visibility = View.GONE
    }
}