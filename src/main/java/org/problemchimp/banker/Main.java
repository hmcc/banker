package org.problemchimp.banker;

import java.io.File;
import java.io.IOException;

import org.apache.spark.api.java.JavaRDD;

public class Main {
	
	static void writeToFile(JavaRDD<String> rdd, File outputDir, String filename) throws IOException {
		File outputFile = new File(outputDir, filename);
		rdd.saveAsTextFile(outputFile.getPath());
	}
	
	public static void main(String[] args) {
		SparkApp app = null;
		try {
			AppConfig config = new AppConfig();
			app = new SparkApp(config);
			app.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (app != null) {
				app.cleanup();
			}
		}
		
		
	}

}
