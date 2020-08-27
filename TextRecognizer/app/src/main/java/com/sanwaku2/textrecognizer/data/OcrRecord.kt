package com.sanwaku2.textrecognizer.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "ocr_records")
data class OcrRecord(
    @PrimaryKey @ColumnInfo(name = "uuid") var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "image_path") var imagePath: String = "",
    @ColumnInfo(name = "language") var language: String = "",
    @ColumnInfo(name = "text") var text: String = "",
    @ColumnInfo(name = "created_at") var createdAt: Long = -1
) {
}