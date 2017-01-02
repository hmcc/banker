package org.problemchimp.banker;

import java.io.File;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;

public class ArgumentParser {
	
	private String[] args;
	
	public ArgumentParser(String[] args) {
		this.args = args;
	}
	
	private void helpAndExit() {
		System.out.println("Usage: java -jar banker.jar Main <input directory> <output directory>");
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
	
	private static File getOrDefault(String[] args, int index, Supplier<File> fn) {
		File value;
		if (args.length > index) {
			value = new File(args[index]);
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
		File inputDir = getOrDefault(args, 0, this::getDefaultInputDirectory);
		if (!inputDir.isDirectory()) {
			System.out.println("Input " + inputDir + " is not a directory");
			helpAndExit();
		}
		return inputDir;
	}
	
	public File getOutputDirectory() {
		File outputDir = getOrDefault(args, 1, this::getDefaultOutputDirectory);
		if (!outputDir.isDirectory()) {
			outputDir.mkdirs();
		}
		if (!outputDir.isDirectory()) {
			System.out.println("Output " + outputDir + " is not a directory and could not be created");
			helpAndExit();
		}
		return outputDir;
	}

}
