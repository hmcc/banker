package org.problemchimp.banker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;

public class AppConfig {


    private Properties properties;

    Properties loadAppProperties() throws IOException {
        String propertiesFilename = "app.properties";
        InputStream in = App.class.getResourceAsStream("/" + propertiesFilename);
        if (in == null) {
            throw new FileNotFoundException("Application properties file " + propertiesFilename + " not found");
        }
        Properties appProperties = new Properties();
        appProperties.load(in);
        return appProperties;
    }

    public AppConfig() throws IOException {
        properties = new Properties();
        properties.putAll(loadAppProperties());
        // system properties take precedence
        properties.putAll(System.getProperties());
    }

    private void helpAndExit(String key) {
        System.out.println("Usage: java -jar banker.jar Main");
        System.out.println("The application is configured with the properties file in src/main/resources");
        System.out.println("If input and/or output directories are not specified, the defaults are <user home>/in and <user home>/out");
        throw new ConfigurationException(key);
    }

    private String getUserHome() {
        String home = System.getProperty("user.home");
        if (StringUtils.isEmpty(home)) {
            System.out.println("Input and/or output directories were not specified and user.home is not defined");
            helpAndExit("user.home");
        }
        return home;
    }

    private File getOrDefault(String key, Supplier<File> fn) {
        File value;
        if (properties.containsKey(key)) {
            value = new File(properties.getProperty(key));
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
        File inputDir = getOrDefault("banker.inputDir", this::getDefaultInputDirectory);
        if (!inputDir.isDirectory()) {
            System.out.println("Input " + inputDir + " is not a directory");
            helpAndExit("banker.inputDir");
        }
        return inputDir;
    }

    public File getOutputDirectory() {
        File outputDir = getOrDefault("banker.outputDir", this::getDefaultOutputDirectory);
        if (!outputDir.isDirectory()) {
            outputDir.mkdirs();
        }
        if (!outputDir.isDirectory()) {
            System.out.println("Output " + outputDir + " is not a directory and could not be created");
            helpAndExit("banker.outputDir");
        }
        return outputDir;
    }
}
