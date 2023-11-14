package com.estivensh4.aws_kmp

class AwsException : Exception {
    @Throws(Exception::class)
    constructor(message: String?) : super(message)

    @Throws(Exception::class)
    constructor(cause: Throwable?) : super(cause)

    @Throws(Exception::class)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}