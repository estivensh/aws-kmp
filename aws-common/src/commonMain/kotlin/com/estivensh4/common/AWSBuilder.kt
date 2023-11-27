/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.common

class AWSBuilder {
    var accessKey: String = ""
    var secretKey: String = ""
    var endpoint: String = ""

    fun accessKey(secretKey: String) = apply { this.accessKey = secretKey }
    fun secretKey(secretKey: String) = apply { this.secretKey = secretKey }
    fun setEndpoint(endpoint: String) = apply { this.endpoint = endpoint }
}

fun builder(): AWSBuilder = AWSBuilder()
