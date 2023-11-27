/*
 * Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
 */

package com.estivensh4.dynamo

import com.estivensh4.common.AwsException

open class General(message: String) : AwsException(message)
open class DynamoException(message: String?) : AwsException(message)
