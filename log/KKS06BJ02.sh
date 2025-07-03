#!/bin/bash
set -x
cd "$(dirname "$0")"

YYYYMM=$(date '+%Y%m')

TMP_FILE="/home/$(whoami)/work/${YYYYMM}_KKS06BJ01_temp"

if [ ! -f "${TMP_FILE}" ]; then
  echo "[INFO] 一時ファイルが存在しないため処理せず終了 (ステータス 0)"
  exit 0
fi

echo "[INFO] KKS06BJ02.jar 起動"

java -Dfile.encoding=UTF-8 -jar KKS06BJ02.jar
returnCodeJava=$?

echo "[INFO] KKS06BJ02.jar 終了ステータス = ${returnCodeJava}"

if [ "${returnCodeJava}" -eq 0 ]; then
  echo "[INFO] Java 処理が正常終了したため、一時ファイルを削除します"
  rm -f "${TMP_FILE}"
  if [ $? -ne 0 ]; then
    echo "[WARN] 一時ファイル削除に失敗しました (処理は継続)"
  fi
  echo "[INFO] 処理正常終了 (ステータス 0)"
  exit 0
else
  echo "[ERROR] Java 処理で異常終了を検知 (ステータス 8) : ${returnCodeJava}"
  exit 8
fi

echo ""
echo "アプリケーションが終了しました。Enterキーを押すとこのウィンドウは閉じます。"
read
