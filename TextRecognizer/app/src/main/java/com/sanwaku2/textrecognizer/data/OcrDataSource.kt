package com.sanwaku2.textrecognizer.data

/**
 * OCRデータアクセス用のIF
 */
interface OcrDataSource {

    /**
     * OCRデータを保存する
     * coroutineスコープから呼び出し、内部でIO等の適切なCoroutineContextで実行する
     */
    suspend fun saveOcrRecord(ocrRecord: OcrRecord)

    /**
     * OCRデータを取得する
     * coroutineスコープから呼び出し、内部でIO等の適切なCoroutineContextで実行する
     */
    suspend fun getOcrRecord(id: String): OcrRecord

    /**
     * OCR全レコードを取得する
     * coroutineスコープから呼び出し、内部でIO等の適切なCoroutineContextで実行する
     *
     * @param asc trueの場合は作成日時の昇順、 falseの場合は作成日時の降順
     * @return OCR全レコード
     */
    suspend fun getAllOcrRecords(asc: Boolean = true): List<OcrRecord>
}