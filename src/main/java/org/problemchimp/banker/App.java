package org.problemchimp.banker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.problemchimp.banker.pdf.PdfFileIterator;
import org.problemchimp.banker.pdf.PdfFilename;
import org.problemchimp.banker.pdf.PdfTextExtractor;
import org.problemchimp.banker.task.Identify;
import org.problemchimp.banker.task.Identify.Type;

public class App {
	
	private File inputDir;
	private File outputDir;
	private Identify identify = new Identify();
	private PdfTextExtractor extractor = new PdfTextExtractor();
	
	public App(AppConfig appConfig) throws IOException {
		this.inputDir = appConfig.getInputDirectory();
		this.outputDir = appConfig.getOutputDirectory();
	}
	
	private void writeToFile(List<String> lines, String filename) throws IOException {
		PrintWriter pw = null;
		try {
			File outputFile = new File(outputDir, filename);
			if (!outputFile.exists()) {
				outputFile.createNewFile();
			}
			pw = new PrintWriter(new FileWriter(outputFile));
			for (String line : lines) {
				pw.println(line);
			}
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		
	}

	private void processFile(File file) {
		try {
			List<String> lines = extractor.extractLines(file);
			Type type = identify.run(lines);
			if (type != Type.UNKNOWN) {
				System.out.println(file + " is of type " + type);
			}
			String outputFilename = PdfFilename.replacePdfExtension(file.getName(), "txt");
			writeToFile(lines, outputFilename);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		PdfFileIterator it = new PdfFileIterator(inputDir);
		it.forEachRemaining(this::processFile);
	}
	
	public static void main(String[] args) {
		App app = null;
		try {
			AppConfig config = new AppConfig();
			app = new App(config);
			app.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		
	}

}
