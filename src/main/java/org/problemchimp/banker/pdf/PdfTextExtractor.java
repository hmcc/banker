package org.problemchimp.banker.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfTextExtractor {

	private static String NEWLINE = "\\r?\\n";

	private PDFTextStripper stripper;

	public PdfTextExtractor() {
		initPdfBox();
	}

	private void initPdfBox() {
		try {
			stripper = new PDFTextStripper();
			stripper.setSortByPosition(true);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private String getText(File file) throws IOException {
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
			return stripper.getText(pdDoc);
		} finally {
			IOUtils.closeQuietly(cosDoc);
			IOUtils.closeQuietly(pdDoc);
		}
	}

	public List<String> extractLines(File input) throws IOException {
		String text = getText(input);
		String[] lines = text.split(NEWLINE);
		return Arrays.asList(lines);

	}
}
