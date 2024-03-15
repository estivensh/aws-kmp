set -e

(cd sample/androidapp && ./local-check.sh)
(cd sample/desktopApp && ./local-check.sh)
(cd sample/wearApp && ./local-check.sh)
(cd sample/webApp && ./local-check.sh)
