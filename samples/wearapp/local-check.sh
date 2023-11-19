#
# Copyright 2023 estiven. Use of this source code is governed by the Apache 2.0 license.
#

set -e

log() {
  echo "\033[0;32m> $1\033[0m"
}

../gradlew clean build
log "wear-app success"
