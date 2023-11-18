set -e

log() {
  echo "\033[0;32m> $1\033[0m"
}

../gradlew clean build
log "web-app success"
