package com.anil.newapp.persistance

interface InternalErrorConverter {
    fun convertToGeneralErrorType(error: Throwable): ErrorType
}
