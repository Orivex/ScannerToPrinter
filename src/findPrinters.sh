#!/bin/bash

echo -n "" > foundPrinters.txt

powershell -Command "Get-WmiObject -Class Win32_Printer | Select-Object -ExpandProperty Name" >> foundPrinters.txt
