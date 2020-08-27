package com.sanwaku2.textrecognizer.ui

/**
 * 文字列リソースid解決用IF
 * context.resourcesやRに直接依存しない為のものだが、不要かも。
 */
interface StringResourceResolver {
    fun getStringResourceIdByName(name: String): Int
}