package com.leonard.noteplus.scanner.ui.viewModels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itextpdf.text.*
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfWriter
import com.leonard.noteplus.scanner.ui.adapters.EditImageAdapter
import com.leonard.noteplus.scanner.ui.fragment.EditImageFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class CameraViewModel : ViewModel(){

    private val _docList = MutableLiveData<List<Document>>()
    private val _pdfCreating = MutableLiveData<Boolean>()

    val docList: LiveData<List<Document>>
        get() = _docList

    val isPdfCreating: LiveData<Boolean>
        get() = _pdfCreating

    fun createPdf(docList: ArrayList<com.leonard.noteplus.scanner.data.Document>, filePath: String) = viewModelScope.launch {
        _pdfCreating.postValue(true)
        createPdfInBackground(docList,filePath)
    }

    fun deleteTempFiles(docList: ArrayList<com.leonard.noteplus.scanner.data.Document>) = viewModelScope.launch {
        deleteTempFilesInBackground(docList)
    }

    private suspend fun deleteTempFilesInBackground(docList: List<Document>) = withContext(
        Dispatchers.IO){
        docList.forEach {
            val file = File(it.photoUri?.path)
            file.delete()
        }
    }


    private suspend fun createPdfInBackground(docs:List<Document>, filePath:String) = withContext(
        Dispatchers.IO){

        val document = Document()
        val writer = PdfWriter.getInstance(document, FileOutputStream(filePath))

        document.open()

        docs.forEachIndexed { index, doc ->
            val quality = 30
            val bmp: Bitmap = doc.bitmap!!
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray: ByteArray = stream.toByteArray()
            bmp.recycle()
            val image = Image.getInstance(byteArray)
            val qualityMod = quality * 0.09
            image.compressionLevel = qualityMod.toInt()
            image.border = Rectangle.BOX
            image.borderWidth = 1f
            image.rotate()

            val bmOptions: BitmapFactory.Options = BitmapFactory.Options()
            val bitmap = BitmapFactory.decodeFile(doc.photoUri.toString(), bmOptions)

            val pageWidth: Float =
                document.pageSize.width - (document.leftMargin() + document.rightMargin())
            val pageHeight: Float =
                document.pageSize.height - (document.bottomMargin() + document.topMargin())

            image.scaleToFit(pageWidth, pageHeight)

            image.setAbsolutePosition(
                (document.pageSize.width - image.scaledWidth) / 2,
                (document.pageSize.height - image.scaledHeight) / 2)

            ColumnText.showTextAligned(writer.directContent,
                Element.ALIGN_BOTTOM,
                Phrase(String.format("Page %d of %d", writer.pageNumber, docs.size)),
                ((document.pageSize.right + document.pageSize.left) / 2),
                document.pageSize.bottom + 25, 0f)

            document.add(image)
            document.newPage()

        }


        document.close()

        withContext(Dispatchers.Main){
            _pdfCreating.postValue(false)
        }

    }

    fun updateDocList(docList: ArrayList<com.leonard.noteplus.scanner.data.Document>){
        _docList.postValue(docList)
    }

    fun removeItemAtPosition(selectedPosition: Int) {
        val temp = _docList.value as MutableList
        if (temp.isNotEmpty()){
            temp.removeAt(selectedPosition)
            _docList.postValue(temp)
            if (EditImageAdapter.selectedPosition>0 && EditImageFragment.retakePicture==-1)
                EditImageAdapter.selectedPosition--
        }
    }

    val currentFragmentVisible = MutableLiveData<Int>()


}