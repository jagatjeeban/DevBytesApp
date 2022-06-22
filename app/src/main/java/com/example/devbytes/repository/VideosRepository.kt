package com.example.devbytes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.devbytes.database.VideosDatabase
import com.example.devbytes.database.asDomainModel
import com.example.devbytes.domain.DevByteVideo
import com.example.devbytes.network.DevByteNetwork
import com.example.devbytes.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Repository for fetching devbyte videos from the network and storing them on disk
 */
class VideosRepository(private val database: VideosDatabase){

    /**
     * Refresh the videos stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshVideos(){
        withContext(Dispatchers.IO){
            Timber.d("refresh videos is called")

            //fetch the devbyte video playlist
            val playlist = DevByteNetwork.devbytes.getPlaylist()

            //store the playlist in room database
            database.videoDao.insertAll(playlist.asDatabaseModel())
        }
    }

    val videos: LiveData<List<DevByteVideo>> = Transformations.map(database.videoDao.getVideos()){
        it.asDomainModel()
    }
}