package com.sanwaku2.textrecognizer.data.local

import android.content.Context
import com.sanwaku2.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class FileDaoImpl(val context: Context) : FileDao {

    override suspend fun saveFile(fileName: String, byteArray: ByteArray): String =
        withContext(Dispatchers.IO) {
            try {
                File(context.filesDir, fileName).writeBytes(byteArray)
                "${context.filesDir.absolutePath}/${fileName}"
            } catch (e: IOException) {
                Logger.e(e)
                ""
            }
        }

    override suspend fun loadFile(path: String): ByteArray =
        withContext(Dispatchers.IO) {
            try {
                File(path).readBytes()
            } catch (e: IOException) {
                Logger.e(e)
                ByteArray(0)
            }
        }

    override fun exists(path: String): Boolean {
        return File(path).exists()
    }

    override fun delete(path: String): Boolean {
        return File(path).delete()
    }
}