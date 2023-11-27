/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo

data class UpdateItemResult(
    val attributes: Map<String, AttributeValue>
)
