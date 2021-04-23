package com.leonard.noteplus.scanner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import com.leonard.noteplus.R
import com.leonard.noteplus.scanner.Constants
import com.leonard.noteplus.scanner.data.Document
import com.leonard.noteplus.scanner.ui.fragment.CaptureImageFragment
import com.leonard.noteplus.scanner.ui.fragment.EditImageFragment
import com.leonard.noteplus.scanner.ui.viewModels.CameraViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_camera.*
import timber.log.Timber

@AndroidEntryPoint
class CameraActivity : AppCompatActivity(),
    CaptureImageFragment.CaptureImageInteractor,
    EditImageFragment.EditImageInteractor
{
    private val vm: CameraViewModel by  viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        shouldOpenFromGallery = intent?.getBooleanExtra(Constants.OPEN_FROM_GALLERY, false) ?: false


    }


    override fun onThumbClicked() {
        cameraNavHostFragment.findNavController().navigate(R.id.action_to_editImageFragment)
    }

    override fun onNavigateToCapture() {
        if (shouldOpenFromGallery){
            finish()
        }else{
            cameraNavHostFragment.findNavController().navigate(R.id.action_to_captureImageFragment)
        }
    }

    override fun onFinishFromEditImage(docList: List<com.itextpdf.text.Document>) {
        if (docList.isNotEmpty()){
            vm.deleteTempFiles(docList as ArrayList<Document>)
        }
        finish()
    }

    override fun onFinishFromCaptureImage(docList: List<Document>) {
        if (docList.isNotEmpty()){
            vm.deleteTempFiles(docList as ArrayList<Document>)
        }
        finish()
    }

    override fun onBackPressed() {
        if (cameraNavHostFragment.findNavController().currentDestination?.id == R.id.captureImageFragment){
            Timber.d("temp files: ${CaptureImageFragment.tempDocList}")
            if (CaptureImageFragment.tempDocList.isNotEmpty()){
                vm.deleteTempFiles(CaptureImageFragment.tempDocList)
            }
            finish()
        }
        else if (shouldOpenFromGallery){
            finish()
        }else
            super.onBackPressed()
    }

    companion object{
        var shouldOpenFromGallery:Boolean = false
    }
}