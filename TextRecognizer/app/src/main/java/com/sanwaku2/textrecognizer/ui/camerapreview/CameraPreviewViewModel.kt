package com.sanwaku2.textrecognizer.ui.camerapreview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.azure.cognitiveservices.vision.computervision.models.OcrResult
import com.sanwaku2.logger.Logger
import com.sanwaku2.textrecognizer.api.IComputerVisionService
import com.sanwaku2.textrecognizer.data.OcrDataSource
import com.sanwaku2.textrecognizer.data.OcrRecord
import com.sanwaku2.textrecognizer.data.local.FileDao
import com.sanwaku2.textrecognizer.ui.Event
import com.sanwaku2.textrecognizer.ui.StringResourceResolver
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class CameraPreviewViewModel(
    private val computerVisionService: IComputerVisionService,
    private val ocrRepository: OcrDataSource,
    private val fileDao: FileDao,
    private val stringResource: StringResourceResolver
) : ViewModel() {

    val status = MutableLiveData<Status>(Status.CAMERA_NOT_OPEN)
    val messageEvent = MutableLiveData<Event<Int>>()
    val ocrRecord: MutableLiveData<OcrRecord?> by lazy { MutableLiveData<OcrRecord?>(null) }
    private var captureImageByteArray: ByteArray = ByteArray(0)

    enum class Status {
        CAMERA_NOT_OPEN,
        READY,
        OCR_IN_PROGRESS,
        OCR_SUCCESS,
        SAVE_OCR_IN_PROGRESS
    }

    fun onCaptureCameraPreview(imgByteArray: ByteArray) {
        status.value = Status.OCR_IN_PROGRESS

        captureImageByteArray = imgByteArray

        viewModelScope.launch {
            val ocrResult: OcrResult = try {
                computerVisionService.recognizeTextOcr(imgByteArray)
            } catch (e: Exception) {
                Logger.e(e)
                if (e.cause is UnknownHostException) {
                    messageEvent.value =
                        Event(stringResource.getStringResourceIdByName("err_msg_cv_unknown_host_error"))
                } else {
                    messageEvent.value =
                        Event(stringResource.getStringResourceIdByName("err_msg_cv_error"))
                }
                status.value = Status.READY

                return@launch
            }

            val text = makeOcrText(ocrResult)

            if (text.isEmpty()) {
                messageEvent.value =
                    Event(stringResource.getStringResourceIdByName("err_msg_ocr_result_empty"))
                status.value = Status.READY

                return@launch
            }

            status.value = Status.OCR_SUCCESS
            ocrRecord.postValue(
                OcrRecord(
                    language = ocrResult.language(),
                    text = text
                )
            )
        }
    }

    private fun makeOcrText(ocrResult: OcrResult): String {
        var text = ""
        ocrResult.regions().forEach { region ->
            var regionText = ""
            region.lines().forEach { line ->
                var lineText = ""
                line.words().forEach { word ->
                    lineText += "${word.text()} "
                }
                Logger.d("recognize text line=$lineText")
                regionText += "${lineText}\n"
            }
            regionText += "\n"

            text += regionText
        }
        return text
    }

    fun saveOcrResult() {
        status.value = Status.SAVE_OCR_IN_PROGRESS

        val record = ocrRecord.value
        ocrRecord.value = null

        Logger.d("saveOcrResult")
        if (record != null) {
            viewModelScope.launch {
                val fileName = "${record.id}.jpg"
                val filePath = fileDao.saveFile(fileName, captureImageByteArray)
                captureImageByteArray = ByteArray(0)

                Logger.d("save image filePath=${filePath}")

                if (filePath.isNotEmpty()) {
                    record.imagePath = filePath
                    ocrRepository.saveOcrRecord(record)
                    Logger.d("save done.")
                } else {
                    // TODO : error
                }

                status.value = Status.READY
            }
        } else {
            status.value = Status.READY
        }
    }

    fun cancelSaveOcr() {
        ocrRecord.value = null
        status.value = Status.READY
    }

    fun onCameraOpened() {
        status.value = Status.READY
    }

    fun onCameraClosed() {
        status.value = Status.CAMERA_NOT_OPEN
    }

    fun onCameraError(exception: Exception) {
        status.value = Status.READY
        messageEvent.value =
            Event(stringResource.getStringResourceIdByName("err_msg_camera"))
    }
}