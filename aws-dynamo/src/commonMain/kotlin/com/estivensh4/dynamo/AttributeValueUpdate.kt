/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo

data class AttributeValueUpdate(
    val action: String?,
    val value: AttributeValue?,
) {
    data class Builder(
        var action: String? = null,
        var value: AttributeValue? = null,
    ) {
        fun withAction(action: String?) = apply { this.action = action }
        fun withValue(value: AttributeValue?) = apply { this.value = value }

        fun build() = AttributeValueUpdate(action, value)
    }
}
