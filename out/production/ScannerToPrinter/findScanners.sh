#!/bin/bash

echo -n "" > foundScanners.txt

NAPS2_PATH=$1

echo "wia:" >> foundScanners.txt
"$NAPS2_PATH" --listdevices --driver wia >> foundScanners.txt

echo " " >> foundScanners.txt

echo "twain:" >> foundScanners.txt
"$NAPS2_PATH" --listdevices --driver twain >> foundScanners.txt

echo " " >> foundScanners.txt

echo "escl:" >> foundScanners.txt
"$NAPS2_PATH" --listdevices --driver escl >> foundScanners.txt