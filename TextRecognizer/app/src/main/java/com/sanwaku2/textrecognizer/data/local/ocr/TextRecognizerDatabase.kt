package com.sanwaku2.textrecognizer.data.local.ocr

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sanwaku2.textrecognizer.data.OcrRecord

@Database(entities = [OcrRecord::class], version = 1, exportSchema = true)
abstract class TextRecognizerDatabase : RoomDatabase() {
    abstract fun ocrRecordDao(): OcrRecordDao

    companion object {
        private var instance: TextRecognizerDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(
                context.applicationContext,
                TextRecognizerDatabase::class.java,
                "TextRecognizer.db"
            ).build()
                .also { instance = it }
        }
    }
}