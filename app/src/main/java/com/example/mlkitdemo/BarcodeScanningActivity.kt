package com.example.mlkitdemo

import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.mlkit.vision.barcode.common.Barcode
import com.king.camera.scan.AnalyzeResult
import com.king.camera.scan.CameraScan
import com.king.mlkit.vision.barcode.BarcodeCameraScanActivity

/**
 * Copyright (c) 2023, Bongmi
 * All rights reserved
 * Author: yaoxiawen@lollitech.com
 */
class BarcodeScanningActivity : BarcodeCameraScanActivity() {

    override fun initCameraScan(cameraScan: CameraScan<MutableList<Barcode>>) {
        super.initCameraScan(cameraScan)
        cameraScan.setPlayBeep(true)
            .setVibrate(true)
    }

    override fun onScanResultCallback(result: AnalyzeResult<MutableList<Barcode>>) {
        cameraScan.setAnalyzeImage(false)
        Toast.makeText(this, "result:${result.result[0].displayValue}", Toast.LENGTH_SHORT).show()
    }

    override fun getLayoutId(): Int = R.layout.custom_camera_scan

    override fun getFlashlightId(): Int = View.NO_ID

}