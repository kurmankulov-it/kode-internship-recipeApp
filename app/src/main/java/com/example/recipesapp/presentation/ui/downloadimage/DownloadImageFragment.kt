package com.example.recipesapp.presentation.ui.downloadimage

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.recipesapp.R
import com.example.recipesapp.databinding.ImageDownloadFragmentBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception

class DownloadImageFragment : Fragment() {

    private val args by navArgs<DownloadImageFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val binding = ImageDownloadFragmentBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(this)
                .load(args.image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                    ): Boolean {
                        binding.recipeDetailsProgressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(binding.recipeImageDownload)

        val dir = File(Environment.DIRECTORY_PICTURES)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.image_download_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_download_image -> {
                if (checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                } else {
                    saveImage()
                }
            }
        }
        return false
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (requestCode != 1)
            return
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveImage()
        }
    }

    private fun saveImage() {
        val binding = ImageDownloadFragmentBinding.bind(requireView())
        val directory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()
        val dirFile = File(directory)
        dirFile.mkdirs()
        val filename: String = String.format("%d.jpg", System.currentTimeMillis())
        val file = File(directory, filename)

        try {
            val stream: OutputStream = FileOutputStream(file)
            val bitmapDrawable: BitmapDrawable = binding.recipeImageDownload.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
            Toast.makeText(context, "Image was successfully saved" + Uri.parse(file.absolutePath), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
        }

    }
}