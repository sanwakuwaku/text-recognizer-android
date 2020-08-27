package com.sanwaku2.textrecognizer.api

import com.microsoft.azure.cognitiveservices.vision.computervision.models.OcrResult

/**
 * Computer Vision サービスのIF
 */
interface IComputerVisionService {

    /**
     * 画像に含まれる文字を認識する
     *
     * @param imgByteArray 画像データ
     * @return OCR結果
     */
    suspend fun recognizeTextOcr(imgByteArray: ByteArray): OcrResult
}