package org.problemchimp.banker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.spark.api.java.JavaRDD;

public class Main {
	
	static AppConfig loadConfig() throws IOException {
		String propertiesFilename = "app.properties";
		InputStream in = Main.class.getResourceAsStream("/" + propertiesFilename);
		if (in == null) {
			throw new FileNotFoundException("Application properties file " + propertiesFilename + " not found");
		}
		Properties appProperties = new Properties();
		appProperties.load(in);
		return new AppConfig(appProperties);
	}
	
	static void writeToFile(JavaRDD<String> rdd, File outputDir, String filename) throws IOException {
		File outputFile = new File(outputDir, filename);
		rdd.saveAsTextFile(outputFile.getPath());
	}
	
	public static void main(String[] args) {
		SparkApp app = null;
		try {
			AppConfig config = loadConfig();
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
