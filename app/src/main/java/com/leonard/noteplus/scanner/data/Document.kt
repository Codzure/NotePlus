package com.leonard.noteplus.scanner.data

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Document(
    val bitmap: Bitmap?,
    val photoUri: Uri?
): Parcelable