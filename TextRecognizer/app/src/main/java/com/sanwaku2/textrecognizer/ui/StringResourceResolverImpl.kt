package com.sanwaku2.textrecognizer.ui

import android.content.Context

class StringResourceResolverImpl(val context: Context) : StringResourceResolver {
    override fun getStringResourceIdByName(name: String): Int {
        return context.resources.getIdentifier(name, "string", context.packageName)
    }
}