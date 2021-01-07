package com.leonard.noteplus.models

import java.io.Serializable

/**
 * A Data Model Class for Happy Place details. We will you this data class in all over the project even when
 * dealing with local SQLite database.
 */
data class NoteModel(
    val id: Int,
    val title: String,
    val image: String,
    val description: String,
    val date: String,
    val location: String,
    val latitude: Double,
    val longitude: Double
) : Serializable