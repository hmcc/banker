package org.problemchimp.banker;

import java.io.File;

public class Main {
	
	public static void main(String[] args) {
		ArgumentParser parser = new ArgumentParser(args);
		File inputDir = parser.getInputDirectory();
		File outputDir = parser.getOutputDirectory();
		PdfTextExtractor extractor = new PdfTextExtractor(inputDir, outputDir);
		extractor.extractAll();
	}

}
