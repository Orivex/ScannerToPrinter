#!/bin/bash

SCANNER_NAME=$1
PRINTER_NAME=$2
DRIVER_NAME=$3
SCANS=$4
DIR_PATH=$5
NAPS2_PATH=$6
SUMATRAPDF_PATH=$7

for i in $(seq 1 "$SCANS"); do
    "$NAPS2_PATH" -o "/$DIR_PATH/scan.jpg" --driver "$DRIVER_NAME" --device "$SCANNER_NAME" --force
    "$SUMATRAPDF_PATH" -print-to "$PRINTER_NAME" "/$DIR_PATH/scan.jpg"
done