package com.sanwaku2.logger

import timber.log.Timber

class Logger{
    companion object {
        init {
            Timber.plant(Timber.DebugTree())
        }

        fun d(message : String){
            Timber.d(message)
        }

        fun e(throwable: Throwable) {
            Timber.e(throwable)
        }

        fun e(message : String, vararg args : Any?) {
            Timber.e(message, args)
        }
    }
}