package com.sanwaku2.textrecognizer.data.local.ocr

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sanwaku2.textrecognizer.data.OcrRecord

/**
 * ocr_recordsテーブルアクセス用のIF
 */
@Dao
interface OcrRecordDao {

    @Query("SELECT * FROM ocr_records")
    suspend fun getAll(): List<OcrRecord>

    @Query("SELECT * FROM ocr_records ORDER BY created_at DESC")
    suspend fun getAllDesc(): List<OcrRecord>

    @Query("SELECT * FROM ocr_records WHERE uuid = :uuid")
    suspend fun getOcrRecordByUuid(uuid: String): OcrRecord

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertOcrRecord(ocrRecord: OcrRecord): Long

    @Update
    suspend fun updateOcrRecord(ocrRecord: OcrRecord): Int

    @Query("DELETE FROM ocr_records WHERE uuid = :uuid")
    suspend fun deleteOcrRecordByUuId(uuid: String)
}