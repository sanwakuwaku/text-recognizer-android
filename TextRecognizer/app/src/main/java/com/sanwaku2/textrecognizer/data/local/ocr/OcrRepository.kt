package com.sanwaku2.textrecognizer.data.local.ocr

import com.sanwaku2.textrecognizer.data.OcrDataSource
import com.sanwaku2.textrecognizer.data.OcrRecord

/**
 * OcrDataSource実装クラス<p>
 * DBへアクセスしデータ保存、読み込みを行う
 */
class OcrRepository(private val dao: OcrRecordDao) : OcrDataSource {

    companion object {
        private var instance: OcrRepository? = null

        fun getInstance(dao: OcrRecordDao) = instance ?: synchronized(this) {
            instance ?: OcrRepository(dao)
                .also { instance = it }
        }
    }

    override suspend fun saveOcrRecord(ocrRecord: OcrRecord) {
        ocrRecord.createdAt = System.currentTimeMillis()
        dao.insertOcrRecord(ocrRecord)
    }

    override suspend fun getOcrRecord(id: String): OcrRecord {
        return dao.getOcrRecordByUuid(id)
    }

    override suspend fun getAllOcrRecords(asc: Boolean): List<OcrRecord> {
        return if (asc) {
            dao.getAll()
        } else {
            dao.getAllDesc()
        }
    }
}