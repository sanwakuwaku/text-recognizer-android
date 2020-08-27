package com.sanwaku2.textrecognizer.ui.camerapreview

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.otaliastudios.cameraview.CameraException
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.sanwaku2.logger.Logger
import com.sanwaku2.textrecognizer.BuildConfig
import com.sanwaku2.textrecognizer.R
import com.sanwaku2.textrecognizer.databinding.CamerapreviewFragmentBinding
import com.sanwaku2.textrecognizer.ServiceLocator
import com.sanwaku2.textrecognizer.ui.dialog.OcrResultDialogFragment

/**
 * カメラプレビュー画面
 */
class CameraPreviewFragment : Fragment(R.layout.camerapreview_fragment),
    OcrResultDialogFragment.DialogCallback {
    private lateinit var cameraView: CameraView
    private lateinit var shutterBtn: Button
    private lateinit var binding: CamerapreviewFragmentBinding
    private lateinit var gridBtn: Button

    private val viewModel: CameraPreviewViewModel by viewModels {
        ServiceLocator.provideCameraPreviewViewModelFactory(
            requireContext(),
            BuildConfig.COMPUTER_VISION_SUBSCRIPTION_KEY,
            BuildConfig.COMPUTER_VISION_ENDPOINT
        )
    }

    companion object {
        private const val REQUEST_CODE_OCR_DIALOG = 1
        fun newInstance() = CameraPreviewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.camerapreview_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shutterBtn = view.findViewById(R.id.bt_shutter)
        gridBtn = view.findViewById(R.id.bt_ocr_records)
        gridBtn.setOnClickListener {
            findNavController().navigate(R.id.action_cameraPreviewFragment_to_ocrRecordsFragment)
        }

        cameraView = view.findViewById(R.id.camera_view)
        cameraView.setLifecycleOwner(viewLifecycleOwner)

        cameraView.addCameraListener(object : CameraListener() {
            override fun onCameraOpened(options: CameraOptions) {
                Logger.d("onCameraOpened")
                viewModel.onCameraOpened()
            }

            override fun onCameraClosed() {
                Logger.d("onCameraClosed")
                viewModel.onCameraClosed()
            }

            override fun onCameraError(exception: CameraException) {
                Logger.e(exception)
                viewModel.onCameraError(exception)
            }

            override fun onPictureTaken(result: PictureResult) {
                Logger.d("onPictureTaken format=${result.format}, size=${result.size}, dataSize=${result.data.size}")

                viewModel.onCaptureCameraPreview(result.data)
            }
        })

        shutterBtn.setOnClickListener {
            cameraView.takePicture()
        }

        viewModel.ocrRecord.observe(viewLifecycleOwner) { ocrRecord ->
            ocrRecord?.let { showOcrResultDialog(it.text) }
                ?: hideOcrResultDialog()
        }

        viewModel.messageEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { content ->
                showSnackBar(content)
            }
        }
    }

    private fun showOcrResultDialog(text: String) {
        hideOcrResultDialog()
        OcrResultDialogFragment.Builder(
            target = this,
            tag = "camera_preview",
            requestCode = REQUEST_CODE_OCR_DIALOG,
            cancelable = false,
            message = text,
            positiveBtnLabel = "save",
            negativeBtnLabel = "cancel"
        ).show()
    }

    private fun hideOcrResultDialog() {
        Logger.d("hideOcrResultDialog")
        (childFragmentManager.findFragmentByTag("camera_preview") as? OcrResultDialogFragment)?.apply {
            this.dismiss()
        }
    }

    override fun onDialogButtonClicked(requestCode: Int, resultCode: Int) {
        when (requestCode) {
            REQUEST_CODE_OCR_DIALOG -> {
                if (resultCode == DialogInterface.BUTTON_POSITIVE) {
                    viewModel.saveOcrResult()
                    return
                }
                viewModel.cancelSaveOcr()
            }
        }
    }

    override fun onDialogCancelled(requestCode: Int) {
        when (requestCode) {
            REQUEST_CODE_OCR_DIALOG -> {
                viewModel.cancelSaveOcr()
            }
        }
    }

    private fun showSnackBar(stringId: Int) {
        Snackbar.make(requireView(), stringId, Snackbar.LENGTH_LONG).show()
    }
}
