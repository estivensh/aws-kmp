/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo

data class AttributeValue(
    val s: String?,
    val sS: List<String>?,
    val n: String?,
    val ns: List<String>?,
    val m: Map<String, AttributeValue>?,
    val l: List<AttributeValue>?
) {
    data class Builder(
        var s: String? = null,
        var sS: List<String>? = null,
        var n: String? = null,
        var ns: List<String>? = null,
        var m: Map<String, AttributeValue>? = null,
        var l: List<AttributeValue>? = null
    ) {
        fun withS(s: String?) = apply { this.s = s }
        fun withSS(sS: List<String>?) = apply { this.sS = sS }
        fun withN(n: String?) = apply { this.n = n }
        fun withNS(ns: List<String>?) = apply { this.ns = ns }
        fun withM(m: Map<String, AttributeValue>?) = apply { this.m = m }
        fun withL(l: List<AttributeValue>?) = apply { this.l = l }

        fun build() = AttributeValue(s, sS, n, ns, m, l)
    }
}
