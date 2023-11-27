#!/bin/bash

# Reemplazar la constante OLD_VALUE con el valor proporcionado en la variable de entorno NEW_VALUE
sed -i '' "s|private val accessKey = .*|private val accessKey = \"accessKey\"|g" aws-s3/src/commonTest/kotlin/com/estivensh4/s3/AWSS3CommonTest.kt
sed -i '' "s|private val secretKey = .*|private val secretKey = \"secretKey\"|g" aws-s3/src/commonTest/kotlin/com/estivensh4/s3/AWSS3CommonTest.kt

sed -i '' "s|private val accessKey = .*|private val accessKey = \"accessKey\"|g" aws-dynamo/src/commonTest/kotlin/com/estivensh4/dynamo/AWSDynamoTest.kt
sed -i '' "s|private val secretKey = .*|private val secretKey = \"secretKey\"|g" aws-dynamo/src/commonTest/kotlin/com/estivensh4/dynamo/AWSDynamoTest.kt
