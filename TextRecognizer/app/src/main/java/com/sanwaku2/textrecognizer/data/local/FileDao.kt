package com.sanwaku2.textrecognizer.data.local

/**
 * ファイルアクセス用クラス
 */
interface FileDao {

    /**
     * raw データをfileとして保存する
     * @param fileName   ファイル名
     * @param byteArray  raw data
     * @return 保存先ファイルパス
     */
    suspend fun saveFile(fileName: String, byteArray: ByteArray): String

    /**
     * rawデータをファイルから読み込む
     * @param path  保存先ファイルパス
     * @return raw data
     */
    suspend fun loadFile(path: String): ByteArray

    fun exists(path: String): Boolean

    fun delete(path: String): Boolean
}