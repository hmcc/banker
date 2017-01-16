package org.problemchimp.banker;

import java.io.File;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkConf;

public class AppConfig {
	
	static Pattern SPARK_PROPERTY = Pattern.compile("^spark\\..*$");
	
	private Properties appProperties;
	private SparkConf sparkConf;
	
	public AppConfig(Properties appProperties) {
		this.appProperties = appProperties;
		sparkConf = new SparkConf();
		for (Entry<Object, Object> e : appProperties.entrySet()) {
			String key = e.getKey().toString();
			String value = e.getValue().toString();
			if (SPARK_PROPERTY.matcher(key).matches()) {
				sparkConf.set(key, value);
			}
		}
	}

	private void helpAndExit() {
		System.out.println("Usage: java -jar banker.jar Main");
		System.out.println("The application is configured with the properties file in src/main/resources");
		System.out.println("If input and/or output directories are not specified, the defaults are <user home>/in and <user home>/out");
		System.exit(1);
	}

	private String getUserHome() {
		String home = System.getProperty("user.home");
		if (StringUtils.isEmpty(home)) {
			System.out.println("Input and/or output directories were not specified and user.home is not defined");
			helpAndExit();
		}
		return home;
	}
	
	private static File getOrDefault(Properties appProperties, String key, Supplier<File> fn) {
		File value;
		if (appProperties.containsKey(key)) {
			value = new File(appProperties.getProperty(key));
		} else {
			value = fn.get();
		}
		return value;
	}
	
	private File getDefaultInputDirectory() {
		return new File(getUserHome(), "in");
	}

	private File getDefaultOutputDirectory() {
		return new File(getUserHome(), "out");
	}
	
	public File getInputDirectory() {
		File inputDir = getOrDefault(appProperties, "banker.inputDir", this::getDefaultInputDirectory);
		if (!inputDir.isDirectory()) {
			System.out.println("Input " + inputDir + " is not a directory");
			helpAndExit();
		}
		return inputDir;
	}
	
	public File getOutputDirectory() {
		File outputDir = getOrDefault(appProperties, "banker.outputDir", this::getDefaultOutputDirectory);
		if (!outputDir.isDirectory()) {
			outputDir.mkdirs();
		}
		if (!outputDir.isDirectory()) {
			System.out.println("Output " + outputDir + " is not a directory and could not be created");
			helpAndExit();
		}
		return outputDir;
	}
	
	public SparkConf getSparkConf() {
		return sparkConf;
	}

}
