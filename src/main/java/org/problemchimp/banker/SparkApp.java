package org.problemchimp.banker;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.problemchimp.banker.pdf.PdfFilename;
import org.problemchimp.banker.pdf.PdfFileIterator;
import org.problemchimp.banker.pdf.PdfTextExtractor;

public class SparkApp {
	
	static Pattern SPARK_PROPERTY = Pattern.compile("^spark\\..*$");
	
	private File inputDir;
	private File outputDir;
	private PdfTextExtractor extractor = new PdfTextExtractor();
	private JavaSparkContext sc;
	
	public SparkApp(AppConfig appConfig) throws IOException {
		this.inputDir = appConfig.getInputDirectory();
		this.outputDir = appConfig.getOutputDirectory();
		sc = new JavaSparkContext(appConfig.getSparkConf());
	}
	
	private void writeToFile(JavaRDD<String> rdd, String filename) {
		File outputFile = new File(outputDir, filename);
		if (outputFile.exists()) {
			FileUtils.deleteQuietly(outputFile);
		}
		rdd.saveAsTextFile(outputFile.getPath());
	}

	private void processFile(File file) {
		try {
			List<String> lines = extractor.extractLines(file);
			JavaRDD<String> rdd = sc.parallelize(lines);
			String outputFilename = PdfFilename.stripPdfExtension(file.getName());
			writeToFile(rdd, outputFilename);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		PdfFileIterator it = new PdfFileIterator(inputDir);
		it.forEachRemaining(this::processFile);
	}
	
	public void cleanup() {
		if (sc != null) {
			sc.stop();
		}
	}

}
