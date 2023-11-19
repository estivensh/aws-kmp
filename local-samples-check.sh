set -e

(cd samples/androidapp && ./local-check.sh)
(cd samples/desktopApp && ./local-check.sh)
(cd samples/wearApp && ./local-check.sh)
(cd samples/webApp && ./local-check.sh)
