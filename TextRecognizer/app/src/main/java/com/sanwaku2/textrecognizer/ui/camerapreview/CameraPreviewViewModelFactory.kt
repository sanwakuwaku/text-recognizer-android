package com.sanwaku2.textrecognizer.ui.camerapreview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sanwaku2.textrecognizer.api.IComputerVisionService
import com.sanwaku2.textrecognizer.data.OcrDataSource
import com.sanwaku2.textrecognizer.data.local.FileDao
import com.sanwaku2.textrecognizer.ui.StringResourceResolver

class CameraPreviewViewModelFactory(
    private val computerVisionService: IComputerVisionService,
    private val recognizedDataRepository: OcrDataSource,
    private val fileDao: FileDao,
    private val stringResource: StringResourceResolver
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = CameraPreviewViewModel(
        computerVisionService,
        recognizedDataRepository,
        fileDao,
        stringResource
    ) as T

}