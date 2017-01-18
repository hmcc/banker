package org.problemchimp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.spark.SparkConf;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.problemchimp.banker.AppConfig;

@RunWith(JUnit4.class)
public class AppConfigTest {

	private String thisDir;

	@Before
	public void setUp() {
		thisDir = this.getClass().getClassLoader().getResource("").getPath();
		if (thisDir.endsWith("/")) {
			thisDir = thisDir.substring(0, thisDir.length() - 1);
		}
	}

	/**
	 * The application name for Spark is defined in the config file.
	 */
	@Test
	public void testGetAppName_configFile() throws IOException {
		AppConfig config = new AppConfig();
		SparkConf sparkConf = config.getSparkConf();
		assertEquals("banker", sparkConf.get("spark.app.name"));
	}
	
	/**
	 * spark.master is defined in the config file.
	 */
	@Test
	public void testGetMaster_configFile() throws IOException {
		AppConfig config = new AppConfig();
		SparkConf sparkConf = config.getSparkConf();
		assertNotNull(sparkConf.get("spark.master"));
	}

	/**
	 * If a property is found in the config file and provided as a system
	 * property, the system property takes precedence.
	 */
	@Test
	public void testGetAppName_systemProperty() throws IOException {
		System.setProperty("spark.app.name", "override");
		try {
			AppConfig config = new AppConfig();
			SparkConf sparkConf = config.getSparkConf();
			assertEquals("override", sparkConf.get("spark.app.name"));
		} finally {
			System.clearProperty("spark.app.name");
		}
	}

	/**
	 * The input directory can be configured as a system property.
	 */
	@Test
	public void testGetInputDir_systemProperty() throws IOException {
		System.setProperty("banker.inputDir", thisDir);
		try {
			AppConfig config = new AppConfig();
			File inputDir = config.getInputDirectory();
			assertEquals(thisDir, inputDir.getAbsolutePath());
		} finally {
			System.clearProperty("banker.inputDir");
		}
	}

	/**
	 * The output directory can be configured as a system property.
	 */
	@Test
	public void testGetOutputDir_systemProperty() throws IOException {
		System.setProperty("banker.outputDir", thisDir);
		try {
			AppConfig config = new AppConfig();
			File outputDir = config.getOutputDirectory();
			assertEquals(thisDir, outputDir.getAbsolutePath());
		} finally {
			System.clearProperty("banker.outputDir");
		}
	}
}
