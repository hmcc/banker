# banker

## Overview
Import bank statements from PDF using [PDFBox](https://pdfbox.apache.org). For each file, identify those lines that contain transactions, and output those transactions in a standard format.

## Why Spark?
I'm using [Apache Spark](http://spark.apache.org/) for the data processing. There's absolutely no need; I'm not processing large amounts of data here. However, I've been meaning to have a play with it for a while, and this seemed like a good excuse.

## Build and run
Build: 
`mvn clean install`

Run: `cd target; java -jar banker-<version>-jar-with-dependencies.jar`

## Configure
Configure the input and output directories with the banker.inputDir and banker.outputDir in app.properties or as a system property. 
