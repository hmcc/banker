package org.problemchimp.banker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfTextExtractor {

	private static Pattern PDF = Pattern.compile("(.+)\\.pdf$");
	
	private File inputDir;
	private File outputDir;
	private PDFTextStripper stripper;

	public PdfTextExtractor(File inputDir, File outputDir) {
		this.inputDir = inputDir;
		this.outputDir = outputDir;
		initPdfBox();
	}
	
	private void initPdfBox() {
		try {
			stripper = new PDFTextStripper();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static FilenameFilter pdfFilter = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String filename) {
			return new File(dir, filename).isFile() && PDF.matcher(filename).matches();
		}
	};

	private static String pdfToTxt(String filename) {
		Matcher m = PDF.matcher(filename);
		if (m.find()) {
			return m.group(1) + ".txt";
		}
		throw new IllegalArgumentException("Filename " + filename + " should end with .pdf");
	}

	private void writeText(File file, BufferedWriter writer) throws IOException {
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath());
		}

		COSDocument cosDoc = null;
		PDDocument pdDoc = null;
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			cosDoc = parser.getDocument();
			pdDoc = new PDDocument(cosDoc);
			stripper.writeText(pdDoc, writer);
		} finally {
			IOUtils.closeQuietly(cosDoc);
			IOUtils.closeQuietly(pdDoc);
		}
	}

	public void extractAll() {
		String[] contents = inputDir.list(pdfFilter);
		for (String inputFilename : contents) {

			BufferedWriter writer = null;
			try {
				File inputFile = new File(inputDir, inputFilename);
				File outputFile = new File(outputDir, pdfToTxt(inputFilename));

				Path outputPath = Paths.get(outputFile.toURI());
				writer = Files.newBufferedWriter(outputPath, Charset.forName("utf-8"), StandardOpenOption.CREATE);
				writeText(inputFile, writer);

			} catch (IOException e) {
				System.out.println(e.toString());
			} finally {
				IOUtils.closeQuietly(writer);
			}

		}
	}
}
