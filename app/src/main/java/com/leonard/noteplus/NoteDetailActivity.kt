package com.leonard.noteplus

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.leonard.noteplus.models.NoteModel
import kotlinx.android.synthetic.main.activity_note_detail.*


class NoteDetailActivity : AppCompatActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_note_detail)

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

            if (iv_place_image.drawable !== null) {
                iv_place_image.setImageURI(Uri.parse(happyPlaceDetailModel.image))
                iv_place_image.visibility = View.VISIBLE
            } else {
                iv_place_image.visibility = View.GONE
            }

            tv_description.text = happyPlaceDetailModel.description
            iv_place_image.setImageURI(Uri.parse(happyPlaceDetailModel.image))
            iv_place_image.visibility = View.VISIBLE
        }

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.share_menu, menu)
//        return true
////        if (null == getShareIntent().resolveActivity(this.packageManager)) {
////            if (menu != null) {
////                menu.findItem(R.id.share)?.isVisible = false
////            }
////        }
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.share -> shareSuccess()
//        }
//
//        return super.onOptionsItemSelected(item)
//    }
//
//
//    @SuppressLint("StringFormatMatches")
//    private fun getShareIntent(noteModel: NoteModel): Intent {
//        //val args = WonFragmentArgs.fromBundle(requireArguments())
//        val shareIntent = Intent(Intent.ACTION_SEND)
//        shareIntent.setType("text/plain")
//            .putExtra(
//                Intent.EXTRA_TEXT,
//                getString(R.string.share_text, "${noteModel.title} ${noteModel.image} ${noteModel.description}")
//            )
//        return shareIntent
//    }
//
//    private fun shareSuccess() {
//        //startActivity(getShareIntent(NoteModel())
//    }

}