package org.problemchimp.banker;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

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
