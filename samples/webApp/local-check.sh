set -e

log() {
  echo "\033[0;32m> $1\033[0m"
}

../gradlew clean build --warning-mode all --stacktrace
log "web-app success"
