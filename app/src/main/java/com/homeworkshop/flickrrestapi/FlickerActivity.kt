package com.homeworkshop.flickrrestapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flicker)

        //Wszystkie pobrania z internetu muszą być zrobione w wątkach - coroutines

        val job = CoroutineScope(Dispatchers.IO).launch {
//            Log.d("HTTP_JSON", FlickrFetcher().getJSONString())
//            Log.d("HTTP_SITE", FlickrFetcher().getUrlString("https://www.bignerdranch.com") )
            val gson = Gson()
            val jsonString = FlickrFetcher().getJSONString()
//            val obiekt = gson.fromJson(jsonString, JSONPhotos::class.java)
//            val arrayPhotos = obiekt.photos.photo
//            Log.d("HTTP_JSON", arrayPhotos.get(0).toString())
//            Log.d("JSON_SIZE", arrayPhotos.size.toString())

            val jsonBody = JsonParser.parseString(jsonString).asJsonObject
            val jsonPhotoMain = jsonBody.get("photos").asJsonObject
            val jsonPhotosArray = jsonPhotoMain.get("photo").asJsonArray
            val listOfPhotos = gson.fromJson(jsonPhotosArray,ArrayList::class.java)

            Log.d("photo array", listOfPhotos.size.toString())
            Log.d("One photo", listOfPhotos.get(0).toString())
        }

        job.start()
        if(job.isCompleted){
            job.cancel()
        }
    }
}
