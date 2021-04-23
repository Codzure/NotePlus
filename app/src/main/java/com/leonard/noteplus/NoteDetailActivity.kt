package com.leonard.noteplus

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.leonard.noteplus.models.NoteModel
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.android.synthetic.main.activity_note_detail.*
import kotlinx.android.synthetic.main.activity_note_detail.iv_place_image
import kotlinx.android.synthetic.main.activity_note_detail.toolbar


class NoteDetailActivity : AppCompatActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_note_detail)

        setSupportActionBar(toolbar) // Use the toolbar to set the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // This is to use the home back button.
        imgToolbarBtnBack.setOnClickListener {
            onBackPressed()
            AcTrans.Builder(this).performSlideToRight()
        }

        var happyPlaceDetailModel: NoteModel? = null

        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            // get the Serializable data model class with the details in it
            happyPlaceDetailModel =
                intent.getSerializableExtra(MainActivity.EXTRA_PLACE_DETAILS) as NoteModel
        }

        if (happyPlaceDetailModel != null) {

            setSupportActionBar(toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = happyPlaceDetailModel.title

            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            if (iv_place_image.drawable != null) {
                iv_place_image.setImageURI(Uri.parse(happyPlaceDetailModel.image))
                iv_place_image.visibility = View.VISIBLE
            } else {
                iv_place_image.setBackgroundResource(R.drawable.add_screen_image_placeholder)
            }

            tv_description.text = happyPlaceDetailModel.description

            iv_place_image.visibility = View.VISIBLE
        }

        share.setOnClickListener {
            shareNote()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.share_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.share -> {
                shareNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareNote(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
                .putExtra(
                        Intent.EXTRA_TEXT,
                        //getString(R.string.share_text, "${noteModel.title} ${noteModel.image} ${noteModel.description}")
                "Share note details"
                )
        return shareIntent
    }

}