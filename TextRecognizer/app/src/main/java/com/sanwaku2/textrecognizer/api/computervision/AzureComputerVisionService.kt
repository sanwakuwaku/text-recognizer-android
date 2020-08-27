package com.sanwaku2.textrecognizer.api.computervision

import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionManager
import com.microsoft.azure.cognitiveservices.vision.computervision.models.OcrLanguages
import com.microsoft.azure.cognitiveservices.vision.computervision.models.OcrResult
import com.sanwaku2.textrecognizer.api.IComputerVisionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * IComputerVisionService実装クラス<p>
 * Azure CognitiveService ComputerVisionを使っている
 */
class AzureComputerVisionService(
    private val subscriptionKey: String,
    private val endpoint: String
) :
    IComputerVisionService {
    var cvClient: ComputerVisionClient =
        ComputerVisionManager
            .authenticate(subscriptionKey)
            .withEndpoint(endpoint)

    override suspend fun recognizeTextOcr(imgByteArray: ByteArray): OcrResult =
        withContext(Dispatchers.IO) {
            cvClient.computerVision()
                .recognizePrintedTextInStream()
                .withDetectOrientation(true)
                .withImage(imgByteArray)
                .withLanguage(OcrLanguages.UNK)
                .execute()
        }

    companion object {
        private var instance: AzureComputerVisionService? = null

        fun getInstance(subscriptionKey: String, endpoint: String): AzureComputerVisionService =
            instance ?: synchronized(this) {
                instance ?: AzureComputerVisionService(subscriptionKey, endpoint).also {
                    instance = it
                }
            }
    }
}