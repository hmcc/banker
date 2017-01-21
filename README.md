# banker

## Overview
Import bank statements from PDF using [PDFBox](https://pdfbox.apache.org). For each file, identify those lines that contain transactions, and output those transactions in a standard format.

## Build and run
Build: 
`mvn clean install`

Run: `cd target; java -jar banker-<version>-jar-with-dependencies.jar`

## Configure
Configure the input and output directories with banker.inputDir and banker.outputDir in app.properties or as a system property. 
