package org.problemchimp.banker.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public abstract class PdfTextExtractor<T extends PDFTextStripper> {
    
    private static String NEWLINE = "\\r?\\n";

    protected T stripper;
    
    public PdfTextExtractor() {
        initStripper();
    }
    
    protected abstract void initStripper();
    
    protected abstract String getText(PDDocument pdDoc) throws IOException;
    
    protected String getText(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        PDDocument pdDoc = null;
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            pdDoc = parser.getPDDocument();
            return getText(pdDoc);
        } finally {
            IOUtils.closeQuietly(pdDoc);
        }
    }
    
    public List<String> extractLines(File input) throws IOException {
        String text = getText(input);
        String[] lines = text.split(NEWLINE);
        return Arrays.asList(lines);

    }
}
