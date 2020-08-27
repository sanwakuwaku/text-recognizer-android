package com.sanwaku2.textrecognizer.ui.ocrrecords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sanwaku2.textrecognizer.data.OcrDataSource

class OcrRecordsViewModelFactory(private val ocrRepository: OcrDataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        OcrRecordsViewModel(ocrRepository) as T
}