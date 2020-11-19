package com.homeworkshop.flickrrestapi

import android.net.Uri
import android.util.Log
import org.json.JSONException
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Klasa odpowiedzialna za pobieranie z Flickra
 */
class FlickrFetcher {

    companion object {
        private val API_KEY: String = BuildConfig.FlickApiKey
    }

    fun getUrlBytes(urlSpec: String): ByteArray {
        val url = URL(urlSpec)
        val connection = url.openConnection() as HttpsURLConnection

        try {
            val out = ByteArrayOutputStream()
            val input = connection.inputStream

            if (connection.responseCode != HttpsURLConnection.HTTP_OK) {
                throw IOException(connection.responseMessage)
            }

            var bytesRead: Int
            val buffer = ByteArray(4096)

            //To działa ale nie zawsze, na końcowego bytesRead = -1 wywala się
            // Poza tym czyta pewnie podwójnie z inputa i nie zapisuje jednej lini
//            while ((input.read(buffer)) > 0) {
//                bytesRead = input.read(buffer)
//                out.write(buffer, 0, bytesRead)
//            }

            do {
                bytesRead = input.read(buffer)
                if(bytesRead<0){
                    break
                }
                out.write(buffer, 0, bytesRead)

            } while (bytesRead > 0)

            //To też czasami nie działa - outOfBound
//            input.use { input ->
//                out.use { output ->
//                    input.copyTo(output)
//                }
//            }
            out.close()
            return out.toByteArray()

        } catch (e: IOException) {
            Log.e("ERROR_HTTP_CONNECTION", e.message ?: "")
            return ByteArray(0)
        } finally {
            connection.disconnect()
        }
    }

    /**
     * Metoda tworzy Stringa z tablicy bytów
     */
    fun getUrlString(urlSpec: String): String {
        return String(getUrlBytes(urlSpec))
    }

    fun getJSONString(): String {
        var jsonString = "COS POSZLO NIE TAK"

        //https://www.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=5000000014&format=json&extras=urls_s&nojsoncallback=1
        try {
            val url: String = Uri.parse("https://www.flickr.com/services/rest/")
                .buildUpon()
                .appendQueryParameter("method", "flickr.photos.getRecent")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .appendQueryParameter("extras", "url_s")
                .build().toString()

            jsonString = getUrlString(url)

        } catch (je: JSONException) {
            Log.e("JSON_ERROR", je.message ?: "")
        }
        return jsonString
    }
}