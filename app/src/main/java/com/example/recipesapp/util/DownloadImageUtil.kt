package com.example.recipesapp.util

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.example.recipesapp.R
import java.io.File

fun downloadImage(context: Context, url: String) {
    if (context.isConnected) {
        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(url)
        val request = DownloadManager.Request(downloadUri)
        val filename: String = String.format("%d.jpg", System.currentTimeMillis())
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, File.separator + filename)
        dm.enqueue(request)
        Toast.makeText(context, "Image download started to " + Environment.DIRECTORY_PICTURES + File.separator + filename, Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, R.string.no_internet_message, Toast.LENGTH_SHORT).show()
    }
}