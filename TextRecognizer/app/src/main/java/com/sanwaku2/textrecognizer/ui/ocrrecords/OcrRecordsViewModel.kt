package com.sanwaku2.textrecognizer.ui.ocrrecords

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanwaku2.textrecognizer.data.OcrDataSource
import com.sanwaku2.textrecognizer.data.OcrRecord
import kotlinx.coroutines.launch

class OcrRecordsViewModel(private val ocrRepository: OcrDataSource) : ViewModel() {
    val ocrRecords: MutableLiveData<List<OcrRecord>> by lazy {
        MutableLiveData<List<OcrRecord>>()
    }

    fun fetchOcrRecords() {
        viewModelScope.launch {
            ocrRecords.value = ocrRepository.getAllOcrRecords(asc = false)
        }
    }
}