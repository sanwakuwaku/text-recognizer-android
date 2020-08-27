package com.sanwaku2.textrecognizer

import android.content.Context
import com.sanwaku2.textrecognizer.api.computervision.AzureComputerVisionService
import com.sanwaku2.textrecognizer.data.local.FileDaoImpl
import com.sanwaku2.textrecognizer.data.local.ocr.OcrRepository
import com.sanwaku2.textrecognizer.data.local.ocr.TextRecognizerDatabase
import com.sanwaku2.textrecognizer.ui.StringResourceResolverImpl
import com.sanwaku2.textrecognizer.ui.camerapreview.CameraPreviewViewModelFactory
import com.sanwaku2.textrecognizer.ui.ocrrecords.OcrRecordsViewModelFactory

/**
 * サービスロケータ<p>
 * 依存関係を生成する
 */
object ServiceLocator {
    fun provideCameraPreviewViewModelFactory(
        context: Context,
        cvSubscriptionKey: String,
        cvEndpoint: String
    ): CameraPreviewViewModelFactory {
        val dao = TextRecognizerDatabase.getInstance(context.applicationContext).ocrRecordDao()

        return CameraPreviewViewModelFactory(
            AzureComputerVisionService.getInstance(
                cvSubscriptionKey,
                cvEndpoint
            ), OcrRepository.getInstance(dao),
            FileDaoImpl(context.applicationContext),
            StringResourceResolverImpl(context.applicationContext)
        )
    }

    fun provideOcrRecordsViewModelFactory(context: Context): OcrRecordsViewModelFactory {
        val dao = TextRecognizerDatabase.getInstance(context.applicationContext).ocrRecordDao()

        return OcrRecordsViewModelFactory(OcrRepository.getInstance(dao))
    }
}