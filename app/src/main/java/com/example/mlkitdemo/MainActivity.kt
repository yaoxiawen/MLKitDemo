package com.example.mlkitdemo

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.mlkit.vision.barcode.common.Barcode
import com.king.camera.scan.analyze.Analyzer
import com.king.camera.scan.util.LogUtils
import com.king.mlkit.vision.barcode.BarcodeDecoder
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity() {
    private val openGalleryRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                it.data?.data?.let { uri -> processPhoto(uri) }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.bt).setOnClickListener {
            startActivity(Intent(this, BarcodeScanningActivity::class.java))
        }
        findViewById<Button>(R.id.bt2).setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        openGalleryRequest.launch(Intent.createChooser(intent, "11111"))
    }

    private fun processPhoto(data: Uri?) {
        data?.let {
            try {
                val srcBitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                BarcodeDecoder.process(srcBitmap, object : Analyzer.OnAnalyzeListener<List<Barcode>?> {
                    override fun onSuccess(result: List<Barcode>) {
                        if (result.isNotEmpty()) {
                            Toast.makeText(this@MainActivity, "result:${result[0].displayValue}", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "result is null", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(e: Exception?) {
                        Toast.makeText(this@MainActivity, "onFailure", Toast.LENGTH_SHORT).show()
                    }

                    // 如果指定具体的识别条码类型，速度会更快
                }, Barcode.FORMAT_ALL_FORMATS)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
            }

        }
    }
}