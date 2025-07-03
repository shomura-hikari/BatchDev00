#!/bin/bash
cd "$(dirname "$0")"
java -Dfile.encoding=UTF-8 -jar KKS06BJ01.jar

returnCodeJava=$?

if [ "$returnCodeJava" -eq 0 ]; then
  yyyyMM=$(date +%Y%m)
  workDir="/home/${USER}/work"
  tempFile="${workDir}/${yyyyMM}_KKS06BJ01_temp"

  mkdir -p "${workDir}"
  > "${tempFile}"

  echo "[INFO] 正常終了。一時ファイルを作成しました: ${tempFile}"
  exit 0
else
  echo "[ERROR] Java 戻り値=${returnCodeJava} のため異常終了"
  exit 8
fi

echo ""
echo "アプリケーションが終了しました。Enterキーを押すとこのウィンドウは閉じます。"
read
