#!/bin/bash

file=$1
javac -d . $file && javap -c "bytecode.${file%.*}"
