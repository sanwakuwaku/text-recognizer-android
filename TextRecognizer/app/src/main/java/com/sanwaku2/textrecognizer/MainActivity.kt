package com.sanwaku2.textrecognizer

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sanwaku2.logger.Logger
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            showCameraCaptureViewWithPermissionCheck()
        }

        Logger.d("onCreate")
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun showCameraCaptureView() {
    }


    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraPermissionDenied(){
        Toast.makeText(this, R.string.camera_permission_denied_message, Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
}