#!/bin/bash

DOWNLOAD_URL=$(curl -ILs -o /dev/null -w %{url_effective} https://github.com/1fexd/fastforward-ext/releases/latest | sed "s/tag/download/")
wget "${DOWNLOAD_URL}/FastForwardRules.kt" -O ../src/main/kotlin/fe/fastforwardkt/FastForwardRules.kt
