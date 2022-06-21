package com.example.devbytes.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.devbytes.domain.DevByteVideo

/**
 * DatabaseVideo represents a video entity in the database.
 */
@Entity
data class DatabaseVideo constructor(
    @PrimaryKey
    val url: String,
    val updated: String,
    val title: String,
    val description: String,
    val thumbnail: String
)

/**
 * Map DatabaseVideos to domain entities
 */
fun List<DatabaseVideo>.asDomainModel(): List<DevByteVideo>{
    return map {
        DevByteVideo(
            url = it.url,
            title = it.title,
            updated = it.updated,
            description = it.description,
            thumbnail = it.thumbnail
        )
    }
}