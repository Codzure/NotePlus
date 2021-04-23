package com.leonard.noteplus.scanner.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.leonard.noteplus.R
import com.leonard.noteplus.scanner.CameraXUtility
import com.leonard.noteplus.scanner.ViewExtension.hide
import com.leonard.noteplus.scanner.ViewExtension.show
import com.leonard.noteplus.scanner.data.Document
import com.leonard.noteplus.scanner.ui.viewModels.CameraViewModel
import kotlinx.android.synthetic.main.activity_open_from_gallery.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OpenFromGalleryActivity : AppCompatActivity() {

    private val _docList = ArrayList<Document>()

    private val vm: CameraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_from_gallery)

        openGallery()

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        vm.isPdfCreating.observe(this, androidx.lifecycle.Observer {isPdfCreating ->
            if (isPdfCreating){
                linGalleryCreatingPdf.show()
            }else{
                linGalleryCreatingPdf.hide()
                Toast.makeText(this, "PDF created", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && resultCode == Activity.RESULT_OK){
            _docList.clear()
            Timber.d("imagesList data: ${data?.data} ${data?.clipData}")
            val imagesPath: List<String>? = data?.getStringExtra("data")?.split("\\|")
            Timber.d("imagesList: $imagesPath")

            if (data?.data == null && data?.clipData == null)
                finish()

            data?.data.let {
                it?.let { it1 ->
                    _docList.add(Document(CameraXUtility.getBitmapFromUri(it1,this),it1))
                }
            }

            val imagesSize = data?.clipData?.itemCount

            if (imagesSize != null) {
                for (i in 0 until imagesSize){
                    data.clipData?.getItemAt(i)?.uri?.let {
                        _docList.add(Document(CameraXUtility.getBitmapFromUri(it,this),it))
                    }
                }
            }

            if (_docList.isNotEmpty()){
                val filePath = "${CameraXUtility.getOutputDirectory(this)}/${
                    SimpleDateFormat(
                    CameraXUtility.FILENAME, Locale.US
                ).format(System.currentTimeMillis())}.pdf"
                vm.createPdf(_docList,filePath)
            }
        }else{
            finish()
        }
    }
}