package com.apptware.util

object CustomExceptions {
    class MissingFieldException(fieldName: String) : RuntimeException("$fieldName is missing")
}